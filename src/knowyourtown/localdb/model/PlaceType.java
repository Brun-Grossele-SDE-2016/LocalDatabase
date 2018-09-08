package knowyourtown.localdb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import knowyourtown.localdb.dao.PeopleDao;
import knowyourtown.localdb.model.Place;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="PlaceType") // to whate table must be persisted
@NamedQuery(name="PlaceType.findAll", query="SELECT p FROM PlaceType p")
@XmlRootElement
public class PlaceType implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_placetype")
	@TableGenerator(name="sqlite_placetype", table="sqlite_sequence",
				    pkColumnName="name", valueColumnName="seq",
				    pkColumnValue="PlaceType")
    
    @Column(name="idPlaceType") // maps the following attribute to a column
    private int idPlaceType;
    
    @Column(name="type")
    private String type;
    
    // MappedBy must be equal to the name of the attribute in Place that maps this relation
    @OneToMany(mappedBy="placeType")
    private List<Place> place;
       
    public int getIdPlaceType() {
		return idPlaceType;
	}

	public void setIdPlaceType(int id) {
		this.idPlaceType = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		
		switch(type)
		{
			case "visited":
				this.idPlaceType = 1;
				break;
			case "to visit":
				this.idPlaceType = 2;
				break;
			case "dream":
				this.idPlaceType = 3;
				break;
		}
	}
	
	// Transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
    public List<Place> getPlace() {
        return place;
    }
    public void setPlace(List<Place> place) {
        this.place = place;
    }
	
	// Database operations
	public static PlaceType getPlaceTypeById(int personId) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        PlaceType p = em.find(PlaceType.class, personId);
        PeopleDao.instance.closeConnections(em);
        return p;
    }
	
	public static int getIdPlaceTypeByType(String type) {
		int id = 0;
		EntityManager em = PeopleDao.instance.createEntityManager();
		
		String query = "SELECT m.idPlaceType FROM PlaceType m WHERE m.type = \"" + type	+ "\"";
		// System.out.println(query);
		
		List<Integer> placeType = em.createQuery(query, Integer.class).getResultList();
				
		PeopleDao.instance.closeConnections(em);

		if(placeType != null)
		{
			id = placeType.get(0);
		}
		
		return id; 
    }

	public static List<PlaceType> getAll() {
        EntityManager em = PeopleDao.instance.createEntityManager();
        List<PlaceType> list = em.createNamedQuery("PlaceType.findAll", PlaceType.class)
            .getResultList();
        PeopleDao.instance.closeConnections(em);
        return list;
    }

    public static PlaceType savePlaceType(PlaceType p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    } 

    public static PlaceType updatePlaceType(PlaceType p) {
        EntityManager em = PeopleDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
        return p;
    }

    public static void removePlaceType(PlaceType p) {
        EntityManager em = PeopleDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        PeopleDao.instance.closeConnections(em);
    }
    
}