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
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;

public class AdresseDaoCsv implements IAdresseDao {

	private final String chemin;

	public AdresseDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = readAll();

		return adresses;
	}

	@Override
	public Adresse findById(Long id) {
		List<Adresse> adresses = readAll();

		for (Adresse adresse : adresses) {
			if (adresse.getId() == id) {
				return adresse;
			}
		}

		return null;
	}

	@Override
	public void create(Adresse obj) {
		List<Adresse> adresses = readAll();

		Long max = 0L;

		for (Adresse adresse : adresses) {
			if (adresse.getId() > max) {
				max = adresse.getId();
			}
		}

		obj.setId(++max);

		adresses.add(obj);

		writeAll(adresses);
	}

	@Override
	public void update(Adresse obj) {
		List<Adresse> adresses = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < adresses.size(); pos++) {
			Adresse adresse = adresses.get(pos);

			if (adresse.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			adresses.set(pos, obj);
		}

		writeAll(adresses);
	}

	@Override
	public void delete(Adresse obj) {
		List<Adresse> adresses = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < adresses.size(); pos++) {
			Adresse adresse = adresses.get(pos);

			if (adresse.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			adresses.remove(pos);
		}

		writeAll(adresses);
	}

	private List<Adresse> readAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");

					Long id = Long.valueOf(items[0]);
					String rue = items[1];
					String complement = items[2];
					String codePostal = items[3];
					String ville = items[4];

					Adresse adresse = new Adresse(rue, complement, codePostal, ville);
					adresse.setId(id);

					adresses.add(adresse);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return adresses;
	}

	private void writeAll(List<Adresse> adresses) {
		List<String> lignes = new ArrayList<String>();

		for (Adresse adresse : adresses) {
			StringBuilder sb = new StringBuilder();

			sb.append(adresse.getId()).append(";");
			sb.append(adresse.getRue()).append(";");
			sb.append(adresse.getComplement()).append(";");
			sb.append(adresse.getCodePostal()).append(";");
			sb.append(adresse.getVille());

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
