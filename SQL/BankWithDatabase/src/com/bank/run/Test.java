package com.bank.run;

import java.util.List;

import com.bank.dao.DatabaseDao;
import com.bank.pojos.Account;
import com.bank.pojos.User;

public class Test {
	
	public static void main(String[] args) {
		
		DatabaseDao dao = new DatabaseDao();
		
		System.out.println(dao.getUser("test@test.com", "p4ssw0rd"));
		System.out.println(dao.getUser("arod@packers.com", "gopackgo"));
		System.out.println(dao.getUser("test@TEST.com", "p4ssw0rd"));
		System.out.println();
		
		System.out.println("Create account = " + dao.addAccount(1, 2));
		System.out.println("Create account = " + dao.addAccount(3, 1));
		System.out.println();
		
		System.out.println("Has cobb = " + dao.hasUser("cobb@packers.com"));
		System.out.println("Get cobb (good) = " + dao.getUser("cobb@packers.com", "ogkcapog"));
		System.out.println("Get cobb (bad)  = " + dao.getUser("cobb@packers.com", "gopkgo"));
		System.out.println();
		
		System.out.println("Update Randall to Randy = " + dao.updateFirstName(7, "Randy"));
		System.out.println("Update Cobb to Bobb = " + dao.updateLastName(7, "Bobb"));
		System.out.println("Update gopackgo to ogkcapog = " + dao.updatePassword(7, "ogkcapog"));
		System.out.println();

		System.out.println("Has fake = " + dao.hasUser("fake@fake.com"));
		System.out.println();
		/*
		User cobb = new User("Randall", "Cobb", "cobb@packers.com");
		System.out.println(dao.addUser(cobb, "gopackgo"));
		System.out.println();
		
		User ty = new User("Ty", "Montgomery", "asdf@packers.com");
		System.out.println("Add user Ty = " + dao.addUser(ty, "gopackgo"));
		System.out.println();
		//*/
		System.out.println("1000 balance = " + dao.getBalance(1000));
		System.out.println("1001 balance = " + dao.getBalance(1001));
		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println("1003 balance = " + dao.getBalance(1003));
		System.out.println();

		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println("Add $123 to 1002 = " + dao.deposit(123, 1002));
		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println("Add $50 to 1002 = " + dao.deposit(50, 1002));
		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println("Withdraw $123 from 1002 = " + dao.withdraw(123, 1002));
		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println("Withdraw $50 from 1002 = " + dao.withdraw(50, 1002));
		System.out.println("1002 balance = " + dao.getBalance(1002));
		System.out.println();
		
		List<Account> list = dao.getAccounts(3);
		System.out.println("User 3 accounts");
		for (Account a : list) {
			System.out.println(a);
		}
		System.out.println();
		
		list = dao.getAccounts(2);
		System.out.println("User 2 accounts");
		for (Account a : list) {
			System.out.println(a);
		}
		System.out.println();
		
		System.out.println("Transfer $70 from 1000 to 1001 = " + dao.transferFunds(.01f, 1000, 1001));
	}
}
