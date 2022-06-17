package monRdv.test;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IMotifDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Motif;
import monRdv.model.Praticien;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MotifDaoTest {
	
	@Autowired
	private IMotifDao motifDao;
	@Autowired
	private IUtilisateurDao utilisateurDao;

	@Test
	public void motif() {
		Praticien jekyll = new Praticien("JEKYLL", "Henri");
	
		jekyll = (Praticien) utilisateurDao.save(jekyll);

		int sizeStart = motifDao.findAll().size();

		Motif motif = new Motif("Première Consultation", 30);
		motif.setPraticien(jekyll);

		motif = motifDao.save(motif);

		Long idMotif = motif.getId();

		Optional<Motif> optMotifFind = motifDao.findById(idMotif);
		
		if(!optMotifFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		Motif motifFind = optMotifFind.get();

		Assert.assertEquals("Première Consultation", motifFind.getTitre());
		Assert.assertEquals(30, motifFind.getDuree());
		Assert.assertEquals("JEKYLL", motifFind.getPraticien().getNom());

		motif.setTitre("Consultation suivi");
		motif.setDuree(15);

		motif = motifDao.save(motif);

		optMotifFind = motifDao.findById(idMotif);

		if(!optMotifFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		motifFind = optMotifFind.get();

		Assert.assertEquals("Consultation suivi", motifFind.getTitre());
		Assert.assertEquals(15, motifFind.getDuree());

		int sizeEnd = motifDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		motifDao.delete(motif);

		optMotifFind = motifDao.findById(idMotif);

		if(!optMotifFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		motifFind = optMotifFind.get();

		Assert.assertNull(motifFind);
		
		int sizeEndAfterDelete = motifDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}