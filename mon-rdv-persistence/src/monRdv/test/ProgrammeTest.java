package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import monRdv.dao.IAdresseDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.csv.AdresseDaoCsv;
import monRdv.dao.csv.LieuDaoCsv;
import monRdv.dao.csv.PracticienDaoCsv;
import monRdv.dao.csv.RendezVousDaoCsv;
import monRdv.dao.csv.SpecialiteDaoCsv;
import monRdv.dao.IPatientDao;
import monRdv.dao.csv.PatientDaoCsv;
import monRdv.model.Adresse;
import monRdv.model.Civilite;
import monRdv.model.Creneau;
import monRdv.model.Lieu;
import monRdv.model.Motif;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.RendezVous;
import monRdv.model.Specialite;
import monRdv.model.Statut;

public class ProgrammeTest {

	public static void main(String[] args) throws ParseException {
		IAdresseDao adresseDao = new AdresseDaoCsv("adresses.csv");
		ILieuDao lieuDao = new LieuDaoCsv("lieux.csv");

		IPracticienDao practicienDao = new PracticienDaoCsv("practiciens.csv");

		IPatientDao patientDao = new PatientDaoCsv("patients.csv");

		IRendezVousDao rendezVousDao = new RendezVousDaoCsv("rendezvous.csv");
		
		Praticien jekyll = new Praticien("JEKYLL", "Henri");
		jekyll.setEmail("dr.jekyll@gmail.com");
		jekyll.setMotDePasse("Hyde");
		jekyll.setMatricule("8888888");

		practicienDao.create(jekyll);

		Patient dupont = new Patient(Civilite.M, "DUPONT", "Pierre");
		dupont.setEmail("pierre.dupont@gmail.com");
		dupont.setMotDePasse("P@assword");
		dupont.setAge(35);
		dupont.setNumeroSS("1 85 33 14 745");
		dupont.setTelephone("06 06 06 06 06");

		patientDao.create(dupont);
		
		Specialite generaliste = new Specialite("Généraliste");
		generaliste.setDescription("Médecine Générale");
		
		Specialite dentiste = new Specialite("Dentiste");
		dentiste.setDescription("Médecine dentaire");

		ISpecialiteDao specialiteDao = new SpecialiteDaoCsv("specialites.csv");
		specialiteDao.create(generaliste);
		specialiteDao.create(dentiste);
		Specialite bidoniste = new Specialite("Bidon", "Bidon");
		specialiteDao.create(bidoniste);
		List<Specialite> specialitesFromCsv = specialiteDao.findAll();
		specialiteDao.delete(specialitesFromCsv.get(specialitesFromCsv.size() - 1));
		
		
		generaliste.getPraticiens().add(jekyll);
		// jekyll.getSpecialites().add(generaliste);
		
		specialiteDao.update(generaliste);
		
		Praticien house = new Praticien("HOUSE", "hugh");
		house.setEmail("dr.house@gmail.com");
		house.setMotDePasse("HouseMD");
		house.setMatricule("888888");

		practicienDao.create(house);
		
		dentiste.getPraticiens().add(house);
		generaliste.getPraticiens().add(house);
		
		specialiteDao.update(generaliste);
		specialiteDao.update(dentiste);
		
		Lieu clinique = new Lieu("Clinique de la Victoire");
		clinique.setCommentaires("Se présenter à l'accueil");
		
		lieuDao.create(clinique);

		Adresse adrClinique = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");
		
		adresseDao.create(adrClinique);

		clinique.setAdr(adrClinique);
		
		lieuDao.update(clinique);

		clinique.setPraticien(jekyll);
		// jekyll.getLieux().add(clinique); // à discuter

		Motif premiereConsultation = new Motif("Première Consultation", 30);

		premiereConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(premiereConsultation); // à discuter

		Motif suiviConsultation = new Motif("Consultation suivi", 15);

		suiviConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(suiviConsultation); // à discuter

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Creneau creneau0800 = new Creneau(sdf.parse("20/06/2022 08:00"), 15);

		creneau0800.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0800); // à discuter

		creneau0800.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0800); // à discuter

		Creneau creneau0815 = new Creneau(sdf.parse("20/06/2022 08:15"), 15);
		creneau0815.setDuree(15);

		creneau0815.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0815); // à discuter

		creneau0815.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0815); // à discuter

		Creneau creneau0830 = new Creneau(sdf.parse("20/06/2022 08:30"), 15);

		creneau0830.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0830); // à discuter

		creneau0830.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0830); // à discuter

		Creneau creneau0845 = new Creneau(sdf.parse("20/06/2022 08:45"), 15);

		creneau0845.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0845); // à discuter

		creneau0845.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0845); // à discuter

		RendezVous rendezVousDupont = new RendezVous();


		rendezVousDupont.setPatient(dupont);
		rendezVousDupont.setStatut(Statut.VALIDER);
		
		rendezVousDao.create(rendezVousDupont);
		// dupont.getRendezVous().add(rendezVousDupont); // à discuter

		rendezVousDupont.setMotif(premiereConsultation);

		creneau0800.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0800); // à discuter

		creneau0815.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0815); // à discuter
	}

}
