package com.revature.helloworldpackage;

public class HelloWorld extends HelloWorldAbstract implements HelloWorldInterface {
	public static void main(String[] args) 
	{
		new HelloWorld().printHelloWorld();
	}
	
	public void printHelloWorld()
	{
		System.out.println("Hello world!");
	}
}