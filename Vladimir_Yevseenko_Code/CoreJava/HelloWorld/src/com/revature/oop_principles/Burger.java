package com.revature.oop_principles;

/*
 *  Inheritance is displayed
 */
public class Burger extends Food {
	/*
	 * private variables for encapsulation
	 */
	private String meat;
	private boolean pickles;
	
	public Burger(String meat, boolean pickles) {
		this.meat = meat;
		this.pickles = pickles;
		/*
		 * using inherited tastiness instance variable
		 * and setTastiness() method
		 */
		setTastiness(calculateTastiness());
	}
	
	/* 
	 * Example of encapsulation, since Burger implements Edible
	 * we provide an implementation of eat() and don't need to worry
	 * that this type of food is a Burger, but specifically
	 * that it has the eat() method
	 */
	public void eat() {
		System.out.println("Mmmm, a " + meat  + " burger" +
				(pickles ? " with pickles on it." : "."));
	}
	
	public int calculateTastiness() {
		int tastiness;
		switch (meat.toLowerCase()) {
			case "beef": tastiness = 7; break;
			case "chicken": tastiness = 5; break;
			case "pork": tastiness = 3; break;
			default: tastiness = 2;
		}
		if (pickles)
			tastiness++;
		return tastiness;
	}
}
