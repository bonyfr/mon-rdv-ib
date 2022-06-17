package maCommande.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.Adresse;

public class IAdresseDao {
	public interface IAdresseDao extends JpaRepository<Adresse, Long>{
		List<Adresse> findByVille(String ville); // par convention de nommage
		
		@Query("select l.adr from Lieu l where l.praticien.id = :id")
		List<Adresse> findAllByPraticien(@Param("id") Long idPraticien); // par annotation (à privilégier)
		
		List<Adresse> findByCodePostal(@Param("cp") String codePostal); // par NamedQuery dans Adresse

}
