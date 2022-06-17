package achat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import achat.model.Personne;

public interface IPersonneDao extends JpaRepository<Personne, Long> {

}
