package com.bank.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.pojos.Account;
import com.bank.pojos.User;
import com.bank.util.ConnectionFactory;

public class DatabaseDao implements DAO {

	public int addUser(User user, String password) {
		int id = -1;

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO USERS (FIRSTNAME, LASTNAME, EMAIL, PASSWORD) "
					   + "VALUES (?, ?, ?, ?)";

			String[] key = { "USERID" };

			PreparedStatement ps = connection.prepareStatement(sql, key);

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, password);

			ps.execute();

			ResultSet pk = ps.getGeneratedKeys();
			if (pk.next()) {
				id = pk.getInt(1);
			}

			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public boolean hasUser(String email) {
		
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT USERID FROM USERS "
					   + "WHERE LOWER(EMAIL) = ?";

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email.toLowerCase());
			ResultSet results = ps.executeQuery();

			return results.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public User getUser(String email, String password) {
		User user = null;

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT USERID, FIRSTNAME, LASTNAME, EMAIL FROM USERS "
					   + "WHERE LOWER(EMAIL) = ? AND PASSWORD = ?";

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email.toLowerCase());
			ps.setString(2, password);
			ResultSet results = ps.executeQuery();

			if (results.next()) {
				int id = results.getInt(1);
				String first = results.getString(2);
				String last = results.getString(3);
				String em = results.getString(4);

				user = new User(first, last, em);
				user.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public int addAccount(int userId, int typeId) {
		int newId = -1;

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "INSERT INTO ACCOUNT (USERID, TYPEID) "
					   + "VALUES (?, ?)";

			String[] key = { "ACCOUNTID" };

			PreparedStatement ps = connection.prepareStatement(sql, key);
			ps.setInt(1, userId);
			ps.setInt(2, typeId);

			ps.executeUpdate();
			ResultSet pk = ps.getGeneratedKeys();

			if (pk.next()) {
				newId = pk.getInt(1);
			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newId;
	}

	@Override
	public List<Account> getAccounts(int userId) {
		List<Account> accounts = new ArrayList<>();

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM ACCOUNT "
					   + "WHERE USERID = ? AND UPPER(ACTIVE) = 'Y'";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int accountId = rs.getInt("ACCOUNTID");
				int typeId = rs.getInt("TYPEID");
				int usId = rs.getInt("USERID");
				float balance = rs.getFloat("BALANCE");
				
				Account a = new Account(accountId, typeId, usId);
				a.setBalance(balance);
				
				accounts.add(a);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}

	@Override
	public boolean closeAccount(int accountId) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "CALL DEACTIVATE_ACCOUNT (?)";

			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, accountId);
			cs.executeUpdate();
			
			connection.commit();			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public float getBalance(int accountId) {
		float balance = -1.0f;
		
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNTID = ?";

			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, accountId);
			ResultSet rs = cs.executeQuery();
			
			if (rs.next()) {
				balance = rs.getFloat(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return balance;
	}

	@Override
	public boolean withdraw(float amount, int accountId) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "CALL WITHDRAW (?, ?)";
			
			CallableStatement cs = connection.prepareCall(sql);
			cs.setFloat(1, amount);
			cs.setInt(2, accountId);
			
			cs.executeUpdate();
			
			connection.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deposit(float amount, int accountId) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "CALL DEPOSIT (?, ?)";
			
			CallableStatement cs = connection.prepareCall(sql);
			cs.setFloat(1, amount);
			cs.setInt(2, accountId);
			
			cs.executeUpdate();
			
			connection.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean transferFunds(float amount, int fromAccountId, int toAccountId) {
		
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "CALL WITHDRAW (?, ?)";
			CallableStatement cs = connection.prepareCall(sql);
			cs.setFloat(1, amount);
			cs.setInt(2, fromAccountId);
			
			cs.executeUpdate();
			
			sql = "CALL DEPOSIT (?, ?)";
			cs = connection.prepareCall(sql);
			cs.setFloat(1, amount);
			cs.setInt(2, toAccountId);
			
			cs.executeUpdate();
			
			connection.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateFirstName(int userId, String firstName) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "CALL UPDATE_FIRST_NAME (?, ?)";

			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, userId);
			cs.setString(2, firstName);
			
			cs.executeUpdate();
			
			connection.commit();			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateLastName(int userId, String lastName) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "CALL UPDATE_LAST_NAME (?, ?)";

			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, userId);
			cs.setString(2, lastName);
			
			cs.executeUpdate();
			
			connection.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updatePassword(int userId, String password) {

		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			String sql = "CALL UPDATE_PASSWORD (?, ?)";

			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, userId);
			cs.setString(2, password);
			
			cs.executeUpdate();
			
			connection.commit();			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
