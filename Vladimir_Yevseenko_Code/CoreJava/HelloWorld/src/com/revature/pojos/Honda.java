package com.revature.pojos;

import com.revature.vehicles.Car;

public class Honda extends Car {
	
	private String model;
	private double mpg;
	private String description;
	
	public Honda() {
		
	}
	
	public Honda(String model, double mpg, String description) {
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

	public double getMpg() {
		return mpg;
	}

	public void setMpg(double mpg) {
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
		System.out.println("Starting");
	}

	@Override
	public void steer() {
		// TODO Auto-generated method stub
		System.out.println("Steering");
	}
	
	public double stop(double mph) {
		System.out.println("Stopping");
		for (double i=mph; i > 0; i--)
			System.out.println(i);
		return super.stop(mph);
	}
	
}
