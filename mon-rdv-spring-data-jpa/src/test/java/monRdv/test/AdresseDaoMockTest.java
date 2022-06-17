package monRdv.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IAdresseDao;
import monRdv.model.Adresse;

@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = ApplicationConfig.class)
public class AdresseDaoMockTest {
	
	@Mock
	private IAdresseDao adresseDao;

	@Test
	public void adresse() {
		Adresse adresse = new Adresse("1 rue de la Paix", "3èmdse étage", "75008", "Paris");
		adresse.setId(5L);
		
		Mockito.when(adresseDao.findById(5L)).thenReturn(adresse);
		
		Adresse adresseFind = adresseDao.findById(5L);
		
		Assert.assertEquals("1 rue de la Paix", adresseFind.getRue());
		Assert.assertEquals("3ème étage", adresseFind.getComplement());
		Assert.assertEquals("75008", adresseFind.getCodePostal());
		Assert.assertEquals("Paris", adresseFind.getVille());
	}


}
