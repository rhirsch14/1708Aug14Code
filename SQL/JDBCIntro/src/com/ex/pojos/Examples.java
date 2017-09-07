package com.ex.pojos;

public class Examples {

	int id;
	String fn;
	String ln;
	String song;
	
	public Examples() {};
	
	public Examples(int id, String fn, String ln, String song) {
		super();
		this.id = id;
		this.fn = fn;
		this.ln = ln;
		this.song = song;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public String getLn() {
		return ln;
	}

	public void setLn(String ln) {
		this.ln = ln;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	@Override
	public String toString() {
		return "Examples [id=" + id + ", fn=" + fn + ", ln=" + ln + ", song=" + song + "]";
	}
	
}