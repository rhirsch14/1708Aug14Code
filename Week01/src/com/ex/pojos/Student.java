package com.ex.pojos;

public class Student {
	
	private String firstName;
	private String lastName;
	private String email;
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, String email) {
		if (firstName == null) {
			throw new IllegalArgumentException("First name cannot be null");
		}
		if (lastName == null) {
			throw new IllegalArgumentException("Last name cannot be null");
		}
		if (email == null) {
			throw new IllegalArgumentException("Email cannot be null");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	

}