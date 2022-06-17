import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import achat.config.ApplicationConfig;
import achat.dao.IAchatDao;
import achat.dao.IAdresseDao;
import achat.dao.ICommandeDao;
import achat.dao.IPersonneDao;
import achat.dao.IProduitDao;
import achat.model.Achat;
import achat.model.Adresse;
import achat.model.Civilite;
import achat.model.Client;
import achat.model.Commande;
import achat.model.CommandeEtat;
import achat.model.Fournisseur;
import achat.model.Produit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class AchatTest {
	@Autowired
	IAdresseDao adresseDao;
	@Autowired
	ICommandeDao commandeDao;
	@Autowired
	IPersonneDao personneDao;
	@Autowired
	IProduitDao produitDao;
	@Autowired
	IAchatDao achatDao;
	
	@Test
	public void achat()
	{
		Adresse adresse = new Adresse();
		adresse.setVille("Mouazé");
		
		adresse = adresseDao.save(adresse);
		
		Client client = new Client();
		client.setNom("Riou");
		client.setPrenom("Thibault");
		client.setCivilite(Civilite.M);
		client.setCommentaires("Blabalb");
		client.setAdresse(adresse);
		
		client = personneDao.save(client);
		
		Commande commande=new Commande();
		commande.setEtat(CommandeEtat.ENCOURS);
		//commande.setDate(new LocalDate(""));
		commande = commandeDao.save(commande);
		Produit produit = new Produit();
		produit.setNom("Biène");
		produit.setPrixAchat(new BigDecimal(3.5));
		produit.setPrixVente(new BigDecimal(2.5));
		produit = produitDao.save(produit);
		
	
		
		Fournisseur fournisseur = new Fournisseur();
		fournisseur.setNom("Man");
		fournisseur.setPrenom("Bar");
		fournisseur.setCivilite(Civilite.M);
		fournisseur.setSociete("Bar");
		fournisseur = personneDao.save(fournisseur);
		produit.setFournisseur(fournisseur);
		produit = produitDao.save(produit);
		
		Achat achat  = new Achat();
		achat.setQuantite(1);
		achat.setProduit(produit);
		achat = achatDao.save(achat);
		
		commande.getAchats().add(achat);
		commande = commandeDao.save(commande);
		client.getCommandes().add(commande);
		client = personneDao.save(client);
	}
}
