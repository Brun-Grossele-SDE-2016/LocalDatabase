package knowyourtown.localdb.webservice;

import knowyourtown.localdb.model.Person;
import knowyourtown.localdb.model.Place;
import knowyourtown.localdb.model.PlaceType;
import knowyourtown.localdb.model.Suggestion;

import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "knowyourtown.localdb.webservice.People",
    serviceName="PeopleService")
public class PeopleImplementation implements People 
{
	/* Manage Person*/
	
    @Override
    public Person readPerson(int id) {
        System.out.println("Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

    @Override
    public List<Person> getPeople() {
    	System.out.println("Read person List");
    	return Person.getAll();
    }

    @Override
    public int addPerson(Person person) {
    	System.out.println("Save Person with id = " + person.getIdPerson());
    	person = Person.savePerson(person);
        return person.getIdPerson();
    }

    @Override
    public int updatePerson(Person person) {
    	if(person == null)
    	{
    		System.out.println("Zio billy");
    		return 1;
    	}
    	
    	System.out.println("Update Person with id = " + person.getIdPerson());
    	person = Person.updatePerson(person);
        return person.getIdPerson();
    }

    @Override
    public int deletePerson(int id) {
    	System.out.println("Delete Person with id = " + id);
    	Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            return 0;
        } else {
            return -1;
        }
    }
    
    
    /* Manage Place */
    
    @Override
    public Place readPlace(int pId, String placeType, int mId) {
        System.out.println("Reading Place by pId = " + pId + ", mId = " + mId + ", type = " + placeType);
        List<Place> p = Place.getPlaceByMidAndType(pId, mId, placeType);
        return p.get(0);
    }
    
    @Override
    public List<Place> readLastPlace(int pId) {
    	System.out.println("Reading LastPlace by pId = " + pId);
        List<Place> p = Place.getLastPlace(pId);
        return p;
    }
    
    @Override
    public Place readLastPlaceByType(int pId, String type) {
    	System.out.println("Reading LastPlace by pId = " + pId);
        Place p = Place.getLastPlaceByType(pId, type);
        return p;
    }

    @Override
    public List<Place> getPlace(int pId, String placeType) {
    	System.out.println("Reading Place by pId = " + pId + ", type = " + placeType);
        List<Place> p = Place.getPlaceByPidAndType(pId, placeType);
        return p;
    }
    
    @Override
    public List<Place> getSomePlace(int pId, String placeType, int nPlace) {
    	System.out.println("Reading Place by pId = " + pId + ", type = " + placeType + ", max = " + nPlace);
        List<Place> p = Place.getSomeOrderedPlaceByPidAndType(pId, placeType, nPlace);
        return p;
    }

    @Override
    public int addPlace(int pId, Place place) {
    	System.out.println("Save Place with id = " + place.getIdPlace());
    	place.setPerson(Person.getPersonById(pId));
		Place.savePlace(place);
        return place.getIdPlace();
    }

    @Override
    public int updatePlace(int pId, Place place) {
    	System.out.println("Update Place with id = " + place.getIdPlace());
    	place.setPerson(Person.getPersonById(pId));
    	Place.updatePlace(place);
    	return place.getIdPlace();
    }
    
    @Override
    public int deletePlace(int id) {
    	System.out.println("Delete Place with id = " + id);
    	Place p = Place.getPlaceById(id);
        if (p!=null) {
            Place.removePlace(p);
            return 0;
        } else {
            return -1;
        }
    }
    
    
    /* Manage PlaceType */
    
    @Override
    public PlaceType readPlaceType(int id) {
        System.out.println("Reading PlaceType by id = " + id);
        PlaceType m = PlaceType.getPlaceTypeById(id);
        if (m!=null) {
            System.out.println("---> Found PlaceType by id = " + id + " => " + m.getType());
        } else {
            System.out.println("---> Didn't find any PlaceType with  id = " + id);
        }
        return m;
    }
    
    @Override
    public int getIdPlaceTypeByType(String type) {
        System.out.println("Reading PlaceType by type = " + type);
        int m = PlaceType.getIdPlaceTypeByType(type);
        if (m!=0) {
            System.out.println("---> Found PlaceType by type = " + type + " => id = " + m);
        } else {
            System.out.println("---> Didn't find any PlaceType with type = " + type);
        }
        return m;
    }

    @Override
    public List<PlaceType> getPlaceType() {
    	System.out.println("Read PlaceType List");
        return PlaceType.getAll();
    }
    
    
    /* Manage Suggestion */
    
    @Override
    public List<Suggestion> getSuggestions(int pId) {
    	System.out.println("Read persons " + pId + " suggestion list");
    	return Suggestion.getSuggestionsById(pId);
    }
    
    @Override
    public Suggestion readSuggestion(int pId, String place, int gId) {
        System.out.println("Reading Suggestion by pId = " + pId + ", mId = " + gId );
        List<Suggestion> p = Suggestion.getSuggestionBySid(pId, gId);
        return p.get(0);
    }

    @Override
    public List<Suggestion> getSuggestion(int pId, String place) {
    	System.out.println("Reading Suggestion by pId = " + pId + ", place = " + place);
        List<Suggestion> p = Suggestion.getSuggestionByPidAndPlace(pId, place);
        return p;
    }

    @Override
    public int addSuggestion(int pId, Suggestion suggestion) {
    	System.out.println("Save Suggestion with id = " + suggestion.getIdSuggestion());

    	suggestion.setPerson(Person.getPersonById(pId));
		Suggestion.saveSuggestion(suggestion);
        return suggestion.getIdSuggestion();
    }

    @Override
    public int updateSuggestion(int pId, Suggestion suggestion) {
    	System.out.println("Update Suggestion with id = " + suggestion.getIdSuggestion());
    	suggestion.setPerson(Person.getPersonById(pId));
    	Suggestion.updateSuggestion(suggestion);
    	return suggestion.getIdSuggestion();
    }
    
    @Override
    public int deleteSuggestion(int id) {
    	System.out.println("Delete Suggestion with id = " + id);
    	Suggestion p = Suggestion.getSuggestionById(id);
        if (p!=null) {
            Suggestion.removeSuggestion(p);
            return 0;
        } else {
            return -1;
        }
    }

    public Suggestion getSuggestionByTitle(int pId, String title){
    	System.out.println("Reading Suggestion by title = " + title);
    	Suggestion p = null;
    	List<Suggestion> gList = Suggestion.getSuggestionByTitle(pId, title);
        if(!gList.isEmpty()) {
        	p = gList.get(0); //It should have only an element
        }
        if (p!=null) {
            System.out.println("---> Found Suggestion");
            System.out.println(p.getIdSuggestion() + " " + p.getDescription());
        } else {
            System.out.println("---> Didn't find any Suggestion");
        }
        return p;
    }
    

}
