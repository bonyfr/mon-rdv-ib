package monRdv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import monRdv.dao.IAdministrateurDao;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IPatientDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.csv.CreneauDaoCsv;
import monRdv.dao.csv.LieuDaoCsv;
import monRdv.dao.csv.PatientDaoCsv;
import monRdv.dao.csv.PracticienDaoCsv;
import monRdv.dao.csv.RendezVousDaoCsv;
import monRdv.dao.sql.AdministrateurDaoSql;
import monRdv.dao.sql.AdresseDaoSql;
import monRdv.dao.sql.SpecialiteDaoSql;

public class Singleton {
	private static Singleton instance = null;
	
	private final IAdministrateurDao administrateurDao = new AdministrateurDaoSql();
	private final IAdresseDao adresseDao = new AdresseDaoSql();
	private final ICreneauDao creneauDao = new CreneauDaoCsv("creneaux.csv");
	private final ILieuDao lieuDao = new LieuDaoCsv("lieux.csv");
	private final IPatientDao patientDao = new PatientDaoCsv("patients.csv");
	private final IPracticienDao practicienDao = new PracticienDaoCsv("practiciens.csv");
	private final IRendezVousDao rendezVousDao = new RendezVousDaoCsv("rendezvous.csv");
	private final ISpecialiteDao specialiteDao = new SpecialiteDaoSql();
	
	private Singleton() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/mon_rdv", "postgres", "admin");
	}

	public IAdministrateurDao getAdministrateurDao() {
		return administrateurDao;
	}

	public IAdresseDao getAdresseDao() {
		return adresseDao;
	}

	public ICreneauDao getCreneauDao() {
		return creneauDao;
	}

	public ILieuDao getLieuDao() {
		return lieuDao;
	}

	public IPatientDao getPatientDao() {
		return patientDao;
	}

	public IPracticienDao getPracticienDao() {
		return practicienDao;
	}

	public IRendezVousDao getRendezVousDao() {
		return rendezVousDao;
	}

	public ISpecialiteDao getSpecialiteDao() {
		return specialiteDao;
	}
	
	

}
