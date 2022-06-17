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
import monRdv.model.Utilisateur;

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

        Optional<Utilisateur> optUtilisateur =  utilisateurDao.findById(dupont.getId());

		if(!optUtilisateur.isPresent()) {
			Assert.fail("Erreur find adresse");
		}		

		Patient testPatient = (Patient) optUtilisateur.get();

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

        utilisateurDao.delete(testPatient);

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

        Optional<Utilisateur> optUtilisateur = utilisateurDao.findById(jekyll.getId());
        
		if(!optUtilisateur.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
        Praticien hyde = (Praticien) optUtilisateur.get();

        Assert.assertNotNull(hyde);
            
        Assert.assertEquals(hyde.getNom(), "JEKYLL");
        Assert.assertEquals(hyde.getPrenom(), "Henri");
        Assert.assertEquals(hyde.getMotDePasse(), "Hyde");
        Assert.assertEquals(hyde.getEmail(), "dr.jekyll@gmail.com");
        Assert.assertEquals(hyde.getMatricule(), "8888888");


        Assert.assertEquals( utilisateurDao.findAll().size() - sizeStart, 1);

        utilisateurDao.delete(hyde);

        Assert.assertEquals(sizeStart, utilisateurDao.findAll().size());
    }

    @Test
    public void administrateur() {
    }

}
