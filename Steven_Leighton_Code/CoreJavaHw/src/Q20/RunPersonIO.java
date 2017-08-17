package Q20;

import java.util.List;

public class RunPersonIO {
	static PersonIO io;
	
	public static void main(String[] args) {
		readPersons();
	}
	
	static void readPersons(){
		
		//setup personIO and get persons
		io = new PersonIO();
		List<Person> people = io.readPersons();
		
		//print out in proper format
		for (Person person : people) {
			System.out.println("Name: " + person.getFirstname() +" "+person.getLastname());
			System.out.println("Age: "+ person.getAge());
			System.out.println("State: " + person.getState() + " State");
		}
	}
}
