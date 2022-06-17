package achat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import achat.model.Adresse;

public interface IAdresseDao extends JpaRepository<Adresse, Long> {

}
