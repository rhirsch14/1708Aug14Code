package com.bank.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.bank.dao.DAOTextImpl;
import com.bank.pojos.User;
import com.bank.service.Service;

public class RunBank {
	static DAOTextImpl g;
	static Scanner scan = new Scanner(System.in);
	static Service bankService = new Service();

	public static void main(String[] args) {
		
		Service bankService = new Service();
		System.out.println("Welcom to THE BANK:");
		boolean here = true;
		Menu();
		
		
		
			
	}
	 static void Menu(){
		 System.out.println("Please choose one of the option below:");
		 System.out.println("1:Login");
		 System.out.println("2:Create Account");
		 System.out.print("Enter you choice here:");
		 int n = scan.nextInt();
		 switch(n){
			 case 1:
				 String email, password;
				 System.out.println("Enter your Email:");
				 email = scan.next();
				 System.out.println("enter your password");
				 password = scan.next();
				 bankService.login(email,password);
				 loginMenu();
				 System.out.println();
				 break;
			 case 2:
				 String firstname, lastname, email1, pass;
				 System.out.println("Enter yourFirst Name:");
				 firstname = scan.next();
				 System.out.println("Enter your Last Name:");
				 lastname = scan.next();
				 System.out.println("Enter your Email:");
				 email1= scan.next();
				 System.out.println("Enter your password:");
				 pass = scan.next();
				 bankService.createAccount(firstname, lastname, email1, pass);
				 //System.out.println("soon to come:");
				 break;
			default:
				System.out.println("GoodBye");
				
		 }
	 }
	 static void loginMenu(){
		 System.out.println();
		 System.out.println("Please choose one of the option below:");
		 System.out.println("1:Whithdraw Money");
		 System.out.println("2:Deposti Money");
		 System.out.println("3:View Account Blance");
		 System.out.println("4:Edit Account");
		 System.out.println("5:Logout");
		 //switch (option{
			// case 1:
				 
		 }
	 }
		//static void login(){}	
		
		/*
		 * As a User I can:
		 * - login
		 * - create an account
		 * - withdraw or deposit funds
		 * - view my account balance
		 * - edit my information
		 * 
		 *  
		 *  Tech Spec
		 *  - core java, file i/o
		 * 
		 * 
		 */
		// TODO Auto-generated method stub

	

