package introsde.assignment.soap.webservice;

import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.Measure;
import introsde.assignment.soap.model.MeasureType;

import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.webservice.People",
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
    
    
    /* Manage Measure */
    
    @Override
    public Measure readMeasure(int pId, String measureType, int mId) {
        System.out.println("Reading Measure by pId = " + pId + ", mId = " + mId + ", type = " + measureType);
        List<Measure> p = Measure.getMeasureByMidAndType(pId, mId, measureType);
        if (p!=null) {
            System.out.println("---> Found Measure");
            for(Measure m : p)
            {
            	System.out.println(m.getIdMeasure() + " " + m.getValue());
            }	
        } else {
            System.out.println("---> Didn't find any Measure");
        }
        return p.get(0);
    }

    @Override
    public List<Measure> getMeasure(int pId, String measureType) {
    	System.out.println("Reading Measure by pId = " + pId + ", type = " + measureType);
        List<Measure> p = Measure.getMeasureByPidAndType(pId, measureType);
        if (p!=null) {
            System.out.println("---> Found Measure");
            for(Measure m : p)
            {
            	System.out.println(m.getIdMeasure() + " " + m.getValue());
            }
        } else {
            System.out.println("---> Didn't find any Measure");
        }
        return p;
    }

    @Override
    public int addMeasure(int pId, Measure measure) {
    	System.out.println("Save Measure with id = " + measure.getIdMeasure());
        
    	if(measure.getMeasureType()==null)
    	{
    		System.out.println("QUi");
    	}
    	else
    	{
    		System.out.println("QUa " + measure.getMeasureType().getType());
    	}
    	
    	measure.setPerson(Person.getPersonById(pId));
		Measure.saveMeasure(measure);
        return measure.getIdMeasure();
    }

    @Override
    public int updateMeasure(int pId, Measure measure) {
    	System.out.println("Update Measure with id = " + measure.getIdMeasure());
    	measure.setPerson(Person.getPersonById(pId));
    	Measure.updateMeasure(measure);
    	return measure.getIdMeasure();
    }
    
    @Override
    public int deleteMeasure(int id) {
    	System.out.println("Delete Measure with id = " + id);
    	Measure p = Measure.getMeasureById(id);
        if (p!=null) {
            Measure.removeMeasure(p);
            return 0;
        } else {
            return -1;
        }
    }
    
    
    /* Manage MeasureType */
    
    @Override
    public MeasureType readMeasureType(int id) {
        System.out.println("Reading MeasureType by id = " + id);
        MeasureType m = MeasureType.getMeasureTypeById(id);
        if (m!=null) {
            System.out.println("---> Found MeasureType by id = " + id + " => " + m.getType());
        } else {
            System.out.println("---> Didn't find any MeasureType with  id = " + id);
        }
        return m;
    }

    @Override
    public List<MeasureType> getMeasureType() {
    	System.out.println("Read MeasureType List");
        return MeasureType.getAll();
    }
}

