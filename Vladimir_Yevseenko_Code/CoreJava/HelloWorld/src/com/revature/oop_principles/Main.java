package com.revature.oop_principles;

public class Main {

	public static void main(String[] args) {
		/*
		 * creating an array of Food with different types of 
		 * classes that extend Food is polymorphism
		 */
		String[] p1Tops = {"pepperoni", "bacon" };
		String[] p2Tops = {"pineapple", "anchovies" };
		Food[] food = { new Burger("beef", true),
						new Burger("chicken", false),
						new Burger("pork", true),
						new Pizza(9, p1Tops),
						new Pizza(3, p2Tops) };
		
		/*
		 * Though we have different types of Food,
		 * polymorphism allows us to use the same 
		 * method on all subclasses of Food due to the
		 * Edible interface
		 */
		for (Food f: food)
			f.eat();
	}
	


}
