package com.ex.pojos;

import static java.lang.Math.sqrt;

import java.io.Serializable;

import com.ex.vehicles.Car;

public class Honda extends Car implements Serializable {
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 1L;
	
	private String model;
	private int mpg;
	private String description;
	
	public Honda(){}
	
	public Honda(String model, int mpg, String description) {
		super();
		this.model = model;
		this.mpg = mpg;
		this.description = description;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getMpg() {
		return mpg;
	}

	public void setMpg(int mpg) {
		this.mpg = mpg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void steer() {
		// TODO Auto-generated method stub
		
	}
	
	// Overriding stop method
	public double stop(double mph) {
		System.out.println("The Honda is stopping.");
		double seconds = sqrt(mph);
		
		for (double i = mph; i >= 0; i--)
			System.out.println(i);
		
		return seconds/2;
	}
	
	public String toString() {
		return "Honda: " + model + ", mpg: " + mpg + ", description: " + description;
	}

}