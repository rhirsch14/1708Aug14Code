package com.ex.question15;

public class MathTest {

	public static void main(String[] args) {
		
		//interface implemented
		Mathing m = new Mathing();
		
		float x = 20;
		float y = 2;
		
		//testing out implementation
		System.out.println(m.add(x, y));
		System.out.println(m.subtract(x, y));
		System.out.println(m.multiply(x, y));
		System.out.println(m.divide(x, y));

	}

}
