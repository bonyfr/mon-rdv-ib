package monRdv.model;

import java.util.Date;

import javax.persistence.Transient;

public class Creneau {
	private Long id;
	private Date date;
	private int duree;
	@Transient
	private Praticien praticien;
	@Transient
	private RendezVous rendezVous;
	@Transient
	private Lieu lieu;

	public Creneau() {
		super();
	}

	public Creneau(Date date, int duree) {
		super();
		this.date = date;
		this.duree = duree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public RendezVous getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(RendezVous rendezVous) {
		this.rendezVous = rendezVous;
	}

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

}
