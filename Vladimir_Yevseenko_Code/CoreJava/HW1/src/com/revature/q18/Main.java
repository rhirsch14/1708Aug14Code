package com.revature.q18;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ConcreteStrOps strOps = new ConcreteStrOps();
		try (Scanner s = new Scanner(System.in)) {
			System.out.print("Enter a string: ");
			String str = s.nextLine();
			System.out.println(str + (strOps.hasUpperCase(str) ? " has " : " does not have ") + "upper case characters");
			System.out.print("Enter a string: ");
			str = s.nextLine();
			System.out.println(str + " to uppercase is: " + strOps.toUpperCase(str));
			System.out.print("Enter a string integer: ");
			str = s.nextLine();
			System.out.println("Parsed to an integer plus 10: " + strOps.toIntPlusTen(str));
		} catch (NumberFormatException ex) {
			System.out.print("That is not an integer");
			System.exit(1);
		}
	}
}