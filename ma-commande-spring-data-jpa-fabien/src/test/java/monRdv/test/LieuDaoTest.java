package monRdv.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Lieu;
import monRdv.model.Adresse;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;
import monRdv.model.Creneau;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class LieuDaoTest {
	
	@Autowired
	private IAdresseDao adresseDao;
	@Autowired
	private ILieuDao lieuDao;
	@Autowired
	private IUtilisateurDao utilisateurDao;
	@Autowired
	private ICreneauDao creneauDao;

	@Test
	public void Lieu() throws ParseException {	
		
		
		
		Adresse adresse = new Adresse("1 rue de la Paix", "3ème étage", "75008", "Paris");
		adresse = adresseDao.save(adresse);
		
		Praticien house = new Praticien("HOUSE", "hugh");
		house.setEmail("dr.house@gmail.com");
		house.setMotDePasse("HouseMD");
		house.setMatricule("888888");
		
		house = (Praticien) utilisateurDao.save(house);
		
		int sizeStart = lieuDao.findAll().size();
		
		Lieu lieu = new Lieu("Hopital");
		lieu.setCommentaires("Urgence");
		lieu.setAdr(adresse);
		lieu.setPraticien(house);	
		lieu = lieuDao.save(lieu);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Creneau creneau0830 = new Creneau(sdf.parse("20/06/2022 08:30"), 15);
		creneau0830.setLieu(lieu);
		creneau0830 = creneauDao.save(creneau0830);
		
		Long idLieu = lieu.getId();
		
		Optional<Lieu> optLieuFind = lieuDao.findById(idLieu);
			
		if(!optLieuFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		Lieu lieuFind = optLieuFind.get();

		Assert.assertEquals("Hopital", lieuFind.getNom());
		Assert.assertEquals("Urgence", lieuFind.getCommentaires());
		
		Optional<Adresse> optAdresseFind = adresseDao.findById(lieuFind.getAdr().getId());
		
		if(!optAdresseFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		Adresse adresseFind = optAdresseFind.get();

		Assert.assertEquals("1 rue de la Paix", adresseFind.getRue());
		Assert.assertEquals("3ème étage", adresseFind.getComplement());
		Assert.assertEquals("75008", adresseFind.getCodePostal());
		Assert.assertEquals("Paris", adresseFind.getVille());

		Optional<Utilisateur> optUtilisateurFind = utilisateurDao.findById(lieuFind.getPraticien().getId());
		
		if(!optUtilisateurFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		Utilisateur utilisateurFind = optUtilisateurFind.get();

		Assert.assertEquals("HOUSE", utilisateurFind.getNom());
		Assert.assertEquals("hugh", utilisateurFind.getPrenom());
		Assert.assertEquals("dr.house@gmail.com", utilisateurFind.getEmail());
		Assert.assertEquals("HouseMD", utilisateurFind.getMotDePasse());
		
		lieu.setNom("Cabinet");
		lieu.setCommentaires("Ouvert");
		
		lieu = lieuDao.save(lieu);
		optLieuFind = lieuDao.findById(idLieu);
		
		if(!optLieuFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		lieuFind = optLieuFind.get();

		Assert.assertEquals("Cabinet", lieuFind.getNom());
		Assert.assertEquals("Ouvert", lieuFind.getCommentaires());
		
		int sizeEnd = lieuDao.findAll().size();
		
		Assert.assertEquals(1, sizeEnd - sizeStart);
		
		creneauDao.delete(creneau0830);
		
		lieuDao.delete(lieu);

		
		optLieuFind = lieuDao.findById(idLieu);

		if(!optLieuFind.isPresent()) {
			Assert.fail("Erreur find adresse");
		}
		
		lieuFind = optLieuFind.get();

		Assert.assertNull(lieuFind);
		
		int sizeEndAfterDelete = lieuDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
		
	}

}
