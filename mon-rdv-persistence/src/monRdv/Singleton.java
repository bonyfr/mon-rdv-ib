package monRdv;

import monRdv.dao.IAdministrateurDao;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IPatientDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.csv.AdministrateurDaoCsv;
import monRdv.dao.csv.AdresseDaoCsv;
import monRdv.dao.csv.CreneauDaoCsv;
import monRdv.dao.csv.LieuDaoCsv;
import monRdv.dao.csv.PatientDaoCsv;
import monRdv.dao.csv.PracticienDaoCsv;
import monRdv.dao.csv.RendezVousDaoCsv;
import monRdv.dao.csv.SpecialiteDaoCsv;

public class Singleton {
	private static Singleton instance = null;
	
	private final IAdministrateurDao administrateurDao = new AdministrateurDaoCsv("administrateurs.csv");
	private final IAdresseDao adresseDao = new AdresseDaoCsv("adresses.csv");
	private final ICreneauDao creneauDao = new CreneauDaoCsv("creneaux.csv");
	private final ILieuDao lieuDao = new LieuDaoCsv("lieux.csv");
	private final IPatientDao patientDao = new PatientDaoCsv("patients.csv");
	private final IPracticienDao practicienDao = new PracticienDaoCsv("practiciens.csv");
	private final IRendezVousDao rendezVousDao = new RendezVousDaoCsv("rendezvous.csv");
	private final ISpecialiteDao specialiteDao = new SpecialiteDaoCsv("specialites.csv");
	
	private Singleton() {
		
	}
	
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		
		return instance;
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
