package monRdv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.RendezVous;

public interface IRendezVousDao extends JpaRepository <RendezVous, Long>{
    List<RendezVous> findAllByPraticien(Long id);
}
