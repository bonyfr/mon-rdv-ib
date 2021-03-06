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
import monRdv.dao.ISpecialiteDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Praticien;
import monRdv.model.Specialite;

public class SpecialiteDaoSql implements ISpecialiteDao {

	@Override
	public List<Specialite> findAll() {
		List<Specialite> specialites = new ArrayList<Specialite>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, nom, description FROM specialite");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String description = rs.getString("description");
				
				Specialite specialite = new Specialite(nom, description);
				specialite.setId(id);
				
				PreparedStatement psLien = connection
						.prepareStatement("SELECT praticien_id, specialite_id FROM specialite_praticien WHERE specialite_id = ?");
				psLien.setLong(1, id);
				ResultSet rsLien = psLien.executeQuery();
				while(rsLien.next())
				{
					Long praticienId = rsLien.getLong("praticien_id");
					IPracticienDao daoPraticien = Singleton.getInstance().getPracticienDao();
					specialite.getPraticiens().add(daoPraticien.findById(praticienId));
				}
				specialites.add(specialite);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return specialites;
	}

	@Override
	public Specialite findById(Long id) {
		Specialite specialite = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, nom, description FROM specialite WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String nom = rs.getString("nom");
				String description = rs.getString("description");
				
				specialite = new Specialite(nom, description);
				specialite.setId(id);
				
				PreparedStatement psLien = connection
						.prepareStatement("SELECT praticien_id  FROM specialite WHERE??specialite_id = ?");
				psLien.setLong(1, id);
				ResultSet rsLien = psLien.executeQuery();
				while(rsLien.next())
				{
					Long praticienId = rsLien.getLong("praticien_id");
					IPracticienDao daoPraticien = Singleton.getInstance().getPracticienDao();
					specialite.getPraticiens().add(daoPraticien.findById(praticienId));
				}

			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return specialite;
	}

	@Override
	public void create(Specialite obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO specialite (nom, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getDescription());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Specialite en Erreur");
			}
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				Long id = rs.getLong(1);
				obj.setId(id);
			}
			
			for(Praticien praticien : obj.getPraticiens())
			{
				PreparedStatement psLien = connection
						.prepareStatement("INSERT INTO specialite_praticien (specialite_id, praticien_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				
				psLien.setLong(1, obj.getId());
				psLien.setLong(2, praticien.getId());
				
				rows = psLien.executeUpdate();
				
				if(rows != 1) {
					throw new MonRdvPersistenceException("Insert Specialite en Erreur");
				}
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void update(Specialite obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE specialite SET nom = ?, description = ? WHERE id = ?");
			
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getDescription());
			ps.setLong(3, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Specialite en Erreur");
			}
			
			if(obj.getPraticiens().size() > 0)
			{
				PreparedStatement psClean = connection
						.prepareStatement("DELETE FROM specialite_praticien WHERE specialite_id = ?");
				psClean.setLong(1, obj.getId());
				rows = psClean.executeUpdate();

				for(Praticien praticien : obj.getPraticiens())
				{
					PreparedStatement psLien = connection
							.prepareStatement("INSERT INTO specialite_praticien (specialite_id, praticien_id) VALUES (?, ?)");
					
					psLien.setLong(1, obj.getId());
					psLien.setLong(2, praticien.getId());
					
					rows = psLien.executeUpdate();
					
					if(rows != 1) {
						throw new MonRdvPersistenceException("Insert Specialite en Erreur");
					}
				}
				
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Specialite obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM specialite WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Specialite en Erreur");
			}
			
			if(obj.getPraticiens().size() > 0)
			{
				PreparedStatement psLien = connection
						.prepareStatement("DELETE FROM specialite_praticien WHERE specialite_id = ?");
				
				psLien.setLong(1, obj.getId());
				rows = psLien.executeUpdate();				
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
