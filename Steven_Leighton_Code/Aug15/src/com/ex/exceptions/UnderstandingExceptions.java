package com.ex.exceptions;

public class UnderstandingExceptions {

	public static void main(String[] args) {
		
		int[] arr = new int[4];
		
		//try something
		try {
			//System.out.println(arr[4]); first catch
			
			String s = null;
			s.length();
			
			
		} catch (ArrayIndexOutOfBoundsException e) { //do this if caught array bounds excp.
			System.out.println("caught out of bounds");
			// doesnt print this: e.printStackTrace();
		} catch (NullPointerException e){
			System.out.println("caught null pointer");
		}
		finally{
			System.out.println("finally block");
		}
		
		System.out.println("passed");
		
		try {
			duckException();
		} catch (MyException me) {
			System.out.println(me.getMessage());
//			System.out.println("caught thrown exception from method");
//			e.printStackTrace();
		}
	}
	
	//example of a method that throws back an exception
	static void duckException() throws MyException{
		System.out.println("propogating exception");
		throw new MyException();
	}
	
}
