package com.ex.day2;

public class UnderstandingException {

	public static void main(String[] args) {

		int[] arr = new int[4];

		try {
			arr[0] = 1;
			arr[4] = 5;

			System.out.println(arr[4]);
		} catch (NullPointerException e) {
			System.out.println("Caught a null-pointer");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds exception caught");
			System.out.println(e.getMessage());
		}

		System.out.println("Past the try-catch block");
		System.out.println("Back into another try-catch block");
		
		try {
			duckException();
		} catch (Exception e) {
			System.out.println("The ducked exception is caught");
			System.out.println("The exception's message is: " + e.getMessage());
		}
	}
	
	static void duckException() throws Exception {
		System.out.println("We are ducking exceptions");
		throw new MyException();
	}

}