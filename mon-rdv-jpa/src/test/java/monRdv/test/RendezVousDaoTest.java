package monRdv.test;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import monRdv.Singleton;
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
	public void rendezVous() {
		IRendezVousDao rendezVousDao = Singleton.getInstance().getRendezVousDao();
		IUtilisateurDao patientDao = Singleton.getInstance().getUtilisateurDao();
		IMotifDao motifDao = Singleton.getInstance().getMotifDao();

		int sizeStart = rendezVousDao.findAll().size();

		RendezVous rendezVous = new RendezVous();
		Patient patient = new Patient(Civilite.M, "DUPONT", "Jean"); 
		Motif motif = new Motif(); 
				
		rendezVous = rendezVousDao.save(rendezVous);
		patient = (Patient) patientDao.save(patient); 
		motif = motifDao.save(motif); 
				
			
		rendezVous.setPatient(patient);
		rendezVous.setStatut(Statut.VALIDER);
		rendezVous.setMotif(motif);		
		
		rendezVous = rendezVousDao.save(rendezVous);	
		
		Long idRendezVous = rendezVous.getId();	
		
		RendezVous rendezVousFind = rendezVousDao.findById(idRendezVous);

		Assert.assertEquals(patient.getId(), rendezVousFind.getPatient().getId());
		Assert.assertEquals(Statut.VALIDER, rendezVousFind.getStatut());
		Assert.assertEquals(motif.getId(), rendezVousFind.getMotif().getId());

		int sizeEnd = rendezVousDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		rendezVousDao.delete(rendezVous);

		rendezVousFind = rendezVousDao.findById(idRendezVous);

		Assert.assertNull(rendezVousFind);
		
		int sizeEndAfterDelete = rendezVousDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}
