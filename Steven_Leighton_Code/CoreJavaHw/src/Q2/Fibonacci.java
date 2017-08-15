package Q2;

import java.util.Scanner;

public class Fibonacci {

	public static void main(String[] args) {
//		Scanner scan = new Scanner(System.in);
//		System.out.println("Enter Fibonacci Number: ");
//		int i = scan.nextInt();
//		
		fib(25);
	}

	private static void fib(int i) {
		if(i >= 1 ) System.out.println("Fib 1: 0"); //print out if first or higher number
		if(i >= 2 ) System.out.println("Fib 2: 1"); //print out if second or higher number
		if(i > 2 ){
			int prev1 = 0;
			int prev2 = 1;
			//loops through rest of fib numbers
			for(int j = 1; j < i+1; j++){
				//Fib num = previous two fib nums
				int fibNum = prev1 + prev2;
				//print out
				System.out.println("Fib " + j + ": " + fibNum);
				//set previous
				prev1 = prev2;
				prev2 = fibNum;
			}
		}
	}

}
