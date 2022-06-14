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
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Civilite;
import monRdv.model.Patient;

public class PatientDaoCsv implements IPatientDao{

	private final String chemin;
	
	public PatientDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> patients = readAll();

		return patients;
	}


	@Override
	public Patient findById(Long id) {
		List<Patient> patients = readAll();

		for (Patient patient : patients) {
			if (patient.getId() == id) {
				return patient;
			}
		}

		return null;
	}

	@Override
	public void create(Patient obj) {
		List<Patient> patients = readAll();

		Long max = 0L;

		for (Patient patient : patients) {
			if (patient.getId() > max) {
				max = patient.getId();
			}
		}

		obj.setId(++max);

		patients.add(obj);

		writeAll(patients);		
	}

	@Override
	public void update(Patient obj) {
		List<Patient> patients = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < patients.size(); pos++) {
			Patient patient = patients.get(pos);

			if (patient.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			patients.set(pos, obj);
		}

		writeAll(patients);		
	}

	@Override
	public void delete(Patient obj) {
		// TODO Auto-generated method stub
		
	}
	
	private List<Patient> readAll() {
		List<Patient> patients = new ArrayList<Patient>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");

					Long id = Long.valueOf(items[0]);
					Civilite civ = Civilite.valueOf(items[5]);
					String nom = items[1];
					String prenom = items[2];

					Patient patient = new Patient(civ, nom, prenom);
					patient.setId(id);
					patient.setEmail(items[3]);
					patient.setMotDePasse(items[4]);
					patient.setNumeroSS(items[6]);
					patient.setAge(Integer.parseInt(items[7]));
					patient.setTelephone(items[8]);
	

					
					patients.add(patient);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return patients;
	}

	private void writeAll(List<Patient> patients) {
		List<String> lignes = new ArrayList<String>();

		for (Patient patient : patients) {
			StringBuilder sb = new StringBuilder();

			sb.append(patient.getId()).append(";");
			sb.append(patient.getNom()).append(";");
			sb.append(patient.getPrenom()).append(";");
			sb.append(patient.getEmail()).append(";");
			sb.append(patient.getMotDePasse()).append(";");
			sb.append(patient.getCivilite().toString()).append(";");
			sb.append(patient.getNumeroSS()).append(";");
			sb.append(patient.getAge()).append(";");
			sb.append(patient.getTelephone());


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
