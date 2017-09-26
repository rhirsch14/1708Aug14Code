package com.pone.dto;

import java.util.ArrayList;

import com.pone.pojos.AUser;
import com.pone.pojos.RStatus;
import com.pone.pojos.Reimbursement;

public class DTO {

	private AUser aUser;
	private ArrayList<Reimbursement> reimbursements;
	private ArrayList<RStatus> statuses;
	private ArrayList<AUser> allUsers;
	
	public DTO() {}

	public DTO(AUser aUser, ArrayList<Reimbursement> reimbursements) {
		super();
		this.aUser = aUser;
		this.reimbursements = reimbursements;
	}
	
	public DTO(AUser aUser, ArrayList<Reimbursement> reimbursements, ArrayList<RStatus> statuses) {
		super();
		this.aUser = aUser;
		this.reimbursements = reimbursements;
		this.statuses = statuses;
	}
	
	public DTO(AUser aUser, ArrayList<Reimbursement> reimbursements, ArrayList<RStatus> statuses, ArrayList<AUser> allUsers) {
		super();
		this.aUser = aUser;
		this.reimbursements = reimbursements;
		this.statuses = statuses;
		this.setAllUsers(allUsers);
	}

	public AUser getAUser() {
		return aUser;
	}

	public void setAUser(AUser aUser) {
		this.aUser = aUser;
	}

	public ArrayList<Reimbursement> getReimbursements() {
		return reimbursements;
	}

	public void setReimbursements(ArrayList<Reimbursement> reimbursements) {
		this.reimbursements = reimbursements;
	}

	public ArrayList<RStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<RStatus> statuses) {
		this.statuses = statuses;
	}

	public ArrayList<AUser> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(ArrayList<AUser> allUsers) {
		this.allUsers = allUsers;
	}
	
	
	
	
}