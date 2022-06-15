package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IMotifDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Motif;
import monRdv.model.Praticien;


public class MotifDaoCsv implements IMotifDao {

	private final String chemin;
	
	public MotifDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Motif> findAll() {
		List<Motif> motifs = readAll();

		return motifs;
	}

	@Override
	public Motif findById(Long id) {
		List<Motif> motifs = readAll();

		for (Motif motif : motifs) {
			if (motif.getId() == id) {
				return motif;
			}
		}

		return null;
	}

	@Override
	public void create(Motif obj) {
		List<Motif> motifs = readAll();

		Long max = 0L;

		for (Motif motif : motifs) {
			if (motif.getId() > max) {
				max = motif.getId();
			}
		}

		obj.setId(++max);

		motifs.add(obj);

		writeAll(motifs);
		
	}

	@Override
	public void update(Motif obj) {
		List<Motif> motifs = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < motifs.size(); pos++) {
			Motif motif = motifs.get(pos);

			if (motif.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			motifs.set(pos, obj);
		}

		writeAll(motifs);
	}
		
	private List<Motif> readAll() {
		List<Motif> motifs = new ArrayList<Motif>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";");

					Long id = Long.valueOf(items[0]);
					String titre = items[1];
					int duree = Integer.parseInt(items[2]);
					Long idPraticien = !items[3].isEmpty() ? Long.valueOf(items[3]) : null;

					Motif motif = new Motif(titre, duree);
					motif.setId(id);

					if(idPraticien != null) {
						Praticien praticien = Singleton.getInstance().getPracticienDao().findById(idPraticien);
						motif.setPraticien(praticien);
					}

					motifs.add(motif);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return motifs;
	}

	private void writeAll(List<Motif> motifs) {
		List<String> lignes = new ArrayList<String>();

		for (Motif motif : motifs) {
			StringBuilder sb = new StringBuilder();

			sb.append(motif.getId()).append(";");
			sb.append(motif.getTitre()).append(";");
			sb.append(motif.getDuree());

			if(motif.getPraticien() != null && motif.getPraticien().getId() != null) {
				sb.append(motif.getPraticien().getId());
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
	
	@Override
	public void delete(Motif obj) {
		List<Motif> motifs = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < motifs.size(); pos++) {
			Motif motif = motifs.get(pos);

			if (motif.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			motifs.remove(pos);
		}

		writeAll(motifs);
		
	}
}
