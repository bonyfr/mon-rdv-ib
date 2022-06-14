package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import monRdv.dao.IAdministrateurDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Administrateur;

public class AdministrateurDaoCsv implements IAdministrateurDao {

	private final String chemin;

	public AdministrateurDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Administrateur> findAll() {
		List<Administrateur> administrateurs = readAll();

		return administrateurs;
	}

	@Override
	public Administrateur findById(Long id) {
		List<Administrateur> administrateurs = readAll();

		for (Administrateur administrateur : administrateurs) {
			if (administrateur.getId() == id) {
				return administrateur;
			}
		}

		return null;
	}

	@Override
	public void create(Administrateur obj) {
		List<Administrateur> administrateurs = readAll();

		Long max = 0L;

		for (Administrateur administrateur : administrateurs) {
			if (administrateur.getId() > max) {
				max = administrateur.getId();
			}
		}

		obj.setId(++max);

		administrateurs.add(obj);

		writeAll(administrateurs);
	}

	@Override
	public void update(Administrateur obj) {
		List<Administrateur> administrateurs = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < administrateurs.size(); pos++) {
			Administrateur administrateur = administrateurs.get(pos);

			if (administrateur.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			administrateurs.set(pos, obj);
		}

		writeAll(administrateurs);
	}

	@Override
	public void delete(Administrateur obj) {
		List<Administrateur> administrateurs = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < administrateurs.size(); pos++) {
			Administrateur administrateur = administrateurs.get(pos);

			if (administrateur.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			administrateurs.remove(pos);
		}

		writeAll(administrateurs);
	}

	private List<Administrateur> readAll() {
		List<Administrateur> administrateurs = new ArrayList<Administrateur>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");

					Long id = Long.valueOf(items[0]);
					String nom = items[1];
					String prenom = items[2];
					String email = items[3];
					String motDePasse = items[4];

					Administrateur administrateur = new Administrateur(nom, prenom);
					administrateur.setId(id);
					administrateur.setEmail(email);
					administrateur.setMotDePasse(motDePasse);

					administrateurs.add(administrateur);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return administrateurs;
	}

	private void writeAll(List<Administrateur> administrateurs) {
		List<String> lignes = new ArrayList<String>();

		for (Administrateur administrateur : administrateurs) {
			StringBuilder sb = new StringBuilder();

			sb.append(administrateur.getId()).append(";");
			sb.append(administrateur.getNom()).append(";");
			sb.append(administrateur.getPrenom()).append(";");
			sb.append(administrateur.getEmail()).append(";");
			sb.append(administrateur.getMotDePasse());

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
