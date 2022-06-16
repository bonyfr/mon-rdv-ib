package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.IUtilisateurDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

public class UtilisateurDaoJpa implements IUtilisateurDao {

	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> utilisateurs = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur", Utilisateur.class);

			utilisateurs = query.getResultList();

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return utilisateurs;
	}

	@Override
	public Utilisateur findById(Long id) {
		Utilisateur utilisateur = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

//			TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur a where a.id = :id", Utilisateur.class);
//			query.setParameter("id", id);
//			
//			utilisateur = query.getSingleResult();

			utilisateur = em.find(Utilisateur.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return utilisateur;
	}

	@Override
	public Utilisateur save(Utilisateur obj) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			obj = em.merge(obj);

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return obj;
	}

	@Override
	public void delete(Utilisateur obj) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.remove(em.merge(obj));

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<Administrateur> findAllAdministrateur() {
		List<Administrateur> administrateurs = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Administrateur> query = em.createQuery("from Administrateur", Administrateur.class);

			administrateurs = query.getResultList();

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return administrateurs;
	}

	@Override
	public List<Patient> findAllPatient() {
		List<Patient> patients = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Patient> query = em.createQuery("from Patient", Patient.class);

			patients = query.getResultList();

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return patients;
	}

	@Override
	public List<Praticien> findAllPraticien() {
		List<Praticien> praticiens = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Praticien> query = em.createQuery("from Praticien", Praticien.class);

			praticiens = query.getResultList();

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new MonRdvPersistenceException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return praticiens;
	}

}
