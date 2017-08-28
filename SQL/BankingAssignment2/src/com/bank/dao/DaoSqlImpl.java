package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bank.pojos.*;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.util.ConnectionFactory;

// NOTE: SQL is 1-indexed rather than 0-indexed
// FIXME remove redundant code

public class DaoSqlImpl implements DaoSql {

	public String getFormatedDate(LocalDate day) {
		return day.toString();
	}

	public Person createPerson(int SSN, String firstName, String lastName, LocalDate birthDate) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "INSERT INTO person(ssn, first_name, last_name, email, birth_date, deceased)" + 
					" VALUES(?, ?, ?, ?, TO_DATE(?,'yyyy-mm-dd'), ?)";
			String[] key = new String[1];
			key[0] = "ssn";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, SSN);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, null);
			ps.setString(5, getFormatedDate(birthDate));
			ps.setInt(6, 0);		// false

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			conn.commit();
			per = new Person(SSN, firstName, lastName, birthDate);

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;

	}

	@Override
	public Person readPerson(int SSN) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM person WHERE ssn=?";
			String[] key = new String[1];
			key[0] = "ssn";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, SSN);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;
	}

	public Person readPerson(String email) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM person WHERE email=?";
			String[] key = new String[1];
			key[0] = "ssn";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				email = rs.getString(4);		// This line is redundant
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;
	}

	@Override
	public boolean updatePerson(int SSN, Person per) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "UPDATE person SET" + 
					" first_name=?, last_name=?, email=?, birth_date=TO_DATE(?,'yyyy-mm-dd'), deceased=?" + 
					" WHERE ssn=?";
			String[] key = new String[1];
			key[0] = "ssn";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, per.getFirstName());
			ps.setString(2, per.getLastName());
			ps.setString(3, per.getEmail());
			ps.setString(4, getFormatedDate(per.getBirthDate()));
			ps.setInt(5, per.isDeceased()?1:0);
			ps.setInt(6, SSN);

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return false;

	}

	@Override
	public boolean deletePerson(int SSN) {

		Person per = readPerson(SSN);
		if (per == null)
			return false;

		per.setDeceased(true);
		return updatePerson(SSN, per);

	}

	@Override
	public ArrayList<Person> readAllPersons() {
		ArrayList<Person> list = new ArrayList<Person>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * from person";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				Person per = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);

				list.add(per);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return list;
	}

	public BankUser createBankUser(Person per, String username, String password) {
		BankUser guy = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO bank_user(username, password, ssn) VALUES(?, ?, ?)";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, per.getSSN());

			ps.executeUpdate();

			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);

			conn.commit();
			guy = new BankUser(per, id, username, password);

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return guy;

	}

	public BankUser readBankUser(String username) {
		BankUser guy = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM bank_user WHERE username=?";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int id = rs.getInt(1);
				username = rs.getString(2);		// This line is redundant
				String password = rs.getString(3);
				int SSN = rs.getInt(4);

				Person per = readPerson(SSN);

				guy = new BankUser(per, id, username, password);

			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return guy;
	}

	@Override
	public BankUser readBankUser(int userId) {
		BankUser guy = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM bank_user WHERE user_id=?";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				userId = rs.getInt(1);		// This line is redundant
				String username = rs.getString(2);
				String password = rs.getString(3);
				int SSN = rs.getInt(4);

				Person per = readPerson(SSN);

				guy = new BankUser(per, userId, username, password);

			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return guy;
	}

	@Override
	public boolean updateBankUser(int userId, BankUser guy) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "UPDATE bank_user SET" + 
					" username=?, password=?" + 
					" WHERE user_id=?";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, guy.getUsername());
			ps.setString(2, guy.getPassword());
			ps.setInt(3, guy.getUserId());

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();
			
			conn.commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return false;

	}

	// Delete all of the user's accounts
	@Override
	public boolean deleteBankUser(int userId) {

		BankUser guy = readBankUser(userId);
		if (guy == null)
			return false;

		ArrayList<Account> list = readAllAccounts(userId);
		if (list == null)
			return true;	// User has no accounts, there's nothing to delete

		for (Account acc : list)	// Delete each account
			if(!deleteAccount(acc.getAccountId()))	// If something went wrong
				return false;						// return false

		return true;
	}

	@Override
	public ArrayList<BankUser> readAllBankUsers() {
		ArrayList<BankUser> list = new ArrayList<BankUser>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * from bank_user";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int userId = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				int SSN = rs.getInt(4);

				Person per = readPerson(SSN);

				BankUser guy = new BankUser(per, userId, username, password);

				list.add(guy);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return list;
	}

	@Override
	public Account createAccount(BankUser guy, BigDecimal balance, accountType type, accountLevel level) {
		Account acc = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO account(balance, opened_date, user_id, type_id, level_id, deleted)" + 
					" VALUES(?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, balance.toString());
			LocalDate day = LocalDate.now();
			ps.setString(2, getFormatedDate(day));
			ps.setInt(3, guy.getUserId());
			ps.setInt(4, type.ordinal());
			ps.setInt(5, level.ordinal());
			ps.setInt(6, 0);		// false

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			Integer id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);

			conn.commit();
			acc = new Account(id, day, balance, false, type, level, guy.getUserId());

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		// Something went wrong
		return acc;

	}

	@Override
	public Account readAccount(int accountId) {
		Account acc = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM account WHERE account_id=?";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, accountId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = LocalDate.parse(rs.getString(3).substring(0, 10));
				int userId = rs.getInt(4);
				int typeId = rs.getInt(5);
				int levelId = rs.getInt(6);
				boolean deleted = (rs.getInt(7) == 0)?false:true;

				acc = new Account(accountId, accountOpenedDate, balance, deleted, accountType.values()[typeId], accountLevel.values()[levelId], userId);

			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return acc;
	}

	@Override
	public boolean updateAccount(int accountId, Account acc) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "UPDATE account SET" + 
					" balance=?, level_id=?, deleted=?" + 
					" WHERE account_id=?";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, acc.getBalance().toString());
			ps.setInt(2, acc.getLevel().ordinal());
			ps.setInt(3, acc.isDeleted()?1:0);
			ps.setInt(4, accountId);

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return false;

	}

	@Override
	public boolean deleteAccount(int accountId) {
		
		Account acc = readAccount(accountId);
		if (acc == null)
			return false;
		
		acc.setDeleted(true);
		return updateAccount(accountId, acc);
		
	}

	@Override
	public ArrayList<Account> readAllAccounts() {
		ArrayList<Account> list = new ArrayList<Account>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * from account";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = LocalDate.parse(rs.getString(3).substring(0, 10));
				int userId = rs.getInt(4);
				int typeId = rs.getInt(5);
				int levelId = rs.getInt(6);
				boolean deleted = (rs.getInt(7) == 0)?false:true;

				Account acc = new Account(accountId, accountOpenedDate, balance, deleted, accountType.values()[typeId], accountLevel.values()[levelId], userId);

				list.add(acc);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return list;
	}

	@Override
	public ArrayList<Account> readAllAccounts(int userId) {
		ArrayList<Account> list = new ArrayList<Account>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * from account WHERE user_id=?";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = LocalDate.parse(rs.getString(3).substring(0, 10));
				userId = rs.getInt(4);
				int typeId = rs.getInt(5);
				int levelId = rs.getInt(6);
				boolean deleted = (rs.getInt(7) == 0)?false:true;

				Account acc = new Account(accountId, accountOpenedDate, balance, deleted, accountType.values()[typeId], accountLevel.values()[levelId], userId);

				list.add(acc);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return list;
	}

	public Clerk createClerk(Person per, int employeeId, String password, double hourlyWage) {
		Clerk cler = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "INSERT INTO clerk(employee_id, password, date_hired, hourly_wage, hired, ssn)" + 
					" VALUES(?, ?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "employee_id";

			LocalDate dateHired = LocalDate.now();
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, employeeId);
			ps.setString(2, password);
			ps.setString(3, getFormatedDate(dateHired));
			ps.setDouble(4, hourlyWage);
			ps.setInt(5, 1);		// true
			ps.setInt(6, per.getSSN());

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			conn.commit();
			cler = new Clerk(per, employeeId, dateHired, password, hourlyWage);

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return cler;

	}

	@Override
	public Clerk readClerk(int employeeId) {
		Clerk cler = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM clerk WHERE employee_id=?";
			String[] key = new String[1];
			key[0] = "employee_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, employeeId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				employeeId = rs.getInt(1);		// This line is redundant
				String password = rs.getString(2);
				LocalDate dateHired = LocalDate.parse(rs.getString(3).substring(0, 10));
				double hourlyWage = rs.getDouble(4);
				boolean hired = (rs.getInt(5) == 0)?false:true;
				int SSN = rs.getInt(6);
				
				Person per = readPerson(SSN);
				
				cler = new Clerk(per, employeeId, dateHired, password, hourlyWage);
				cler.setHired(hired);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return cler;
	}

	@Override
	public boolean updateClerk(int employeeId, Clerk cler) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "UPDATE clerk SET" + 
					" password=?, hourly_wage=?, hired=?" + 
					" WHERE employee_id=?";
			String[] key = new String[1];
			key[0] = "employee_id";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, cler.getPassword());
			ps.setDouble(2, cler.getHourlyWage());
			ps.setInt(3, cler.isHired()?1:0);
			ps.setInt(4, employeeId);

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return false;

	}

	@Override
	public boolean deleteClerk(int employeeId) {

		Clerk cler = readClerk(employeeId);
		if (cler == null)
			return false;

		cler.setHired(false);
		return updateClerk(employeeId, cler);

	}

/*
	@Override
	public ArrayList<Clerk> readAllClerks() {
		ArrayList<Clerk> list = new ArrayList<Clerk>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * from clerk";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				Clerk cler = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);

				list.add(per);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return list;
	}
*/
	public ArrayList<Clerk> readAllClerks() {
		// TODO method stub
		return null;
	}
}
