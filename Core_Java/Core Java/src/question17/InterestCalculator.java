package question17;

import java.util.Scanner;

/*
 * Q17. Write a program that calculates the simple interest on
 * the principal, rate of interest and number of years provided
 * by the user. Enter principal, rate and time through the console
 * using the Scanner class.

	Interest = Principal* Rate* Time

 */

public class InterestCalculator {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		try {
			
			// Take in the three inputs
			double principle, rate, years;
			System.out.println("This will calculate interest based on principle, rate, and time");
			System.out.println("Enter the principle: ");
			principle = Integer.parseInt(scan.next());
			System.out.println("Enter the rate/year in percentage: ");
			rate = Integer.parseInt(scan.next());
			System.out.println("Enter the number of years: ");
			years = Integer.parseInt(scan.next());
			
			// Print out the calculated simple interest
			System.out.println("The simple interest is " + principle*(rate/100)*years);
			
		} catch (NumberFormatException e) {
			System.out.println("That input was supposed to be a number");
		}
		
		scan.close();
	}

}