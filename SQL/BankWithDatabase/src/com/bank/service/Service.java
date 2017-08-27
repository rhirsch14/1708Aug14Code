package com.bank.service;

import java.util.List;

import com.bank.dao.DatabaseDao;
import com.bank.pojos.Account;
import com.bank.pojos.User;

public class Service {

	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static boolean withdraw(float amount, Account account) {
		
		return dao.withdraw(amount, account.getId());
	}
	
	public static boolean deposit(float amount, Account account) {
		
		return dao.deposit(amount, account.getId());
	}
	
	public static boolean transfer(float amount, Account from, Account to) {
		
		return dao.transferFunds(amount, from.getId(), to.getId());
	}
	
	public static String createUser(User user, String password) {
		if (dao.hasUser(user.getEmail())) return "Account already associated with that email";
		
		int userId = dao.addUser(user, password);
		
		if (userId == -1) {
			return "An unknown error occurred creating account";
		}
		
		user.setId(userId);
		
		return null;
	}
	
	public static String openAccount(int typeId) {
		
		int accountId = dao.addAccount(currentUser.getId(), typeId);
		
		if (accountId == -1) {
			return "An unknown error occured";
		}		
		
		return null;
	}
	
	public static String closeAccount(Account account) {
		
		if (dao.closeAccount(account.getId())) {
			return "An unknown error occured";
		}		
		
		return null;
	}

	public static boolean updateFirstName(String firstName) {
		
		if (dao.updateFirstName(currentUser.getId(), firstName)) {
			currentUser.setFirstName(firstName);
			return true;
		}
		return false;
	}

	public static boolean updateLastName(String lastName) {
		
		if (dao.updateLastName(currentUser.getId(), lastName)) {
			currentUser.setLastName(lastName);
			return true;
		}
		return false;
	}

	public static boolean updatePassword(String password) {
		
		return dao.updatePassword(currentUser.getId(), password);
	}
	
	public static String loginUser(String email, String password) {
		if ("".equals(email) || "".equals(password)) return "You must enter an email and password";
		
		if (!dao.hasUser(email)) return "There is no account associated with that email";
		
		User user = dao.getUser(email, password);
		
		if (user != null) {
			currentUser = user;
			return null;
		}
		else {
			return "Incorrect email or password";
		}
	}
	
	public static void logoutUser() {
		currentUser = null;
	}
	
	public static List<Account> getAccounts(int userId) {
		return dao.getAccounts(userId);
	}
	
	private static User currentUser = null;	
	private static DatabaseDao dao = new DatabaseDao();
}
