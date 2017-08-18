package com.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.bank.dao.*;
import com.bank.pojos.Account;
import com.bank.pojos.Person;
import com.bank.pojos.User;

// FIXME always check if user has been deleted
// FIXME check that SSNs are unique among those already in the data set
// FIXME ignore case when checking strings against each other like email and username
//			but not password or possibly some others

public class Service {

	DAO daoImpl = new DaoTextImpl();

	public Account validateUser(String username, String password) {

		User guy = daoImpl.readUser(username);

		if (guy == null)
			return null;

		if (guy.getAccount().isDeleted()) {
			System.out.println("This account has been deleted.");
			return null;
		}

		if (guy.getAccount().getPassword().equals(password))
			return guy.getAccount();
		else return null;
	}

	public boolean isEmailValid(String email) {
		// FIXME method stub
		return true;
	}
	// FIXME add a function to check whether SSN is valid
	public boolean isSSNAvailable(String SSN) {
		
		ArrayList<Person> personList = new ArrayList<Person>();
		
		for (Person per : personList)
			if (per.getSSN().equals(SSN)) {
				System.out.println("Someone else already has the SSN: " + SSN);
				return false;
			}
		
		return true;
	}

	public boolean isEmailAvailable(String email) {

		ArrayList<Person> peopleList = daoImpl.readAllPersons();

		if (peopleList.size() == 0)
			return true;

		for (Person per : peopleList)
			if (per.getEmail().equals(email)) {
				System.out.println("Email unavailable");
				return false;
			}

		return true;
	}

	private boolean isUsernameAvailable(String username) {

		ArrayList<User> userList = daoImpl.readAllUsers();

		for (User guy : userList) {
			if (guy.getAccount().getUsername().equals(username))
				return false;
		}

		return true;

	}

	// Attempts to create a person unless a person already exists with
	// the given SSN. Returns the person object either way
	// An email must be supplied but it can be an empty string where it will not
	// be checked for uniqueness
	public Person tryCreatePerson(String SSN, String firstName, String lastName, String email) {

		Person per = daoImpl.readPerson(SSN);

		// If person does not exist yet
		if (per == null) {

			if (isSSNAvailable(SSN)) {
				Person per2 = new Person(SSN, firstName, lastName);

				if (!(email.equals("")))
					if (isEmailAvailable(email))
						per2.setEmail(email);

				daoImpl.createPerson(per2);
				return per2;
			} else return null;
		}
		
		if (firstName.equalsIgnoreCase(per.getFirstName()) && lastName.equalsIgnoreCase(per.getLastName()))
			return per;
		else {
			System.out.println("That is not the name we have on file for that SSN.");
			return null;
		}

	}

	// FIXME check if that person already has an account
	public boolean createUser(Person per, Account acc) {

		// Check that username is unique
		if (!isUsernameAvailable(acc.getUsername())) {
			System.out.println("That username is already taken.");
			return false;
		}

		// Create an account and associated user
		User myUser = new User(per, acc);

		daoImpl.createUser(myUser);

		return true;
	}

	// Updates a person with the new person object.
	// Matches the old person with the new by SSN
	// All non-final fields of the person object
	// will be updated
	public boolean updatePerson(Person per) {

		// If the person-to-be updated on file is deceased, their records cannot be updated
		if (daoImpl.readPerson(per.getSSN()).isDeceased())
			return false;
		return daoImpl.updatePerson(per);

	}

	// FIXME this class is where I will checking updating variables to make
	// sure they are correct. i.e. valid emails, untaken username, untaken SSNs, etc
	public boolean updateUser(User guy) {

		// If the user-to-be updated on file has had their account deleted, their records cannot be updated
		if (daoImpl.readUser(guy.getAccount().getUsername()).getAccount().isDeleted())
			return false;
		return daoImpl.updateUser(guy);
	}

	public User getUser(String username) {

		return daoImpl.readUser(username);

	}

	public BigDecimal getCheckingAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getCheckingBalance();

	}

	public BigDecimal getSavingsAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getSavingsBalance();

	}

	public BigDecimal getRewardsAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getRewardsBalance();

	}

	public boolean deleteAccount(String username, boolean erase) {

		return daoImpl.deleteUser(username, erase);

	}

}