package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.IMotifDao;
import monRdv.model.Motif;

@Repository
@Transactional(readOnly = true)
public class MotifDaoJpa implements IMotifDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Motif> findAll() {
		TypedQuery<Motif> query = em.createQuery("from Motif", Motif.class);

		return query.getResultList();
	}

	@Override
	public Motif findById(Long id) {
		return em.find(Motif.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Motif save(Motif obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Motif obj) {
		em.remove(em.merge(obj));
	}

}
