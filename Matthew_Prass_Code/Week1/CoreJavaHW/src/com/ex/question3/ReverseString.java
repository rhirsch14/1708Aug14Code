package com.ex.question3;

public class ReverseString {

	public static void main(String[] args) {
		
		//or maybe this?
		//String str = "Revature";
		
		//does this count as a temp variable?
		//hope not...
		StringBuilder s = new StringBuilder("Revature");
		
		for(int i = s.length()-1; i >= 0; i--)
		{
			s.append(s.charAt(i));
		}
		
		System.out.println(s);
		
		//ORrrrr...
		
		String str = "Revature";
		char[] c = str.toCharArray();
		
		for(int i = c.length-1; i >= 0; i--)
		{
			System.out.print(c[i]);
		}
	}
	
	

}
