package introsde.assignment.soap.client;

import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.Measure;
import introsde.assignment.soap.model.MeasureType;
import introsde.assignment.soap.webservice.People;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient
{
    public static void main(String[] args) throws Exception 
    {
        URL url = new URL("http://127.0.1.1:6903/ws/people?wsdl");
        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://webservice.soap.assignment.introsde/", "PeopleService");
        Service service = Service.create(url, qname);
        People people = service.getPort(People.class);
        
        
        System.out.println("IntroSDE Assignment 3");
        System.out.println("=================================================");
        
        
        /*  readPersonList() */
        System.out.println("1: List of perosn");
        
        List<Person> pList = people.getPeople();        
        
        for(Person p: pList)
        {
        	System.out.println("\t--> " + p.getFirstname() + " " + p.getLastname() + " " + p.getBirthdate());
        }        
        
        System.out.println("=================================================");
        
        
        /* readPerson(Long id) */
        Person p = people.readPerson(1);
        
        System.out.println("2: Person by id");
        System.out.println("\t--> " + p.getFirstname() + " " + p.getLastname() + " " + p.getBirthdate());
        
        System.out.println("=================================================");
        
        
        /* updatePerson(Person p) */
        System.out.println("3: Update person");
        
        System.out.println("\t--> Before: " + p.getFirstname() + " " + p.getLastname() + " " + p.getBirthdate());
        
        p.setFirstname("Nuovo"); 
        int i = people.updatePerson(p);
        
        p = people.readPerson(i);        
        System.out.println("\t--> After: " + p.getFirstname() + " " + p.getLastname() + " " + p.getBirthdate());
        
        System.out.println("=================================================");
        
        /* createPerson(Person p) */
        System.out.println("4: Create person");
        
        Person p1 = makePerson(7, "Chuck", "Norris", "1945-01-01");
        
        List<Measure> mList = new ArrayList<Measure>();
        mList.add(makeMeasure(7, (float) 1.72, "1978-09-02", p1, makeMeasureType(1, people)));
        mList.add(makeMeasure(8, 75, "1978-09-02", p1, makeMeasureType(2, people)));               
        p1.setMeasure(mList);
        
        i = people.addPerson(p1);
        
        p = people.readPerson(i);
        System.out.println("\t--> " + p.getFirstname() + " " + p.getLastname() + " " + p.getBirthdate());
        
        /* TODO: Inserire anche le misure con la nuova persona
	        Measure m3 = people.readMeasure(7, makeMeasureType(1, people).getType(), 7);
	        System.out.println("\t\t--> " + m3.getIdMeasure() + " " + m3.getValue());
	        
	        m3 = people.readMeasure(7, makeMeasureType(2, people).getType(), 8);
	        System.out.println("\t\t--> " + m3.getIdMeasure() + " " + m3.getValue());
        */
        
        System.out.println("=================================================");
                
        /* deletePerson(Long id) */        
        System.out.println("5: Delete person 7");
        
        pList = people.getPeople();
        System.out.println("\t--> Before:");
        for(Person p3: pList)
        {
        	System.out.println("\t\t--> " + p3.getFirstname() + " " + p3.getLastname() + " " + p3.getBirthdate());
        }
        
        people.deletePerson(7);
        
        pList = people.getPeople();
        System.out.println("\t--> After:");
        for(Person p4: pList)
        {
        	System.out.println("\t\t--> " + p4.getFirstname() + " " + p4.getLastname() + " " + p4.getBirthdate());
        }
       
        System.out.println("=================================================");
        
        /* readPersonHistory(Long id, String measureType) */
        System.out.println("6: History");
        
        List<Measure> mH = people.getMeasure(1, "height");
        
        for(Measure m: mH)
        {
        	System.out.println("\t--> " + m.getIdMeasure() + " " + m.getValue());
        }
        
        System.out.println("=================================================");
        
        /* readMeasureTypes() */
        System.out.println("7: List of MeasureType");
        
        List<MeasureType> mT = people.getMeasureType();
        
        for(MeasureType m: mT)
        {
        	System.out.println("\t--> " + m.getIdMeasureType() + " " + m.getType());
        }
        
        System.out.println("=================================================");
        
        /* readPersonMeasure(Long id, String measureType, Long mid) */
        System.out.println("8: Read measure");
        
        Measure m1 = people.readMeasure(1, "height", 1);
        System.out.println("\t--> " + m1.getIdMeasure() + " " + m1.getValue());
        
        System.out.println("=================================================");
        
        /* savePersonMeasure(Long id, Measure m) */
        System.out.println("9: Create measure");
        
        Person p2 = people.readPerson(1);
        i = people.addMeasure(1, makeMeasure(17, (float) 1.72, "2011-12-09", p2, makeMeasureType(1, people)));
        
        Measure m4 = people.readMeasure(1, makeMeasureType(1, people).getType(), i);
        System.out.println("\t--> " + m4.getIdMeasure() + " " + m4.getValue());
        
        System.out.println("=================================================");
        
        /* updatePersonMeasure(Long id, Measure m) */
        System.out.println("10: Update measure");
        
        Measure m2 = people.readMeasure(1, "height", 17);
        System.out.println("\t--> Before: " + m2.getIdMeasure() + " " + m2.getValue());
        
        m2.setValue((float) 1.81);
        i = people.updateMeasure(1, m2);
        
        m2 = people.readMeasure(1, "height", i);
        
        System.out.println("\t--> After: " + m2.getIdMeasure() + " " + m2.getValue());
        
        System.out.println("=================================================");
        
        // Restore the initial conditions
        p = people.readPerson(1);
        p.setFirstname("Giorgio"); 
        people.updatePerson(p);
        
        people.deleteMeasure(17);
    }
    
    public static Person makePerson(int id, String fname, String lname, String date)
    {
    	Person p = new Person();
        
    	p.setIdPerson(id);
        p.setFirstname(fname);
        p.setLastname(lname);
        p.setBirthdate(date);
        
    	return p;
    }
    
    public static Measure makeMeasure(int id, float value, String date, Person person, MeasureType mType)
    {
    	Measure m = new Measure();
    	
    	m.setIdMeasure(id);
    	m.setValue(value);
    	m.setDate(date);
    	m.setMeasureType(mType);
    	
    	return m;
    }
    
    public static MeasureType makeMeasureType(int id, People people)
    {    	
    	return people.readMeasureType(id);
    }
}
