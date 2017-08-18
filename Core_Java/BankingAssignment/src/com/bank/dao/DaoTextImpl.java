package com.bank.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.bank.pojos.Clerk;
import com.bank.pojos.Person;
import com.bank.pojos.User;

// FIXME ensure that users/people/clerks can be created even if the text files are empty
// FIXME after it is functioning try replacing the basic CRUD operations with generics
//		so that there is only one copy of each of the CRUD operations but it operates
//		generically on either a person, clerk, or user

public class DaoTextImpl implements DAO {
	
	private static String personFilename = "src/com/bank/files/persons.txt";
	private static String userFilename = "src/com/bank/files/users.txt";
	private static String clerkFilename = "src/com/bank/files/clerks.txt";
	
//------------------------------------------------------------------------------
// Persons 
//------------------------------------------------------------------------------
	
	@Override
	public boolean createPerson(Person per) {
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(personFilename, true))) {

			bw.write(per.toString() + "\n");
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Person readPerson(String SSN) {
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(personFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				Person per = Person.fromString(line);
				
				if (per.getSSN().equals(SSN))
					return per;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public boolean updatePerson(Person per) {
		boolean updated = false;
		
		ArrayList<Person> personList = readAllPersons();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(personFilename, false))) {

			for (Person myPer : personList) {
				if (myPer.getSSN().equals(per.getSSN())) {
					updated = true;
					bw.write(per.toString() + "\n");
				} else bw.write(myPer.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	// if erase == true then delete the record entirely
	// if erase == false then mark the record as deleted but keep it in memory
	@Override
	public boolean deletePerson(String SSN, boolean erase) {
		boolean deleted = false;
		
		ArrayList<Person> personList = readAllPersons();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(personFilename, false))) {

			for (Person myPer : personList) {
				if (myPer.getSSN().equals(SSN)) {
					deleted = true;
					
					if (erase)
						;	// This person is not copied over and therefore is deleted
					else {
						// Mark person as deleted (deceased) but keep their records
						myPer.setDeceased(true);
						bw.write(myPer.toString() + "\n");
					}
				} else bw.write(myPer.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deleted;
	}
	
	@Override
	public ArrayList<Person> readAllPersons() {
		ArrayList<Person> personList = new ArrayList<Person>();
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(personFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				Person per = Person.fromString(line);
				
				personList.add(per);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return personList;
	}
	
//------------------------------------------------------------------------------
// Users
//------------------------------------------------------------------------------

	@Override
	public boolean createUser(User guy) {
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(userFilename, true))) {

			bw.write(guy.toString() + "\n");
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public User readUser(String username) {
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(userFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				User guy = User.fromString(line);
				
				if (guy.getAccount().getUsername().equals(username))
					return guy;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateUser(User guy) {
		boolean updated = false;
		
		ArrayList<User> userList = readAllUsers();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(userFilename, false))) {

			for (User myGuy : userList) {
				if (myGuy.getAccount().getUsername().equals(guy.getAccount().getUsername())) {
					updated = true;
					bw.write(guy.toString() + "\n");
				} else bw.write(myGuy.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return updated;
	}

	// if erase == true then delete the record entirely
	// if erase == false then mark the record as deleted but keep it in memory
	@Override
	public boolean deleteUser(String username, boolean erase) {
		boolean deleted = false;
		
		ArrayList<User> userList = readAllUsers();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(userFilename, false))) {

			for (User myGuy : userList) {
				if (myGuy.getAccount().getUsername().equals(username)) {
					deleted = true;
					
					if (erase)
						;		// This user is not copied over and therefore is deleted
					else {
						// Mark account as deleted but keep the records
						myGuy.getAccount().setDeleted(true);
						bw.write(myGuy.toString() + "\n");
					}
				} else bw.write(myGuy.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deleted;
	}

	@Override
	public ArrayList<User> readAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(userFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				User guy = User.fromString(line);
				
				userList.add(guy);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
//------------------------------------------------------------------------------
// Clerks
//------------------------------------------------------------------------------

	@Override
	public boolean createClerk(Clerk cler) {
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(clerkFilename, true))) {

			bw.write(cler.toString() + "\n");
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Clerk readClerk(int employeeId) {
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(clerkFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				Clerk cler = Clerk.fromString(line);
				
				if (cler.getEmployeeId() == employeeId)
					return cler;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateClerk(Clerk cler) {
		boolean updated = false;
		
		ArrayList<Clerk> clerkList = readAllClerks();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(clerkFilename, false))) {

			for (Clerk myCler : clerkList) {
				if (myCler.getEmployeeId() == cler.getEmployeeId()) {
					updated = true;
					bw.write(cler.toString() + "\n");
				} else bw.write(myCler.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return updated;
	}

	// if erase == true then delete the record entirely
	// if erase == false then mark the record as deleted but keep it in memory
	@Override
	public boolean deleteClerk(int employeeId, boolean erase) {
		boolean deleted = false;
		
		ArrayList<Clerk> clerkList = readAllClerks();
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(clerkFilename, false))) {

			for (Clerk myCler : clerkList) {
				if (myCler.getEmployeeId() == employeeId) {
					deleted = true;
					
					if (erase)
						;	// This Clerk is not copied over and is therefore deleted
					else {
						// Mark person as deleted (no longer hired) but keep their records
						myCler.setHired(false);
						bw.write(myCler.toString() + "\n");
					}
				} else bw.write(myCler.toString() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deleted;
	}

	@Override
	public ArrayList<Clerk> readAllClerks() {
		ArrayList<Clerk> clerkList = new ArrayList<Clerk>();
		
		// try-with-resources block
		try(BufferedReader br = new BufferedReader(
				new FileReader(clerkFilename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				Clerk cler = Clerk.fromString(line);
				
				clerkList.add(cler);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return clerkList;
	}	
	
	
}