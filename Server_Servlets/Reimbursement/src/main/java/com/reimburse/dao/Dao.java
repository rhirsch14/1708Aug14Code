package com.reimburse.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.pojos.Worker;

public interface Dao {

	// Worker
	public Worker createWorker(String firstName, String lastName, String email,
			String username, String password, boolean isManager, boolean isHired);
	public Worker readWorker(int workerId);
	public Worker readWorker(String username);
	// change any worker's field except their uniquely identifying keys
	public boolean updateWorker(int workerId, Worker work);
	// A deleted worker is merely marked as deceased
	public boolean deleteWorker(int workerId);
	public ArrayList<Worker> readAllWorkers();

	// Reimbursement
	public Reimbursement createReimbursement(int submitterId, reimbursementStatus status,
			LocalDateTime submitDate, String description, int ammount);
	public Reimbursement readReimbursement(int reimbursementId);
	// change any reimbursement's field except their uniquely identifying keys
	public boolean updateReimbursement(int reimbursementId, Reimbursement acc);
	// A deleted Reimbursement is merely marked as deleted
	public ArrayList<Reimbursement> readAllReimbursements();
	
}