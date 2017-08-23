package com.bank.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.bank.dao.DAO;
import com.bank.dao.DAOTextImpl;
import com.bank.pojos.User;
//import com.ex.pojos.Stude


public class Service {
	static DAO dao = new DAOTextImpl();
	static ArrayList<User> user = new ArrayList();
	



	public  static User login(String email, String password){
		user = dao.getUser();
		for(User i: user){
			if(i.getEmail().equals(email ) &&  i.getPassword().equals(password)){
				System.out.println(i.getFirstname());
				return i ;
			}
			else
				System.out.println("You don't have an Account, ");			
				
		}
		return null;
	}
	public  User createAccount(String firstname, String lastname, String email, String password) {
		int id = 0;
		double balance = 0;
		User newUser = new User(id, firstname, lastname, email, password, balance);
		dao.addUser(newUser);
		return newUser;
		
	}
	
	public static void logout(){
		System.out.println("You have been Logged out");
	}
	public static boolean withdraw(User user, double amount) {
		double balance = user.getBalance();
		if(balance < amount || balance == 0) {
			return false;
			}
		else {
			user.setBalance(balance - amount);
			System.out.println("You withdrew " + amount);
			return true;
			}
		
		}
	
	public static void deposit(User user, double amount){
		double current = user.getBalance();
		user.setBalance(current+(amount));
			
		}
	

	

}