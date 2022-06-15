package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Creneau;

public class CreneauDaoSql implements ICreneauDao {

	@Override
	public List<Creneau> findAll() {
		List<Creneau> creneaus = new ArrayList<Creneau>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, date, duree FROM creneau");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				Date date = rs.getDate("date");
				int duree = rs.getInt("duree");

				Creneau creneau = new Creneau(date, duree);
				creneau.setId(id);

				creneaus.add(creneau);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return creneaus;
	}

	@Override
	public Creneau findById(Long id) {
		Creneau creneau = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT date, duree FROM creneau WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate("date");
				int duree = rs.getInt("duree");

				creneau = new Creneau(date, duree);
				creneau.setId(id);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return creneau;
	}

	@Override
	public void create(Creneau obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO creneau (date, duree) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setDate(1, new java.sql.Date(obj.getDate().getTime()));
			ps.setInt(2, obj.getDuree());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Creneau en Erreur");
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
	public void update(Creneau obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE creneau SET date = ?, duree = ? WHERE id = ?");
			
			ps.setDate(1, new java.sql.Date(obj.getDate().getTime()));
			ps.setInt(2, obj.getDuree());
			ps.setLong(3, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Creneau en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Creneau obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM creneau WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Creneau en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
