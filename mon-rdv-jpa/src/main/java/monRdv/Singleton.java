package monRdv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import monRdv.dao.IAdministrateurDao;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IMotifDao;
import monRdv.dao.IPatientDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;

public class Singleton {
	private static Singleton instance = null;

	private final IAdministrateurDao administrateurDao = null;
	private final IAdresseDao adresseDao = null;
	private final ICreneauDao creneauDao = null;
	private final ILieuDao lieuDao = null;
	private final IMotifDao motifDao = null;
	private final IPatientDao patientDao = null;
	private final IPracticienDao practicienDao = null;
	private final IRendezVousDao rendezVousDao = null;
	private final ISpecialiteDao specialiteDao = null;

	private Singleton() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Singleton getInstance() {
		if (instance == null) {
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

	public IMotifDao getMotifDao() {
		return motifDao;
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
