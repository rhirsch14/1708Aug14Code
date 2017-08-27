package com.bank.dao;

import java.io.Closeable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.bank.pojos.Account;
import com.bank.pojos.User;
import com.rev.utils.ConnectionFactory;

public class DBDAO implements Closeable, DAO {
	private Connection dbConn;
	
	public static void main(String[] args) {
		DBDAO d = new DBDAO();
		d.createNewUser(new User("vlad", "yev", "vlad@gmail.com", "swag"));
		System.out.println("success");
		d.close();
	}
	
	public DBDAO() {
		dbConn = ConnectionFactory.getInstance().getConnection();
	}
	
	
	public void createNewUser(User user) {
		try (CallableStatement cs = dbConn.prepareCall("{call add_user(?, ?, ?, ?)}")) {
			cs.setString(1, user.getFirst());
			cs.setString(2, user.getLast());
			cs.setString(3, user.getEmail());
			cs.setString(4, user.getPassword());
			cs.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateUserInfo(User user) {
		try (CallableStatement cs = dbConn.prepareCall("{call update_user(?, ?, ?, ?)}")) {
			cs.setInt(1, user.getId());
			cs.setString(2,  user.getFirst());
			cs.setString(3, user.getLast());
			cs.setString(4, user.getPassword());
			cs.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void createNewAccount(Account acct) {
		try (CallableStatement cs = dbConn.prepareCall("{call add_account(?, ?)}")) {
			cs.setInt(1, acct.getUser().getId());
			cs.setInt(2, acct.getType().getId());
			cs.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		try {
			dbConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
