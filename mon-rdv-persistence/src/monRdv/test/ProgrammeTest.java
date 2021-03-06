package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.IPatientDao;
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
		IAdresseDao adresseDao = Singleton.getInstance().getAdresseDao();
		ILieuDao lieuDao = Singleton.getInstance().getLieuDao();
		IPracticienDao practicienDao = Singleton.getInstance().getPracticienDao();
		IPatientDao patientDao = Singleton.getInstance().getPatientDao();
		IRendezVousDao rendezVousDao = Singleton.getInstance().getRendezVousDao();
		
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
		
		Singleton.getInstance().getSpecialiteDao().create(generaliste);
		
		Specialite bidoniste = new Specialite("Bidon", "Bidon");
		Singleton.getInstance().getSpecialiteDao().create(bidoniste);
		List<Specialite> specialitesFromCsv = Singleton.getInstance().getSpecialiteDao().findAll();
		Singleton.getInstance().getSpecialiteDao().delete(specialitesFromCsv.get(specialitesFromCsv.size() - 1));
		
		
		Specialite dentiste = new Specialite("Dentiste", "Dentiste");
		Singleton.getInstance().getSpecialiteDao().create(dentiste);
		
		generaliste.getPraticiens().add(jekyll);
		// jekyll.getSpecialites().add(generaliste);
		
		Singleton.getInstance().getSpecialiteDao().update(generaliste);
		
		Praticien house = new Praticien("HOUSE", "hugh");
		house.setEmail("dr.house@gmail.com");
		house.setMotDePasse("HouseMD");
		house.setMatricule("888888");

		practicienDao.create(house);
		
		dentiste.getPraticiens().add(house);
		generaliste.getPraticiens().add(house);
		
		Singleton.getInstance().getSpecialiteDao().update(generaliste);
		Singleton.getInstance().getSpecialiteDao().update(dentiste);
		
		Lieu clinique = new Lieu("Clinique de la Victoire");
		clinique.setCommentaires("Se présenter à l'accueil");
		clinique.setPraticien(house);
		
		lieuDao.create(clinique);

		Adresse adrClinique = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");
		
		System.out.println("avant=" + adrClinique.getId());
		
		adresseDao.create(adrClinique);
		
		System.out.println("après=" + adrClinique.getId());

		clinique.setAdr(adrClinique);
		
		lieuDao.update(clinique);

		clinique.setPraticien(jekyll);
		// jekyll.getLieux().add(clinique); // à discuter

		Motif premiereConsultation = new Motif("Première Consultation", 30);
		premiereConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(premiereConsultation); // à discuter
		
		Singleton.getInstance().getMotifDao().create(premiereConsultation);

		Motif suiviConsultation = new Motif("Consultation suivi", 15);

		suiviConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(suiviConsultation); // à discuter

		Singleton.getInstance().getMotifDao().create(suiviConsultation);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Creneau creneau0800 = new Creneau(sdf.parse("20/06/2022 08:00"), 15);

		creneau0800.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0800); // à discuter

		creneau0800.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0800); // à discuter
		
		Singleton.getInstance().getCreneauDao().create(creneau0800);

		Creneau creneau0815 = new Creneau(sdf.parse("20/06/2022 08:15"), 15);
		creneau0815.setDuree(15);

		creneau0815.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0815); // à discuter

		creneau0815.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0815); // à discuter
		
		Singleton.getInstance().getCreneauDao().create(creneau0815);

		Creneau creneau0830 = new Creneau(sdf.parse("20/06/2022 08:30"), 15);

		creneau0830.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0830); // à discuter

		creneau0830.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0830); // à discuter
		
		Singleton.getInstance().getCreneauDao().create(creneau0830);

		Creneau creneau0845 = new Creneau(sdf.parse("20/06/2022 08:45"), 15);

		creneau0845.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0845); // à discuter

		creneau0845.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0845); // à discuter
		Singleton.getInstance().getCreneauDao().create(creneau0845);

		RendezVous rendezVousDupont = new RendezVous();

		rendezVousDupont.setPatient(dupont);
		rendezVousDupont.setStatut(Statut.VALIDER);
		rendezVousDupont.setMotif(premiereConsultation);
		
		rendezVousDao.create(rendezVousDupont);
		// dupont.getRendezVous().add(rendezVousDupont); // à discuter

		

		creneau0800.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0800); // à discuter
		
		Singleton.getInstance().getCreneauDao().update(creneau0800);

		creneau0815.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0815); // à discuter
		
		Singleton.getInstance().getCreneauDao().update(creneau0815);
	}

}
