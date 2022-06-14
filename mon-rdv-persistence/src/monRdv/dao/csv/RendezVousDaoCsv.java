package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import monRdv.dao.IPatientDao;
import monRdv.dao.IRendezVousDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Patient;
import monRdv.model.RendezVous;
import monRdv.model.Statut;

public class RendezVousDaoCsv implements IRendezVousDao {

	private final String chemin;
	private IPatientDao patientDao = new PatientDaoCsv("patients.csv");
	
	public RendezVousDaoCsv(String chemin) {
		super(); 
		this.chemin = chemin; 
	}
	
	@Override
	public List<RendezVous> findAll() {
		List<RendezVous> creneaux = readAll(); 
		return creneaux;
	}



	@Override
	public RendezVous findById(Long id) {
		List<RendezVous> rendezVousS = readAll(); 
		
		for (RendezVous rendezVous : rendezVousS) {
			if (rendezVous.getId() == id) {
				return rendezVous; 
			}
		}
		return null; 
	}

	@Override
	public void create(RendezVous obj) {
		List<RendezVous> rendezVousS = readAll(); 
		
		Long max = 0L; 
		
		for (RendezVous rendezVous : rendezVousS) {
			if (rendezVous.getId() > max) {
				max = rendezVous.getId(); 
			}
		}
		
		obj.setId(++max);
		
		rendezVousS.add(obj); 
		
		writeAll(rendezVousS); 
		
	}

	@Override
	public void update(RendezVous obj) {
		List<RendezVous> rendezVousS = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < rendezVousS.size(); pos++) {
			RendezVous rendezVous = rendezVousS.get(pos);

			if (rendezVous.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			rendezVousS.set(pos, obj);
		}

		writeAll(rendezVousS);
	}

	@Override
	public void delete(RendezVous obj) {
		List<RendezVous> rendezVousS = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < rendezVousS.size(); pos++) {
			RendezVous rendezVous = rendezVousS.get(pos);

			if (rendezVous.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			rendezVousS.remove(pos);
		}

		writeAll(rendezVousS);
	}
	
	private List<RendezVous> readAll()  {
		List<RendezVous> rendezVousS = new ArrayList<RendezVous>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");
					
					Long id = Long.valueOf(items[0]);
					Statut statut = Statut.valueOf(items[1]);
					Long idPatient = !items[2].isEmpty() ? Long.valueOf(items[2]) : null;

					RendezVous rendezVous = new RendezVous();
					rendezVous.setId(id);
					rendezVous.setStatut(statut);
					
					if(idPatient != null) {
						Patient patient = patientDao.findById(idPatient);
						rendezVous.setPatient(patient);
					}

					rendezVousS.add(rendezVous);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		} 

		return rendezVousS;
	}

	private void writeAll(List<RendezVous> rendezVousS) {
		List<String> lignes = new ArrayList<String>();

		for (RendezVous rendezVous : rendezVousS) {
			StringBuilder sb = new StringBuilder();

			sb.append(rendezVous.getId()).append(";");
			sb.append(rendezVous.getStatut()).append(";");

			if(rendezVous.getPatient() != null && rendezVous.getPatient().getId() != null) {
				sb.append(rendezVous.getPatient().getId());
			}
			
			lignes.add(sb.toString());
		}

		try {
			Files.write(Paths.get(this.chemin), lignes, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à l'écriture fichier", e);
		}
	}

}
