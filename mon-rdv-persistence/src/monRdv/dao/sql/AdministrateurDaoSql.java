package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IAdministrateurDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Administrateur;

public class AdministrateurDaoSql implements IAdministrateurDao {

	@Override
	public List<Administrateur> findAll() {
		List<Administrateur> administrateurs = new ArrayList<Administrateur>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, nom, prenom, email, mot_de_passe FROM utilisateur WHERE disc = ?");

			ps.setString(1, "Administrateur");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String motDePasse = rs.getString("mot_de_passe");

				Administrateur administrateur = new Administrateur(nom, prenom);
				administrateur.setId(id);
				administrateur.setEmail(email);
				administrateur.setMotDePasse(motDePasse);

				administrateurs.add(administrateur);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return administrateurs;
	}

	@Override
	public Administrateur findById(Long id) {
		Administrateur administrateur = null;

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement(
					"SELECT id, nom, prenom, email, mot_de_passe FROM utilisateur WHERE id = ? AND disc = ?");

			ps.setLong(1, id);
			ps.setString(2, "Administrateur");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String motDePasse = rs.getString("mot_de_passe");

				administrateur = new Administrateur(nom, prenom);
				administrateur.setId(id);
				administrateur.setEmail(email);
				administrateur.setMotDePasse(motDePasse);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return administrateur;
	}

	@Override
	public void create(Administrateur obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO utilisateur (disc, nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, "Administrateur");
			ps.setString(2, obj.getNom());
			ps.setString(3, obj.getPrenom());
			ps.setString(4, obj.getEmail());
			ps.setString(4, obj.getMotDePasse());

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new MonRdvPersistenceException("Insert Administrateur en Erreur");
			}

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				Long id = rs.getLong(1);
				obj.setId(id);
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void update(Administrateur obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement(
					"UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ? WHERE id = ? AND disc = ?");

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setString(3, obj.getEmail());
			ps.setString(4, obj.getMotDePasse());
			ps.setLong(5, obj.getId());
			ps.setString(6, "Administrateur");

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new MonRdvPersistenceException("Update Administrateur en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Administrateur obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement("DELETE FROM utilisateur WHERE id = ? AND disc = ?");

			ps.setLong(1, obj.getId());
			ps.setString(2, "Administrateur");

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new MonRdvPersistenceException("Delete Administrateur en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
