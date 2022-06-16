package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;


import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Civilite;
import monRdv.model.Creneau;
import monRdv.model.Patient;
import monRdv.model.Praticien;
import monRdv.model.RendezVous;

public class UtilisateurDaoTest {

    IUtilisateurDao utilisateurDao = Singleton.getInstance().getUtilisateurDao();
    IRendezVousDao rendezVousDao = Singleton.getInstance().getRendezVousDao();
    ICreneauDao creneauDao = Singleton.getInstance().getCreneauDao();

    @Test
    public void patient() {
        int sizeStart = utilisateurDao.findAll().size();

        Patient dupont = new Patient(Civilite.M, "DUPONT", "Pierre");
        
		dupont.setEmail("pierre.dupont@gmail.com");
		dupont.setMotDePasse("P@assword");
		dupont.setAge(35);
		dupont.setNumeroSS("1 85 33 14 745");
		dupont.setTelephone("06 06 06 06 06");

        dupont = (Patient) utilisateurDao.save(dupont);

        Patient testPatient = (Patient) utilisateurDao.findById(dupont.getId());

        Assert.assertNotNull(testPatient);

        Assert.assertEquals(Civilite.M, testPatient.getCivilite());
        Assert.assertEquals("DUPONT", testPatient.getNom());
        Assert.assertEquals("Pierre", testPatient.getPrenom());
        Assert.assertEquals("pierre.dupont@gmail.com", testPatient.getEmail());
        Assert.assertEquals("P@assword", testPatient.getMotDePasse());
        Assert.assertEquals(35, testPatient.getAge());
        Assert.assertEquals("1 85 33 14 745", testPatient.getNumeroSS());
        Assert.assertEquals("06 06 06 06 06", testPatient.getTelephone());

        Assert.assertEquals( utilisateurDao.findAll().size() - sizeStart, 1);

        RendezVous rendezVousDupont = new RendezVous();
        rendezVousDupont.setPatient(dupont);
        rendezVousDupont = rendezVousDao.save(rendezVousDupont);
        
        dupont = (Patient) utilisateurDao.findByIdwithRendezVous(dupont.getId());

        Assert.assertNotNull(dupont.getRendezVous());
        Assert.assertEquals(dupont.getRendezVous().get(0).getId(), dupont.getRendezVous().get(0).getId());

    }

    @Test
    public void practicien() throws ParseException {
        int sizeStart = utilisateurDao.findAll().size();

        Praticien jekyll = new Praticien("JEKYLL", "Henri");
		jekyll.setEmail("dr.jekyll@gmail.com");
		jekyll.setMotDePasse("Hyde");
		jekyll.setMatricule("8888888");

        jekyll = (Praticien) utilisateurDao.save(jekyll);

        Praticien hyde = (Praticien) utilisateurDao.findById(jekyll.getId());

        Assert.assertNotNull(hyde);
            
        Assert.assertEquals(hyde.getNom(), "JEKYLL");
        Assert.assertEquals(hyde.getPrenom(), "Henri");
        Assert.assertEquals(hyde.getMotDePasse(), "Hyde");
        Assert.assertEquals(hyde.getEmail(), "dr.jekyll@gmail.com");
        Assert.assertEquals(hyde.getMatricule(), "8888888");


        Assert.assertEquals( utilisateurDao.findAll().size() - sizeStart, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Creneau creneau0800 = new Creneau(sdf.parse("20/06/2022 08:00"), 15);
        creneau0800.setPraticien(hyde);

        creneau0800 = creneauDao.save(creneau0800);

        hyde = (Praticien) utilisateurDao.findByIdwithCreneau(hyde.getId());

        Assert.assertNotNull(hyde.getCreneaux());
        Assert.assertEquals(hyde.getCreneaux().get(0).getId(), creneau0800.getId());
    }

    @Test
    public void administrateur() {
    }

}
