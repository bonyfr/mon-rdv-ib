package monRdv.test;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import monRdv.config.ApplicationConfig;
import monRdv.dao.IMotifDao;
import monRdv.dao.IRendezVousDao;
import monRdv.dao.IUtilisateurDao;
import monRdv.model.Civilite;
import monRdv.model.Motif;
import monRdv.model.Patient;
import monRdv.model.RendezVous;
import monRdv.model.Statut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class RendezVousDaoTest {
	
	@Autowired
	private IMotifDao motifDao;
	@Autowired
	private IRendezVousDao rendezVousDao;
	@Autowired
	private IUtilisateurDao utilisateurDao;

	@Test
	public void rendezVous() {
		int sizeStart = rendezVousDao.findAll().size();

		RendezVous rendezVous = new RendezVous();
		Patient patient = new Patient(Civilite.M, "DUPONT", "Jean"); 
		Motif motif = new Motif(); 
				
		rendezVous = rendezVousDao.save(rendezVous);
		patient = (Patient) utilisateurDao.save(patient); 
		motif = motifDao.save(motif); 
				
			
		rendezVous.setPatient(patient);
		rendezVous.setStatut(Statut.VALIDER);
		rendezVous.setMotif(motif);		
		
		rendezVous = rendezVousDao.save(rendezVous);	
		
		Long idRendezVous = rendezVous.getId();	
		
		RendezVous rendezVousFind = rendezVousDao.findById(idRendezVous).get();

		Assert.assertEquals(patient.getId(), rendezVousFind.getPatient().getId());
		Assert.assertEquals(Statut.VALIDER, rendezVousFind.getStatut());
		Assert.assertEquals(motif.getId(), rendezVousFind.getMotif().getId());

		int sizeEnd = rendezVousDao.findAll().size();

		Assert.assertEquals(1, sizeEnd - sizeStart);

		rendezVousDao.delete(rendezVous);

		Optional<RendezVous> optRendezVousFind = rendezVousDao.findById(idRendezVous);

		Assert.assertFalse(optRendezVousFind.isPresent());
		
		int sizeEndAfterDelete = rendezVousDao.findAll().size();

		Assert.assertEquals(0, sizeEndAfterDelete - sizeStart);
	}


}
