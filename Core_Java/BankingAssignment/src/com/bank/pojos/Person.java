package com.bank.pojos;

// NOTE: None of these strings can contain the ':' character or else it will corrupt the toString() and fromString() methods
// FIXME test for the ':' character in any field
// FIXME add the ":" as a public static delimiter string, then reference by varaible in toString and fromString
//			same for Clerk, User, and account

public class Person {

	// Each person must have a unique Social Security Number
	private final String SSN;

	// Each person must have a firstName and lastName
	private String firstName;
	private String lastName;

	// If a person has an email then it must be unique.
	private String email = "";

	private boolean deceased = false;
	
	// Note: Do not create a Person object unless the given SSN is unique
	public Person(String SSN, String firstName, String lastName) {

		this.SSN = SSN;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSSN() {
		return SSN;
	}
	
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}
	
	public boolean isDeceased() {
		return deceased;
	}

	// Turns a person into a string object
	public String toString() {
		return SSN + ":" + firstName + ":" + lastName + ":" + deceased + ":" + email;
	}
	// Creates a person object from the given string
	public static Person fromString(String str) {

		String[] splitStr = str.split(":");

		Person per = new Person(splitStr[0], splitStr[1], splitStr[2]);
		per.setDeceased(Boolean.parseBoolean(splitStr[3]));
		
		if (splitStr.length > 4)
			per.setEmail(splitStr[4]);

		return per;
	}
}