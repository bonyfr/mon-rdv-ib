package monRdv.model;

import java.util.ArrayList;
import java.util.List;

public class Specialite {
	private Long id;
	private String nom;
	private String description;
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
