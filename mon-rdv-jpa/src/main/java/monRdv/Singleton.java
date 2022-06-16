package monRdv;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IMotifDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.dao.jpa.AdresseDaoJpa;
import monRdv.dao.jpa.CreneauDaoJpa;
import monRdv.dao.jpa.LieuDaoJpa;
import monRdv.dao.jpa.SpecialiteDaoJpa;
import monRdv.dao.jpa.UtilisateurDaoJpa;
import monRdv.dao.jpa.MotifDaoJpa;
import monRdv.dao.jpa.RendezVousDaoJpa;

public class Singleton {
	private static Singleton instance = null;

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mon-rdv-jpa");

	private final IAdresseDao adresseDao = new AdresseDaoJpa();
	private final ICreneauDao creneauDao = new CreneauDaoJpa();
	private final ILieuDao lieuDao = new LieuDaoJpa();
	private final IMotifDao motifDao = new MotifDaoJpa();
	private final IRendezVousDao rendezVousDao = new RendezVousDaoJpa();
	private final ISpecialiteDao specialiteDao =  new SpecialiteDaoJpa();
	private final IUtilisateurDao utilisateurDao = new UtilisateurDaoJpa();

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

	public EntityManagerFactory getEmf() {
		return emf;
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

	public IRendezVousDao getRendezVousDao() {
		return rendezVousDao;
	}

	public ISpecialiteDao getSpecialiteDao() {
		return specialiteDao;
	}

	public IUtilisateurDao getUtilisateurDao() {
		return utilisateurDao;
	}

}
