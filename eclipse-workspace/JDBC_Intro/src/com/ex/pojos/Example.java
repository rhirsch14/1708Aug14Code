package com.ex.pojos;

public class Example {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String songName;
	
	public Example(int id, String firstName, String lastName, String songName) {
		this.ID = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.songName = songName;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
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
	
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	@Override
	public String toString() {
		return "Example [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", songName=" + songName
				+ "]";
	}

}