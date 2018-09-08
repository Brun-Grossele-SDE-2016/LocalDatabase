package knowyourtown.localdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum PeopleDao {
	instance;
	private EntityManagerFactory emf;
	
	private PeopleDao() {
		if (emf!=null) {
			emf.close();
		}
		emf = Persistence.createEntityManagerFactory("knowyourtown-jpa");
	}
	
	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	public void closeConnections(EntityManager em) {
		em.close();
	}

	public EntityTransaction getTransaction(EntityManager em) {
		return em.getTransaction();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	// Add other database global access operations

}