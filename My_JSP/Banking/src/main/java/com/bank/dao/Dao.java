package com.bank.dao;

import java.util.List;

import com.bank.pojos.Account;
import com.bank.pojos.User;

/**
 * Send and receive from data source
 * 
 * @author Nathan Koszuta
 *
 */
public interface Dao {

	/**
	 * Creates a new User in the database
	 * 
	 * @param user
	 *            Initialized with non-null first name, last name, and email
	 * @param password
	 * @return USERID created for new User, return -1 if add failed
	 */
	public int addUser(User user, String password);

	/**
	 * Returns whether a User already exists with a specific email in the
	 * database
	 * 
	 * @param email
	 * @return Whether the email is already in use
	 */
	public boolean hasUser(String email);

	/**
	 * Gets the User with matching email and password from the database
	 * 
	 * @param email
	 * @param password
	 * @return User from database, returns null if User doesn't exist
	 */
	public User getUser(String email, String password);

	/**
	 * Creates a new account for the specified User in the database
	 * 
	 * @param userId
	 *            User to which account belongs
	 * @param typeId
	 *            AccountType for new Account
	 * @return ACCOUNTID created for new Account, returns -1 if create failed
	 */
	public int addAccount(int userId, int typeId);

	/**
	 * Get all accounts belonging to a specific User from the database
	 * 
	 * @param userId
	 *            User to whom accounts belong
	 * @return List of accounts belonging to the User
	 */
	public List<Account> getAccounts(int userId);

	/**
	 * Deactivates specified Account in the database
	 * 
	 * @param accountId
	 *            Account to be deactivated
	 * @return Whether the Account was successfully deactivated
	 */
	public boolean closeAccount(int accountId);

	/**
	 * Gets the current balance of the specified account from the database
	 * 
	 * @param accountId
	 *            Account from which to get balance
	 * @return The Account's current balance, returns -1.0f on error
	 */
	public float getBalance(int accountId);

	/**
	 * Withdraw funds from the specified Account in the database
	 * 
	 * @param amount
	 *            Funds to withdraw
	 * @param accountId
	 *            Account to withdraw from
	 * @return Whether the funds were successfully withdrawn from the account
	 */
	public boolean withdraw(float amount, int accountId);

	/**
	 * Deposit funds into the specified Account in the database
	 * 
	 * @param amount
	 *            Funds to deposit
	 * @param accountId
	 *            Account to withdraw from
	 * @return Whether the funds were successfully deposited into the account
	 */
	public boolean deposit(float amount, int accountId);

	/**
	 * Transfer funds from one Account to another in the database
	 * 
	 * @param amount
	 *            Funds to transfer
	 * @param fromAccountId
	 *            Source of funds
	 * @param toAccountId
	 *            Destination of funds
	 * @return Whether the funds were successfully transfered across Accounts
	 */
	public boolean transferFunds(float amount, int fromAccountId,
			int toAccountId);

	/**
	 * Updates the first name of the specified User in the database
	 * 
	 * @param userId
	 *            User to be updated
	 * @param firstName
	 *            New value
	 * @return Whether the first name of the User was successfully updated
	 */
	public boolean updateFirstName(int userId, String firstName);

	/**
	 * Updates the last name of the specified User in the database
	 * 
	 * @param userId
	 *            User to be updated
	 * @param lastName
	 *            New value
	 * @return Whether the last name of the User was successfully updated
	 */
	public boolean updateLastName(int userId, String lastName);

	/**
	 * Updates the password of the specified User in the database
	 * 
	 * @param userId
	 *            User to be updated
	 * @param password
	 *            New value
	 * @return Whether the password of the User was successfully updated
	 */
	public boolean updatePassword(int userId, String password);
}
