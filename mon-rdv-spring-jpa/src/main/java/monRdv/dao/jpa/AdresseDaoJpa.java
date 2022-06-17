package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.IAdresseDao;
import monRdv.model.Adresse;

@Repository
@Transactional(readOnly = true)
public class AdresseDaoJpa implements IAdresseDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Adresse> findAll() {
		TypedQuery<Adresse> query = em.createQuery("from Adresse", Adresse.class);

		return query.getResultList();
	}

	@Override
	public Adresse findById(Long id) {
		return em.find(Adresse.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Adresse save(Adresse obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Adresse obj) {
		em.remove(em.merge(obj));
	}

}
