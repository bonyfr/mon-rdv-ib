package monRdv.test;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import monRdv.dao.IAdresseDao;
import monRdv.model.Adresse;

@RunWith(MockitoJUnitRunner.class)
public class AdresseDaoMockTest {
	
	@Mock
	private IAdresseDao adresseDao;

	@Test
	public void adresse() {
		Adresse adresse = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");
		adresse.setId(5L);
		
		Optional<Adresse> optAdresse = Optional.of(adresse);
		
		Mockito.when(adresseDao.findById(5L)).thenReturn(optAdresse);
		
		Adresse adresseFind = adresseDao.findById(5L).get();
		
		Assert.assertEquals("1 rue de la Paix", adresseFind.getRue());
		Assert.assertEquals("3ème étage", adresseFind.getComplement());
		Assert.assertEquals("75008", adresseFind.getCodePostal());
		Assert.assertEquals("Paris", adresseFind.getVille());
	}


}
