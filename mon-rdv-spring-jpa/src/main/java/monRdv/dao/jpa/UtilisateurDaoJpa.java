package monRdv.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import monRdv.dao.IUtilisateurDao;
import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

@Repository
@Transactional(readOnly = true)
public class UtilisateurDaoJpa implements IUtilisateurDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Utilisateur> findAll() {
		TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur", Utilisateur.class);

		return query.getResultList();
	}

	@Override
	public Utilisateur findById(Long id) {
//			TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur a where a.id = :id", Utilisateur.class);
//			query.setParameter("id", id);
//			
//			return query.getSingleResult();

		return em.find(Utilisateur.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Utilisateur save(Utilisateur obj) {
		return em.merge(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Utilisateur obj) {
		em.remove(em.merge(obj));
	}

	@Override
	public List<Administrateur> findAllAdministrateur() {
		TypedQuery<Administrateur> query = em.createQuery("from Administrateur", Administrateur.class);

		return query.getResultList();
	}

	@Override
	public List<Patient> findAllPatient() {
		TypedQuery<Patient> query = em.createQuery("from Patient", Patient.class);

		return query.getResultList();
	}

	@Override
	public List<Praticien> findAllPraticien() {
		TypedQuery<Praticien> query = em.createQuery("from Praticien", Praticien.class);

		return query.getResultList();
	}

	@Override
	public Utilisateur findByEmail(String email) {
		TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur u where u.email = :email", Utilisateur.class);

		query.setParameter("email", email);

		return query.getSingleResult();
	}

	@Override
	public List<Patient> findAllPatientByVille(String ville) {
		TypedQuery<Patient> query = em.createQuery("from Patient p join p.adresse a where a.ville = :ville",
				Patient.class);

		query.setParameter("ville", ville);

		return query.getResultList();
	}

}
