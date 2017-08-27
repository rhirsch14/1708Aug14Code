package com.bank.main;

import java.math.BigDecimal;
import java.util.Scanner;

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
	 *  - view account balance
	 *  - edit my info
	 *  
	 *  Tech Specs:
	 *  - core java
	 *  - file i/o
	 *  
	 */
	
	static Service bankService;
	static User currentUser;
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
		
		//make new user object and pass to data file
		User user = new User(bankService.getNewUserId(), fn, ln, username, password, new BigDecimal(0));
		currentUser = bankService.addUser(user);
		
		//repeat above process for new username if taken
		while(currentUser == null){
			System.out.println("Username in use, enter a different username:");
			username = scan.nextLine();
			user = new User(bankService.getNewUserId(), fn, ln, username, password, new BigDecimal(0));
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
		
		if((currentUser = bankService.getUser(username, password)) != null){
			System.out.println("Welcome " + currentUser.getFirstname());
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
		System.out.println("a - view account balance");
		System.out.println("b - make a deposit");
		System.out.println("c - make a withdrawal");
		System.out.println("d - update your user information");
		System.out.println("e - logout");
		
		String input = scan.nextLine();
		if(input.equals("a")){
			viewBalance();
		}
		else if(input.equals("b")){
			deposit();
		}
		else if(input.equals("c")){
			withdraw();
		}
		else if(input.equals("d")){
			updateInfo();
		}
		else if(input.equals("e")){
			logout();
		}
		else{
			System.out.println("Try Again\n");
			landing();
		}
	}
	
	/*
	 * Log out by removing current user and going to home page
	 */
	static void logout(){
		currentUser = null;
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
		System.out.println("Your balance is: $" + currentUser.getBalance());
		System.out.println("a - make a deposit");
		System.out.println("b - make a withdrawal");
		System.out.println("c - home page");
		String input = scan.nextLine();
		if(input.equals("a")){
			deposit();
		}
		else if(input.equals("b")){
			withdraw();
		}
		else if(input.equals("c")){
			landing();
		}
		else{
			viewBalance();
		}
	}
	
	/*
	 * Deposit money to bank account
	 * 
	 * get amount
	 * pass info to service (changes file info)
	 * update currentuser
	 * print info
	 */
	static void deposit(){
		System.out.println("Enter amount to deposit: ");
		BigDecimal deposit = new BigDecimal(scan.nextLine());
		currentUser = bankService.deposit(currentUser, deposit);
		System.out.println("Deposited $"+deposit);
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
		System.out.println("Enter amount to withdraw: ");
		BigDecimal withdraw = new BigDecimal(scan.nextLine());
		currentUser = bankService.withdraw(currentUser, withdraw);
		System.out.println("Withdrew $"+withdraw);
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
			bankService.updateFirstName(currentUser, change);
			System.out.println("Changed your first name");
		}
		else if(input.equals("b")){
			bankService.updateLastName(currentUser, change);
			System.out.println("Changed your last name");
		}
		else if(input.equals("c")){
			bankService.updatePassword(currentUser, change);
		}
		else{
			System.out.println("Try Again");
		}
		
		updateInfo();
		
	}
	
	static void removeUser(){}
	

}