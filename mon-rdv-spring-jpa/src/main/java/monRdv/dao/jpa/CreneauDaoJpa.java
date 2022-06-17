package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.ICreneauDao;
import monRdv.model.Creneau;

@Repository
@Transactional(readOnly = true)
public class CreneauDaoJpa implements ICreneauDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Creneau> findAll() {
		TypedQuery<Creneau> query = em.createQuery("from Creneau", Creneau.class);

		return query.getResultList();
	}

	@Override
	public Creneau findById(Long id) {
		return em.find(Creneau.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Creneau save(Creneau obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Creneau obj) {
		em.remove(em.merge(obj));
	}

}
