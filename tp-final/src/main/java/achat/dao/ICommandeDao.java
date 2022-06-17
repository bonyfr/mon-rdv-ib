package achat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import achat.model.Commande;

public interface ICommandeDao extends JpaRepository<Commande, Long> {

}
