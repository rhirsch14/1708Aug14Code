package com.revature.oop_principles;

/*
 * The Food class abstracts common properties of
 * different types of food into some shared properties
 * and behaviors
 * Food itself is not something concrete so this is an abstract
 * class
 * Inheritance is demonstrated through implementation
 * of the Edible interface
 * though the Edible interface's eat() method is not implemented
 * here, all concrete subclasses will implement it
 */
public abstract class Food implements Edible {
	
	/*
	 * The usage of private variables encapsulates
	 * the instance variables of Food
	 */
	private int tastiness; /* 0 to 10 */
	
	
	/*
	 *  Encapsulation, using a getter to access instance 
	 *  variables of a class
	 *  Behavior of the class is encapsulated via methods
	 */
	public int getTastiness(){
		return tastiness;
	}
	
	public void setTastiness(int tastiness) {
		this.tastiness = tastiness;
	}
}
