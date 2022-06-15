package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IMotifDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Motif;

public class MotifDaoSql implements IMotifDao {

	@Override
	public List<Motif> findAll() {
		List<Motif> motifs = new ArrayList<Motif>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, titre, duree FROM Motif");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String titre = rs.getString("titre");
				int duree = rs.getInt("duree");

				Motif motif = new Motif(titre, duree);
				motif.setId(id);

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
					.prepareStatement("SELECT titre, duree FROM motif WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String titre = rs.getString("titre");
				int duree = rs.getInt("duree");

				motif = new Motif(titre, duree);
				motif.setId(id);
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
					.prepareStatement("INSERT INTO adresse (titre, duree) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getTitre());
			ps.setLong(2, obj.getDuree());
			
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
					.prepareStatement("UPDATE motif SET titre = ?, duree = ? WHERE id = ?");
			
			ps.setString(1, obj.getTitre());
			ps.setLong(2, obj.getDuree());
			ps.setLong(3, obj.getId());
			
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
