package com.ex.homework1JavaOOP;

// Abstract class Restaurant implements Interface Store

public abstract class Restaurant implements Store {

	@Override
	public void sellProducts() {
		// TODO Auto-generated method stub
		System.out.println("Sells Products");
	}

	public void cooksFood() {
		System.out.println("Cooks Food");
	}
	
}