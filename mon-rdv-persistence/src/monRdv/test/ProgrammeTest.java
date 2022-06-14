package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import monRdv.dao.IAdresseDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.csv.AdresseDaoCsv;
import monRdv.dao.csv.LieuDaoCsv;
import monRdv.dao.csv.SpecialiteDaoCsv;
import monRdv.model.Adresse;
import monRdv.model.Civilite;
import monRdv.model.Creneau;
import monRdv.model.Lieu;
import monRdv.model.Motif;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.RendezVous;
import monRdv.model.Specialite;

public class ProgrammeTest {

	public static void main(String[] args) throws ParseException {
		IAdresseDao adresseDao = new AdresseDaoCsv("adresses.csv");
		ILieuDao lieuDao = new LieuDaoCsv("lieux.csv");
		
		Praticien jekyll = new Praticien("JEKYLL", "Henri");
		jekyll.setEmail("dr.jekyll@gmail.com");
		jekyll.setMotDePasse("Hyde");
		jekyll.setMatricule("8888888");

		Patient dupont = new Patient(Civilite.M, "DUPONT", "Pierre");
		dupont.setEmail("pierre.dupont@gmail.com");
		dupont.setMotDePasse("P@assword");
		dupont.setAge(35);
		dupont.setNumeroSS("1 85 33 14 745");
		dupont.setTelephone("06 06 06 06 06");

		Specialite generaliste = new Specialite("Généraliste");
		generaliste.setDescription("Médecine Générale");

		ISpecialiteDao specialiteDao = new SpecialiteDaoCsv("specialites.csv");
		specialiteDao.create(generaliste);
		
		Specialite bidoniste = new Specialite("Bidon", "Bidon");
		specialiteDao.create(bidoniste);
		List<Specialite> specialitesFromCsv = specialiteDao.findAll();
		specialiteDao.delete(specialitesFromCsv.get(specialitesFromCsv.size() - 1));
		
		
		jekyll.getSpecialites().add(generaliste);
		// generaliste.getPraticiens().add(jekyll); // A discuter ?

		Lieu clinique = new Lieu("Clinique de la Victoire");
		clinique.setCommentaires("Se présenter à l'accueil");
		
		lieuDao.create(clinique);

		Adresse adrClinique = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");
		
		adresseDao.create(adrClinique);

		clinique.setAdr(adrClinique);

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
		// dupont.getRendezVous().add(rendezVousDupont); // à discuter

		rendezVousDupont.setMotif(premiereConsultation);

		creneau0800.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0800); // à discuter

		creneau0815.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0815); // à discuter
	}

}
