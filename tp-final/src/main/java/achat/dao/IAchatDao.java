package achat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import achat.model.Achat;

public interface IAchatDao extends JpaRepository<Achat, Long>{

}
