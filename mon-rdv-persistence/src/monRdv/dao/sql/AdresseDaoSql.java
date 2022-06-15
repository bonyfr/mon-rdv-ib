package monRdv.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monRdv.Singleton;
import monRdv.dao.IAdresseDao;
import monRdv.exception.MonRdvPersistenceException;
import monRdv.model.Adresse;

public class AdresseDaoSql implements IAdresseDao {

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT id, rue, complement, code_postal, ville FROM adresse");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");

				Adresse adresse = new Adresse(rue, complement, codePostal, ville);
				adresse.setId(id);

				adresses.add(adresse);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return adresses;
	}

	@Override
	public Adresse findById(Long id) {
		Adresse adresse = null;
		
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("SELECT rue, complement, code_postal, ville FROM adresse WHERE id = ?");
			
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");

				adresse = new Adresse(rue, complement, codePostal, ville);
				adresse.setId(id);
			}
		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}

		return adresse;
	}

	@Override
	public void create(Adresse obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO adresse (rue, complement, code_postal, ville) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal());
			ps.setString(4, obj.getVille());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Insert Adresse en Erreur");
			}
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				Long id = rs.getLong(1);
				obj.setId(id);
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void update(Adresse obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("UPDATE adresse SET rue = ?, complement = ?, code_postal = ?, ville = ? WHERE id = ?");
			
			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal());
			ps.setString(4, obj.getVille());
			ps.setLong(5, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Update Adresse en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

	@Override
	public void delete(Adresse obj) {
		try (Connection connection = Singleton.getInstance().getConnection()) {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM adresse WHERE id = ?");
			
			ps.setLong(1, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				throw new MonRdvPersistenceException("Delete Adresse en Erreur");
			}

		} catch (SQLException e) {
			throw new MonRdvPersistenceException(e);
		}
	}

}
