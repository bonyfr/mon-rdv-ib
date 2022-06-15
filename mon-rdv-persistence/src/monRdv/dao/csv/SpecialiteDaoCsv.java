package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import monRdv.dao.IAdresseDao;
import monRdv.dao.IPracticienDao;
import monRdv.dao.ISpecialiteDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Praticien;
import monRdv.model.Specialite;

public class SpecialiteDaoCsv implements ISpecialiteDao {
	private IPracticienDao praticienDao = new PracticienDaoCsv("practiciens.csv");

	private final String chemin;
	private final String cheminLien = "praticien_specialite.csv";

	public SpecialiteDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Specialite> findAll() {
		List<Specialite> specialites = readAll();

		return specialites;
	}

	@Override
	public Specialite findById(Long id) {
		List<Specialite> specialites = readAll();

		for (Specialite specialite : specialites) {
			if (specialite.getId() == id) {
				return specialite;
			}
		}

		return null;
	}

	@Override
	public void create(Specialite obj) {
		List<Specialite> specialites = readAll();

		Long max = 0L;

		for (Specialite specialite : specialites) {
			if (specialite.getId() > max) {
				max = specialite.getId();
			}
		}

		obj.setId(++max);

		specialites.add(obj);

		writeAll(specialites);
	}

	@Override
	public void update(Specialite obj) {
		List<Specialite> specialites = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < specialites.size(); pos++) {
			Specialite specialite = specialites.get(pos);

			if (specialite.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			specialites.set(pos, obj);
		}

		writeAll(specialites);
	}

	@Override
	public void delete(Specialite obj) {
		List<Specialite> specialites = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < specialites.size(); pos++) {
			Specialite specialite = specialites.get(pos);

			if (specialite.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			specialites.remove(pos);
		}

		writeAll(specialites);
	}

	private List<Specialite> readAll() {
		List<Specialite> specialites = new ArrayList<Specialite>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");

					Long id = Long.valueOf(items[0]);
					String nom = items[1];
					String description = items[2];
					ArrayList<Praticien> praticiens = new ArrayList<Praticien>(); 
					Path pathLien = Paths.get(this.cheminLien);
					if(Files.exists(pathLien))
					{
						List<String> lignesLien = Files.readAllLines(pathLien, StandardCharsets.UTF_8);
						for(String ligneLien : lignesLien) {
							String[] liens = ligneLien.split(";");
							if(liens.length > 1)
							{
								if(Long.valueOf(liens[1]) == id)
								{
									Praticien praticien = praticienDao.findById(Long.valueOf(liens[0]));
									if(praticien == null) {
										throw new MonRdvPersistenceException("Unable to find praticient id"+liens[0]);
									}
									praticiens.add(praticien);
								}
							}
						}
					}
					Specialite specialite = new Specialite(id, nom, description);
					specialite.setPraticiens(praticiens);
					specialites.add(specialite);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return specialites;
	}

	private void writeAll(List<Specialite> specialites) {
		List<String> lignes = new ArrayList<String>();
		List<String> lignesLien = new ArrayList<String>();

		for (Specialite specialite : specialites) {
			StringBuilder sb = new StringBuilder();

			sb.append(specialite.getId()).append(";");
			sb.append(specialite.getNom()).append(";");
			sb.append(specialite.getDescription()).append(";");
			
			lignes.add(sb.toString());
			StringBuilder sbLien = new StringBuilder();
			for(Praticien praticien : specialite.getPraticiens()) {
				sbLien.append(praticien.getId()).append(";").append(specialite.getId()).append(System.lineSeparator());
			}
			lignesLien.add(sbLien.toString());
		}

		try {
			Files.write(Paths.get(this.chemin), lignes, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à l'écriture fichier", e);
		}
		
		try {
			Files.write(Paths.get(this.cheminLien), lignesLien, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à l'écriture fichier", e);
		}
	}

}