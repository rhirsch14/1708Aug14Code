package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bank.pojos.Account;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.BankUser;
import com.bank.pojos.Clerk;
import com.bank.pojos.Person;
import com.bank.util.ConnectionFactory;

// NOTE: SQL is 1-indexed rather than 0-indexed

public class DaoSqlImpl implements DaoSql {

	public String getFormattedDate(LocalDate day) {
		if (day == null)
			return null;
		return day.toString();
	}
	public LocalDate fromFormattedDate(String str) {
		if (str == null || str.equals("null"))
			return null;
		
		return LocalDate.parse(str.substring(0, 10));
	}

	public Person createPerson(String firstName, String lastName, String email, boolean deceased) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			// No semi-colon inside the quotes
			String sql = "INSERT INTO person(first_name, last_name, email, deceased)" + 
					" VALUES(?, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "person_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setInt(4, deceased?1:0);

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);
			
			conn.commit();
			per = new Person(id, firstName, lastName, email);
			per.setDeceased(deceased);

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;

	}
	
	public Person createPerson(String firstName, String lastName, String email, LocalDate birthDate, boolean deceased) {
		Person per = null;
		
		if (birthDate == null)
			return createPerson(firstName, lastName, email, deceased);
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			// No semi-colon inside the quotes
			String sql = "INSERT INTO person(first_name, last_name, email, birth_date, deceased)" + 
					" VALUES(?, ?, ?, TO_DATE(?,'yyyy-mm-dd'), ?)";
			String[] key = new String[1];
			key[0] = "person_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, getFormattedDate(birthDate));
			ps.setInt(5, deceased?1:0);

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);
			
			conn.commit();
			per = new Person(id, firstName, lastName, email);
			per.setBirthDate(birthDate);
			per.setDeceased(deceased);

		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;

	}

	@Override
	public Person readPerson(int personId) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * FROM person WHERE person_id=?";
			String[] key = new String[1];
			key[0] = "person_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, personId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				personId = rs.getInt(1);		// This line is redundant
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = fromFormattedDate(rs.getString(5));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(personId, firstName, lastName, email);
				per.setBirthDate(birthDate);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;
	}

	public Person readPerson(String email) {
		Person per = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * FROM person WHERE email=?";
			String[] key = new String[1];
			key[0] = "person_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int personId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				email = rs.getString(4);		// This line is redundant
				LocalDate birthDate = fromFormattedDate(rs.getString(5));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(personId, firstName, lastName, email);
				per.setBirthDate(birthDate);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return per;
	}

	@Override
	public boolean updatePerson(int personId, Person per) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			// No semi-colon inside the quotes
			String sql = "UPDATE person SET" + 
					" first_name=?, last_name=?, email=?, birth_date=TO_DATE(?,'yyyy-mm-dd'), deceased=?" + 
					" WHERE person_id=?";
			String[] key = new String[1];
			key[0] = "person_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, per.getFirstName());
			ps.setString(2, per.getLastName());
			ps.setString(3, per.getEmail());
			ps.setString(4, getFormattedDate(per.getBirthDate()));
			ps.setInt(5, per.isDeceased()?1:0);
			ps.setInt(6, personId);

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
	public boolean deletePerson(int personId) {

		Person per = readPerson(personId);
		if (per == null)
			return false;

		per.setDeceased(true);
		return updatePerson(personId, per);

	}

	@Override
	public ArrayList<Person> readAllPersons() {
		ArrayList<Person> list = new ArrayList<Person>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from person";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int personId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = fromFormattedDate(rs.getString(5));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				Person per = new Person(personId, firstName, lastName, email);
				per.setBirthDate(birthDate);
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "INSERT INTO bank_user(username, password, person_id) VALUES(?, ?, ?)";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, per.getPersonId());

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

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
				int personId = rs.getInt(4);

				Person per = readPerson(personId);

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

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
				int personId = rs.getInt(4);

				Person per = readPerson(personId);

				guy = new BankUser(per, userId, username, password);

			}
		} catch (SQLException e) {
			System.out.println("Database error");
		}

		return guy;
	}

	@Override
	public boolean updateBankUser(int userId, BankUser guy) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from bank_user";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int userId = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				int personId = rs.getInt(4);

				Person per = readPerson(personId);

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "INSERT INTO account(balance, opened_date, user_id, type_id, level_id, deleted)" + 
					" VALUES(?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, balance.toString());
			LocalDate day = LocalDate.now();
			ps.setString(2, getFormattedDate(day));
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * FROM account WHERE account_id=?";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, accountId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = fromFormattedDate(rs.getString(3));
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from account";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = fromFormattedDate(rs.getString(3));
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from account WHERE user_id=?";
			String[] key = new String[1];
			key[0] = "user_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getString(2));
				LocalDate accountOpenedDate = fromFormattedDate(rs.getString(3));
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			// No semi-colon inside the quotes
			String sql = "INSERT INTO clerk(employee_id, password, date_hired, hourly_wage, hired, person_id)" + 
					" VALUES(?, ?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "employee_id";

			LocalDate dateHired = LocalDate.now();
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, employeeId);
			ps.setString(2, password);
			ps.setString(3, getFormattedDate(dateHired));
			ps.setDouble(4, hourlyWage);
			ps.setInt(5, 1);		// true
			ps.setInt(6, per.getPersonId());

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * FROM clerk WHERE employee_id=?";
			String[] key = new String[1];
			key[0] = "employee_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, employeeId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				employeeId = rs.getInt(1);		// This line is redundant
				String password = rs.getString(2);
				LocalDate dateHired = fromFormattedDate(rs.getString(3));
				double hourlyWage = rs.getDouble(4);
				boolean hired = (rs.getInt(5) == 0)?false:true;
				int personId = rs.getInt(6);
				
				Person per = readPerson(personId);
				
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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

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

		try(Connection conn = ConnectionFactory.getInstance().getConnection();
		        AutoSetAutoCommit a = new AutoSetAutoCommit(conn,false);
		        AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from clerk";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int personId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = fromFormattedDate(rs.getString(5));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				Clerk cler = new Person(personId, firstName, lastName, birthDate);
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