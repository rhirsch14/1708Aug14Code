package com.ex.strings;

public class StringStuff {
	public static void main(String[] args) {
		/*
		 * Strings are immutable
		 * once they are created, cant be changed
		 * The object created as a string is stored in the string pool
		 * 
		 * Every immutable object in java is "thread safe",
		 * meaning they cant be manipulated by 2 threads at the same time
		 */
		
		String a = new String();
		a = "Hello";
		String b = "hello";
		String c = "hello";
		String d = b;
		String e = new String("hello");
		
		System.out.println(a==b);
		System.out.println(a==d);
		System.out.println(a==e); //not true because == compares entities not value
		System.out.println(a.equalsIgnoreCase(e)); //compares value ignoring case sensitivity
		
		
	}
}