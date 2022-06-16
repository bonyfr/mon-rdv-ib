package monRdv.test;

import org.junit.Assert;
import org.junit.Test;

import monRdv.Singleton;
import monRdv.dao.IMotifDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Motif;
import monRdv.model.Praticien;

public class MotifDaoTest {

	@Test
	public void motif() {
		IMotifDao motifDao = Singleton.getInstance().getMotifDao();
		IUtilisateurDao utilisateurDao = Singleton.getInstance().getUtilisateurDao();

		Praticien jekyll = new Praticien("JEKYLL", "Henri");
	
		jekyll = (Praticien) utilisateurDao.save(jekyll);

		int sizeStart = motifDao.findAll().size();

		Motif motif = new Motif("Première Consultation", 30);
		motif.setPraticien(jekyll);

		motif = motifDao.save(motif);

		Long idMotif = motif.getId();

		Motif motifFind = motifDao.findById(idMotif);

		Assert.assertEquals("Première Consultation", motifFind.getTitre());
		Assert.assertEquals(30, motifFind.getDuree());
		Assert.assertEquals("JEKYLL", motifFind.getPraticien().getNom());


		motif.setTitre("Consultation suivi");
		motif.setDuree(15);

		motif = motifDao.save(motif);

		motifFind = motifDao.findById(idMotif);

		Assert.assertEquals("Consultation suivi", motifFind.getTitre());
		Assert.assertEquals(15, motifFind.getDuree());


		int sizeEnd = motifDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		motifDao.delete(motif);

		motifFind = motifDao.findById(idMotif);

		Assert.assertNull(motifFind);
		
		int sizeEndAfterDelete = motifDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}
