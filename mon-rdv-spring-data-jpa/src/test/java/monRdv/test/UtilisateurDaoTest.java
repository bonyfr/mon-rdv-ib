package monRdv.test;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Civilite;
import monRdv.model.Patient;
import monRdv.model.Praticien;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class UtilisateurDaoTest {

	@Autowired
	private IUtilisateurDao utilisateurDao;

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

        Optional<Patient> testPatient =utilisateurDao.findById(dupont.getId()).map(Patient.class::cast);

        Assert.assertNotNull(testPatient);

        Assert.assertEquals(Civilite.M, testPatient.get().getCivilite());
        Assert.assertEquals("DUPONT", testPatient.get().getNom());
        Assert.assertEquals("Pierre", testPatient.get().getPrenom());
        Assert.assertEquals("pierre.dupont@gmail.com", testPatient.get().getEmail());
        Assert.assertEquals("P@assword", testPatient.get().getMotDePasse());
        Assert.assertEquals(35, testPatient.get().getAge());
        Assert.assertEquals("1 85 33 14 745", testPatient.get().getNumeroSS());
        Assert.assertEquals("06 06 06 06 06", testPatient.get().getTelephone());

        Assert.assertEquals( utilisateurDao.findAll().size() - sizeStart, 1);

        utilisateurDao.delete(testPatient.get());

        Assert.assertEquals(sizeStart, utilisateurDao.findAll().size());
    }

    @Test
    public void practicien() {
        int sizeStart = utilisateurDao.findAll().size();

        Praticien jekyll = new Praticien("JEKYLL", "Henri");
		jekyll.setEmail("dr.jekyll@gmail.com");
		jekyll.setMotDePasse("Hyde");
		jekyll.setMatricule("8888888");

        jekyll = (Praticien) utilisateurDao.save(jekyll);

        Optional<Praticien> hyde = utilisateurDao.findById(jekyll.getId()).map(Praticien.class::cast);

        Assert.assertNotNull(hyde);
            
        Assert.assertEquals(hyde.get().getNom(), "JEKYLL");
        Assert.assertEquals(hyde.get().getPrenom(), "Henri");
        Assert.assertEquals(hyde.get().getMotDePasse(), "Hyde");
        Assert.assertEquals(hyde.get().getEmail(), "dr.jekyll@gmail.com");
        Assert.assertEquals(hyde.get().getMatricule(), "8888888");


        Assert.assertEquals( utilisateurDao.findAll().size() - sizeStart, 1);

        utilisateurDao.delete(hyde.get());

        Assert.assertEquals(sizeStart, utilisateurDao.findAll().size());
    }

    @Test
    public void administrateur() {
    }

}
