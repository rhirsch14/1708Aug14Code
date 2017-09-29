package com.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import com.bank.dao.AccountDAO;
import com.bank.dao.AccountDAOImpl;
import com.bank.dao.AccountTypeDAO;
import com.bank.dao.AccountTypeDAOImpl;
import com.bank.dao.UserDAO;
import com.bank.dao.UserDAOImpl;
import com.bank.model.Account;
import com.bank.model.AccountType;
import com.bank.model.User;


/**
 * Controls the flow of the program.
 * Processes user input.
 * Manages CRUD operations
 * @author will underwood
 *
 */
public class BankService {
	
	//private DAO dao;
	private AccountDAO<Account> accountDAO;
	private AccountTypeDAO<AccountType> accountTypeDAO;
	private UserDAO<User> userDAO;
	private Scanner scanner;
	private User loggedInUser;
	
	/**
	 * Makes a new Service for managing bank accounts
	 * @precondition None.
	 * @param None.
	 * @postcondition A new Service was created
	 */
	public BankService() {
		this.accountDAO = new AccountDAOImpl();
		this.accountTypeDAO = new AccountTypeDAOImpl();
		this.userDAO = new UserDAOImpl();
		this.scanner = new Scanner(System.in);
	}

	/**
	 * Displays the initial menu when the app is started.
	 * @precondition None.
	 * @param None.
	 * @postcondition Showing the initial menu to the user
	 */
	public void run() {
		this.displayInitialMenu();
	}
	
	private void displayInitialMenu() {
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
		System.out.println("2. Register New User Account");
		System.out.println("3. Quit");
	}
	
	private void processInitialMenu() {
		int input = getInput();
		switch (input) {
			case 1:
				this.login();
				break;
			case 2:
				this.registerNewUserAccount();
				break;
			case 3:
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
		ArrayList<User> allUsers = this.userDAO.readAllUsers();
		User user = null;
		do {
			System.out.println("Login");
			System.out.print("Enter email >");
			String email = this.scanner.nextLine();
			for(User currentUser : allUsers) {
				if (currentUser.getEmail().equals(email) && currentUser.isEnabled()) {
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
						this.printOptionsForLoggedInUser(email);
					} else {
						System.out.println("Password incorrect. Please try again.");
					}
				} while (!passwordCorrect);
			} else {
				System.out.println("Email not found. Press any key to continue...");
				this.scanner.nextLine();
			}
		} while(user == null);
		
	}
	
	private void registerNewUserAccount() {
		System.out.println("Register New Account");
		System.out.print("What is your first name? >");
		String firstName = this.scanner.nextLine();
		System.out.print("What is your last name? >");
		String lastName = this.scanner.nextLine();
		System.out.print("What is your email address? >");
		String email = this.scanner.nextLine();
		System.out.print("Enter a password >");
		String password = this.scanner.nextLine();
		System.out.println("Thank you");
		User newUser = new User();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setPassword(password);
		this.userDAO.createUser(newUser);
		System.out.println("Press any key to continue...");
		scanner.nextLine();
		this.displayInitialMenu();
	}
	
	private void printOptionsForLoggedInUser(String username) {
		System.out.println("Welcome " + username);
		System.out.println("Options:");
		System.out.println("1. Logout");
		System.out.println("2. Withdraw Funds");
		System.out.println("3. Deposit Funds");
		System.out.println("4. View Account Balance");
		System.out.println("5. Open New Bank Account");
		System.out.println("6. Edit My Information");
		System.out.println("7. Transfer Funds");
		System.out.println("8. Close Account");
		this.processLoggedInMenu();
	}
	
	private void processLoggedInMenu() {
		int input = this.getInput();
		switch(input) {
		case 1:
			System.out.println("Logging out...");
			this.displayInitialMenu();
			break;
		case 2:
			this.printWithdrawMenu();
			break;
		case 3:
			this.printDepositMenu();
			break;
		case 4:
			this.viewBalance();
			break;
		case 5:
			this.openNewBankAccount();
			break;
		case 6:
			this.editInfo();
			break;
		case 7:
			this.printTransferMenu();
			break;
		case 8:
			this.closeAccount();
			break;
		}
	}
	
	private void printWithdrawMenu() {
		System.out.println("Withdraw");
		System.out.println("From which account do you want to withdraw? >");
		System.out.println("1. Checking");
		System.out.println("2. Savings");
		System.out.print("Which account? >");
		int choice = Integer.parseInt(this.scanner.nextLine());
		if (choice == 1) {
			if (this.loggedInUser.hasChecking()) {
				this.withdrawFunds(1);
			} else {
				System.out.println("You do not have a checking account.");
			}
		}
		if (choice == 2) {
			if (this.loggedInUser.hasSavings()) {
				this.withdrawFunds(2);
			} else {
				System.out.println("You do not have a savings account.");
			}
		}
		this.returnToLoggedInMenu();
	}

	private void withdrawFunds(int accountTypeID) {
		System.out.print("How much money do you want to withdraw? >");
		double withdrawal = Double.parseDouble(this.scanner.nextLine());
		Account account = this.accountDAO.readAccount(this.loggedInUser.getUserID(), accountTypeID);
		if (account.getBalance().doubleValue() > withdrawal) {
			account.setBalance(account.getBalance().subtract(new BigDecimal(withdrawal)));
			this.accountDAO.updateAccount(account);
			System.out.println("You have withdrawn $" + withdrawal);
		}
	}
	
	private void printDepositMenu() {
		System.out.println("Deposit");
		System.out.print("How much money do you want to deposit? >");
		double deposit = Double.parseDouble(this.scanner.nextLine());
		System.out.println("1. Checking");
		System.out.println("2. Savings");
		System.out.print("Which account? >");
		int choice = Integer.parseInt(this.scanner.nextLine());
		if (choice == 1) {
			if (this.loggedInUser.hasChecking()) {
				this.depositFunds(deposit, 1);
			} else {
				System.out.println("You do not have a checking account.");
			}
		}
		if (choice == 2) {
			if (this.loggedInUser.hasSavings()) {
				this.depositFunds(deposit, 2);
			} else {
				System.out.println("You do not have a savings account.");
			}
		}
		this.returnToLoggedInMenu();
	}

	private void depositFunds(double deposit, int accountTypeID) {
		Account account = this.accountDAO.readAccount(this.loggedInUser.getUserID(), accountTypeID);
		account.setBalance(account.getBalance().add(new BigDecimal(deposit)));
		this.accountDAO.updateAccount(account);
		System.out.println(deposit + " has been deposited to your account.");
	}

	private void returnToLoggedInMenu() {
		System.out.println("Press any key to continue...");
		this.scanner.nextLine();
		this.printOptionsForLoggedInUser(this.loggedInUser.getEmail());
	}
	
	private void viewBalance() {
		if (this.loggedInUser.hasChecking()) {
			System.out.println("Checking Balance: $" + this.accountDAO.readAccount(this.loggedInUser.getUserID(), 1).getBalance());
		}
		if (this.loggedInUser.hasSavings()) {
			System.out.println("Savings Balance: $" + this.accountDAO.readAccount(this.loggedInUser.getUserID(), 2).getBalance());
		}
		this.returnToLoggedInMenu();
	}
	
	private void openNewBankAccount() {
		System.out.println("Open New Bank Account");
		int accountTypeChoice = 0;
		do {
			System.out.println("What kind of account would you like to open?");
			System.out.println("1. Personal Checking");
			System.out.println("2. Personal Savings");
			System.out.print("Enter your choice >");
			try {
				accountTypeChoice = Integer.parseInt(this.scanner.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Your input was not recognized. Please input a number.");
			}
		} while (accountTypeChoice == 0);
		if (accountTypeChoice == 1) {
			if (this.loggedInUser.hasChecking()) {
				System.out.println("You already have a checking account.");
			} else {
				this.createAccount(accountTypeChoice);
				this.loggedInUser.setHasChecking(true);
			}
		} else if (accountTypeChoice == 2) {
			if (this.loggedInUser.hasSavings()) {
				System.out.println("You already have a savings account.");
			} else {
				this.createAccount(accountTypeChoice);
				this.loggedInUser.setHasSavings(true);
			}
		} else {
			System.err.println("Invalid input");
		}
		System.out.println("Press any key to continue...");
		this.scanner.nextLine();
		this.returnToLoggedInMenu();
	}

	private void createAccount(int accountType) {
		Account account = new Account();
		account.setAccountTypeID(accountType);
		account.setUserID(this.loggedInUser.getUserID());
		account.setBalance(new BigDecimal(0));
		this.accountDAO.createAccount(account);
		System.out.println("Thank you. Your account has been created.");
	}
	
	private void editInfo() {
		System.out.print("Enter new email >");
		String email = this.scanner.nextLine();
		System.out.print("Enter a password >");
		String password = this.scanner.nextLine();
		this.loggedInUser.setEmail(email);
		this.loggedInUser.setPassword(password);
		this.userDAO.updateUser(this.loggedInUser);
		this.returnToLoggedInMenu();
	}
	
	private void printTransferMenu() {
		System.out.println("Transfer Funds");
		if (this.loggedInUser.hasChecking() && this.loggedInUser.hasSavings()) {
			System.out.println("1. Checking");
			System.out.println("2. Savings");
			System.out.print("From which account do you want to transfer funds? >");
			int choice = Integer.parseInt(this.scanner.nextLine());
			Account source = null;
			Account destination = null;
			if (choice == 1) {
				source = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 1);
				destination = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 2);
			}
			if (choice == 2) {
				source = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 2);
				destination = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 1);
			}
			if (source != null && destination != null) {
				System.out.print("How much do you want to transfer? >");
				double amount = Double.parseDouble(this.scanner.nextLine());
				if (source.getBalance().doubleValue() > amount) {
					source.setBalance(source.getBalance().subtract(new BigDecimal(amount)));
					destination.setBalance(destination.getBalance().add(new BigDecimal(amount)));
					this.accountDAO.updateAccount(source);
					this.accountDAO.updateAccount(destination);
				} else {
					System.out.println("Insufficient funds");
				}
			} else {
				System.err.println("Invalid input");
			}
		} else {
			System.out.println("You only have one bank account");
			System.out.println("Press any key to continue...");
			this.scanner.nextLine();
		}
		this.returnToLoggedInMenu();
	}
	
	private void closeAccount() {
		System.out.print("Close account");
		Account checking = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 1);
		Account savings = this.accountDAO.readAccount(this.loggedInUser.getUserID(), 2);
		if (this.loggedInUser.hasChecking()) {
			System.out.println("1. Checking");
		}
		if (this.loggedInUser.hasSavings()) {
			System.out.println("2. Savings");
		}
		System.out.println("3. My User Account");
		System.out.println("Which account do you want to close? >");
		int answer = Integer.parseInt(this.scanner.nextLine());
		if (answer == 1) {
			if (this.loggedInUser.hasSavings()) {
				System.out.println("Remaining funds in your checking account will be transfered to your savings account.");
				savings.setBalance(savings.getBalance().add(checking.getBalance()));
				this.accountDAO.updateAccount(savings);
				this.accountDAO.destroyAccount(checking.getAccountID());
				this.loggedInUser.setHasChecking(false);
				this.userDAO.updateUser(this.loggedInUser);
			} else {
				System.out.println("Remaining funds will be mailed to you.");
				this.accountDAO.destroyAccount(checking.getAccountID());
				this.loggedInUser.setHasChecking(false);
				this.userDAO.updateUser(this.loggedInUser);
			}
		}
		if (answer == 2) {
			if (this.loggedInUser.hasChecking()) {
				System.out.println("Remaining funds in your savings account will be transfered to your checking account.");
				checking.setBalance(savings.getBalance().add(savings.getBalance()));
				this.accountDAO.updateAccount(checking);
				this.accountDAO.destroyAccount(savings.getAccountID());
				this.loggedInUser.setHasSavings(false);
				this.userDAO.updateUser(this.loggedInUser);
			} else {
				System.out.println("Remaining funds will be mailed to you.");
				this.accountDAO.destroyAccount(savings.getAccountID());
				this.loggedInUser.setHasSavings(false);
				this.userDAO.updateUser(this.loggedInUser);
			}
		}
		if (answer == 3) {
			System.out.println("We're sorry to see you go. Your account details will remain on record, and remaining funds will be mailed to you.");
			this.userDAO.disableUser(this.loggedInUser);
			this.loggedInUser = null;
			this.printOptionsForLoggedOutUser();
		} else {
			this.returnToLoggedInMenu();
		}
	}

}