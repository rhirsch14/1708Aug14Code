package com.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import com.bank.dao.DAO;
import com.bank.dao.DAOFileIO;
import com.bank.pojos.User;

/**
 * Controls the flow of the program.
 * Processes user input.
 * Manages CRUD operations
 * @author will underwood
 *
 */
public class Service {
	
	private DAO dao;
	private Scanner scanner;
	private boolean userLoggedIn;
	private User loggedInUser;
	
	/**
	 * Makes a new Service for managing bank accounts
	 * @precondition None.
	 * @param None.
	 * @postcondition A new Service was created
	 */
	public Service() {
		this.dao = new DAOFileIO();
		this.scanner = new Scanner(System.in);
		this.userLoggedIn = false;
		
	}
	
	/**
	 * Displays the appropriate menu
	 * @precondition None.
	 * @param None.
	 * @postcondition Showing a menu to the user
	 */
	public void displayInitialMenu() {
		this.printWelcomeMessage();
		this.printOptionsForLoggedOutUser();
		this.processInitialMenu();
	}
	
	private void printWelcomeMessage() {
		System.out.println("Welcome to The Bank!");
	}
	
	private void printOptionsForLoggedOutUser() {
		System.out.println("Options:");
		System.out.println("1. Login");
		System.out.println("2. Register New Account");
		System.out.println("3. Quit");
	}
	
	private void processInitialMenu() {
		int input = getInput();
		switch (input) {
			case 1: // login
				this.login();
				break;
			case 2: // register new account
				this.registerNewAccount();
				break;
			case 3: //quit
				System.out.println("Goodbye");
				System.exit(0);
			default: System.err.println("Input not recognized. Please type the number of your option.");
		}
	}

	private int getInput() {
		System.out.print("What do you want to do? >");
		String inputText = this.scanner.nextLine();
		int input;
		try {
			input = Integer.parseInt(inputText);
		} catch (NumberFormatException nfe) {
			input = 0;
		}
		return input;
	}
	
	private void login() {
		ArrayList<User> allUsers = dao.readAllUsers();
		User user = null;
		do {
			System.out.println("Login");
			System.out.print("Enter username >");
			String username = this.scanner.nextLine();
			for(User currentUser : allUsers) {
				if (currentUser.getUsername().equals(username)) {
					user = currentUser;
					break;
				}
			}
			if (user != null) {
				boolean passwordCorrect = false;
				do {
					System.out.print("Enter password >");
					String password = this.scanner.nextLine();
					if (user.getPassword().equals(password)) {
						passwordCorrect = true;
						this.loggedInUser = user;
						this.printOptionsForLoggedInUser(username);
					} else {
						System.out.println("Password incorrect. Please try again.");
					}
				} while (!passwordCorrect);
			} else {
				System.out.println("Username not found. Press any key to continue...");
				this.scanner.nextLine();
			}
		} while(user == null);
		
	}
	
	private void registerNewAccount() {
		System.out.println("Register New Account");
		System.out.print("What is your first name? >");
		String firstName = this.scanner.nextLine();
		System.out.print("What is your last name? >");
		String lastName = this.scanner.nextLine();
		System.out.print("Enter a username >");
		String username = this.scanner.nextLine();
		System.out.print("Enter a password >");
		String password = this.scanner.nextLine();
		System.out.print("Enter initial balance >");
		BigDecimal balance = new BigDecimal(Double.parseDouble(this.scanner.nextLine()));
		System.out.println("Thank you");
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setBalance(balance);
		this.dao.createUser(newUser);
		System.out.println("Press any key to continue...");
		scanner.nextLine();
		this.displayInitialMenu();
	}
	
	private void printOptionsForLoggedInUser(String username) {;
		System.out.println("Welcome " + username);
		System.out.println("Options:");
		System.out.println("1. Logout");
		System.out.println("2. Withdraw Funds");
		System.out.println("3. Deposit Funds");
		System.out.println("4. View Account Balance");
		System.out.println("5. Edit My Information");
		System.out.println("6. Close Account1");
		this.processLoggedInMenu();
	}
	
	private void processLoggedInMenu() {
		int input = this.getInput();
		switch(input) {
		case 1: // logout
			System.out.println("Logging out...");
			this.displayInitialMenu();
			break;
		case 2: // Withdraw Funds
			this.withdrawFunds();
			break;
		case 3: // Deposit Funds
			this.depositFunds();
			break;
		case 4: // View Balance
			this.viewBalance();
			break;
		case 5: // Edit information
			this.editInfo();
			break;
		case 6:
			this.closeAccount();
			break;
		}
	}
	
	private void withdrawFunds() {
		System.out.print("How much money do you want to withdraw? >");
		double withdrawl = Double.parseDouble(this.scanner.nextLine());
		if (this.loggedInUser.getBalance().doubleValue() >= withdrawl) {
			this.loggedInUser.setBalance(this.loggedInUser.getBalance().subtract(new BigDecimal(withdrawl)));
			this.dao.updateUser(this.loggedInUser);
			System.out.println(withdrawl + " has been withdrawn from your account.");
			this.returnToLoggedInMenu();
		} else {
			System.out.println("Insufficient funds. Returning to previous menu.");
			this.returnToLoggedInMenu();
		}
		
	}
	
	private void depositFunds() {
		System.out.print("How much money do you want to deposit? >");
		double deposit = Double.parseDouble(this.scanner.nextLine());
		this.loggedInUser.setBalance(this.loggedInUser.getBalance().add(new BigDecimal(deposit)));
		this.dao.updateUser(this.loggedInUser);
		System.out.println(deposit + " has been deposited to your account.");
		this.returnToLoggedInMenu();
	}

	private void returnToLoggedInMenu() {
		System.out.println("Press any key to continue...");
		this.scanner.nextLine();
		this.printOptionsForLoggedInUser(this.loggedInUser.getUsername());
	}
	
	private void viewBalance() {
		System.out.println("Your balance is : " + this.loggedInUser.getBalance());
		this.returnToLoggedInMenu();
	}
	
	private void editInfo() {
		System.out.print("Enter your first name >");
		String firstName = this.scanner.nextLine();
		System.out.print("Enter your last name >");
		String lastName = this.scanner.nextLine();
		System.out.print("Enter a password >");
		String password = this.scanner.nextLine();
		this.loggedInUser.setFirstName(firstName);
		this.loggedInUser.setLastName(lastName);
		this.loggedInUser.setPassword(password);
		this.dao.updateUser(this.loggedInUser);
		this.returnToLoggedInMenu();
	}
	
	private void closeAccount() {
		System.out.print("Warning! You have selected to close your account. Are you sure you want to do this? y/n >");
		String answer = this.scanner.nextLine();
		if (answer.equalsIgnoreCase("y")) {
			System.out.println("We're sorry to see you go. Your account will be closed.");
			this.dao.destroyUser(this.loggedInUser);
			this.displayInitialMenu();
		}
		else if (answer.equalsIgnoreCase("n")) {
			System.out.println("Returning to previous menu...");
			this.returnToLoggedInMenu();
		}
		else {
			System.out.println("Your input was not recognized. Returning to previous menu...");
			this.returnToLoggedInMenu();
		}
	}

}