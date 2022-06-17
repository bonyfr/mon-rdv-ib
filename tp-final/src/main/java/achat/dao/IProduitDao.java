package achat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import achat.model.Produit;

public interface IProduitDao  extends JpaRepository<Produit, Long>  {

}
