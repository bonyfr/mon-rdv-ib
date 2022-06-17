package achat.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="produit")
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nom;
	@Column
	private BigDecimal prixAchat;
	@Column
	private BigDecimal prixVente;
	@ManyToOne
	@JoinColumn(name="fournisseur_id")
	Fournisseur fournisseur;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public BigDecimal getPrixAchat() {
		return prixAchat;
	}
	public void setPrixAchat(BigDecimal prixAchat) {
		this.prixAchat = prixAchat;
	}
	public BigDecimal getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(BigDecimal prixVente) {
		this.prixVente = prixVente;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
}
