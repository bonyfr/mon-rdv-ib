package monRdv.model;

import javax.persistence.Transient;

public class Motif {
	private Long id;
	private String titre;
	private int duree;
	@Transient
	private Praticien praticien;

	public Motif() {
		super();
	}

	public Motif(String titre, int duree) {
		super();
		this.titre = titre;
		this.duree = duree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public Praticien getPraticien() {
		return praticien;
	}

	public void setPraticien(Praticien praticien) {
		this.praticien = praticien;
	}

}
