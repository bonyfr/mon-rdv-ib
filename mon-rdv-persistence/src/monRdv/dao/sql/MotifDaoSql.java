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
import monRdv.dao.IMotifDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;
import monRdv.model.Motif;
import monRdv.model.Praticien;

public class MotifDaoSql implements IMotifDao {

	@Override
	public List<Motif> findAll() {
		List<Motif> motifs = new ArrayList<Motif>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, titre, duree, praticienId FROM motif");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String titre = rs.getString("titre");
				int duree = rs.getInt("duree");
				Long praticienId = rs.getLong("praticien_id");

				Motif motif = new Motif(titre, duree);
				motif.setId(id);

				if(praticienId != null) {
					Praticien praticien = Singleton.getInstance().getPracticienDao().findById(praticienId);
					motif.setPraticien(praticien);
				}

				motifs.add(motif);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return motifs;
	}

	@Override
	public Motif findById(Long id) {
		Motif motif = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT titre, duree praticien_id FROM FROM motif WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String titre = rs.getString("titre");
				int duree = rs.getInt("duree");
				Long praticienId = rs.getLong("praticien_id");

				motif = new Motif(titre, duree);
				motif.setId(id);
				
				if(praticienId != null) {
					Praticien praticien = Singleton.getInstance().getPracticienDao().findById(praticienId);
					motif.setPraticien(praticien);
				}

			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return motif;
	}

	@Override
	public void create(Motif obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO motif (titre, duree , praticien_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getTitre());
			ps.setLong(2, obj.getDuree());
			
			if(obj.getPraticien() != null && obj.getPraticien().getId() != null) {
				ps.setLong(3, obj.getPraticien().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Adresse en Erreur");
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
	public void update(Motif obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE motif SET titre = ?, duree = ?, praticien_id WHERE id = ?");
			
			ps.setString(1, obj.getTitre());
			ps.setLong(2, obj.getDuree());
			ps.setLong(4, obj.getId());
			
			if(obj.getPraticien() != null && obj.getPraticien().getId() != null) {
				ps.setLong(3, obj.getPraticien().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}

			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Adresse en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
		
	}

	@Override
	public void delete(Motif obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM motif WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Motif en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
