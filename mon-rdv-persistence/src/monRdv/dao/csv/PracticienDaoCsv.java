package monRdv.dao.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import monRdv.dao.IPracticienDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Praticien;

public class PracticienDaoCsv implements IPracticienDao {

    private final String chemin;

    public PracticienDaoCsv(String chemin) {
        super();
        this.chemin = chemin;
    }

    @Override
    public void create(Praticien obj) {
        List<Praticien> praticiens = readAll();

        // Check that matricule doesn't exist else do not add it
        if (findById(obj.getMatricule()) != null) {
            praticiens.add(obj);
        }

        writeAll(praticiens);
    }

    @Override
    public void delete(Praticien obj) {
        List<Praticien> praticiens = readAll();

        boolean find = false;
        int pos = 0;
        for (; pos < praticiens.size(); pos++) {
            Praticien praticien = praticiens.get(pos);

            if (praticien.getMatricule().equals(obj.getMatricule())) {
                find = true;
                break;
            }
        }
        if (find) {
            praticiens.remove(pos);
        }

        writeAll(praticiens);
    }

    @Override
    public List<Praticien> findAll() {
        return readAll();
    }

    @Override
    public Praticien findById(String id) {
        // Find by matricule
        List<Praticien> praticiens = readAll();
        for (Praticien praticien : praticiens) {
            if (praticien.getMatricule().equals(id)) {
                return praticien;
            }
        }

        return null;
    }

    @Override
    public void update(Praticien obj) {
        List<Praticien> praticiens = readAll();

        boolean find = false;
        int pos = 0;
        for (; pos < praticiens.size(); pos++) {
            Praticien praticien = praticiens.get(pos);

            if (praticien.getMatricule().equals(obj.getMatricule())) {
                find = true;
                break;
            }
        }
        if (find) {
            praticiens.set(pos, obj);
        }

        writeAll(praticiens);
    }

    private List<Praticien> readAll() {
        List<Praticien> praticiens = new ArrayList<Praticien>();
        try {
            Path path = Paths.get(chemin);
            if (Files.exists((path))) {
                List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);

                for (String ligne : lignes) {
                    String[] items = ligne.split(";");

                    String matricule = items[0];
                    String nom = items[1];
                    String prenom = items[2];

                    Praticien praticien = new Praticien(nom, prenom);
                    praticien.setMatricule(matricule);

                    praticiens.add(praticien);
                }
            }
        } catch (IOException e) {
            throw new MonRdvPersistenceException("Erreur à la lecture du fichier", e);
        }

        return praticiens;
    }

    private void writeAll(List<Praticien> praticiens) {
        List<String> lignes = new ArrayList<String>();

        for (Praticien praticien : praticiens) {
            StringBuilder sb = new StringBuilder();

            sb.append(praticien.getMatricule())
                    .append(";")
                    .append(praticien.getNom())
                    .append(";")
                    .append(praticien.getPrenom());

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
