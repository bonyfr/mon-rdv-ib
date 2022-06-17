package monRdv.test;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.dao.ICreneauDao;
import monRdv.dao.ILieuDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Lieu;
import monRdv.model.Adresse;
import monRdv.model.Praticien;
import monRdv.model.Utilisateur;
import monRdv.model.Creneau;

public class LieuDaoTest {

	@Test
	public void Lieu() throws ParseException {	
		IAdresseDao adresseDao = Singleton.getInstance().getAdresseDao();
		ILieuDao lieuDao = Singleton.getInstance().getLieuDao();
		IUtilisateurDao utilisateurDao = Singleton.getInstance().getUtilisateurDao();
		ICreneauDao creneauDao = Singleton.getInstance().getCreneauDao();
		
		
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
		
		Lieu lieuFind = lieuDao.findById(idLieu);
			
		Assert.assertEquals("Hopital", lieuFind.getNom());
		Assert.assertEquals("Urgence", lieuFind.getCommentaires());
		
		Adresse adresseFind = adresseDao.findById(lieuFind.getAdr().getId());
		
		Assert.assertEquals("1 rue de la Paix", adresseFind.getRue());
		Assert.assertEquals("3ème étage", adresseFind.getComplement());
		Assert.assertEquals("75008", adresseFind.getCodePostal());
		Assert.assertEquals("Paris", adresseFind.getVille());

		Utilisateur utilisateurFind = utilisateurDao.findById(lieuFind.getPraticien().getId());
		
		Assert.assertEquals("HOUSE", utilisateurFind.getNom());
		Assert.assertEquals("hugh", utilisateurFind.getPrenom());
		Assert.assertEquals("dr.house@gmail.com", utilisateurFind.getEmail());
		Assert.assertEquals("HouseMD", utilisateurFind.getMotDePasse());
		
		lieu.setNom("Cabinet");
		lieu.setCommentaires("Ouvert");
		
		lieu = lieuDao.save(lieu);
		lieuFind = lieuDao.findById(idLieu);
		
		Assert.assertEquals("Cabinet", lieuFind.getNom());
		Assert.assertEquals("Ouvert", lieuFind.getCommentaires());
		
		int sizeEnd = lieuDao.findAll().size();
		
		Assert.assertEquals(1, sizeEnd - sizeStart);
		
		creneauDao.delete(creneau0830);
		
		lieuDao.delete(lieu);

		lieuFind = lieuDao.findById(idLieu);

		Assert.assertNull(lieuFind);
		
		int sizeEndAfterDelete = lieuDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
		
	}

}
