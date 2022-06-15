package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Creneau;
import monRdv.model.Lieu;
import monRdv.model.Praticien;
import monRdv.model.RendezVous;

public class CreneauDaoSql implements ICreneauDao {

	@Override
	public List<Creneau> findAll() {
		List<Creneau> creneaus = new ArrayList<Creneau>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, date, duree, praticien_id, lieu_id, rendez_vous_id   FROM creneau");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				Date date = rs.getDate("date");
				int duree = rs.getInt("duree");
				Long praticienId = rs.getLong("praticien_id");
				Long lieuId =  rs.getLong("lieu_id");
				Long rendezVousId = rs.getLong("rendez_vous_id");

				Creneau creneau = new Creneau(date, duree);
				creneau.setId(id);

				if(praticienId != null) {
					Praticien patricien = Singleton.getInstance().getPracticienDao().findById(praticienId);
					creneau.setPraticien(patricien);
				}
				
				if(lieuId != null) {
					Lieu lieu = Singleton.getInstance().getLieuDao().findById(lieuId);
					creneau.setLieu(lieu);
				}
				
				if(rendezVousId != null) {
					RendezVous rendezVous = Singleton.getInstance().getRendezVousDao().findById(rendezVousId);
					creneau.setRendezVous(rendezVous);
				}
				
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
					.prepareStatement("SELECT date, duree, praticien_id, lieu_id, rendez_vous_id FROM creneau WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Date date = rs.getDate("date");
				int duree = rs.getInt("duree");
				Long praticienId = rs.getLong("praticien_id");
				Long lieuId =  rs.getLong("lieu_id");
				Long rendezVousId = rs.getLong("rendez_vous_id");
				
				
				creneau = new Creneau(date, duree);
				creneau.setId(id);
				
				if(praticienId != null) {
					Praticien patricien = Singleton.getInstance().getPracticienDao().findById(praticienId);
					creneau.setPraticien(patricien);
				}
				
				if(lieuId != null) {
					Lieu lieu = Singleton.getInstance().getLieuDao().findById(lieuId);
					creneau.setLieu(lieu);
				}
				
				if(rendezVousId != null) {
					RendezVous rendezVous = Singleton.getInstance().getRendezVousDao().findById(rendezVousId);
					creneau.setRendezVous(rendezVous);
				}
				
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
					.prepareStatement("INSERT INTO creneau (date, duree, praticien_id, lieu_id, rendez_vous_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setDate(1, new java.sql.Date(obj.getDate().getTime()));
			ps.setInt(2, obj.getDuree());
			
			if(obj.getPraticien() != null && obj.getPraticien().getId() != null) {
				ps.setLong(3, obj.getPraticien().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			if(obj.getLieu() != null && obj.getLieu().getId() != null) {
				ps.setLong(4, obj.getLieu().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			
			if(obj.getRendezVous() != null && obj.getRendezVous().getId() != null) {
				ps.setLong(5, obj.getRendezVous().getId());
			} else {
				ps.setNull(5, Types.INTEGER);
			}
			
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
					.prepareStatement("UPDATE creneau SET date = ?, duree = ?, praticien_id = ?, lieu_id = ?, rendez_vous_id = ? WHERE id = ?");
			
			ps.setDate(1, new java.sql.Date(obj.getDate().getTime()));
			ps.setInt(2, obj.getDuree());
			ps.setLong(6, obj.getId());
			
			if(obj.getPraticien() != null && obj.getPraticien().getId() != null) {
				ps.setLong(3, obj.getPraticien().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			if(obj.getLieu() != null && obj.getLieu().getId() != null) {
				ps.setLong(4, obj.getLieu().getId());
			} else {
				ps.setNull(4, Types.INTEGER);
			}
			
			if(obj.getRendezVous() != null && obj.getRendezVous().getId() != null) {
				ps.setLong(5, obj.getRendezVous().getId());
			} else {
				ps.setNull(5, Types.INTEGER);
			}
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
