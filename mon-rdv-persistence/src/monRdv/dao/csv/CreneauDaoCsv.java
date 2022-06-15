package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.ICreneauDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Creneau;
import monRdv.model.Lieu;

public class CreneauDaoCsv implements ICreneauDao {

	private final String chemin; 
	
	public CreneauDaoCsv(String chemin) {
		super(); 
		this.chemin = chemin; 
	}
	
	@Override
	public List<Creneau> findAll() {
		List<Creneau> creneaux = readAll(); 
		return creneaux;
	}



	@Override
	public Creneau findById(Long id) {
		List<Creneau> creneaux = readAll(); 
		
		for (Creneau creneau : creneaux) {
			if (creneau.getId() == id) {
				return creneau; 
			}
		}
		return null; 
	}

	@Override
	public void create(Creneau obj) {
		List<Creneau> creneaux = readAll(); 
		
		Long max = 0L; 
		
		for (Creneau creneau : creneaux) {
			if (creneau.getId() > max) {
				max = creneau.getId(); 
			}
		}
		
		obj.setId(++max);
		
		creneaux.add(obj); 
		
		writeAll(creneaux); 
		
	}

	@Override
	public void update(Creneau obj) {
		List<Creneau> creneaux = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < creneaux.size(); pos++) {
			Creneau creneau = creneaux.get(pos);

			if (creneau.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			creneaux.set(pos, obj);
		}

		writeAll(creneaux);
	}

	@Override
	public void delete(Creneau obj) {
		List<Creneau> creneaux = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < creneaux.size(); pos++) {
			Creneau creneau = creneaux.get(pos);

			if (creneau.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			creneaux.remove(pos);
		}

		writeAll(creneaux);
	}
	
	private List<Creneau> readAll()  {
		List<Creneau> creneaux = new ArrayList<Creneau>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");
					
					Long id = Long.valueOf(items[0]);
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(items[1]); 
					int duree = Integer.parseInt(items[2]); 
					Long idLieu = !items[3].isEmpty() ? Long.valueOf(items[3]) : null; 
					Creneau creneau = new Creneau(date, duree);
					creneau.setId(id);

					if (idLieu != null) {
						Lieu lieu = Singleton.getInstance().getLieuDao().findById(idLieu); 
						creneau.setLieu(lieu);
					}
					
					creneaux.add(creneau);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		} catch (ParseException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return creneaux;
	}

	private void writeAll(List<Creneau> creneaux) {
		List<String> lignes = new ArrayList<String>();

		for (Creneau creneau : creneaux) {
			StringBuilder sb = new StringBuilder();

			sb.append(creneau.getId()).append(";");
			sb.append(creneau.getDate()).append(";");
			sb.append(creneau.getDuree()).append(";");

			if(creneau.getLieu() != null && creneau.getLieu().getId() != null) {
				sb.append(creneau.getLieu().getId());
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
