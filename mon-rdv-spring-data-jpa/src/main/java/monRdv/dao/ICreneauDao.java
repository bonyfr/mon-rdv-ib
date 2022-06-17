package monRdv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Creneau;

public interface ICreneauDao extends JpaRepository<Creneau, Long>{

}
