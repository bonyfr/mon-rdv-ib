package monRdv.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("praticien")
public class Praticien extends Utilisateur {
	@Column(length = 20)
	private String matricule;
	@Transient
	private List<Motif> motifs = new ArrayList<Motif>();
	@Transient
	private List<Creneau> creneaux = new ArrayList<Creneau>();
	@Transient
	private List<Lieu> lieux = new ArrayList<Lieu>();
	@Transient
	private List<Specialite> specialites = new ArrayList<Specialite>();

	public Praticien() {
		super();
	}

	public Praticien(String nom, String prenom) {
		super(nom, prenom);
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public List<Motif> getMotifs() {
		return motifs;
	}

	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}

	public List<Creneau> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<Creneau> creneaux) {
		this.creneaux = creneaux;
	}

	public List<Lieu> getLieux() {
		return lieux;
	}

	public void setLieux(List<Lieu> lieux) {
		this.lieux = lieux;
	}

	public List<Specialite> getSpecialites() {
		return specialites;
	}

	public void setSpecialites(List<Specialite> specialites) {
		this.specialites = specialites;
	}

}
