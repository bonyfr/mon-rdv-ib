package monRdv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long>{
	@Query("from Administrateur")
	List<Administrateur> findAllAdministrateur();
	@Query("from Patient")
	List<Patient> findAllPatient();
	@Query("from Praticien")
	List<Praticien> findAllPraticien();
	Utilisateur findByEmail(String email);
	@Query("from Patient p where p.adresse.ville = :ville")
	List<Patient> findAllPatientByVille(@Param("ville") String ville);
}
