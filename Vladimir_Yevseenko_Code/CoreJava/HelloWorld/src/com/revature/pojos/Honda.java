package com.revature.pojos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.revature.vehicles.Car;

public class Honda extends Car implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020593938552833565L;
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
	
	public void serialize(String file) {
		try (ObjectOutputStream out = 
				new ObjectOutputStream(
						new FileOutputStream(file))) {
			out.writeObject(this);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Honda deserialize(String file) {
		try (ObjectInputStream in =
				new ObjectInputStream(
						new FileInputStream(file))) {
			return (Honda) in.readObject();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}