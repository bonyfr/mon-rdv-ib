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
import monRdv.dao.IRendezVousDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Motif;
import monRdv.model.Patient;
import monRdv.model.RendezVous;
import monRdv.model.Statut;

public class RendezVousDaoSql implements IRendezVousDao {

	@Override
	public List<RendezVous> findAll() {
		List<RendezVous> rendezVouss = new ArrayList<RendezVous>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, statut, patient_id, motif_id FROM rendez_vous");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String statut = rs.getString("statut");
				Long patientId = rs.getLong("patient_id");
				Long motifId = rs.getLong("motif_id");

				RendezVous rendezVous = new RendezVous();
				rendezVous.setId(id);
				if(statut != null && !statut.isEmpty()) {
					rendezVous.setStatut(Statut.valueOf(statut));
				}
				
				if(patientId != null) {
					Patient patient = Singleton.getInstance().getPatientDao().findById(patientId);
					rendezVous.setPatient(patient);
				}
				
				if(motifId != null) {
					Motif motif = Singleton.getInstance().getMotifDao().findById(motifId);
					rendezVous.setMotif(motif);
				}

				rendezVouss.add(rendezVous);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return rendezVouss;
	}

	@Override
	public RendezVous findById(Long id) {
		RendezVous rendezVous = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT statut, patient_id, motif_id FROM rendez_vous WHERE id = ?");
			
			ps.setLong(1, id);
			

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String statut = rs.getString("statut");
				Long patientId = rs.getLong("patient_id");
				Long motifId = rs.getLong("motif_id");

				rendezVous = new RendezVous();
				rendezVous.setId(id);
				if(statut != null && !statut.isEmpty()) {
					rendezVous.setStatut(Statut.valueOf(statut));
				}
				if(patientId != null) {
					Patient patient = Singleton.getInstance().getPatientDao().findById(patientId);
					rendezVous.setPatient(patient);
				}
				
				if(motifId != null) {
					Motif motif = Singleton.getInstance().getMotifDao().findById(motifId);
					rendezVous.setMotif(motif);
				}
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return rendezVous;
	}

	@Override
	public void create(RendezVous obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO rendez_vous (statut, patient_id, motif_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getStatut().name());
			
			if(obj.getPatient() != null && obj.getPatient().getId() != null) {
				ps.setLong(2, obj.getPatient().getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			
			if(obj.getMotif() != null && obj.getMotif().getId() != null) {
				ps.setLong(3, obj.getMotif().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert RendezVous en Erreur");
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
	public void update(RendezVous obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE rendez_vous SET statut = ?, patient_id = ?, motif_id = ? WHERE id = ?");
			
			ps.setString(1, obj.getStatut().name());
			ps.setLong(4, obj.getId());
			
			if(obj.getPatient() != null && obj.getPatient().getId() != null) {
				ps.setLong(2, obj.getPatient().getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			
			if(obj.getMotif() != null && obj.getMotif().getId() != null) {
				ps.setLong(3, obj.getMotif().getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update RendezVous en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(RendezVous obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM rendez_vous WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete RendezVous en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
