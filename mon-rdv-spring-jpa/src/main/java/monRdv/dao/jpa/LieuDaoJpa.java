package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ILieuDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Lieu;

public class LieuDaoJpa implements ILieuDao {

	private IAdresseDao adresseDao;

	public IAdresseDao getAdresseDao() {
		return adresseDao;
	}

	public void setAdresseDao(IAdresseDao adresseDao) {
		this.adresseDao = adresseDao;
	}

	@Override
	public List<Lieu> findAll() {
		List<Lieu> Lieus = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Lieu> query = em.createQuery("from Lieu", Lieu.class);

			Lieus = query.getResultList();

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

		return Lieus;
	}

	@Override
	public Lieu findById(Long id) {
		Lieu Lieu = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Singleton.getInstance().getEmf().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

//			TypedQuery<Lieu> query = em.createQuery("from Lieu a where a.id = :id", Lieu.class);
//			query.setParameter("id", id);
//			
//			Lieu = query.getSingleResult();

			Lieu = em.find(Lieu.class, id);

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

		return Lieu;
	}

	@Override
	public Lieu save(Lieu obj) {
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
	public void delete(Lieu obj) {
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
