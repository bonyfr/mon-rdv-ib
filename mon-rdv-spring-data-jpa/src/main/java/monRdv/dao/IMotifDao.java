package monRdv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Motif;

public interface IMotifDao extends JpaRepository<Motif, Long> {

}
