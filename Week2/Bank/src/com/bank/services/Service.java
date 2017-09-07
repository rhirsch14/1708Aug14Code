package com.bank.services;

import java.util.ArrayList;
import java.util.Scanner;

import com.bank.dao.DAO;
import com.bank.dao.DAOImpl;
import com.bank.pojos.Account;
import com.bank.pojos.Users;

public class Service {
	static DAOImpl dao = new DAOImpl();
	static Users user = new Users();
	static Scanner scan;
	
	public int addUser(String fn, String ln, String email, String password){	
		
		int id = dao.addUser(fn, ln, email, password);
		return id;
		
	}
	
	public Users getUser(String email, String password){
		
		 Users user =  dao.getUser(email, password);
		
		return user;
	}
	public Account createAccount(int u, int typeId){
		Account acc = dao.createAccount(u, typeId);
		return acc;
	}
	public static Account getAccount(int user_id){
		Account acc = dao.getAccount(user_id);
		return acc;
	}
	
	public double getbalance(Account acc){
		double bal = acc.getBalance();		
		return bal;		
	}
	
	public double deposit(int bal, int amt, int aId){
		bal+=amt;		
		
		return bal;
	}
	public double withdrawal(int bal, int amt, int aId){
		bal-=amt;
		
		return bal;
	}
	public double transfer(){
		System.out.println("Still to come:");
		return 0;
	}

}