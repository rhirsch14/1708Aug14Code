/**
 * 
 */
package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.RUserDAO;
import com.revature.dao.ReimbursementDAO;
import com.revature.dto.EditRequestDTO;
import com.revature.dto.RegisterEmpDTO;
import com.revature.model.RUser;
import com.revature.model.Reimbursement;

/**
 * @author Will Underwood
 *
 */
public class ReimbursementService {
	
	private RUserDAO userDAO;
	private ReimbursementDAO reimbDAO;
	
	/**
	 * Makes a new Service for managing bank accounts
	 * @precondition None.
	 * @param None.
	 * @postcondition A new Service was created
	 */
	public ReimbursementService() {
		this.userDAO = new RUserDAO();
		this.reimbDAO = new ReimbursementDAO();
	}
	
	public boolean checkUserExists(String email) {
		this.errorCheckEmail(email);
		return (this.userDAO.read(email) != null);
	}

	private void errorCheckEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Email cannot be null");
		}
		if (email.equals("")) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
	}
	
	public boolean validateUserCredentials(String email, String password) {
		this.errorCheckEmail(email);
		this.errorCheckPassword(password);
		RUser user = this.userDAO.read(email);
		return password.equals(user.getPassword());
	}

	private void errorCheckPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Password cannot be null");
		}
	}
	
	public RUser getUser(String email) {
		this.errorCheckEmail(email);
		return this.userDAO.read(email);
	}
	
	public ArrayList<Reimbursement> getEmployeeReimbs(int userID) {
		if (userID < 1) {
			throw new IllegalArgumentException("User ID cannot be less than 1");
		}
		ArrayList<Reimbursement> allReimbs = this.reimbDAO.readAll();
		ArrayList<Reimbursement> empReimbs = new ArrayList<Reimbursement>();
		for (Reimbursement reimb : allReimbs) {
			if (reimb.getSubmitterID() == userID) {
				empReimbs.add(reimb);
			}
		}
		return empReimbs;
	}
	
	public ArrayList<Reimbursement> getEmployeeReimbsByEmail(String email) {
		this.errorCheckEmail(email);
		RUser user = this.userDAO.read(email);
		ArrayList<Reimbursement> reimbs = this.reimbDAO.readAll();
		ArrayList<Reimbursement> userReimbs = new ArrayList<Reimbursement>();
		for (Reimbursement reimb : reimbs) {
			if (reimb.getSubmitterID() == user.getRUserID()) {
				userReimbs.add(reimb);
			}
		}
		return userReimbs;
	}
	
	public int editUser(RUser user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		return this.userDAO.update(user);
	}
	
	/**
	 * Submits a reimbursement request to the database
	 * @precondition Reimbursement parameter cannot be null
	 * @param reimb The reimbursement to be submitted
	 * @return The number of rows inserted. Should be 1.
	 */
	public int submitReimb(Reimbursement reimb) {
		if (reimb == null) {
			throw new IllegalArgumentException("Reimbursement cannot be null");
		}
		return this.reimbDAO.create(reimb);
	}
	
	public ArrayList<Reimbursement> getAllReimbs() {
		return this.reimbDAO.readAll();
	}
	
	public ArrayList<RUser> getAllEmps() {
		ArrayList<RUser> allUsers = this.userDAO.readAll();
		ArrayList<RUser> allEmps = new ArrayList<RUser>();
		for (RUser user : allUsers) {
			if (!user.isManager()) {
				allEmps.add(user);
			}
		}
		return allEmps;
	}
	
	public Reimbursement getSingleReimb(int rID) {
		return this.reimbDAO.read(rID);
	}
	
	public int updateReimb(EditRequestDTO reqData) {
		if (reqData == null) {
			throw new IllegalArgumentException("Reimbursement request data cannot be null");
		}
		Reimbursement reimbToEdit = this.reimbDAO.read(reqData.getrID());
		java.sql.Date currentDateTime = new java.sql.Date(new java.util.Date().getTime());
		reimbToEdit.setDateResolved(currentDateTime);
		reimbToEdit.setResolutionNotes(reqData.getResolutionNotes());
		reimbToEdit.setStatusID(reqData.getStatus());
		return this.reimbDAO.update(reimbToEdit);
	}
	
	public int registerEmployee(RegisterEmpDTO registrationData) {
		RUser newUser = new RUser();
		newUser.setFirstName(registrationData.getFirstName());
		newUser.setLastName(registrationData.getLastName());
		newUser.setEmail(registrationData.getEmail());
		newUser.setPassword(registrationData.getPassword());
		return this.userDAO.create(newUser);
	}
	
}