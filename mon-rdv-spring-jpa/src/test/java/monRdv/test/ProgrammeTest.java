package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IMotifDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.dao.IUtilisateurDao;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ProgrammeTest {

	@Autowired
	private IAdresseDao adresseDao;
	@Autowired
	private ICreneauDao creneauDao;
	@Autowired
	private ILieuDao lieuDao;
	@Autowired
	private IMotifDao motifDao;
	@Autowired
	private IRendezVousDao rendezVousDao;
	@Autowired
	private ISpecialiteDao specialiteDao;
	@Autowired
	private IUtilisateurDao utilisateurDao;
	
	@Test
	public void load() throws ParseException {	
		
		
		Praticien jekyll = new Praticien("JEKYLL", "Henri");
		jekyll.setEmail("dr.jekyll@gmail.com");
		jekyll.setMotDePasse("Hyde");
		jekyll.setMatricule("8888888");

		jekyll = (Praticien) utilisateurDao.save(jekyll);

		Patient dupont = new Patient(Civilite.M, "DUPONT", "Pierre");
		dupont.setEmail("pierre.dupont@gmail.com");
		dupont.setMotDePasse("P@assword");
		dupont.setAge(35);
		dupont.setNumeroSS("1 85 33 14 745");
		dupont.setTelephone("06 06 06 06 06");

		dupont = (Patient) utilisateurDao.save(dupont);

		Specialite generaliste = new Specialite("Généraliste");
		generaliste.setDescription("Médecine Générale");

		generaliste = specialiteDao.save(generaliste);

		Specialite bidoniste = new Specialite("Bidon", "Bidon");
		bidoniste = specialiteDao.save(bidoniste);
		List<Specialite> specialitesFromCsv = specialiteDao.findAll();
		specialiteDao.delete(specialitesFromCsv.get(specialitesFromCsv.size() - 1));

		Specialite dentiste = new Specialite("Dentiste", "Dentiste");
		dentiste = specialiteDao.save(dentiste);

		generaliste.getPraticiens().add(jekyll);
		// jekyll.getSpecialites().add(generaliste);

		generaliste = specialiteDao.save(generaliste);

		Praticien house = new Praticien("HOUSE", "hugh");
		house.setEmail("dr.house@gmail.com");
		house.setMotDePasse("HouseMD");
		house.setMatricule("888888");

		house = (Praticien) utilisateurDao.save(house);

		dentiste.getPraticiens().add(house);
		generaliste.getPraticiens().add(house);

		generaliste = specialiteDao.save(generaliste);
		dentiste = specialiteDao.save(dentiste);

		Lieu clinique = new Lieu("Clinique de la Victoire");
		clinique.setCommentaires("Se présenter à l'accueil");
		clinique.setPraticien(house);

		clinique = lieuDao.save(clinique);

		Adresse adrClinique = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");

		System.out.println("avant=" + adrClinique.getId());

		adrClinique = adresseDao.save(adrClinique);

		System.out.println("après=" + adrClinique.getId());

		clinique.setAdr(adrClinique);

		clinique = lieuDao.save(clinique);

		clinique.setPraticien(jekyll);
		// jekyll.getLieux().add(clinique); // à discuter

		Motif premiereConsultation = new Motif("Première Consultation", 30);
		premiereConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(premiereConsultation); // à discuter

		premiereConsultation = motifDao.save(premiereConsultation);

		Motif suiviConsultation = new Motif("Consultation suivi", 15);

		suiviConsultation.setPraticien(jekyll);
		// jekyll.getMotifs().add(suiviConsultation); // à discuter

		suiviConsultation = motifDao.save(suiviConsultation);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Creneau creneau0800 = new Creneau(sdf.parse("20/06/2022 08:00"), 15);

		creneau0800.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0800); // à discuter

		creneau0800.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0800); // à discuter

		creneau0800 = creneauDao.save(creneau0800);

		Creneau creneau0815 = new Creneau(sdf.parse("20/06/2022 08:15"), 15);
		creneau0815.setDuree(15);

		creneau0815.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0815); // à discuter

		creneau0815.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0815); // à discuter

		creneau0815 = creneauDao.save(creneau0815);

		Creneau creneau0830 = new Creneau(sdf.parse("20/06/2022 08:30"), 15);

		creneau0830.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0830); // à discuter

		creneau0830.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0830); // à discuter

		creneau0830 = creneauDao.save(creneau0830);

		Creneau creneau0845 = new Creneau(sdf.parse("20/06/2022 08:45"), 15);

		creneau0845.setLieu(clinique);
		// clinique.getCreneaux().add(creneau0845); // à discuter

		creneau0845.setPraticien(jekyll);
		// jekyll.getCreneaux().add(creneau0845); // à discuter
		creneau0845 = creneauDao.save(creneau0845);

		RendezVous rendezVousDupont = new RendezVous();

		rendezVousDupont.setPatient(dupont);
		rendezVousDupont.setStatut(Statut.VALIDER);
		rendezVousDupont.setMotif(premiereConsultation);

		rendezVousDupont = rendezVousDao.save(rendezVousDupont);
		
		dupont = (Patient) utilisateurDao.findById(dupont.getId());
		
		// dupont.getRendezVous().add(rendezVousDupont); // à discuter

		creneau0800.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0800); // à discuter

		creneau0800 = creneauDao.save(creneau0800);

		creneau0815.setRendezVous(rendezVousDupont);
		// rendezVousDupont.getCreneaux().add(creneau0815); // à discuter

		creneau0815 = creneauDao.save(creneau0815);
	}

}
