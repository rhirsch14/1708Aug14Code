package com.bank.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bank.pojos.*;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;

public interface Dao {
	
	// Person
	public Person createPerson(String firstName, String lastName, String email, LocalDate birthDate, boolean deceased);
	public Person readPerson(int personId);
	// change any person's field except their uniquely identifying keys
	public boolean updatePerson(int personId, Person per);
	// A deleted person is merely marked as deceased
	public boolean deletePerson(int personId);
	public ArrayList<Person> readAllPersons();
	
	// BankUser
	public BankUser createBankUser(Person per, String username, String password);
	public BankUser readBankUser(int userId);
	// change any BankUser's fields except their uniquely identifying keys
	public boolean updateBankUser(int userId, BankUser guy);
	// A deleted User merely has their account marked as deleted
	public boolean deleteBankUser(int userId);
	public ArrayList<BankUser> readAllBankUsers();	
	
	// Clerk
	public Clerk createClerk(Person per, int employeeId, String password, double hourlyWage);
	public Clerk readClerk(int employeeId);
	// change any clerk's field except their uniquely identifying keys
	public boolean updateClerk(int employeeId, Clerk cler);
	// A deleted Clerk is merely marked as no longer hired
	public boolean deleteClerk(int employeeId);
	public ArrayList<Clerk> readAllClerks();
	
	// Account
	public Account createAccount(BankUser guy, BigDecimal balance, accountType type, accountLevel level);
	public Account readAccount(int accountId);
	// change any account's field except their uniquely identifying keys
	public boolean updateAccount(int accountId, Account acc);
	// A deleted Account is merely marked as deleted
	public boolean deleteAccount(int accountId);
	public ArrayList<Account> readAllAccounts();
	public ArrayList<Account> readAllAccounts(int userId);
	
}