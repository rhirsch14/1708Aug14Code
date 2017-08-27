package com.bank.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


import com.bank.pojo.Account;
import com.bank.pojo.User;
import com.bank.service.Service;

public class RunBank {

	/*
	 * the implementation to run bank application
	 * 
	 * Requirements:
	 *  - login
	 *  - create account
	 *  - logout
	 *  - withdraw
	 *  - deposit
	 *  - transfer between accounts
	 *  - view account balance
	 *  - edit my info
	 *  
	 *  Tech Specs:
	 *  - core java
	 *  - jdbc
	 *  
	 */
	
	static Service bankService;
	static User currentUser;
	static ArrayList<Account> currentAccounts;
	static Scanner scan;
	
	public static void main(String[] args) {
		//start service for program
		bankService = new Service();
		
		//create i/o with user
		scan = new Scanner(System.in);
		
		//print welcome, go to first input function home()
		System.out.println("Welcome to the banking application!\nPlease choose an option:");
		home();
		
	}
	
	/*
	 * landing input/output of program
	 * q - to quit
	 * l - to login
	 * r - to register
	 */
	static void home(){
		System.out.println("l - login");
		System.out.println("r - register");
		System.out.println("q - quit");
		
		String input = scan.nextLine();
		if(input.equals("l")) login();
		else if(input.equals("r")) register();
		else if(!input.equals("q")) home();
		else{
			System.out.println("See you next time!");
		}
	}
	
	/*
	 * register by getting all info from user
	 */
	static void register(){
		System.out.println("Enter first name (q to quit): ");
		String fn = scan.nextLine();
		
		//leave if user quits
		if(fn.equals("q")){
			home();
			return;
		}
		
		System.out.println("Enter last name: ");
		String ln = scan.nextLine();
		System.out.println("Enter username: ");
		String username = scan.nextLine().toLowerCase();
		System.out.println("Enter password: ");
		String password = scan.nextLine();
		
		//make new user object and pass to db
		User user = new User(fn, ln, username, password);
		currentUser = bankService.addUser(user);
		currentAccounts = new ArrayList<Account>();
		
		//repeat above process for new username if taken
		while(currentUser.getId() == -1){
			System.out.println("Username in use, enter a different username:");
			username = scan.nextLine();
			currentUser.setUsername(username);
			currentUser = bankService.addUser(user);
		}
		//go to landing page
		landing();
	}
	
	/*
	 * login using username and password
	 * q to go back
	 */
	static void login(){
		System.out.println("Enter username (or 'q' to go back):");
		String username = scan.nextLine().toLowerCase();
		
		//quit if q
		if(username.equals("q")){
			home();
			return;
		}
		
		System.out.println("Enter password:");
		String password = scan.nextLine();
		
		currentUser = bankService.getUser(username, password);
				
		//login if user was found (wasnt found if return is empty user)
		if(!currentUser.equals(new User())){
			System.out.println("Welcome " + currentUser.getFirstname());
			//get accounts if any
			currentAccounts = bankService.getAccounts(currentUser.getId());
			landing();
		}
		else{
			System.out.println("Incorrect username or password");
			login();
		}
	}
	
	static void landing(){
		System.out.println("Home Page");
		System.out.println("Choose an option:");
		System.out.println("a - view accounts");
		System.out.println("b - update your user information");
		System.out.println("c - logout");
		
		String input = scan.nextLine();
		if(input.equals("a")){
			viewBalance();
		}
		else if(input.equals("b")){
			updateInfo();
		}
		else if(input.equals("c")){
			logout();
		}
		else{
			System.out.println("Try Again\n");
			landing();
		}
	}

	/*
	 * Log out by removing current user and accounts and going to home page
	 */
	static void logout(){
		//empty current session
		currentUser = null;
		currentAccounts.clear();
		System.out.println("Successfully logged out");
		home();
	}
	
	/*
	 * View user account balance with options
	 */
	static void viewBalance(){
		/*
		 * print options and process them
		 */
		if(currentAccounts.isEmpty()){
			System.out.println("You have no accounts");
			System.out.println("a - open a new account");
			System.out.println("b - home page");
			String input = scan.nextLine();
			
			if(input.equals("a")){
				newAccount();
			}
			else if(input.equals("b")){
				landing();
			}
			else{
				viewBalance();
			}
		}
		else{
			for(Account a : currentAccounts){
				switch(a.getTypeID()){
				case 1:
					System.out.println("Checking: $" + a.getBalance());
					break;
				case 2:
					System.out.println("Savings: $" + a.getBalance());
					break;
				case 3:
					System.out.println("Credit: $" + a.getBalance());
					break;
				}
			}
			
			System.out.println("a - make a deposit");
			System.out.println("b - make a withdrawal");
			System.out.println("c - make a transfer");
			System.out.println("d - open a new account");
			System.out.println("e - home page");
			String input = scan.nextLine();
			if(input.equals("a")){
				deposit();
			}
			else if(input.equals("b")){
				withdraw();
			}
			else if(input.equals("c")){
				transfer();
			}
			else if(input.equals("d")){
				newAccount();
			}
			else if(input.equals("e")){
				landing();
			}
			else{
				viewBalance();
			}
		}
		
	}
	
	private static void newAccount() {
		int[] typeIdArray = {1,2,3};
		for(Account a: currentAccounts){
			switch(a.getTypeID()){
			case 1: typeIdArray[0] = -1;
			break;
			case 2: typeIdArray[1] = -1;
			break;
			case 3: typeIdArray[2] = -1;
			break;
			}
		}
		
		for(int i : typeIdArray){
			if(i != -1){
				switch(i){
				case 1: System.out.println("a - Open checking account");
				break;
				case 2: System.out.println("b - Open savings account");
				break;
				case 3: System.out.println("c - Open a credit account");
				break;
				}
			}
		}
		System.out.println("d - view accounts");
		System.out.println("e - home page");
		
		String input = scan.nextLine();
		Account newAccount;
		
		if(input.equals("a")){
			newAccount = new Account(new BigDecimal(0), currentUser.getId(), 1);
			newAccount.setAccountID(bankService.addAccount(newAccount));
			currentAccounts.add(newAccount);
			System.out.println("You opened a new checkings account!");
			viewBalance();
		}
		else if(input.equals("b")){
			newAccount = new Account(new BigDecimal(0), currentUser.getId(), 2);
			newAccount.setAccountID(bankService.addAccount(newAccount));
			currentAccounts.add(newAccount);
			System.out.println("You opened a new savings account!");
			viewBalance();
		}
		else if(input.equals("c")){
			newAccount = new Account(new BigDecimal(0), currentUser.getId(), 3);
			newAccount.setAccountID(bankService.addAccount(newAccount));
			currentAccounts.add(newAccount);
			System.out.println("You opened a new credit account!");
			viewBalance();
		}
		else if(input.equals("d")){
			viewBalance();
		}
		else if(input.equals("e")){
			landing();
		}
		else{
			newAccount();
		}
	}
	
	/*
	 * Deposit money to bank account
	 * 
	 */
	static void deposit(){
		int[] typeIdArray = {-1,-1,-1};
		for(Account a: currentAccounts){
			switch(a.getTypeID()){
			case 1: typeIdArray[0] = 1;
			break;
			case 2: typeIdArray[1] = 2;
			break;
			case 3: typeIdArray[2] = 3;
			break;
			}
		}
		
		for(int i : typeIdArray){
			if(i != -1){
				switch(i){
				case 1: System.out.println("a - Deposit to checking");
				break;
				case 2: System.out.println("b - Deposit to savings");
				break;
				case 3: System.out.println("c - Deposit to credit");
				break;
				}
			}
		}
		System.out.println("d - view accounts");
		System.out.println("e - home page");
		
		String input = scan.nextLine();
		if(input.equals("a")){
			
			//get amount to deposit
			System.out.println("Enter amount: ");
			BigDecimal deposit = scan.nextBigDecimal();
			
			//find checking account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 1){
					
					//set new balance = balance + deposit
					currentAccounts.set(i, bankService.deposit(currentAccounts.get(i),deposit));
					break;
				}
			}
		}
		else if(input.equals("b")){
			//get amount to deposit
			System.out.println("Enter amount: ");
			BigDecimal deposit = scan.nextBigDecimal();
			
			//find savings account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 2){
					
					//set new balance = balance + deposit
					currentAccounts.set(i, bankService.deposit(currentAccounts.get(i),deposit));
					break;
				}
			}
		}
		else if(input.equals("c")){
			//get amount to deposit
			System.out.println("Enter amount: ");
			BigDecimal deposit = scan.nextBigDecimal();
			
			//find credit account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 3){
					
					//set new balance = balance + deposit
					currentAccounts.set(i, bankService.deposit(currentAccounts.get(i),deposit));
					break;
				}
			}
		}
		else if(input.equals("d")){
			viewBalance();
		}
		else if(input.equals("e")){
			landing();
		}
		else{
			deposit();
		}
		viewBalance();
	}
	
	/*
	 * Withdraw money from bank account
	 * 
	 * get amount
	 * pass info to service (changes file info)
	 * update currentuser
	 * print info
	 */
	static void withdraw(){
		int[] typeIdArray = {-1,-1,-1};
		for(Account a: currentAccounts){
			switch(a.getTypeID()){
			case 1: typeIdArray[0] = 1;
			break;
			case 2: typeIdArray[1] = 2;
			break;
			case 3: typeIdArray[2] = 3;
			break;
			}
		}
		
		for(int i : typeIdArray){
			if(i != -1){
				switch(i){
				case 1: System.out.println("a - Withdraw from checking");
				break;
				case 2: System.out.println("b - Withdraw from savings");
				break;
				case 3: System.out.println("c - Withdraw from credit");
				break;
				}
			}
		}
		System.out.println("d - view accounts");
		System.out.println("e - home page");
		
		String input = scan.nextLine();
		if(input.equals("a")){
			
			//get amount to withdraw
			System.out.println("Enter amount: ");
			BigDecimal withdraw = scan.nextBigDecimal();
			
			//find checking account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 1){
					
					//set new balance = balance + withdrawal
					currentAccounts.set(i, bankService.withdraw(currentAccounts.get(i),withdraw));
					break;
				}
			}
		}
		else if(input.equals("b")){
			//get amount to withdraw
			System.out.println("Enter amount: ");
			BigDecimal withdraw = scan.nextBigDecimal();
			
			//find savings account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 2){
					
					//set new balance = balance + deposit
					currentAccounts.set(i, bankService.withdraw(currentAccounts.get(i),withdraw));
					break;
				}
			}
		}
		else if(input.equals("c")){
			//get amount to withdraw
			System.out.println("Enter amount: ");
			BigDecimal withdraw = scan.nextBigDecimal();
			
			//find credit account in currentaccounts
			for(int i = 0; i < currentAccounts.size(); i++){
				if(currentAccounts.get(i).getTypeID() == 3){
					
					//set new balance = balance + withdraw
					currentAccounts.set(i, bankService.withdraw(currentAccounts.get(i),withdraw));
					break;
				}
			}
		}
		else if(input.equals("d")){
			viewBalance();
		}
		else if(input.equals("e")){
			landing();
		}
		else{
			withdraw();
		}
		viewBalance();
	}
	
	/*
	 * Transfer money from one account to another
	 */
	static void transfer() {
		if(currentAccounts.size() <= 1){
			System.out.println("You need more than 1 account to transfer.\n Open a new account to continue.");
			newAccount();
			return;
		}
		System.out.println("Choose an account to transfer from: ");
		
		int[] typeIdArray = {-1,-1,-1};
		for(Account a: currentAccounts){
			switch(a.getTypeID()){
			case 1: typeIdArray[0] = 1;
			break;
			case 2: typeIdArray[1] = 2;
			break;
			case 3: typeIdArray[2] = 3;
			break;
			}
		}
		
		for(int i : typeIdArray){
			if(i != -1){
				switch(i){
				case 1: System.out.println("a - Checking");
				break;
				case 2: System.out.println("b - Savings");
				break;
				case 3: System.out.println("c - Credit");
				break;
				}
			}
		}
		System.out.println("d - view accounts");
		
		String input = scan.nextLine();
		String transferTo;
		BigDecimal amount;
		System.out.println("Transfer to:");
		
		//if moving from savings, find what to transfer to
		if(input.equals("a")){
			do{
			for(int i : typeIdArray){
				if(i != -1){
					switch(i){
					case 2: System.out.println("b - Savings");
					break;
					case 3: System.out.println("c - Credit");
					break;
					}
				}
			}
			transferTo = scan.nextLine();
			} while(!transferTo.equals("b") && !transferTo.equals("c"));
			
			System.out.println("Enter amount: ");
			amount = scan.nextBigDecimal();
			
			//checkings to savings
			if(transferTo.equals("b")){
				for(Account a : currentAccounts){
					if(a.getTypeID() == 1){
						for(Account b : currentAccounts){
							if(b.getTypeID() == 2){
								bankService.withdraw(a, amount);
								bankService.deposit(b, amount);
								viewBalance();
								return;
							}
						}
					}
				}
			}
			//checkings to credit
			else{
				for(Account a : currentAccounts){
					if(a.getTypeID() == 1){
						for(Account b : currentAccounts){
							if(b.getTypeID() == 3){
								bankService.withdraw(a, amount);
								bankService.deposit(b, amount);
								viewBalance();
								return;
							}
						}
					}
				}
			}
		}
		else if(input.equals("b")){
			do{
				for(int i : typeIdArray){
					if(i != -1){
						switch(i){
						case 1: System.out.println("a - Checkings");
						break;
						case 3: System.out.println("c - Credit");
						break;
						}
					}
				}
				transferTo = scan.nextLine();
				} while(!transferTo.equals("a") && !transferTo.equals("c"));
				
				System.out.println("Enter amount: ");
				amount = scan.nextBigDecimal();
				
				//savings to credit
				if(transferTo.equals("a")){
					for(Account a : currentAccounts){
						if(a.getTypeID() == 2){
							for(Account b : currentAccounts){
								if(b.getTypeID() == 1){
									bankService.withdraw(a, amount);
									bankService.deposit(b, amount);
									viewBalance();
									return;
								}
							}
						}
					}
				}
				//savings to credit
				else{
					for(Account a : currentAccounts){
						if(a.getTypeID() == 2){
							for(Account b : currentAccounts){
								if(b.getTypeID() == 3){
									bankService.withdraw(a, amount);
									bankService.deposit(b, amount);
									viewBalance();
									return;
								}
							}
						}
					}
				}
		}
		else if(input.equals("c")){
			do{
				for(int i : typeIdArray){
					if(i != -1){
						switch(i){
						case 1: System.out.println("a - Checking");
						break;
						case 2: System.out.println("b - Savings");
						break;
						}
					}
				}
				transferTo = scan.nextLine();
				} while(!transferTo.equals("a") && !transferTo.equals("b"));
				
				System.out.println("Enter amount: ");
				amount = scan.nextBigDecimal();
				
				//credit to checkings
				if(transferTo.equals("a")){
					for(Account a : currentAccounts){
						if(a.getTypeID() == 3){
							for(Account b : currentAccounts){
								if(b.getTypeID() == 1){
									bankService.withdraw(a, amount);
									bankService.deposit(b, amount);
									viewBalance();
									return;
								}
							}
						}
					}
				}
				//credit to savings
				else{
					for(Account a : currentAccounts){
						if(a.getTypeID() == 3){
							for(Account b : currentAccounts){
								if(b.getTypeID() == 2){
									bankService.withdraw(a, amount);
									bankService.deposit(b, amount);
									viewBalance();
									return;
								}
							}
						}
					}
				}
		}
		else if(input.equals("d")){
			viewBalance();
		}
		else{
			transfer();
		}
		viewBalance();
	}
	
	/*
	 * Update any part of account info
	 * 
	 * first name
	 * last name
	 * username
	 * password
	 */
	static void updateInfo(){
		System.out.println("Choose option to change:");
		System.out.println("a - First name ("+currentUser.getFirstname()+")");
		System.out.println("b - Last name ("+currentUser.getLastname()+")");
		System.out.println("c - Password");
		System.out.println("d - Home page");
		System.out.println("e - Delete user and accounts");
		String input = scan.nextLine();
		
		//if e go to home page
		if(input.equals("d")){
			landing();
			return;
		}
		
		//else, get info
		System.out.println("Enter change: ");
		String change = scan.nextLine();
		
		if(input.equals("a")){
			currentUser.setFirstname(change);
			bankService.updateUser(currentUser);
			System.out.println("Changed your first name");
		}
		else if(input.equals("b")){
			currentUser.setLastname(change);
			bankService.updateUser(currentUser);
			System.out.println("Changed your last name");
		}
		else if(input.equals("c")){
			currentUser.setPassword(change);
			bankService.updateUser(currentUser);
		}
		else if(input.equals("e")){
			System.out.println("Are you sure? (y/n)");
			input = scan.nextLine();
			if(input.equals("y")){
				removeUser();
				return;
			}
		}
		else{
			System.out.println("Try Again");
		}
		
		updateInfo();
		
	}
	
	static void removeUser(){
		for(Account a: currentAccounts){
			bankService.removeAccount(a);
		}
		bankService.removeUser(currentUser);
		System.out.println("Successfully removed accounts and user");
		home();
	}
	

}