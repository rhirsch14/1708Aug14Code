package com.revature.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.model.Account;
import com.revature.model.User;
import com.revature.util.ConnectionSingleton;

/**
 * Concrete implementation of interface AccountDAO.
 * @author Will Underwood
 */
public class AccountDAOImpl implements AccountDAO<Account> {
	
	private UserDAO<User> userDAO;
	
	public AccountDAOImpl() {
		this.userDAO = new UserDAOImpl();
	}

	/**
	 * Adds a new user account to the database.
	 * @precondition Account cannot be null
	 * @param account - Account whose details will be placed in the database
	 * @return The id of the inserted account
	 */
	public int createAccount(Account account) {
		if (account == null) {
			throw new IllegalArgumentException("Account cannot be null");
		}
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO bankaccount(account_id, balance, user_id, account_type_id) VALUES(DEFAULT, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "account_id";
			PreparedStatement statement = conn.prepareStatement(sql, key);
			statement.setBigDecimal(1, account.getBalance());
			statement.setInt(2, account.getUserID());
			statement.setInt(3, account.getAccountTypeID());
			statement.executeUpdate();
			int id = 0;
			ResultSet returnedKeys = statement.getGeneratedKeys();
			while(returnedKeys.next()) {
				id = returnedKeys.getInt(1);
			}
			conn.commit();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User user = this.userDAO.readUser(account.getUserID());
		if (account.getAccountTypeID() == 1) {
			user.setHasChecking(true);
		}
		if (account.getAccountTypeID() == 2) {
			user.setHasSavings(true);
		}
		this.userDAO.updateUser(user);
		return 0;
	}

	/**
	 * Returns all Accounts in database
	 * @precondition None
	 * @param None
	 * @return A collection of all Accounts
	 */
	@Override
	public ArrayList<Account> readAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a single account from the database.
	 * @precondition ID cannot be less than 1
	 * @param ID - ID of the user who owns the account
	 * @return A single Account
	 */
	@Override
	public Account readAccount(int userID, int accountTypeID) {
		if (userID < 1 || accountTypeID < 1) {
			throw new IllegalArgumentException("ID cannot be less than 1");
		}
		Account account = new Account();
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM bankaccount WHERE user_id = ? AND account_type_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			statement.setInt(2, accountTypeID);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				int account_id = results.getInt("account_id");
				BigDecimal balance = new BigDecimal(results.getDouble("balance"));
				int user_id = results.getInt("user_id");
				int account_type_id = results.getInt("account_type_id");
				account.setAccountID(account_id);
				account.setBalance(balance);
				account.setUserID(user_id);
				account.setAccountTypeID(account_type_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	/**
	 * Edits the data of the specified account.
	 * @precondition Account cannot be null
	 * @param Account - The account whose data will be edited
	 * @postcondition Account was edited
	 */
	@Override
	public void updateAccount(Account account) {
		try(Connection conn = ConnectionSingleton.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			String sql = "UPDATE bankaccount SET balance = ? WHERE account_id = ?"; // do not use semicolon
			String[] key = new String[1];
			key[0] = "account_id";
			PreparedStatement statement = conn.prepareStatement(sql, key);
			statement.setDouble(1, account.getBalance().doubleValue());
			statement.setInt(2, account.getAccountID());
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Disables the account whose ID is specified
	 * @precondition ID cannot be less than 1
	 * @param ID - The ID of the account to be disabled
	 * @return - The number of rows affected. Should be 1.
	 */
	@Override
	public void destroyAccount(int id) {
		try(Connection conn = ConnectionSingleton.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM bankaccount WHERE account_id = ?"; // do not use semicolon
			String[] key = new String[1];
			key[0] = "account_id";
			PreparedStatement statement = conn.prepareStatement(sql, key);
			statement.setInt(1, id);
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}