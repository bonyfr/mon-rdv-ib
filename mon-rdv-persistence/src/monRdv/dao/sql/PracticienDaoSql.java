package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IPracticienDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Praticien;

public class PracticienDaoSql implements IPracticienDao {

    @Override
    public void create(Praticien obj) {
        try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO utilisateur (disc, nom, prenom, email, mot_de_passe, matricule) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, "Praticien");
			ps.setString(2, obj.getNom());
			ps.setString(3, obj.getPrenom());
			ps.setString(4, obj.getEmail());
			ps.setString(5, obj.getMotDePasse());
            ps.setString(6, obj.getMatricule());

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new MonRdvPersistenceException("Insert Praticien en Erreur");
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
    public void delete(Praticien obj) {
        try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection.prepareStatement("DELETE FROM praticien WHERE id = ? AND disc = ?");

			ps.setLong(1, obj.getId());
			ps.setString(2, "praticien");

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new MonRdvPersistenceException("Delete Praticien en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
    }

    @Override
    public List<Praticien> findAll() {
        List<Praticien> praticiens = new ArrayList<Praticien>();

        try (Connection connection = Singleton.getInstance().getConnection()) {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "SELECT id, nom, prenom, email, mot_de_passe, matricule FROM utilisateur WHERE disc = ?");

            ps.setString(1, "Praticien");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String motDePasse = rs.getString("mot_de_passe");
                String matricule = rs.getString("matricule");

                Praticien praticien = new Praticien(nom, prenom);
                praticien.setId(id);
                praticien.setEmail(email);
                praticien.setMotDePasse(motDePasse);
                praticien.setMatricule(matricule);

                praticiens.add(praticien);
            }
        } catch (SQLException e) {
            throw new MonRdvPersistenceException(e);
        }

        return praticiens;
    }

    @Override
    public Praticien findById(Long id) {
        Praticien praticien = null;

        try (Connection connection = Singleton.getInstance().getConnection()) {

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id, nom, prenom, email, mot_de_passe, matricule FROM utilisateur WHERE id = ? AND disc = ?");

            ps.setLong(1, id);
            ps.setString(2, "Praticien");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String motDePasse = rs.getString("mot_de_passe");
                String matricule = rs.getString("matricule");

                praticien = new Praticien(nom, prenom);
                praticien.setId(id);
                praticien.setEmail(email);
                praticien.setMotDePasse(motDePasse);
                praticien.setMatricule(matricule);
            }

        } catch (SQLException e) {
            throw new MonRdvPersistenceException(e);
        }

        return praticien;
    }

    @Override
    public void update(Praticien obj) {
        try (Connection connection = Singleton.getInstance().getConnection()) {

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE praticien SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, matricule = ? WHERE id = ? AND disc = ?");

            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getMotDePasse());
            ps.setString(5, obj.getMatricule());
            ps.setLong(6, obj.getId());
            ps.setString(7, "Praticien");

            int rows = ps.executeUpdate();

            if (rows != 1){
                throw new MonRdvPersistenceException("Update Praticien Erreur");
            }
        } catch (SQLException e) {
            throw new MonRdvPersistenceException(e);
        }
    }
}
