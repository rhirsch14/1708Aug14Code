package com.ex.books;

import genres.Genre;

/*
 * Inheritance using extends from a Genre
 */
public class Book extends Genre{

	/*
	 * Encapsulation:
	 * 		private accessors
	 * 		getters/setters
	 */
	private String title;
	private String author;
	private String coverDescription;
	private int numberOfPages;
	private int onPage;
	private boolean doneReading;
	
	public Book(){
		super("No genre given");
		this.title = "no title given";
		this.author = "no author given";
		this.coverDescription = "no description given";
		this.numberOfPages = 0;
		this.onPage = 0;
		doneReading = false;
	}
	
	public Book(String title, String author, String coverDescription, int numberOfPages, String genre) {
		super(genre);
		this.title = title;
		this.author = author;
		this.coverDescription = coverDescription;
		this.numberOfPages = numberOfPages;
		this.onPage = 0;
		doneReading = false;
	}


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public String getCoverDescription() {
		return coverDescription;
	}



	public void setCoverDescription(String coverDescription) {
		this.coverDescription = coverDescription;
	}



	public int getNumberOfPages() {
		return numberOfPages;
	}



	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}


	public int getOnPage() {
		return onPage;
	}



	public void setOnPage(int onPage) {
		this.onPage = onPage;
	}



	public boolean isDoneReading() {
		return doneReading;
	}



	public void setDoneReading(boolean doneReading) {
		this.doneReading = doneReading;
	}


	@Override
	public void open(){
		if(this.title.equals("no title given")){
			super.open();
		}
		else{
			System.out.println("You opened the book: " + title);
		}
	}
	
	@Override
	public void close(){
		if(this.title.equals("no title given")){
			super.close();
		}
		else{
			System.out.println("You closed the book: " + title);
		}
	}
	
	/*
	 * Read number of pages based on current page number: onPage
	 * Assume input is positive number.
	 * 
	 * Polymorphism:
	 * 		overriding a method from the abstract class Genre
	 */
	@Override
	public int readBook(int a) {
		//check if finished book, else add to pages read
		if(a + onPage >= numberOfPages){
			onPage = numberOfPages;
			doneReading = true;
		}
		else{
			onPage += a;
		}
		
		System.out.println("You're now on page: " + onPage);
		return onPage;
	}
	
	/*
	 * Read between two page numbers.
	 * Set onPage to last page.
	 * Assume input is correct.
	 */
	public int readBook(int a, int b){
		if(b >=  numberOfPages){
			onPage = numberOfPages;
			doneReading = true;
		}
		else{
			onPage = b;
		}
		System.out.println("You're now on page: " + onPage);
		return onPage;
	}

}
