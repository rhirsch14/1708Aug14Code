package com.revature.andy.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

import com.revature.andy.pojos.Reimbursement;
import com.revature.andy.pojos.ReimStatus;
import com.revature.andy.pojos.User;

public interface DAOInterface {
	
	// Accounts

	public int addUser(User u);
	
	public int updateUser(User u);

	public int addReimbursement(User u, String description, double amount);

	public int updateReimbursement(int reimID, User user, ReimStatus rs, String notes);

	public User getUser(int userID);

	public User getUser(String email, String pwd);

	public HashSet<User> getUsers();
	
	public HashSet<Reimbursement> getReims(Connection con) throws SQLException;
	
	public ReimStatus getReimStatusFromID(int statusID);
}
