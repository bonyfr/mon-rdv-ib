package monRdv.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.dao.IMotifDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Civilite;
import monRdv.model.Creneau;
import monRdv.model.Motif;
import monRdv.model.Patient;
import monRdv.model.RendezVous;
import monRdv.model.Statut;

public class RendezVousDaoTest {

	@Test
	public void rendezVous() throws ParseException {
		IRendezVousDao rendezVousDao = Singleton.getInstance().getRendezVousDao();
		ICreneauDao creneauDao = Singleton.getInstance().getCreneauDao(); 
		IUtilisateurDao utilisateurDao = Singleton.getInstance().getUtilisateurDao(); 
		IMotifDao motifDao = Singleton.getInstance().getMotifDao(); 
		
		int sizeStart = rendezVousDao.findAll().size();

		RendezVous rendezVous = new RendezVous();
		Patient patient = new Patient(Civilite.M, "DUPONT", "Jean"); 
		Motif motif = new Motif(); 
			
		rendezVous = rendezVousDao.save(rendezVous);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Creneau creneau0800 = new Creneau(sdf.parse("20/06/2022 08:00"), 15);
		Creneau creneau0830 = new Creneau(sdf.parse("20/06/2022 08:30"), 15);
		
		creneau0800.setRendezVous(rendezVous); 
		creneau0830.setRendezVous(rendezVous);
		
		creneau0800 = creneauDao.save(creneau0800); 
		creneau0830 = creneauDao.save(creneau0830); 
		
		rendezVous = rendezVousDao.findByIdWithCreneau(rendezVous.getId()); 
		
		patient = (Patient) utilisateurDao.save(patient);
		motif = motifDao.save(motif);
		
		rendezVous.setPatient(patient);
		rendezVous.setStatut(Statut.VALIDER);
		rendezVous.setMotif(motif);			
		
		
		rendezVous = rendezVousDao.save(rendezVous);	
		
		
		Long idRendezVous = rendezVous.getId();	
		
		RendezVous rendezVousFind = rendezVousDao.findByIdWithCreneau(idRendezVous);

		Assert.assertEquals(patient.getId(), rendezVousFind.getPatient().getId());
		Assert.assertEquals(Statut.VALIDER, rendezVousFind.getStatut());
		Assert.assertEquals(motif.getId(), rendezVousFind.getMotif().getId());
		Assert.assertEquals(rendezVousFind.getCreneaux().size(), 2); 
		Creneau creneauFind = rendezVousFind.getCreneaux().get(0);
		Assert.assertEquals(creneauFind.getDuree(), 15); 
		Assert.assertEquals(sdf.parse("20/06/2022 08:00"), creneauFind.getDate()); 
		creneauFind = rendezVousFind.getCreneaux().get(1);
		Assert.assertEquals(creneauFind.getDuree(), 15); 
		Assert.assertEquals(sdf.parse("20/06/2022 08:30"), creneauFind.getDate()); 
		

		int sizeEnd = rendezVousDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		creneauDao.delete(creneau0800); 
		creneauDao.delete(creneau0830); 
		rendezVousDao.delete(rendezVous);

		rendezVousFind = rendezVousDao.findById(idRendezVous);

		Assert.assertNull(rendezVousFind);
		
		int sizeEndAfterDelete = rendezVousDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}
