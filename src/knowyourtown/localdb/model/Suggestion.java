package knowyourtown.localdb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import knowyourtown.localdb.dao.PeopleDao;
import knowyourtown.localdb.model.Person;
import knowyourtown.localdb.model.Place;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Suggestion") // to whate table must be persisted
@NamedQuery(name="Suggestion.findAll", query="SELECT p FROM Suggestion p")
@XmlRootElement
public class Suggestion implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_suggestion")
	@TableGenerator(name="sqlite_suggestion", table="sqlite_sequence",
				    pkColumnName="name", valueColumnName="seq",
				    pkColumnValue="Suggestion")
    @Column(name="idSuggestion") // maps the following attribute to a column
    private int idSuggestion;
    
    @Column(name="evaluation")
    private String evaluation;
    
    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;


    @Column(name="date")
    private String date;
      
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="idPerson")
   	private Person person;
    
   @Column(name="location")
   	private String location;
    
    
    // add below all the getters and setters of all the private attributes   
    public int getIdSuggestion() {
		return idSuggestion;
	}

	public void setIdSuggestion(int id) {
		this.idSuggestion = id;
	}

	public void setTitle(String title){
		this.title = title;	
	}

	public void setDescription(String description){
		this.description = description;	
	}

	public String getTitle(){
		return title;	
	}

	public String getDescription(){
		return description;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	// Transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	// Database operations
	public static Suggestion getSuggestionById(int suggestionId) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        Suggestion p = em.find(Suggestion.class, suggestionId);
        PeopleDao.instance.closeConnections(em);
        return p;
    }
	
	public static List<Suggestion> getSuggestionsById(int personId) {
		EntityManager em = PeopleDao.instance.createEntityManager();
		
		String query = "SELECT g FROM Suggestion g WHERE g.person.idPerson = " + personId;
				
		// System.out.println(query);
		
		List<Suggestion> suggestion = em.createQuery(query, Suggestion.class).getResultList();
				
		PeopleDao.instance.closeConnections(em);
		
		return suggestion;
    }

	public static List<Suggestion> getAll() {
        EntityManager em = PeopleDao.instance.createEntityManager();
        List<Suggestion> list = em.createNamedQuery("Suggestion.findAll", Suggestion.class)
            .getResultList();
        PeopleDao.instance.closeConnections(em);
        return list;
    }

    public static Suggestion saveSuggestion(Suggestion p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    } 

    public static Suggestion updateSuggestion(Suggestion p) {
        EntityManager em = PeopleDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    }

    public static void removeSuggestion(Suggestion p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
    }
    
    // Special methods
    public static List<Suggestion> getSuggestionByPidAndPlace(int pId, String place) {
		EntityManager em = PeopleDao.instance.createEntityManager();
				
		String query = "SELECT g FROM Suggestion g WHERE g.person.idPerson = " + pId 
				+ " AND g.place.idPlace = (SELECT p.idPlace FROM Place p WHERE p.name = \"" + place + "\")";
				
		// System.out.println(query);
		
		List<Suggestion> suggestion = em.createQuery(query, Suggestion.class).getResultList();
		
		PeopleDao.instance.closeConnections(em);
		return suggestion;
	}
    
    public static List<Suggestion> getSuggestionBySid(int pId, int sId)
	{
		EntityManager em = PeopleDao.instance.createEntityManager();
		
		String query = "SELECT g FROM Suggestion g WHERE g.person.idPerson = " + pId + " AND g.idSuggestion = " + sId ;
				
		// System.out.println(query);
		
		List<Suggestion> suggestion = em.createQuery(query, Suggestion.class).getResultList();

		PeopleDao.instance.closeConnections(em);
		return suggestion;
	}

     public static List<Suggestion> getSuggestionByTitle(int pId, String title){

		EntityManager em = PeopleDao.instance.createEntityManager();
		
		String query = "SELECT g FROM Suggestion g WHERE g.title = \"" + title + "\" AND g.person.idPerson = " + pId;
				
		// System.out.println(query);
		
		List<Suggestion> suggestion = em.createQuery(query, Suggestion.class).getResultList();
				
		PeopleDao.instance.closeConnections(em);
		return suggestion;
	}

    
}
