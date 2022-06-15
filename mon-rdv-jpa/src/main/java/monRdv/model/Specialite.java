package monRdv.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="specialite")
public class Specialite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100)
	private String nom;
	@Column(length = 1000)
	private String description;
	@Transient
	private List<Praticien> praticiens = new ArrayList<Praticien>();

	public Specialite() {
		super();
	}
	
	

	public Specialite(String nom) {
		super();
		this.nom = nom;
	}


	public Specialite( String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}



	public Specialite(Long id, String nom, String description) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
	}



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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Praticien> getPraticiens() {
		return praticiens;
	}

	public void setPraticiens(List<Praticien> praticiens) {
		this.praticiens = praticiens;
	}

}
