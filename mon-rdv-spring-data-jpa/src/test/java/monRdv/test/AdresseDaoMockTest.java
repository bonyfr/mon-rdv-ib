package monRdv.test;

import java.util.Optional;

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
		Optional<Adresse> adresse = Optional.of(new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris"));
		adresse.get().setId(5L);
		
		Mockito.when(adresseDao.findById(5L)).thenReturn(adresse);
		
		Optional<Adresse> adresseFind = adresseDao.findById(5L);
		
		Assert.assertEquals("1 rue de la Paix", adresseFind.get().getRue());
		Assert.assertEquals("3ème étage", adresseFind.get().getComplement());
		Assert.assertEquals("75008", adresseFind.get().getCodePostal());
		Assert.assertEquals("Paris", adresseFind.get().getVille());
	}


}
