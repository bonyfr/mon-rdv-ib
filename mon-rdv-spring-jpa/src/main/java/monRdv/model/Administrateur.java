package monRdv.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("administrateur")
public class Administrateur extends Utilisateur {

	public Administrateur() {
		super();
	}

	public Administrateur(String nom, String prenom) {
		super(nom, prenom);
	}

}
