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
import monRdv.dao.ILieuDao;
import monRdv.dao.IPracticienDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;
import monRdv.model.Lieu;
import monRdv.model.Praticien;

public class LieuDaoCsv implements ILieuDao {

	private final String chemin;
	private IPracticienDao practicienDao = new PracticienDaoCsv("practicien.csv"); 
	private IAdresseDao adresseDao = new AdresseDaoCsv("adresses.csv");

	public LieuDaoCsv(String chemin) {
		super();
		this.chemin = chemin;
	}

	@Override
	public List<Lieu> findAll() {
		List<Lieu> lieux = readAll();

		return lieux;
	}

	@Override
	public Lieu findById(Long id) {
		List<Lieu> lieux = readAll();

		for (Lieu lieu : lieux) {
			if (lieu.getId() == id) {
				return lieu;
			}
		}

		return null;
	}

	@Override
	public void create(Lieu obj) {
		List<Lieu> lieux = readAll();

		Long max = 0L;

		for (Lieu lieu : lieux) {
			if (lieu.getId() > max) {
				max = lieu.getId();
			}
		}

		obj.setId(++max);

		lieux.add(obj);

		writeAll(lieux);
	}

	@Override
	public void update(Lieu obj) {
		List<Lieu> lieux = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < lieux.size(); pos++) {
			Lieu lieu = lieux.get(pos);

			if (lieu.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			lieux.set(pos, obj);
		}

		writeAll(lieux);
	}

	@Override
	public void delete(Lieu obj) {
		List<Lieu> lieux = readAll();

		boolean find = false;
		int pos = 0;

		for (; pos < lieux.size(); pos++) {
			Lieu lieu = lieux.get(pos);

			if (lieu.getId() == obj.getId()) {
				find = true;
				break;
			}
		}

		if (find) {
			lieux.remove(pos);
		}

		writeAll(lieux);
	}

	private List<Lieu> readAll() {
		List<Lieu> lieux = new ArrayList<Lieu>();

		try {
			Path path = Paths.get(chemin);
			if (Files.exists(path)) {
				List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String ligne : lignes) {
					String[] items = ligne.split(";",-1);

					Long id = Long.valueOf(items[0]);
					String nom = items[1];
					String commentaires = items[2];
					Long idAdresse = !items[3].isEmpty() ? Long.valueOf(items[3]) : null;
					Long idPrac = !items[4].isEmpty() ? Long.valueOf(items[4]) : null;

					Lieu lieu = new Lieu(nom);
					lieu.setId(id);
					lieu.setCommentaires(commentaires);
					
					if(idAdresse != null) {
						Adresse adresse = adresseDao.findById(idAdresse);
						lieu.setAdr(adresse);
					}

					if (idPrac != null) {
						Praticien praticien = practicienDao.findById(idPrac);
						lieu.setPraticien(praticien);
					}

					lieux.add(lieu);
				}
			}
		} catch (IOException e) {
			throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
		}

		return lieux;
	}

	private void writeAll(List<Lieu> lieus) {
		List<String> lignes = new ArrayList<String>();

		for (Lieu lieu : lieus) {
			StringBuilder sb = new StringBuilder();

			sb.append(lieu.getId()).append(";");
			sb.append(lieu.getNom()).append(";");
			sb.append(lieu.getCommentaires()).append(";");
			
			if(lieu.getAdr() != null && lieu.getAdr().getId() != null) {
				sb.append(lieu.getAdr().getId()).append(";");
			} else {
				sb.append(";");
			}

			if(lieu.getPraticien() != null && lieu.getPraticien().getId() != null){
				sb.append(lieu.getPraticien().getId());
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
