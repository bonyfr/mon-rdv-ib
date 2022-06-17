package monRdv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import monRdv.model.Lieu;

public interface ILieuDao extends JpaRepository<Lieu, Long> {

}
