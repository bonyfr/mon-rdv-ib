package maCommande.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import monRdv.model.Creneau;
import monRdv.model.Patient;
import monRdv.model.Statut;

@Entity
@Table(name="commande")
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Statut statut;
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	@OneToMany(mappedBy = "commande")
	private List<Achat> achat = new ArrayList<Achat>();

	public Commande() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Achat> getAchat() {
		return achat;
	}

	public void setAchat(List<Achat> achat) {
		this.achat = achat;
	}

}
