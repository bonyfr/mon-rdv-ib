package monRdv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long>{
	List<Administrateur> findAllAdministrateur();
	List<Patient> findAllPatient();
	List<Praticien> findAllPraticien();
	Utilisateur findByEmail(String email);
	List<Patient> findAllPatientByVille(String ville);
}
