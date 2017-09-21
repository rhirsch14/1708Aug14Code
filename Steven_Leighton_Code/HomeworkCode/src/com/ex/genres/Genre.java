package com.ex.genres;

import com.ex.interfaces.Readable;

/*
 * Abstraction:
 * 		using an abstract class for a genre,
 * 		which is an abstraction from books
 * 
 * Inheritance:
 * 		implements from the interface Readable 
 * 		to show that something is able to be read
 */
public abstract class Genre implements Readable{
	public String genre;
	
	public Genre(String s){
		this.genre = s;
	}
		
	public String getGenre() {
		System.out.println("The genre is: " + genre);
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void open(){
		System.out.println("You opened the imaginary book.");
		
	}
	
	public void close(){
		System.out.println("You closed the imaginary book.");
	}

	public int readBook(int a) {
		System.out.println("You want to read, but don't have a book.");
		return 0;
	}
}