package knowyourtown.localdb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import knowyourtown.localdb.dao.PeopleDao;
import knowyourtown.localdb.model.Person;
import knowyourtown.localdb.model.PlaceType;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Place") // to whate table must be persisted
@NamedQuery(name="Place.findAll", query="SELECT p FROM Place p")
@XmlRootElement
public class Place implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_place")
	@TableGenerator(name="sqlite_place", table="sqlite_sequence",
				    pkColumnName="name", valueColumnName="seq",
				    pkColumnValue="Place")
    @Column(name="idPlace") // maps the following attribute to a column
    private int idPlace;
    
    @Column(name="location")
    private String location;

   	@Column(name="name")
    private String name;

    @Column(name="date")
    private String date;
      
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="idPerson")
   	private Person person;
    
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="idPlaceType", insertable = true, updatable = true)
   	private PlaceType placeType;
    
    
    // add below all the getters and setters of all the private attributes   
    public int getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(int id) {
		this.idPlace = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
	
	public static List<Place> getLastPlace(int id)
	{
		EntityManager em = PeopleDao.instance.createEntityManager();

		List<Place> place = null;
		List<Place> tmp = null;
		List<PlaceType> types = em.createQuery("SELECT m FROM PlaceType m", PlaceType.class).getResultList();
		
		for(PlaceType type : types)
		{
			int idT = type.getIdPlaceType();
				
			String query = "SELECT mA FROM Place mA WHERE mA.person.idPerson = " + id + " AND mA.placeType.idPlaceType = " + idT
					+" AND mA.date = (SELECT MAX(mB.date) FROM Place mB WHERE mB.person.idPerson = " + id + " AND mB.placeType.idPlaceType = " + idT + ")"
					+" AND mA.idPlace = (SELECT MAX(mC.idPlace) FROM Place mC WHERE mC.person.idPerson = " + id + " AND mC.placeType.idPlaceType = " + idT + ")";
			
			// System.out.println(query);
			
			tmp = em.createQuery(query, Place.class).getResultList();
			
			for(int i = 0; i < tmp.size(); i++)
			{
				if(place == null)
				{
					place = tmp;
				}
				else
				{
					place.add(tmp.get(i));					
				}
			}
		}
		
		PeopleDao.instance.closeConnections(em);
		return place;
	}
	
	public static Place getLastPlaceByType(int id, String type)
	{
		EntityManager em = PeopleDao.instance.createEntityManager();

		List<Place> place = null;
		Place m = null;
		
		int idT = PlaceType.getIdPlaceTypeByType(type);
				
		String query = "SELECT mA FROM Place mA WHERE mA.person.idPerson = " + id + " AND mA.placeType.idPlaceType = " + idT
				+" AND mA.date = (SELECT MAX(mB.date) FROM Place mB WHERE mB.person.idPerson = " + id + " AND mB.placeType.idPlaceType = " + idT + ")"
				+" AND mA.idPlace = (SELECT MAX(mC.idPlace) FROM Place mC WHERE mC.person.idPerson = " + id + " AND mC.placeType.idPlaceType = " + idT + ")";
		
		// System.out.println(query);
		
		place = em.createQuery(query, Place.class).getResultList();	
		
		PeopleDao.instance.closeConnections(em);
		
		if(!place.isEmpty())
		{
			m = place.get(0);
		}
		
		return m;
	}

	// Database operations
	public static Place getPlaceById(int personId) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        Place p = em.find(Place.class, personId);
        PeopleDao.instance.closeConnections(em);
        return p;
    }

	public static List<Place> getAll() {
        EntityManager em = PeopleDao.instance.createEntityManager();
        List<Place> list = em.createNamedQuery("Place.findAll", Place.class)
            .getResultList();
        PeopleDao.instance.closeConnections(em);
        return list;
    }

    public static Place savePlace(Place p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    } 

    public static Place updatePlace(Place p) {
        EntityManager em = PeopleDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    }

    public static void removePlace(Place p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
    }
    
    // Special methods
    public static List<Place> getPlaceByPidAndType(int pId, String type) {
		EntityManager em = PeopleDao.instance.createEntityManager();
				
		String query = "SELECT m FROM Place m WHERE m.person.idPerson = " + pId 
				+ " AND m.placeType.idPlaceType = (SELECT mT.idPlaceType FROM PlaceType mT WHERE mT.type = \"" + type + "\")";
				
		// System.out.println(query);
		
		List<Place> place = em.createQuery(query, Place.class).getResultList();
		
		PeopleDao.instance.closeConnections(em);
		return place;
	}
    
    public static List<Place> getSomeOrderedPlaceByPidAndType(int pId, String type, int nPlace) {
		EntityManager em = PeopleDao.instance.createEntityManager();
				
		String query = "SELECT m FROM Place m WHERE m.person.idPerson = " + pId 
				+ " AND m.placeType.idPlaceType = (SELECT mT.idPlaceType FROM PlaceType mT WHERE mT.type = \"" + type + "\") ORDER BY m.date DESC";
				
		// System.out.println(query);
		
		List<Place> place = em.createQuery(query, Place.class).setMaxResults(nPlace).getResultList();
		
		PeopleDao.instance.closeConnections(em);
		return place;
	}

	public static List<Place> getPlaceByMidAndType(int pId, int mId, String type)
	{
		EntityManager em = PeopleDao.instance.createEntityManager();
		
		String query = "SELECT m FROM Place m WHERE m.person.idPerson = " + pId + " AND m.idPlace = " + mId
				+ " AND m.placeType.idPlaceType = (SELECT mT.idPlaceType FROM PlaceType mT WHERE mT.type = \"" + type + "\")";
				
		// System.out.println(query);
		
		List<Place> place = em.createQuery(query, Place.class).getResultList();

		PeopleDao.instance.closeConnections(em);
		return place;
	}
    
}