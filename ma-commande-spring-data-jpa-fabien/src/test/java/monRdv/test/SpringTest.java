package monRdv.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import monRdv.dao.IAdresseDao;
import monRdv.dao.ILieuDao;
import monRdv.model.Adresse;
import monRdv.model.Lieu;

public class SpringTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		IAdresseDao adresseDao = context.getBean(IAdresseDao.class);
		ILieuDao lieuDao = context.getBean(ILieuDao.class);
		
		
		Adresse adresse = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");

		adresse = adresseDao.save(adresse);
		
		Lieu lieu = new Lieu("Hopital");
		lieu.setCommentaires("Urgence");
		lieu.setAdr(adresse);

		lieu = lieuDao.save(lieu);
				
		
		
		context.close();
	}
}
