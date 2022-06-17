package monRdv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monRdv.model.RendezVous;

public interface IRendezVousDao extends JpaRepository<RendezVous, Long>{
	@Query("select rdv from RendezVous rdv left join fetch rdv.creneaux where rdv.id = :id")
    List<RendezVous> findAllByPraticien(@Param("id") Long id);
}
