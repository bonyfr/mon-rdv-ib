package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IPatientDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Civilite;
import monRdv.model.Patient;

public class PatientDaoSql implements IPatientDao{

	@Override
	public List<Patient> findAll() {
		List<Patient> Patients = new ArrayList<Patient>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, civilite, nom, prenom, email, mot_de_passe, number_ss, age, telephone FROM utilisateur");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				Civilite civ = Civilite.valueOf(rs.getString("civilite"));
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom"); 
				String email = rs.getString("email");
				String motDePasse = rs.getString("mot_de_passe");
				String numeroSS = rs.getString("number_ss"); 
				int age = rs.getInt("age"); 
				String telephone = rs.getString("telephone"); 

				Patient patient = new Patient(civ, nom, prenom);
				patient.setId(id);
				patient.setEmail(email);
				patient.setMotDePasse(motDePasse);
				patient.setNumeroSS(numeroSS);
				patient.setAge(age);
				patient.setTelephone(telephone);

				Patients.add(patient);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return Patients;
	}

	@Override
	public Patient findById(Long id) {
		Patient patient = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT civilite, nom, prenom, email, mot_de_passe, number_ss, age FROM utilisateur WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Civilite civ = Civilite.valueOf(rs.getString("civilite"));
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom"); 
				String email = rs.getString("email");
				String motDePasse = rs.getString("mot_de_passe");
				String numeroSS = rs.getString("number_ss"); 
				int age = rs.getInt("age"); 
				String telephone = rs.getString("telephone"); 

				patient = new Patient(civ, nom, prenom);
				patient.setEmail(email);
				patient.setMotDePasse(motDePasse);
				patient.setNumeroSS(numeroSS);
				patient.setAge(age);
				patient.setTelephone(telephone);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return patient;
	}

	@Override
	public void create(Patient obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO utilisateur (disc, civilite, nom, prenom, email, mot_de_passe, number_ss, age, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, "Patient");
			ps.setString(2, obj.getCivilite().toString());
			ps.setString(3, obj.getNom());
			ps.setString(4, obj.getPrenom());
			ps.setString(5, obj.getEmail());
			ps.setString(6, obj.getMotDePasse());
			ps.setString(7, obj.getNumeroSS());
			ps.setInt(8, obj.getAge());
			ps.setString(9, obj.getTelephone());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Patient en Erreur");
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
	public void update(Patient obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE Patient SET civilite = ?, nom = ?, prenom = ?, email = ?, mot_de_passe = ?, number_ss = ?, age = ?, telephone = ? WHERE disc = ?");
			
			ps.setString(1, obj.getCivilite().toString());
			ps.setString(2, obj.getNom());
			ps.setString(3, obj.getPrenom());
			ps.setString(4, obj.getEmail());
			ps.setString(5, obj.getMotDePasse());
			ps.setString(6, obj.getNumeroSS());
			ps.setInt(7, obj.getAge());
			ps.setString(8, obj.getTelephone());
			ps.setString(9, "Patient");
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Patient en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Patient obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM Patient WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Patient en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
