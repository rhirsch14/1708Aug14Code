package com.bank.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Person {
	
	@Id
	@Column(name = "PERSON_ID")
	@SequenceGenerator(name = "PERSON_ID_SEQ", sequenceName = "PERSON_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ID_SEQ")
	protected int personId;
	
	// Each person must have a firstName and lastName
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;

	// A person must have an email
	@Column(nullable = false)
	private String email = "";
	
	@Column
	private LocalDate birthDate = null;

	@Column
	private boolean deceased = false;
	
	public Person(String firstName, String lastName, String email) {

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
	
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}
	
	public boolean isDeceased() {
		return deceased;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDate=" + birthDate + ", deceased=" + deceased + "]";
	}


}