package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.ISpecialiteDao;
import monRdv.model.Specialite;

@Repository
@Transactional(readOnly = true)
public class SpecialiteDaoJpa implements ISpecialiteDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Specialite> findAll() {
		TypedQuery<Specialite> query = em.createQuery("from Specialite", Specialite.class);

		return query.getResultList();
	}

	@Override
	public Specialite findById(Long id) {
		return em.find(Specialite.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Specialite save(Specialite obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Specialite obj) {
		em.remove(em.merge(obj));
	}

	@Override
	public Specialite findByIdWithPraticiens(Long id) {
		TypedQuery<Specialite> query = em.createQuery(
				"select s from Specialite s left join fetch s.praticiens where s.id = :id", Specialite.class);

		query.setParameter("id", id);

		return query.getSingleResult();
	}

}
