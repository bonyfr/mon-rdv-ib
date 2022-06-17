package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Creneau;

public class CreneauDaoJpa implements ICreneauDao {	
	
	@Override
	public List<Creneau> findAll() {
		List<Creneau> creneaus = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Creneau> query = em.createQuery("from Creneau", Creneau.class);
			
			creneaus = query.getResultList();

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
		
		return creneaus;
	}

	@Override
	public Creneau findById(Long id) {
		Creneau creneau = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			creneau = em.find(Creneau.class, id);

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
		
		return creneau;
	}

	@Override
	public Creneau save(Creneau obj) {
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
	public void delete(Creneau obj) {
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
