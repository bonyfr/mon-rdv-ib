package monRdv.dao;

import java.util.List;

import monRdv.model.Creneau;
import monRdv.model.RendezVous;

public interface IRendezVousDao extends IDao <RendezVous, Long>{
    List<Creneau> findAllCreneau();
}
