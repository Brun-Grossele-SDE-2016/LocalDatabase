package knowyourtown.localdb.webservice;

import knowyourtown.localdb.model.Person;
import knowyourtown.localdb.model.Place;
import knowyourtown.localdb.model.PlaceType;
import knowyourtown.localdb.model.Suggestion;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People 
{
	/* Person*/
	@WebMethod(operationName="getPeopleList")
    @WebResult(name="people") 
    public List<Person> getPeople();
    
	@WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="createPerson")
    @WebResult(name="personId") 
    public int addPerson(@WebParam(name="person", targetNamespace="http://webservice.localdb.knowyourtown/") Person person);

    @WebMethod(operationName="updatePerson")
    @WebResult(name="personId") 
    public int updatePerson(@WebParam(name="person", targetNamespace="http://webservice.localdb.knowyourtown/") Person person);

    @WebMethod(operationName="deletePerson")
    @WebResult(name="result") 
    public int deletePerson(@WebParam(name="personId") int id);
    
    
    /* Place */
    
    @WebMethod(operationName="getPlaceHistory")
    @WebResult(name="placeList") 
    public List<Place> getPlace(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace);
    
    @WebMethod(operationName="getSomePlace")
    @WebResult(name="placeList") 
    public List<Place> getSomePlace(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace, @WebParam(name="nPlace") int nPlace);
    
    @WebMethod(operationName="readLastPlace")
    @WebResult(name="placeList") 
    public List<Place> readLastPlace(@WebParam(name="personId") int pId);
    
    @WebMethod(operationName="readLastPlaceByType")
    @WebResult(name="place") 
    public Place readLastPlaceByType(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace);
    
    @WebMethod(operationName="readPlace")
    @WebResult(name="place") 
    public Place readPlace(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace, @WebParam(name="placeId") int mId);
    
    @WebMethod(operationName="createPlace")
    @WebResult(name="placeId") 
    public int addPlace(@WebParam(name="personId") int pId, @WebParam(name="place", targetNamespace="http://webservice.localdb.knowyourtown/") Place place);

    @WebMethod(operationName="updatePlace")
    @WebResult(name="placeId") 
    public int updatePlace(@WebParam(name="personId") int pId, @WebParam(name="place", targetNamespace="http://webservice.localdb.knowyourtown/") Place place);
    
    @WebMethod(operationName="deletePlace")
    @WebResult(name="result") 
    public int deletePlace(@WebParam(name="placeId") int id);
    
    
    /* PlaceType */
    
    @WebMethod(operationName="getPlaceTypeList")
    @WebResult(name="placeTypeList") 
    public List<PlaceType> getPlaceType();
    
    @WebMethod(operationName="readPlaceType")
    @WebResult(name="placeType") 
    public PlaceType readPlaceType(@WebParam(name="placeTypeId") int id);
    
    @WebMethod(operationName="getIdPlaceTypeByType")
    @WebResult(name="idPlaceType") 
    public int getIdPlaceTypeByType(@WebParam(name="type") String type);
    
    
    /* Suggestion */
    
    @WebMethod(operationName="getSuggestions")
    @WebResult(name="suggestionList") 
    public List<Suggestion> getSuggestions(@WebParam(name="personId") int pId);
    
    @WebMethod(operationName="getSuggestionHistory")
    @WebResult(name="suggestionList") 
    public List<Suggestion> getSuggestion(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace);
    
    @WebMethod(operationName="readSuggestion")
    @WebResult(name="suggestion") 
    public Suggestion readSuggestion(@WebParam(name="personId") int pId, @WebParam(name="placeTypeId") String typePlace, @WebParam(name="suggestionId") int gId);
    
    @WebMethod(operationName="createSuggestion")
    @WebResult(name="suggestionId") 
    public int addSuggestion(@WebParam(name="personId") int pId, @WebParam(name="suggestion", targetNamespace="http://webservice.localdb.knowyourtown/") Suggestion suggestion);

    @WebMethod(operationName="updateSuggestion")
    @WebResult(name="suggestionId") 
    public int updateSuggestion(@WebParam(name="personId") int pId, @WebParam(name="suggestion", targetNamespace="http://webservice.localdb.knowyourtown/") Suggestion suggestion);
    
    @WebMethod(operationName="deleteSuggestion")
    @WebResult(name="result") 
    public int deleteSuggestion(@WebParam(name="suggestionId") int id);
    
    @WebMethod(operationName="getSuggestionByTitle")
    @WebResult(name="suggestion") 
    public Suggestion getSuggestionByTitle(@WebParam(name="personId") int pId, @WebParam(name="title") String title);

}
