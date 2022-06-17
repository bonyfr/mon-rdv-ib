package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.ISpecialiteDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Specialite;

public class SpecialiteDaoJpa  implements ISpecialiteDao {

	@Override
	public List<Specialite> findAll() {
		List<Specialite> specialites = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Specialite> query = em.createQuery("from Specialite", Specialite.class);
			
			specialites = query.getResultList();

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
		
		return specialites;
	}

	@Override
	public Specialite findById(Long id) {
		Specialite specialite = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			specialite = em.find(Specialite.class, id);

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
		
		return specialite;
	}

	@Override
	public Specialite save(Specialite obj) {
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
	public void delete(Specialite obj) {
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
	public Specialite findByIdWithPraticiens(Long id) {
		Specialite specialite = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();	
			
			TypedQuery<Specialite> query = em.createQuery("select s from Specialite s left join fetch s.praticiens where s.id = :id", Specialite.class);
			
			query.setParameter("id", id);
			
			specialite = query.getSingleResult();

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
		
		return specialite;
	}

}
