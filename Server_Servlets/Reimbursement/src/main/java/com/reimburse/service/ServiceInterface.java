package com.reimburse.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.pojos.Worker;

public interface ServiceInterface {

	public boolean login(String username, String password);

	public boolean logout();

	public Worker tryCreateWorker(String firstName, String lastName, String email, String username, String password,
			boolean isManager);

	public Reimbursement tryCreateReimbursement(reimbursementStatus status, LocalDateTime submitDate,
			String description, int ammount);

	public Worker getWorker(int workerId);

	public Integer getWorkerIdLoggedIn();

	public boolean isAWorker(String username);

	public boolean isAManager(String username);

	public boolean isManagerLoggedIn();
	
	public boolean updateWorker(String firstName, String lastName, String email, String username, String password);
	
	public boolean resolveReimbursement(int reimbursementId, reimbursementStatus status,
			LocalDateTime resolvedDate, String resolveNotes);

	public ArrayList<Reimbursement> getPendingReimbursements();
	public ArrayList<Reimbursement> getResolvedReimbursements();
	public ArrayList<Reimbursement> getPendingReimbursements(int employeeId);
	public ArrayList<Reimbursement> getResolvedReimbursements(int employeeId);
	public ArrayList<Reimbursement> getEmployeesReimbursements(int employeeId);
	public ArrayList<Worker> getAllEmployees();
}