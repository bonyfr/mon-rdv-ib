package monRdv.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("patient")
public class Patient extends Utilisateur {
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private Civilite civilite;
	@Column(name="numero_ss", length = 20)
	private String numeroSS;
	private int age;
	@Column(length = 20)
	private String telephone;
	@Transient
	private Adresse adresse;
	@Transient
	private List<RendezVous> rendezVous = new ArrayList<RendezVous>();
	
	public Patient(Civilite civilite, String nom, String prenom) {
		super(nom, prenom);
		this.civilite = civilite;
	}

	public Patient() {
		super();
	}

	public Civilite getCivilite() {
		return civilite;
	}

	public void setCivilite(Civilite civilite) {
		this.civilite = civilite;
	}

	public String getNumeroSS() {
		return numeroSS;
	}

	public void setNumeroSS(String numeroSS) {
		this.numeroSS = numeroSS;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<RendezVous> getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(List<RendezVous> rendezVous) {
		this.rendezVous = rendezVous;
	}

}
