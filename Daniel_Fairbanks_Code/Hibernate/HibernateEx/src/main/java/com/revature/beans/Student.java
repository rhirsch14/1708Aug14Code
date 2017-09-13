package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="STUDENTS")
public class Student {

	@Id
	@Column(name="STUDENT_ID")
	@SequenceGenerator(name="STUDENT_ID_SEQ", sequenceName="STUDENT_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENT_ID_SEQ")
	private int id;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TRANSCRIPT_ID")
	private Transcript transcript;
	
	public Student(int id, String firstname, String lastname, String email, Transcript transcript) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.transcript = transcript;
	}

	public Student() {
		super();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email+ "]";
	}
}