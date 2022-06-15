package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.ILieuDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;
import monRdv.model.Lieu;

public class LieuDaoSql implements ILieuDao {

	@Override
	public List<Lieu> findAll() {
		List<Lieu> lieus = new ArrayList<Lieu>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, nom, commentaires, adresse_id FROM lieu");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String commentaires = rs.getString("commentaires");
				Long adresseId = rs.getLong("adresse_id");

				Lieu lieu = new Lieu(nom);
				lieu.setId(id);
				lieu.setCommentaires(commentaires);
				
				if(adresseId != null) {
					Adresse adresse = Singleton.getInstance().getAdresseDao().findById(adresseId);
					lieu.setAdr(adresse);
				}

				lieus.add(lieu);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return lieus;
	}

	@Override
	public Lieu findById(Long id) {
		Lieu lieu = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT nom, commentaires, adresse_id FROM lieu WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String nom = rs.getString("nom");
				String commentaires = rs.getString("commentaires");
				Long adresseId = rs.getLong("adresse_id");

				lieu = new Lieu(nom);
				lieu.setId(id);
				lieu.setCommentaires(commentaires);
				
				if(adresseId != null) {
					Adresse adresse = Singleton.getInstance().getAdresseDao().findById(adresseId);
					lieu.setAdr(adresse);
				}
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return lieu;
	}

	@Override
	public void create(Lieu obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO lieu (nom, commentaires, adresse_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getCommentaires());
			
			if(obj.getAdr() != null && obj.getAdr().getId() != null) {
				ps.setLong(3, obj.getAdr().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Lieu en Erreur");
			}
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				Long id = rs.getLong(1);
				obj.setId(id);
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void update(Lieu obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE lieu SET nom = ?, commentaires = ?, adresse_id = ? WHERE id = ?");
			
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getCommentaires());
			ps.setLong(4, obj.getId());
			
			if(obj.getAdr() != null && obj.getAdr().getId() != null) {
				ps.setLong(3, obj.getAdr().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Lieu en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Lieu obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM lieu WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Lieu en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
