package monRdv.test;

import org.junit.Assert;
import org.junit.Test;

import monRdv.Singleton;
import monRdv.dao.ISpecialiteDao;
import monRdv.model.Praticien;
import monRdv.model.Specialite;

public class SpecialiteDaoTest {

	@Test
	public void specialite() {
		ISpecialiteDao specialiteDao = Singleton.getInstance().getSpecialiteDao();

		int sizeStart = specialiteDao.findAll().size();


		Specialite specialite = new Specialite("Généraliste");
		specialite.setDescription("Médecine Générale");

		specialite = specialiteDao.save(specialite);

		Long idSpecialite = specialite.getId();

		Specialite specialiteFind = specialiteDao.findById(idSpecialite);

		Assert.assertEquals("Généraliste", specialiteFind.getNom());
		Assert.assertEquals("Médecine Générale", specialiteFind.getDescription());
//		Assert.assertEquals(specialiteFind.getPraticiens().size(), 0);

		
		specialite.setNom("Dentiste");
		specialite.setDescription("Médecine dentaire");
		
		specialite = specialiteDao.save(specialite);

		specialiteFind = specialiteDao.findById(idSpecialite);

		Assert.assertEquals("Dentiste", specialiteFind.getNom());
		Assert.assertEquals("Médecine dentaire", specialiteFind.getDescription());		
		/*
		 * Assert.assertEquals(specialiteFind.getPraticiens().size(), 0);
		 * 
		 * Praticien jekyll = new Praticien("JEKYLL", "Henri");
		 * jekyll.setEmail("dr.jekyll@gmail.com"); jekyll.setMotDePasse("Hyde");
		 * jekyll.setMatricule("8888888");
		 * 
		 * specialite.getPraticiens().add(jekyll);
		 * 
		 * specialite = specialiteDao.save(specialite); specialiteFind =
		 * specialiteDao.findById(idSpecialite);
		 * 
		 * Assert.assertEquals(specialiteFind.getPraticiens().size(), 1); Praticien
		 * praticienFind = specialiteFind.getPraticiens().get(0);
		 * Assert.assertEquals(praticienFind.getNom(), "JEKYLL");
		 * Assert.assertEquals(praticienFind.getPrenom(), "Henri");
		 * Assert.assertEquals(praticienFind.getMotDePasse(), "Hyde");
		 * Assert.assertEquals(praticienFind.getEmail(), "dr.jekyll@gmail.com");
		 * Assert.assertEquals(praticienFind.getMatricule(), "888888");
		 * 
		 * specialite.getPraticiens().add(jekyll); specialite =
		 * specialiteDao.save(specialite); specialiteFind =
		 * specialiteDao.findById(idSpecialite);
		 * Assert.assertEquals(specialiteFind.getPraticiens().size(), 2);
		 * 
		 * specialite.getPraticiens().clear(); specialiteFind =
		 * specialiteDao.findById(idSpecialite); specialite =
		 * specialiteDao.save(specialite);
		 * Assert.assertEquals(specialiteFind.getPraticiens().size(), 0);
		 */
		int sizeEnd = specialiteDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		specialiteDao.delete(specialite);

		specialiteFind = specialiteDao.findById(idSpecialite);

		Assert.assertNull(specialiteFind);
		
		int sizeEndAfterDelete = specialiteDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
		
		

		
	}


}
