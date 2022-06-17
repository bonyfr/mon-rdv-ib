package monRdv.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IAdresseDao;
import monRdv.model.Adresse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class AdresseDaoTest {
	
	@Autowired
	private IAdresseDao adresseDao;

	@Test
	public void adresse() {
		int sizeStart = adresseDao.findAll().size();

		Adresse adresse = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");

		adresse = adresseDao.save(adresse);

		Long idAdresse = adresse.getId();

		Adresse adresseFind = adresseDao.findById(idAdresse);

		Assert.assertEquals("1 rue de la Paix", adresseFind.getRue());
		Assert.assertEquals("3ème étage", adresseFind.getComplement());
		Assert.assertEquals("75008", adresseFind.getCodePostal());
		Assert.assertEquals("Paris", adresseFind.getVille());

		adresse.setRue("5 rue de Couveboie");
		adresse.setComplement("RDC");
		adresse.setCodePostal("94000");
		adresse.setVille("Créteil");

		adresse = adresseDao.save(adresse);

		adresseFind = adresseDao.findById(idAdresse);

		Assert.assertEquals("5 rue de Couveboie", adresseFind.getRue());
		Assert.assertEquals("RDC", adresseFind.getComplement());
		Assert.assertEquals("94000", adresseFind.getCodePostal());
		Assert.assertEquals("Créteil", adresseFind.getVille());

		int sizeEnd = adresseDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		adresseDao.delete(adresse);

		adresseFind = adresseDao.findById(idAdresse);

		Assert.assertNull(adresseFind);
		
		int sizeEndAfterDelete = adresseDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}
