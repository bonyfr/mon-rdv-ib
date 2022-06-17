package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import monRdv.Singleton;
import monRdv.dao.IRendezVousDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.RendezVous;

public class RendezVousDaoJpa implements IRendezVousDao {
    @Override
    public void delete(RendezVous obj) {
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
    public List<RendezVous> findAll() {
        List<RendezVous> rdv = null;

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = Singleton.getInstance().getEmf().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            TypedQuery<RendezVous> query = em.createQuery("from RendezVous", RendezVous.class);

            rdv = query.getResultList();

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
        return rdv;
    }

    @Override
    public RendezVous findById(Long id) {
        RendezVous rdv = null;

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = Singleton.getInstance().getEmf().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            rdv = em.find(RendezVous.class, id);

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

        return rdv;
    }

    @Override
    public RendezVous save(RendezVous obj) {
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
	public List<RendezVous> findAllByPraticien(Long id) {
		List<RendezVous> rendezVous = null;
		
		EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = Singleton.getInstance().getEmf().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

//            TypedQuery<RendezVous> query = em.createQuery("select c.rendezVous from Praticien p join p.creneaux c where p.id = :id", RendezVous.class);
//            
//            TypedQuery<RendezVous> query = em.createQuery("select rv from RendezVous rv join rv.creneaux c where c.praticien.id = :id", RendezVous.class);
            
            TypedQuery<RendezVous> query = em.createQuery("select c.rendezVous from Creneau c where c.praticien.id = :id", RendezVous.class);
            
            query.setParameter("id", id);
            
            rendezVous = query.getResultList();

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
        
		return rendezVous;
	}

}
