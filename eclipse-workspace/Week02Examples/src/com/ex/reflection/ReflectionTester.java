package com.ex.reflection;

public class ReflectionTester {

	public static void main(String[] args) {
		Fish fish = new Fish("Harald");
		Goldfish goldfish = new Goldfish("Goldylocks");
		System.out.println("My name is " + fish.getName() + " and I'm a " + fish.getClass().getSimpleName());
		System.out.println("My name is " + goldfish.getName() + " and I'm a " + goldfish.getClass().getSimpleName());
		System.out.println(Class.class); // wtf
	}

}