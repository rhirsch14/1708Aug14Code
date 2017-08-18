package com.bank.main;

import java.math.BigDecimal;
import java.util.Scanner;

import com.bank.pojos.Account;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.Clerk;
import com.bank.pojos.Person;
import com.bank.pojos.User;
import com.bank.service.Service;

/*
 * Requirements:
 * 	User can...
 * 		- login
 * 		- create an account
 * 		- logout
 * 		- withdraw or deposit funds
 * 		- view own account balance
 * 		- edit own information
 * 
 * Tech Specs
 * 	- core java, file i/o
 */

public class RunBank {

	private static Scanner scan = new Scanner(System.in);
	private static Service bankService = new Service();
	// Keeps track of the account currently logged into the system
	private static Account accountLoggedIn = null;;

	private static String bankName = "People's Bank of Earth";

	// Implementation to actually run bank application
	public static void main(String[] args) {

		System.out.println("Hello. Welcome to the automating banking system for " +
				bankName + ".");

		bankingSystem();

		System.out.println("Thank you for using the automating banking system. Have a wonderful day.");

		scan.close();

	}

	private static void bankingSystem() {

		bankloop:
			while (true) {
				System.out.println();	// Formatting
				if (accountLoggedIn != null)
					displayBasicAccountInfo();
				System.out.println("Please choose one of the following commands:");
				System.out.println("login, logout, create account, withdraw, deposit, view account, edit account, quit");

				String input = "";		// used in a few of the case statements
				switch(scan.nextLine().toLowerCase()) {
				case "login":
					if (accountLoggedIn != null) {
						System.out.println("A user is already logged in.");
						continue;
					}

					login();

					break;
				case "logout":
					if (accountLoggedIn == null) {
						System.out.println("You must log in before you can log out.");
						continue;
					}

					logout();

					break;
				case "create account":
					if (accountLoggedIn != null) {
						System.out.println("Please log out before creating a new account.");
						continue;
					}

					displayTermsAndConditions();
					System.out.println("Do you accept these conditions? yes/no");
					
					if (scan.nextLine().equalsIgnoreCase("yes"))
						if (!createAccount())
							System.out.println("Account creation failed.");
						else {
							System.out.print("Account created, welcome to " + bankName + ".");
							System.out.println(" Please log in to view or modify your account.");
						}

					break;
				case "withdraw":
					if (accountLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					System.out.println("Would you like to withdraw from checking or transfer money from checking to savings?");
					input = scan.nextLine().toLowerCase();
					if (input.contains("checking"))
						transferFunds(true);
					else if (input.contains("saving"))
						transferFunds(false);
					else System.out.println("Input was not recognized. Valid entries are \"checking\" and \"saving\"");

					break;
				case "deposit":
					if (accountLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					System.out.println("Would you like to deposit to checking or to savings?");
					input = scan.nextLine().toLowerCase();
					if (input.contains("checking"))
						deposit(true);
					else if (input.contains("saving"))
						deposit(false);
					else System.out.println("Input was not recognized. Valid entries are \"checking\" and \"saving\" in all lower case");

					break;
				case "view account":
					if (accountLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					viewAccount();

					break;
				case "edit account":
					if (accountLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					editAccount();

					break;
				case "quit":
					break bankloop;
				default:
					System.out.println("Command not recognized. Please try again.");
				}	// end case statement

			}	// end bankloop

	}

	private static void displayBasicAccountInfo() {
		System.out.println("Logged in as: " + accountLoggedIn.getUsername() +
				", Account type: " + accountLoggedIn.getType());
	}

	// Returns true if login was successful
	private static void login() {
		System.out.print("Enter username: ");
		String username = scan.nextLine();
		System.out.print("Enter password: ");
		String password = scan.nextLine();

		Account acc = bankService.validateUser(username, password);

		if (acc == null) {
			System.out.println("Login failed, incorrect username and password combination.");
		} else {
			System.out.println("Login successful, welcome " + acc.getUsername());
			accountLoggedIn = acc;
		}
	}

	// Returns true if logout was successful
	private static boolean logout() {

		System.out.println("Logout successful.");
		accountLoggedIn = null;

		return true;
	}

	// Returns true if an account was successfully created
	private static boolean createAccount() {

		// Get information from user to find/create a person
		System.out.print("Enter your first name: ");
		String firstName = scan.nextLine();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine();
		System.out.print("Enter your Social Security Number: ");
		String SSN = scan.nextLine();

		Person per = bankService.tryCreatePerson(SSN, firstName, lastName, "");
		if (per == null)
			return false;
		
		// Set email. email is not required to be a person but it
		// is required to have an account
		if (!setEmail(per))
			return false;

		// Get information from user to create an account
		System.out.print("Enter your username: ");
		String username = scan.nextLine();
		System.out.print("Enter your password: ");
		String password = scan.nextLine();

		return bankService.createUser(per, new Account(per, username, password, accountType.BRONZE));
	}

	private static boolean transferFunds(boolean checking) {
		BigDecimal balance = null;
		String accountStr = null;

		if (checking) {
			balance = bankService.getCheckingAccountBalance(accountLoggedIn.getUsername());
			accountStr = "Checking";
		}
		else {		// Savings account
			balance = bankService.getSavingsAccountBalance(accountLoggedIn.getUsername());
			accountStr = "Savings";
		}

		try {

			System.out.print("Current funds: $" + balance.toString() +
					"\nEnter an ammount to be withdrawn from your " + accountStr + " account: ");

			BigDecimal withdrawAmmount = new BigDecimal(scan.nextLine());

			if (withdrawAmmount.abs() != withdrawAmmount) {
				System.out.println("You cannot withdraw a negative quantity.");
				return false;
			}

			switch(balance.compareTo(withdrawAmmount)) {
			case -1:
				System.out.println("You cannot withdraw more than your " + accountStr + " balance");
				return false;
			default:

				User guy = bankService.getUser(accountLoggedIn.getUsername());

				BigDecimal finalBalance = balance.subtract(withdrawAmmount);
				if (checking)
					guy.getAccount().setCheckingBalance(finalBalance);
				else {		// Savings account
					guy.getAccount().setSavingsBalance(finalBalance);
					BigDecimal checkingBalance = guy.getAccount().getCheckingBalance();
					guy.getAccount().setCheckingBalance(checkingBalance.add(withdrawAmmount));
				}

				if (!bankService.updateUser(guy)) {
					System.out.println("Transaction failed.");
					return false;
				}

				System.out.println("$" + withdrawAmmount.toString() + " withdrawn from " + accountStr);
				System.out.println("$" + guy.getAccount().getCheckingBalance().toString() + " new checking balance");

				return true;
			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}

	}

	// FIXME there should be a maximum limit to how much can be deposited
	private static boolean deposit(boolean checking) {
		BigDecimal balance = null;
		String accountStr = null;

		if (checking) {
			balance = bankService.getCheckingAccountBalance(accountLoggedIn.getUsername());
			accountStr = "Checking";
		}
		else {		// Savings account
			balance = bankService.getSavingsAccountBalance(accountLoggedIn.getUsername());
			accountStr = "Savings";
		}

		try {

			System.out.print("Current funds: $" + balance.toString() +
					"\nEnter an ammount to be deposited to your " + accountStr + " account: ");

			BigDecimal depositAmmount = new BigDecimal(scan.nextLine());

			if (depositAmmount.abs() != depositAmmount) {
				System.out.println("You cannot deposit a negative quantity.");
				return false;
			}

			User guy = bankService.getUser(accountLoggedIn.getUsername());

			BigDecimal finalBalance = balance.add(depositAmmount);
			if (checking)
				guy.getAccount().setCheckingBalance(finalBalance);
			else 		// Savings account
				guy.getAccount().setSavingsBalance(finalBalance);

			if (!bankService.updateUser(guy)) {
				System.out.println("Transaction failed.");
				return false;
			}

			System.out.println("$" + depositAmmount.toString() + " deposited to " + accountStr);
			System.out.println("$" + guy.getAccount().getCheckingBalance().toString() + " new checking balance");

			return true;


		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}
	}
	private static boolean viewAccount() {
		Account acc = bankService.getUser(accountLoggedIn.getUsername()).getAccount();

		System.out.println("Account created on : " + acc.getAccountOpenedDate());
		System.out.println("Account id number: " + acc.getAccountId());
		System.out.println("Savings account interest rate: " + acc.getInterestRate()*100 + "%");
		System.out.println("Rewards account reward rate: " + acc.getInterestRate()*100 + "%");
		System.out.println("Checking account balance: " + acc.getCheckingBalance().toString());
		System.out.println("Savings account balance: " + acc.getSavingsBalance().toString());
		System.out.println("Rewards account balance: " + acc.getRewardsBalance().toString());

		return true;
	}

	private static void editAccount() {

		// FIXME add option to upgrade account, but requires credentials from a clerk (admin access)
		
		System.out.println("Would you like to change your password or delete your account?");
		String str = scan.nextLine().toLowerCase();
		if (str.contains("password")) {

		} else if (str.contains("delete")) {
			System.out.println("WARNING: This canot be undone!");
			System.out.println("Our bank system does not currently offer the option to restore a delted account" + 
					"\n OR for a previous customer to create a new account.");
			System.out.println("Are you sure you with to delete your account? yes/no");

			String confirmation = scan.nextLine().toLowerCase();

			if (confirmation.contains("yes") || confirmation.equalsIgnoreCase("y")) {

				logout();
				if (bankService.deleteAccount(accountLoggedIn.getUsername(), false))
					System.out.println("Account has ben DELETED. " + bankName + " thanks your for your patronage.");
				else System.out.println("Account could not be deleted. Please see an administrator.");
			}
			else System.out.println("Account NOT deleted. " + bankName + " thanks you for your continued patronage.");

		}

	}

	private static void displayTermsAndConditions() {
		System.out.println("By creating an account with " + bankName + " You agree to the following terms and conditions\n" +
				bankName + " reserves the right to collect and use your personal data for any purpose.\n" +
				bankName + " will not be responsible for any loss or damage of property caused by use of \nservices provided by " + bankName + ".\n" + 
				"The user of services provided by " + bankName + " relinquish all rigts to sue or otherwise \ntake " +
				bankName + " or a representative of " + bankName + " to a court of law for any \nreason regarding services provided by " +
				bankName + " or property stored on the property of \n" + bankName);
	}

	// Returns false if a valid email was not supplied
	private static boolean setEmail(Person per) {

		// If the person already has an email
		if (!per.getEmail().equals(""))
			return true;
		
		// get email
		System.out.print("Enter your email address: ");
		String email = scan.nextLine();

		if (bankService.isEmailValid(email))
			if (bankService.isEmailAvailable(email)) {
				per.setEmail(email);
				bankService.updatePerson(per);
				return true;
			}

		return false;
	}

}