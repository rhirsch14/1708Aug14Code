package com.bank.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

// Manages bank account information
// Has access to every account
// Cannot own an account

public class Clerk extends Person {

	public static transient double MINIMUM_WAGE = 8.85;

	@Id
	@Column(name = "CLERK_ID")
	@SequenceGenerator(name = "CLERK_ID_SEQ", sequenceName = "CLERK_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLERK_ID_SEQ")
	private int clerkId;

	@Column(nullable = false)
	private String password;

	@Column
	private final LocalDate dateHired;

	@Column
	private double hourlyWage;

	// Shows whether the employee is currently working for the company
	// Turns false when an employee is let go
	@Column
	private boolean hired = true;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	public Clerk(Person person, LocalDate dateHired, String password, double hourlyWage) {
		super(person.getFirstName(), person.getLastName(), person.getEmail());

		this.person = person;
		this.password = password;
		this.dateHired = dateHired;
		this.hourlyWage = hourlyWage;
		this.hired = true;
	}
	public Clerk(Person person, String password) {
		super(person.getFirstName(), person.getLastName(), person.getEmail());

		this.person = person;
		this.password = password;
		this.hired = true;
		this.hourlyWage = MINIMUM_WAGE;
		this.dateHired = LocalDate.now();
	}

	public double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public int getclerkId() {
		return clerkId;
	}
	
	public void setClerkId(int id) {
		this.clerkId = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateHired() {
		return dateHired;
	}

	public boolean isHired() {
		return hired;
	}

	public void setHired(boolean hired) {
		this.hired = hired;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Clerk [clerkId=" + clerkId + ", password=" + password + ", dateHired=" + dateHired
				+ ", hourlyWage=" + hourlyWage + ", hired=" + hired + ", person=" + person + "]";
	}

}