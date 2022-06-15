package monRdv.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mon-rdv-jpa");
		

		emf.close();
	}

}
