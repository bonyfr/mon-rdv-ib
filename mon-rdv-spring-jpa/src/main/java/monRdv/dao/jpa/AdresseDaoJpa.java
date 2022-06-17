package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;

public class AdresseDaoJpa implements IAdresseDao {

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Adresse> query = em.createQuery("from Adresse", Adresse.class);
			
			adresses = query.getResultList();

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
		
		return adresses;
	}

	@Override
	public Adresse findById(Long id) {
		Adresse adresse = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	

//			TypedQuery<Adresse> query = em.createQuery("from Adresse a where a.id = :id", Adresse.class);
//			query.setParameter("id", id);
//			
//			adresse = query.getSingleResult();
			
			adresse = em.find(Adresse.class, id);

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
		
		return adresse;
	}

	@Override
	public Adresse save(Adresse obj) {
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
	public void delete(Adresse obj) {
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

}
