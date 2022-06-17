package achat.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Commande")
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private CommandeEtat etat;
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	@OneToMany(mappedBy="commande")
	private List<Achat> achats = new ArrayList<Achat>();
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public CommandeEtat getEtat() {
		return etat;
	}
	public void setEtat(CommandeEtat etat) {
		this.etat = etat;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Achat> getAchats() {
		return achats;
	}
	public void setAchats(List<Achat> achats) {
		this.achats = achats;
	}}
