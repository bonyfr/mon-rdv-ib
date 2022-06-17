package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.ILieuDao;
import monRdv.model.Lieu;

@Repository
@Transactional(readOnly = true)
public class LieuDaoJpa implements ILieuDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Lieu> findAll() {
		TypedQuery<Lieu> query = em.createQuery("from Lieu", Lieu.class);

		return query.getResultList();
	}

	@Override
	public Lieu findById(Long id) {
//			TypedQuery<Lieu> query = em.createQuery("from Lieu a where a.id = :id", Lieu.class);
//			query.setParameter("id", id);
//			
//			return query.getSingleResult();

		return em.find(Lieu.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Lieu save(Lieu obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Lieu obj) {
		em.remove(em.merge(obj));
	}

}
