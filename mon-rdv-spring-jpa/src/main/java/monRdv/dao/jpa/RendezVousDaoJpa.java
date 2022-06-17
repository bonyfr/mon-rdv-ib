package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.IRendezVousDao;
import monRdv.model.RendezVous;

@Repository
@Transactional(readOnly = true)
public class RendezVousDaoJpa implements IRendezVousDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = false)
	public void delete(RendezVous obj) {
		em.remove(em.merge(obj));
	}

	@Override
	public List<RendezVous> findAll() {
		TypedQuery<RendezVous> query = em.createQuery("from RendezVous", RendezVous.class);

		return query.getResultList();
	}

	@Override
	public RendezVous findById(Long id) {
		return em.find(RendezVous.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public RendezVous save(RendezVous obj) {
		return em.merge(obj);
	}

	@Override
	public List<RendezVous> findAllByPraticien(Long id) {
//            TypedQuery<RendezVous> query = em.createQuery("select c.rendezVous from Praticien p join p.creneaux c where p.id = :id", RendezVous.class);
//            
//            TypedQuery<RendezVous> query = em.createQuery("select rv from RendezVous rv join rv.creneaux c where c.praticien.id = :id", RendezVous.class);

		TypedQuery<RendezVous> query = em.createQuery("select c.rendezVous from Creneau c where c.praticien.id = :id",
				RendezVous.class);

		query.setParameter("id", id);

		return query.getResultList();
	}

}
