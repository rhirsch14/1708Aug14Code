package com.example.helloworld;

public interface Drivable {

	public void steer();
	
	public void accelerate(double rate);
	
	public abstract double stop(double mph);
	
}