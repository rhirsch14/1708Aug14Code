package com.ex.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="INSTRUCTORS")
public class Instructor {

	@Id
	@Column(name="I_ID")
	@SequenceGenerator(name="I_ID_SEQ", sequenceName="I_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="I_ID_SEQ")
	private int id;
	
	@Column(name="INSTRUCTOR_NAME")
	private String name;

	public Instructor() {}
	
	public Instructor(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
