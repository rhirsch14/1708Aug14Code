package Q20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonIO {
	static String filename = "src/com/ex/files/Data.txt";
	
	public List<Person> readPersons() {
		//create list to add to 
		List<Person> peopleRead = new ArrayList<Person>();
		
		//open read file stream br -> filereader ->filename
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			String line = null;
			String[] split = null;
			//while there's something to be read
			while((line = br.readLine()) != null){
				split = line.split(":");
				Person newPerson = new Person(split[0],split[1],Integer.valueOf(split[2]),split[3]);
				peopleRead.add(newPerson);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return peopleRead;
	}

}
