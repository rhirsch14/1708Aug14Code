package com.ex.designpatterns;

import java.util.Scanner;

public class Testing {


	public static void main(String[] args) {

		/*
		 * must use getInstance() method to instantiate
		 * singleton class
		 */
		Singleton singleton = Singleton.getInstance();
		singleton.hello();
		
		Singleton anotherone = Singleton.getInstance();
		singleton.count = 10;
		
		System.out.println(anotherone.count);
		
		
		/*
		 * Factory design pattern 
		 * here, we create an object without exposing
		 * creation logic to the client. We refer to the
		 * newly created object using a common interface
		 */
		
		ToolBoxFactory factory = new ToolBoxFactory();
		Scanner in = new Scanner(System.in);
		
		
		System.out.println("hey what tool?");
		String tool = in.nextLine();
		
		ToolBox t = factory.workWithTool(tool);
		System.out.println(t.workWithTool());
	}
}
