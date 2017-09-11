package com.ex.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STUDENT")
public class Student {

	@Id
	@Column(name="STUDENT_ID")
	@SequenceGenerator(name="STUDENTID_SEQ", sequenceName="STUDENTID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENTID_SEQ")
	private int id;
	
	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column
	private LocalDate birthDay;

	@Column
	private String favoriteColor;
	
	public Student(int id, String firstName, String lastName, String email, LocalDate birthDay, String favoriteColor) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDay = birthDay;
		this.favoriteColor = favoriteColor;
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public LocalDate getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	public String getFavoriteColor() {
		return favoriteColor;
	}
	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDay=" + birthDay + ", favoriteColor=" + favoriteColor + "]";
	}
	
}