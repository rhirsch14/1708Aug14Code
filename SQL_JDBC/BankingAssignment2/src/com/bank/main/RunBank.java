package com.bank.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.bank.pojos.Account;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.BankUser;
import com.bank.pojos.Person;
import com.bank.service.Service;

/*
 * Requirements:
 * 	A bank_user can...
		Create account(s)
		Withdraw
		Deposit
		Close account(s)
		View account(s)
		Edit account(s) - change first/last name, password, email
		Transfer funds between a single user's accounts
 * 
 * and apparently I'm not allowed to keep track of transactions now unless I'm using logging
 * 
 * Tech Specs
 * 	- core java, file i/o
 */

// TODO add clerk functionality
//			View all transactions, Users, Persons
//			add new clerks (later there will have to be a hierarchy of clerks where only high-level clerks can add lower-level clerks)

//			Change a user's username to an available username
//			upgrade an account
//			Provide verification for deleting an account
//		Clerks have two ways to log in: to access the secret clerk menu, and a temporary log in which logs out after verification for doing admin things like changing a user's username
// TODO add a password recovery option. Either with optional security questions or somehow
//			simulate sending a security code to an email address. Or simply have a clerk oversee a password reset

public class RunBank {

	private static Scanner scan;
	private static Service bankService = new Service();
	// Keeps track of the account currently logged into the system
	private static Integer userIdLoggedIn = null;
	// Keeps track of the employee ID of the clerk currently logged in
	private static int clerkIDLoggedIn = 0;

	private static String bankName = "People's Bank of Earth";

	// Implementation to actually run bank application
	public static void main(String[] args) {

		scan = new Scanner(System.in);
		
		try {
			
			if (Service.isConnected()) {
				System.out.println("Hello. Welcome to the automating banking system for " + bankName + ".");

				bankingSystem();
				
				System.out.println("Thank you for using the automating banking system. Have a wonderful day.");
			}
				
			else System.out.println("Error connecting to database.");

		} catch (NoSuchElementException e) {
			System.out.println("Bad input. Exiting system...");
		} catch (Exception e) {
			System.out.println("Unforseen error! Exiting system...\n");
			e.printStackTrace();
		}

		
		scan.close();

	}

	private static void bankingSystem() {

		bankloop: while (true) {
			System.out.println(); // Formatting
			if (userIdLoggedIn != null) {
				displayBasicAccountInfo();
				System.out.println("Please choose one of the following commands:");
				System.out.println("logout, create additional account, withdraw, transfer funds, deposit, view account, edit account, quit");
			} else {
				System.out.println("Please choose one of the following commands:");
				System.out.println("login, create account, quit");
			}

			String input = ""; // used in a few of the case statements
			Account acc = null;	// used in a few of the case statements
			switch (scan.nextLine().trim().toLowerCase()) {
			case "login":
			case "log in":
				if (userIdLoggedIn != null) {
					System.out.println("A user is already logged in.");
					continue;
				}

				login();

				break;
			case "logout":
			case "log out":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before you can log out.");
					continue;
				}

				logout();

				break;
			case "create additional account":
			case "create account":
			case "create":
				// If no one is logged in, creates a bankUser. Otherwise creates an account
				if (userIdLoggedIn == null) {
					// Create a bank user
					displayTermsAndConditions();
					System.out.println("Do you accept these conditions? yes/no");

					if (scan.nextLine().trim().equalsIgnoreCase("yes"))
						if (!createBankUser())
							System.out.println("Account creation failed.");
						else {
							System.out.print("Account created, welcome to " + bankName + ".");
							System.out.println(" Please log in to view or modify your account.");
						}
				} else {
					createAccount();
				}

				break;
			case "withdraw":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before modifying your account");
					continue;
				}

				acc = pickAccount();
				if (acc == null)
					continue;

				withdraw(acc);

				break;
			case "transfer":
			case "transfer funds":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before transferring funds");
					continue;
				}

				System.out.println("Pick an account to transfer FROM");
				Account acc1 = pickAccount();
				if (acc1 == null)
					continue;
				System.out.println("Pick an account to transfer TO");
				Account acc2 = pickAccount();
				if (acc2 == null)
					continue;
				if (acc1.getAccountId() == acc2.getAccountId()) {
					System.out.println("You cannnot transfer between the same account");
					continue;
				}

				transferFunds(acc1, acc2);

				break;
			case "deposit":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before modifying your account");
					continue;
				}

				acc = pickAccount();
				if (acc == null)
					continue;

				deposit(acc);

				break;
			case "view account":
			case "view":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before viewing your account");
					continue;
				}

				acc = pickAccount();
				if (acc == null)
					continue;

				viewAccount(acc);

				break;
			case "edit account":
			case "edit":
				if (userIdLoggedIn == null) {
					System.out.println("You must log in before modifying your account");
					continue;
				}

				System.out.println("Would you like to modify your profile or a specific account?");
				input = scan.nextLine().trim().toLowerCase();
				if (input.contains("profile")) {
					editBankUser();

				} else if (input.contains("account")) {

					acc = pickAccount();
					if (acc == null)
						continue;

					editAccount(acc);

				} else System.out.println("Command not recognized. Valid options are \"profile\" and \"account\"");

				break;
			case "quit":
			case "q":
				break bankloop;
			case "clerk login":
			case "clerk log in":

				// TODO add implementation

				break;
			default:
				System.out.println("Command not recognized. Please try again.");
			} // end case statement

		} // end bankloop

	// If the user is still logged in when they quit, the log them off.
	if (userIdLoggedIn != null)
		logout();

	}

	private static Account pickAccount() {

		ArrayList<Account> accountList = bankService.getAccounts(userIdLoggedIn);
		accountList = removeDeletedAccounts(accountList);
		if (accountList.size() == 0) {
			System.out.println("You have no accounts. Please create one to continue.");
			return null;
		}

		System.out.println("Pick an account by its reference number on the left: ");
		int i;
		for (i = 0; i < accountList.size(); i++) {
			System.out.println(i + ": " +
					"Account id: " + accountList.get(i).getAccountId() + ", " + 
					"Account type: " + accountList.get(i).getType() + ", " +
					"Balance: " + accountList.get(i).getBalance());
		}

		try {
			int input = Integer.parseInt(scan.nextLine().trim());

			return accountList.get(input);

		} catch (NumberFormatException e) {
			System.out.println("Invalid choice");
			return null;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("That choice is out of bounds");
			return null;
		}


	}

	private static ArrayList<Account> removeDeletedAccounts(ArrayList<Account> accountList) {
		ArrayList<Account> removeList = new ArrayList<Account>();

		for (Account acc : accountList)
			if (acc.isDeleted())
				removeList.add(acc);

		accountList.removeAll(removeList);
		return accountList;
	}

	private static void displayBasicAccountInfo() {
		BankUser guy = bankService.getBankUser(userIdLoggedIn);

		System.out.println("Logged in as: " + guy.getUsername() + ", Accounts: "
				+ removeDeletedAccounts(bankService.getAccounts(userIdLoggedIn)).size());
	}

	// Returns true if login was successful
	private static boolean login() {
		System.out.print("Enter username: ");
		String username = scan.nextLine().trim().toLowerCase();
		System.out.print("Enter password: ");
		String password = scan.nextLine().trim();

		BankUser guy = bankService.validateBankUser(username, password);

		if (guy == null) {
			System.out.println("Login failed, incorrect username and password combination.");
			return false;
		} else {
			userIdLoggedIn = guy.getUserId();
			System.out.println("Login successful, welcome " + guy.getFirstName() + " " + guy.getLastName());
			return true;
		}
	}

	// Returns true if logout was successful
	private static boolean logout() {

		System.out.println("Logout successful.");
		userIdLoggedIn = null;

		return true;
	}

	private static boolean createBankUser() {

		// Get information from user to find/create a person
		System.out.print("Enter your first name: ");
		String firstName = scan.nextLine().trim();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine().trim();

		String email = setEmail();
		if (email == null) {
			return false;
		}

		Person per = bankService.tryCreatePerson(firstName, lastName, email, null);
		if (per == null)
			return false;

		// Get information from user to create an account
		System.out.print("Enter your username: ");
		String username = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password1 = scan.nextLine().trim();
		System.out.print("Enter password again: ");
		String password2 = scan.nextLine().trim();

		BankUser guy = null;
		if (password1.equals(password2)) {
			guy = bankService.tryCreateBankUser(per, username, password1, accountLevel.BRONZE);

			if (guy == null)
				return false;
			// Free checking account
			bankService.tryCreateAccount(guy, new BigDecimal(0), accountType.CHECKING, accountLevel.BRONZE);
		}
		else {
			System.out.println("Passwords do not match.");
			return false;
		}

		return true;
	}

	// Returns true if an account was successfully created
	private static boolean createAccount() {

		System.out.println("What type of account do you want?");
		Account.printAccountTypes();
		String type = scan.nextLine().trim().toUpperCase();

		try {
			if (accountType.valueOf(type) != accountType.NULL) {
				if (bankService.tryCreateAccount(bankService.getBankUser(userIdLoggedIn), new BigDecimal(0), accountType.valueOf(type), accountLevel.BRONZE) != null) {
					System.out.println("Account created");
					return true;
				}
			}
			else {
				System.out.println("That is not a valid choice");
				return false;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("That is not a valid choice");
			return false;
		}
		return true;
	}

	private static boolean withdraw(Account acc) {

		if (acc.getType() == accountType.SAVINGS) {
			System.out.println(
					"You cannot withdraw directly from a savings account. Transfer funds to a non-savings account first.");
			return false;
		}

		BigDecimal balance = acc.getBalance();
		String accountStr = acc.getType().toString().toLowerCase();

		BigDecimal withdrawAmmount = null;
		try {

			System.out.print("Current funds: $" + balance.toString() + "\nEnter an ammount to be withdrawn from your "
					+ accountStr + " account: ");

			withdrawAmmount = new BigDecimal(scan.nextLine().trim());

			if (withdrawAmmount.abs() != withdrawAmmount) {
				System.out.println("You cannot withdraw a negative quantity.");
				return false;
			}

			switch (balance.compareTo(withdrawAmmount)) {
			case -1:
				System.out.println("You cannot withdraw more than your " + accountStr + " balance");
				return false;
			default:

				BigDecimal finalBalance = balance.subtract(withdrawAmmount);
				acc.setBalance(finalBalance);

				if (!bankService.updateAccount(acc.getAccountId(), acc)) {
					System.out.println("Transaction failed.");
					return false;
				}

				System.out.println("$" + withdrawAmmount.toString() + " withdrawn from " + accountStr);
				System.out.println("$" + acc.getBalance().toString() + " new " + accountStr + " balance");

				return true;
			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}

	}

	// Transfer funds from acc1 to acc2
	private static boolean transferFunds(Account acc1, Account acc2) {

		if (acc1 == null || acc2 == null) {
			System.out.println("Invalid account(s)");
			return false;
		}

		BigDecimal balance1 = acc1.getBalance();
		BigDecimal balance2 = acc2.getBalance();
		String accountStr1 = acc1.getType().toString().toLowerCase();
		String accountStr2 = acc2.getType().toString().toLowerCase();

		BigDecimal transferAmmount = null;
		try {

			System.out.println("Current funds in " + accountStr1 + " account: $" + balance1.toString());
			System.out.println("Current funds in " + accountStr2 + " account: $" + balance2.toString());

			System.out.println("\nEnter an ammount to be transfered from your " + accountStr1 + " account to your "
					+ accountStr2 + "account: ");

			transferAmmount = new BigDecimal(scan.nextLine().trim());

			if (transferAmmount.abs() != transferAmmount) {
				System.out.println("You cannot transfer a negative quantity.");
				return false;
			}

			switch (balance1.compareTo(transferAmmount)) {
			case -1:
				System.out.println("You cannot withdraw more than your " + accountStr1 + " balance");
				return false;
			default:

				BigDecimal finalBalance1 = balance1.subtract(transferAmmount);
				BigDecimal finalBalance2 = balance2.add(transferAmmount);

				acc1.setBalance(finalBalance1);
				acc2.setBalance(finalBalance2);

				if (bankService.updateAccount(acc1.getAccountId(), acc1)
						&& bankService.updateAccount(acc2.getAccountId(), acc2)) {
					System.out.println("$" + transferAmmount.toString() + " transferred from " + accountStr1 + " to "
							+ accountStr2);
					System.out.println("$" + acc1.getBalance().toString() + " new " + accountStr1 + " balance");
					System.out.println("$" + acc2.getBalance().toString() + " new " + accountStr2 + " balance");

					return true;

				} else {
					System.out.println("Transaction failed.");
					return false;
				}

			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}

	}

	private static boolean deposit(Account acc) {
		BigDecimal balance = acc.getBalance();
		String accountStr = acc.getType().toString().toLowerCase();

		if (acc.getType() == accountType.REWARD) {
			System.out.println("You cannot deposit into a rewards account");
			return false;
		}

		BigDecimal depositAmmount = null;
		try {

			System.out.print("Current funds: $" + balance.toString() + "\nEnter an ammount to be deposited to your "
					+ accountStr + " account: ");

			depositAmmount = new BigDecimal(scan.nextLine().trim());

			if (depositAmmount.abs() != depositAmmount) {
				System.out.println("You cannot deposit a negative quantity.");
				return false;
			}

			BigDecimal finalBalance = balance.add(depositAmmount);
			acc.setBalance(finalBalance);

			if (bankService.updateAccount(acc.getAccountId(), acc)) {
				System.out.println("$" + depositAmmount.toString() + " deposited to " + accountStr);
				System.out.println("$" + finalBalance + " new " + accountStr + " balance");

				return true;

			} else {
				System.out.println("Transaction failed.");
				return false;

			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}
	}

	private static boolean viewAccount(Account acc) {

		System.out.println("Account created on : " + acc.getAccountOpenedDate());
		System.out.println("Account id number: " + acc.getAccountId());
		System.out.println("Account level: " + acc.getLevel().toString());
		System.out.println("Account type: " + acc.getType().toString());
		System.out.println("Account balance: " + acc.getBalance().toString());

		accountType type = acc.getType();
		if (type == accountType.SAVINGS)
			System.out.println("Interest rate: " + 100 * acc.getSavingsRate() + "%");
		else if (type == accountType.CREDIT)
			System.out.println("Interest rate: " + 100 * acc.getCreditRate() + "%");
		else if (type == accountType.REWARD)
			System.out.println("Rewards rate: " + 100 * acc.getRewardsRate() + "%");

		return true;
	}

	private static boolean editBankUser() {

		System.out.print("Please enter your current password: ");
		String password1 = scan.nextLine().trim();
		BankUser guy = bankService.getBankUser(userIdLoggedIn);
		if (bankService.validateBankUser(guy.getUsername(), password1) == null) {
			System.out.println("Incorrect password.");
			return false;
		}

		System.out.println("Would you like to change your password, name, or email?");

		String str = scan.nextLine().trim().toLowerCase();
		if (str.contains("password")) {

			System.out.print("Enter a new password: ");
			String password2 = scan.nextLine().trim();
			System.out.print("Enter the new password one more time: ");
			String password3 = scan.nextLine().trim();

			if (!password2.equals(password3)) {
				System.out.println("New passwords do not match. Password not changed.");
				return false;
			}

			guy.setPassword(password2);
			if (bankService.updateBankUser(guy.getUserId(), guy)) {
				System.out.println("Password changed");
				return true;
			} else {
				System.out.println("Password NOT changed");
				return false;
			}


		} else if(str.contains("name")) {

			Person per = bankService.getPerson(guy.getPersonId());

			System.out.print("Enter a new first name: ");
			String firstName = scan.nextLine().trim();
			System.out.print("Enter a new last name: ");
			String lastName = scan.nextLine().trim();

			per.setFirstName(firstName);
			per.setLastName(lastName);

			if (bankService.updatePerson(per.getPersonId(), per)) {
				System.out.println("Name updated");
				return true;
			} else {
				System.out.println("Name not updated.");
				return false;
			}

		} else if (str.contains("mail")) {

			Person per = bankService.getPerson(guy.getPersonId());
			String email = setEmail();

			if (email != null) {
				per.setEmail(email);
				bankService.updatePerson(guy.getPersonId(), per);
				System.out.println("Email updated.");
				return true;
			} else {
				System.out.println("Email could not be updated.");
				return false;
			}


		}
		return false; 
	}

	private static boolean editAccount(Account acc) {

		// FIXME change option to upgrade account so it requires credentials from a clerk (administrator)

		System.out.println("Would you like to upgrade your account or delete your account?");
		String str = scan.nextLine().trim().toLowerCase();
		if (str.contains("upgrade")) {

			System.out.print("Please enter your current password: ");
			String password1 = scan.nextLine().trim();

			BankUser guy = bankService.getBankUser(userIdLoggedIn);
			if (bankService.validateBankUser(guy.getUsername(), password1) == null) {
				System.out.println("Incorrect password.");
				return false;
			} else {

				System.out.println("Which account level do you want? Current level is: " + acc.getLevel().toString());
				Account.printAccountLevels();
				String input = scan.nextLine().trim().toUpperCase();

				try {
					if (acc.setLevel(accountLevel.valueOf(input))) {
						if(bankService.updateAccount(acc.getAccountId(), acc)) {
							System.out.println("Account updated");
							return true;
						} else {
							System.out.println("Update failed");
							return false;
						}
					}
					else {
						System.out.println("That is not a valid choice");
						return false;
					}
				} catch (IllegalArgumentException e) {
					System.out.println("That is not a valid choice");
					return false;
				}

			}

		} else if (str.contains("delete")) {
			System.out.println("WARNING: This canot be undone!");
			System.out.println("Our bank system does not currently offer the option to restore a delted account" +
					"\nYou will lose any funds in this account. Be sure to withdraw all funds before deleting.");
			System.out.println("Are you sure you with to delete your account? yes/no");

			String confirmation = scan.nextLine().trim().toLowerCase();

			if (confirmation.contains("yes") || confirmation.equalsIgnoreCase("y")) {

				if (bankService.deleteAccount(acc.getAccountId())) {
					System.out.println("Account " + acc.getAccountId() + " has ben DELETED. " + bankName + " thanks your for your patronage.");
					return true;
				} else {
					System.out.println("Account could not be deleted. Please see an administrator.");
					return false;
				}
			} else {
				System.out.println("Account " + acc.getAccountId() + " NOT deleted. " + bankName + " thanks you for your continued patronage.");
				return false;
			}
		} else {
			return false;
		}

	}

	private static void displayTermsAndConditions() {
		System.out.println("By creating an account with " + bankName
				+ " You agree to the following terms and conditions\n" + bankName
				+ " reserves the right to collect and use your personal data for any purpose.\n" + bankName
				+ " will not be responsible for any loss or damage of property caused by use of \nservices provided by "
				+ bankName + ".\n" + "The user of services provided by " + bankName
				+ " relinquish all rigts to sue or otherwise \ntake " + bankName + " or a representative of " + bankName
				+ " to a court of law for any \nreason regarding services provided by " + bankName
				+ " or property stored on the property of \n" + bankName);
	}

	// Returns false if a valid email was not supplied
	private static String setEmail() {

		// get email
		System.out.print("Enter your email address: ");
		String email = scan.nextLine().trim();

		if (bankService.isEmailValid(email))
			return email;

		return null;

	}

}
