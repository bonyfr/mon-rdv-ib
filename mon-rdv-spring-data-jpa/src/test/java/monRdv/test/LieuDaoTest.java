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
		
		Optional<monRdv.model.Lieu> lieuFind = lieuDao.findById(idLieu);
			
		Assert.assertEquals("Hopital", lieuFind.get().getNom());
		Assert.assertEquals("Urgence", lieuFind.get().getCommentaires());
		
		Optional<Adresse> adresseFind = adresseDao.findById(lieuFind.get().getAdr().getId());
		
		Assert.assertEquals("1 rue de la Paix", adresseFind.get().getRue());
		Assert.assertEquals("3ème étage", adresseFind.get().getComplement());
		Assert.assertEquals("75008", adresseFind.get().getCodePostal());
		Assert.assertEquals("Paris", adresseFind.get().getVille());

		Optional<Utilisateur> utilisateurFind = utilisateurDao.findById(lieuFind.get().getPraticien().getId());
		
		Assert.assertEquals("HOUSE", utilisateurFind.get().getNom());
		Assert.assertEquals("hugh", utilisateurFind.get().getPrenom());
		Assert.assertEquals("dr.house@gmail.com", utilisateurFind.get().getEmail());
		Assert.assertEquals("HouseMD", utilisateurFind.get().getMotDePasse());
		
		lieu.setNom("Cabinet");
		lieu.setCommentaires("Ouvert");
		
		lieu = lieuDao.save(lieu);
		lieuFind = lieuDao.findById(idLieu);
		
		Assert.assertEquals("Cabinet", lieuFind.get().getNom());
		Assert.assertEquals("Ouvert", lieuFind.get().getCommentaires());
		
		int sizeEnd = lieuDao.findAll().size();
		
		Assert.assertEquals(1, sizeEnd - sizeStart);
		
		creneauDao.delete(creneau0830);
		
		lieuDao.delete(lieu);

		lieuFind = lieuDao.findById(idLieu);

		Assert.assertEquals(lieuFind.isPresent(), false);
		
		int sizeEndAfterDelete = lieuDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
		
	}

}
