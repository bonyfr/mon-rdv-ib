package maCommande.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import monRdv.model.Civilite;

@Entity
@Table(name="fournisseur")
public class Fournisseur {
	@Column(name="societe", length = 20)
	private String societe;

	public Fournisseur(String societe) {
		super();
		this.societe = societe;
	}

	public Fournisseur() {
		super();
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

}
