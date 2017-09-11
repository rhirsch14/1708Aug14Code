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
// This class has been optimized to reduce code duplication

public class DaoImpl {

	public static boolean connection;
	
	// Attempt to connect to database upfront.
	static {
		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {
			
			connection = true;
			
		} catch (Exception e) {
			System.out.println("Error! Could not connect to database!");
			connection = false;
		}

	}
	
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

	// Need to set either String, int, double, LocalDate, or boolean
	private void setPreparedStatement(int i, PreparedStatement ps, Object obj) throws SQLException {
		if (obj instanceof String)
			ps.setString(i, (String) obj);
		else if (obj instanceof Integer)
			ps.setInt(i, (int) obj);
		else if (obj instanceof Double)
			ps.setDouble(i, (double) obj);
		else if (obj instanceof Boolean)
			ps.setInt(i, (Boolean) obj ? 1 : 0); // SQL does not have booleans.
													// Saved as an int
		else if (obj instanceof LocalDate)
			ps.setString(i, getFormattedDate((LocalDate) obj));
		else
			System.out.println("Error. Illegal data type");
	}

	///////////////////////////////////////////////////////////////////////////////
	// CREATE methods
	///////////////////////////////////////////////////////////////////////////////

	private Integer create(String sql, String[] key, ArrayList<Object> objects) {
		Integer id = null;

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			for (int i = 0; i < objects.size(); i++)
				setPreparedStatement(i + 1, ps, objects.get(i));

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			ResultSet pk = ps.getGeneratedKeys();
			while (pk.next())
				id = pk.getInt(1);

			conn.commit();

		} catch (SQLException e) {
			System.out.println("Database error on create");
		}

		return id;

	}

	public Person createPerson(String firstName, String lastName, String email, boolean deceased) {
		ArrayList<Object> objects = new ArrayList<Object>();

		// No semi-colon inside the quotes
		String sql = "INSERT INTO person(first_name, last_name, email, deceased)" + " VALUES(?, ?, ?, ?)";
		String[] key = {"person_id"};

		objects.add((Object) firstName);
		objects.add((Object) lastName);
		objects.add((Object) email);
		objects.add((Object) deceased);

		Integer id = create(sql, key, objects);

		Person per = null;
		if (id != null) {
			per = new Person(id, firstName, lastName, email);
			per.setDeceased(deceased);
		}

		return per;
	}

	public Person createPerson(String firstName, String lastName, String email, LocalDate birthDate, boolean deceased) {

		if (birthDate == null)
			return createPerson(firstName, lastName, email, deceased);

		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "INSERT INTO person(first_name, last_name, email, birth_date, deceased)"
				+ " VALUES(?, ?, ?, TO_DATE(?,'yyyy-mm-dd'), ?)";
		String[] key = {"person_id"};

		objects.add((Object) firstName);
		objects.add((Object) lastName);
		objects.add((Object) email);
		objects.add((Object) birthDate);
		objects.add((Object) deceased);

		Integer id = create(sql, key, objects);

		Person per = null;
		if (id != null) {
			per = new Person(id, firstName, lastName, email);
			per.setBirthDate(birthDate);
			per.setDeceased(deceased);
		}

		return per;
	}

	public BankUser createBankUser(Person per, String username, String password) {

		String sql = "INSERT INTO bank_user(username, password, person_id) VALUES(?, ?, ?)";

		ArrayList<Object> objects = new ArrayList<Object>();

		String[] key = {"user_id"};

		objects.add((Object) username);
		objects.add((Object) password);
		objects.add((Object) per.getPersonId());

		Integer id = create(sql, key, objects);

		BankUser user = null;
		if (id != null)
			user = new BankUser(per, id, username, password);

		return user;
	}

	public Account createAccount(BankUser guy, BigDecimal balance, accountType type, accountLevel level) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "INSERT INTO account(balance, opened_date, user_id, type_id, level_id, deleted)"
				+ " VALUES(?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?, ?)";
		String[] key = {"account_id"};

		LocalDate day = LocalDate.now();
		objects.add((Object) balance.toString());
		objects.add((Object) day);
		objects.add((Object) guy.getUserId());
		objects.add((Object) type.ordinal());
		objects.add((Object) level.ordinal());
		objects.add((Object) 0); // deleted = false

		Integer id = create(sql, key, objects);

		Account acc = null;
		if (id != null)
			acc = new Account(id, day, balance, false, type, level, guy.getUserId());

		return acc;
	}

	public Clerk createClerk(Person per, int employeeId, String password, double hourlyWage) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "INSERT INTO clerk(employee_id, password, date_hired, hourly_wage, hired, person_id)"
				+ " VALUES(?, ?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?)";
		String[] key = {"employee_id"};

		LocalDate dateHired = LocalDate.now();
		objects.add((Object) employeeId);
		objects.add((Object) password);
		objects.add((Object) dateHired);
		objects.add((Object) hourlyWage);
		objects.add((Object) 1); // hired = true
		objects.add((Object) per.getPersonId());

		Integer id = create(sql, key, objects);

		Clerk cler = null;
		if (id != null)
			cler = new Clerk(per, employeeId, dateHired, password, hourlyWage);

		return cler;
	}

	///////////////////////////////////////////////////////////////////////////////
	// READ methods
	///////////////////////////////////////////////////////////////////////////////

	private Object read(String sql, String[] key, Object id) {
		Object obj = null;
		
		if (id == null)
			return null;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			setPreparedStatement(1, ps, id);

			ResultSet rs = ps.executeQuery();

			ArrayList<Object> objectList = getObjectsFromResultSet(rs, key[0]);
			if (objectList.size() > 0)
				obj = objectList.get(0);

		} catch (SQLException e) {
			System.out.println("Database error on read");
		}

		return obj;
	}

	public Person readPerson(int personId) {
		String sql = "SELECT * FROM person WHERE person_id=?";
		String[] key = {"person_id"};

		return (Person) read(sql, key, personId);
	}

	public Person readPerson(String email) {
		String sql = "SELECT * FROM person WHERE email=?";
		String[] key = {"person_id"};

		return (Person) read(sql, key, email);
	}

	public BankUser readBankUser(String username) {
		String sql = "SELECT * FROM bank_user WHERE username=?";
		String[] key = {"user_id"};

		return (BankUser) read(sql, key, username);
	}

	public BankUser readBankUser(int userId) {
		String sql = "SELECT * FROM bank_user WHERE user_id=?";
		String[] key = {"user_id"};

		return (BankUser) read(sql, key, userId);
	}

	public Account readAccount(int accountId) {
		String sql = "SELECT * FROM account WHERE account_id=?";
		String[] key = {"account_id"};

		return (Account) read(sql, key, accountId);
	}

	public Clerk readClerk(int employeeId) {
		String sql = "SELECT * FROM clerk WHERE employee_id=?";
		String[] key = {"employee_id"};

		return (Clerk) read(sql, key, employeeId);
	}

	private ArrayList<Object> getObjectsFromResultSet(ResultSet rs, String key) throws SQLException {
		if (key.equals("person_id"))
			return getPersonsFromResultSet(rs);
		else if (key.equals("user_id"))
			return getBankUsersFromResultSet(rs);
		else if (key.equals("account_id"))
			return getAccountsFromResultSet(rs);
		else if (key.equals("employee_id"))
			return getClerksFromResultSet(rs);
		else if (key.equals("String"))
			return getStringsFromResultSet(rs);

		return null;
	}

	private ArrayList<Object> getPersonsFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> personList = new ArrayList<Object>();

		while (rs.next()) {
			int personId = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String email = rs.getString(4);
			LocalDate birthDate = fromFormattedDate(rs.getString(5));
			boolean deceased = (rs.getInt(6) == 0) ? false : true;

			Person per = new Person(personId, firstName, lastName, email);
			per.setBirthDate(birthDate);
			per.setDeceased(deceased);
			personList.add((Object) per);
		}

		return personList;
	}

	private ArrayList<Object> getBankUsersFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> bankUserList = new ArrayList<Object>();

		while (rs.next()) {
			int id = rs.getInt(1);
			String username = rs.getString(2);
			String password = rs.getString(3);
			int personId = rs.getInt(4);

			Person per = readPerson(personId);
			if (per == null)
				return bankUserList;

			bankUserList.add((Object) new BankUser(per, id, username, password));

		}

		return bankUserList;
	}

	private ArrayList<Object> getAccountsFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> accountList = new ArrayList<Object>();

		while (rs.next()) {
			int accountId = rs.getInt(1);
			BigDecimal balance = new BigDecimal(rs.getString(2));
			LocalDate accountOpenedDate = fromFormattedDate(rs.getString(3));
			int userId = rs.getInt(4);
			int typeId = rs.getInt(5);
			int levelId = rs.getInt(6);
			boolean deleted = (rs.getInt(7) == 0) ? false : true;

			accountList.add((Object) new Account(accountId, accountOpenedDate, balance, deleted,
					accountType.values()[typeId], accountLevel.values()[levelId], userId));

		}

		return accountList;
	}

	private ArrayList<Object> getClerksFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> clerkList = new ArrayList<Object>();

		while (rs.next()) {
			int employeeId = rs.getInt(1);
			String password = rs.getString(2);
			LocalDate dateHired = fromFormattedDate(rs.getString(3));
			double hourlyWage = rs.getDouble(4);
			boolean hired = (rs.getInt(5) == 0) ? false : true;
			int personId = rs.getInt(6);

			Person per = readPerson(personId);

			Clerk cler = new Clerk(per, employeeId, dateHired, password, hourlyWage);
			cler.setHired(hired);

			clerkList.add((Object) cler);
		}

		return clerkList;
	}
	
	private ArrayList<Object> getStringsFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> stringList = new ArrayList<Object>();

		while (rs.next())
			stringList.add(rs.getString(1));
			
		return stringList;
	}

	///////////////////////////////////////////////////////////////////////////////
	// UPDATE methods
	///////////////////////////////////////////////////////////////////////////////

	private boolean update(String sql, String[] key, ArrayList<Object> objects) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			for (int i = 0; i < objects.size(); i++)
				setPreparedStatement(i + 1, ps, objects.get(i));

			ps.executeUpdate();

			conn.commit();
			return true;
			
		} catch (SQLException e) {
			System.out.println("Database error on update");
		}

		return false;

	}
	
	public boolean updatePerson(int personId, Person per) {
		ArrayList<Object> objects = new ArrayList<Object>();
		
		String sql = "UPDATE person SET"
				+ " first_name=?, last_name=?, email=?, birth_date=TO_DATE(?,'yyyy-mm-dd'), deceased=?"
				+ " WHERE person_id=?";
		String[] key = {"person_id"};

		objects.add((Object) per.getFirstName());
		objects.add((Object) per.getLastName());
		objects.add((Object) per.getEmail());
		if (per.getBirthDate() == null) {
			sql = "UPDATE person SET first_name=?, last_name=?, email=?, deceased=? WHERE person_id=?";
		} else objects.add((Object) per.getBirthDate());
		objects.add((Object) per.isDeceased());
		objects.add((Object) personId);

		return update(sql, key, objects);

	}

	// TODO needs to update the associated person too, but both need to be in the same transaction
	public boolean updateBankUser(int userId, BankUser guy) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE bank_user SET" + " username=?, password=?" + " WHERE user_id=?";
		String[] key = {"user_id"};

		objects.add((Object)guy.getUsername());
		objects.add((Object)guy.getPassword());
		objects.add((Object)guy.getUserId());
		
		return update(sql, key, objects);
	}

	public boolean updateAccount(int accountId, Account acc) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE account SET" + " balance=?, level_id=?, deleted=?" + " WHERE account_id=?";
		String[] key = {"account_id"};

		objects.add((Object) acc.getBalance().toString());
		objects.add((Object) acc.getLevel().ordinal());
		objects.add((Object) acc.isDeleted());
		objects.add((Object) accountId);

		return update(sql, key, objects);

	}

	public boolean updateClerk(int employeeId, Clerk cler) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE clerk SET" + " password=?, hourly_wage=?, hired=?" + " WHERE employee_id=?";
		String[] key = {"employee_id"};

		objects.add((Object) cler.getPassword());
		objects.add((Object) cler.getHourlyWage());
		objects.add((Object) cler.isHired());
		objects.add((Object) employeeId);

		return update(sql, key, objects);

	}

	///////////////////////////////////////////////////////////////////////////////
	// DELETE methods
	///////////////////////////////////////////////////////////////////////////////

	public boolean deletePerson(int personId) {

		Person per = readPerson(personId);
		if (per == null)
			return false;

		per.setDeceased(true);
		return updatePerson(personId, per);

	}

	// Delete all of the user's accounts
	public boolean deleteBankUser(int userId) {

		BankUser guy = readBankUser(userId);
		if (guy == null)
			return false;

		ArrayList<Account> list = readAllAccounts(userId);
		if (list == null)
			return true; // User has no accounts, there's nothing to delete

		for (Account acc : list) // Delete each account
			if (!deleteAccount(acc.getAccountId())) // If something went wrong
				return false; // return false

		return true;
	}

	public boolean deleteAccount(int accountId) {

		Account acc = readAccount(accountId);
		if (acc == null)
			return false;

		acc.setDeleted(true);
		return updateAccount(accountId, acc);

	}

	public boolean deleteClerk(int employeeId) {

		Clerk cler = readClerk(employeeId);
		if (cler == null)
			return false;

		cler.setHired(false);
		return updateClerk(employeeId, cler);

	}

	///////////////////////////////////////////////////////////////////////////////
	// READ ALL methods
	///////////////////////////////////////////////////////////////////////////////

	private ArrayList<Object> readAll(String sql, String[] key) {
		ArrayList<Object> list = new ArrayList<Object>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			list = getObjectsFromResultSet(rs, key[0]);

		} catch (SQLException e) {
			System.out.println("Database error on readAll");
		}

		return list;
	}

	public ArrayList<Person> readAllPersons() {
		String sql = "SELECT * from person";
		String[] key = {"person_id"};

		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<Person> personList = new ArrayList<Person>();
		for (Object obj : objectList)
			personList.add((Person) obj);

		return personList;
	}

	public ArrayList<BankUser> readAllBankUsers() {
		String sql = "SELECT * from bank_user";
		String[] key = {"user_id"};

		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<BankUser> bankUserList = new ArrayList<BankUser>();
		for (Object obj : objectList)
			bankUserList.add((BankUser) obj);

		return bankUserList;
	}

	public ArrayList<Account> readAllAccounts() {
		String sql = "SELECT * from account";
		String[] key = {"account_id"};

		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<Account> accountList = new ArrayList<Account>();
		for (Object obj : objectList)
			accountList.add((Account) obj);

		return accountList;
	}

	public ArrayList<Account> readAllAccounts(int userId) {
		ArrayList<Object> list = new ArrayList<Object>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			String sql = "SELECT * from account WHERE user_id=?";
			String[] key = {"user_id"};

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			list = getAccountsFromResultSet(rs);

		} catch (SQLException e) {
			System.out.println("Database error on readAllAccounts");
		}

		ArrayList<Account> newList = new ArrayList<Account>();
		for (Object obj : list)
			newList.add((Account) obj);
		return newList;
	}

	public ArrayList<Clerk> readAllClerks() {
		String sql = "SELECT * from clerk";
		String[] key = {"employee_id"};

		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<Clerk> clerkList = new ArrayList<Clerk>();
		for (Object obj : objectList)
			clerkList.add((Clerk) obj);

		return clerkList;
	}
	
	public ArrayList<String> readAllEmails() {
		String sql = "SELECT email FROM person";
		
		return getAllStrings(sql);
	}
	
	public ArrayList<String> readAllUsernames() {
		String sql = "SELECT username FROM bank_user";
		
		return getAllStrings(sql);
	}
	
	private ArrayList<String> getAllStrings(String sql) {
		String[] key = {"String"};
		
		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<String> stringList = new ArrayList<String>();
		for (Object obj : objectList)
			stringList.add((String)obj);
		
		return stringList;
	}
	
	private ArrayList<Integer> getAllIntegers(String sql) {
		// These are taken in as strings, but then will be parsed to Integers
		String[] key = {"String"};
		
		ArrayList<Object> objectList = readAll(sql, key);
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		for (Object obj : objectList)
			integerList.add(Integer.parseInt((String)obj));
		
		return integerList;
	}

	public ArrayList<Integer> readAllPersonIds() {
		String sql = "SELECT person_id FROM person";
		
		return getAllIntegers(sql);
	}

}