package monRdv.test;

import org.junit.Assert;
import org.junit.Test;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.model.Adresse;

public class AdresseDaoTest {

	@Test
	public void adresse() {
		IAdresseDao adresseDao = Singleton.getInstance().getAdresseDao();

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
