package monRdv.dao;

import java.util.List;

import monRdv.model.RendezVous;

public interface IRendezVousDao extends IDao <RendezVous, Long>{
    List<RendezVous> findAllByPraticien(Long id);
}
