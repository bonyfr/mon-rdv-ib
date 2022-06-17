package monRdv.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long>{
	@Query("From Administrateur")
	List<Administrateur> findAllAdministrateur();
	@Query("From Patient")
	List<Patient> findAllPatient();
	@Query("From Praticien")
	List<Praticien> findAllPraticien();
	Optional<Utilisateur> findByEmail(String email);
	@Query("from Patient p join p.adresse a where a.ville = :ville")
	List<Patient> findAllPatientByVille(@Param("ville") String ville);
	Optional<Utilisateur> findById(Long id);
}
