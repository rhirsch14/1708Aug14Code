package com.ex.beans;

import org.springframework.stereotype.Component;

@Component
public class Student {
	
	public void study() {
		System.out.println("The studying never ends");
	}
	
	public void exist() {
		System.out.println("Existence is pain");
	}
	
	public void eat() {
		System.out.println("Ramen again tonight");
	}
	
	public void work() {
		System.out.println("The perpetual state of work");
	}
	
	public void takeTime() throws InterruptedException {
		int randomMiliseconds = (int)(Math.random() * 1000);
		Thread.sleep(randomMiliseconds);
	}

}