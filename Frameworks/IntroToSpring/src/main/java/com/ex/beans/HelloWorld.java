package com.ex.beans;

public class HelloWorld {

	private String message;
	
	
	public HelloWorld() {
		System.out.println("In empty constructor");
	}
	public HelloWorld(String test) {
		System.out.println(test);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public void getMessage() {
		System.out.println("Your message is: " + message);
	}
	
	public void init() {
		System.out.println("Initializing bean");
	}
	public void destroy() {
		System.out.println("Bean has been destroyed");
	}

}