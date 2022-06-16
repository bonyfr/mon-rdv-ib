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

	@Override
	public Utilisateur findByEmail(String email) {
		Utilisateur utilisateur = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur u where u.email = :email", Utilisateur.class);

			query.setParameter("email", email);
			
			utilisateur = query.getSingleResult();

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
	public List<Patient> findAllPatientByVille(String ville) {
		List<Patient> patients = null;
				
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Patient> query = em.createQuery("from Patient p join p.adresse a where a.ville = :ville", Patient.class);

			query.setParameter("ville", ville);
			
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
	public Patient findByIdwithRendezVous(Long id) {
		Patient patient = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Patient> query = em.createQuery("select p from Patient p left join fetch p.rendezVous where p.id = :id", Patient.class);
			
			query.setParameter("id", id);
			
			patient = query.getSingleResult();

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
		
		return patient;
	}

	@Override
	public Praticien findByIdwithCreneau(Long id) {
		Praticien praticien = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Praticien> query = em.createQuery("select p from Praticien p left join fetch p.creneaux where p.id = :id", Praticien.class);
			
			query.setParameter("id", id);
			
			praticien = query.getSingleResult();

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
		
		return praticien;
	}

	

}
