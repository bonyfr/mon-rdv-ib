package monRdv.dao;

import java.util.List;

import monRdv.model.Administrateur;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;

public interface IUtilisateurDao extends IDao<Utilisateur, Long>{
	List<Administrateur> findAllAdministrateur();
	List<Patient> findAllPatient();
	List<Praticien> findAllPraticien();
	Utilisateur findByEmail(String email);
	List<Patient> findAllPatientByVille(String ville);
}
