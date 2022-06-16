package monRdv.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import monRdv.model.Adresse;

public class JpaTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mon-rdv-jpa");
		
		Adresse adrClinique = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris"); // new (ou transient)
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();	

//			em.persist(adrClinique); // managed
//			
//			System.out.println(adrClinique.getId());
			
			adrClinique = em.merge(adrClinique);
			
			System.out.println(adrClinique.getId());

			adrClinique.setComplement("2ème étage"); // dirty checking => synchronisation automatique

			tx.commit(); // em.flush();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		adrClinique.setRue("8 rue de la Paix"); // detached

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Adresse adrCliniqueCopy = em.merge(adrClinique); // adrClinique => detached - adrCliniqueCopy => managed

			adrClinique.setCodePostal("75001"); // KO => ne fonctionne => adrClinique : detached

			adrCliniqueCopy.setCodePostal("75002"); // OK => mise à jour => adrCliniqueCopy : managed

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Adresse adrCliniqueFind = em.find(Adresse.class, adrClinique.getId()); // managed

			TypedQuery<Adresse> query = em.createQuery("select a from Adresse a", Adresse.class);

			List<Adresse> adresses = query.getResultList(); // managed

			em.remove(adrCliniqueFind); // removed

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		emf.close();
	}

}
