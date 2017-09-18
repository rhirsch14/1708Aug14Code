package com.revature.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.dao.DBDAO;
import com.revature.hashing.Hasher;
import com.revature.logging.Logging;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;

public class Service {
	private DBDAO dao;
	private User curUser;
	
	private static Logger logger = Logging.getLogger();
	
	public Service() {
		dao = new DBDAO();
		logger.debug("Service() created");
	}
	
	public boolean attemptLogin(String email, String password) {
		logger.debug("Service attemptLogin()");
		Hasher hasher = new Hasher();
		String passwordHash = hasher.hashPassword(password);
		logger.debug("Service attemptLogin() passwordHash: " + passwordHash);
		curUser = dao.attemptLogin(email, passwordHash);
		if (curUser == null)
			return false;
		curUser = new User(curUser.getId(), 
				curUser.getFirst(), 
				curUser.getLast(), 
				curUser.getEmail(), 
				password, 
				curUser.getIsManager());
		return true;
	}
	
	public boolean addUser(String first, String last, String email, String pass, boolean isManager) {
		logger.debug("Service addUser()");
		Hasher hasher = new Hasher();
		String passwordHash = hasher.hashPassword(pass);
		logger.debug("Service addUser() passwordHash: " + passwordHash);
		return dao.addUser(new User(first, last, email, passwordHash, isManager));
	}
	
	public User getCurUser() {
		logger.debug("Service getCurUser()");
		return curUser;
	}
	
	
	public static Service getFromSession(HttpSession ses) {
		logger.debug("Service getFromSession()");
		Service serv = (Service) ses.getAttribute("service");
		if (serv == null) {
			Service newServ = new Service();
			ses.setAttribute("service", newServ);
			return newServ;
		}
		return serv;
	}
	
	
	public boolean updateUserInfo(String first, String last, String email, String password) {
		logger.debug("Service updateUserInfo");
		Hasher hasher = new Hasher();
		String passwordHash = hasher.hashPassword(password);
		logger.debug("Service updateUserInfo() passwordHash: " + passwordHash);
		curUser = new User(curUser.getId(), first, last, email, password, curUser.getIsManager());
		if (email.equals(curUser.getEmail())) {
			return dao.updateUserInfoNoEmail(curUser.getId(), first, last, email, passwordHash);
		}
 		return dao.updateUserInfo(curUser.getId(), first, last, email, passwordHash);
	}
	
	
	public Reimbursement[] getUsersReimbursements() {
		logger.debug("Service getUsersReimbursements()");
		List<Reimbursement> reimbs = dao.getUsersReimbursements(curUser);
		return reimbs.toArray(new Reimbursement[reimbs.size()]);
	}
	
	public Reimbursement[] getAllReimbursements() {
		logger.debug("Service getAllReimbursements()");
		List<Reimbursement> reimbs = dao.getAllReimbursements();
		return reimbs.toArray(new Reimbursement[reimbs.size()]);
	}
	
	public boolean resolveReimbursement(int reimbId, boolean approved) {
		logger.debug("Service resolveReimbursement()");
		logger.debug("resolveReimbursment() approved: " + approved);
		return dao.resolveReimbursement(reimbId, curUser.getId(), approved);
	}
	
	public User[] getAllNonManagers() {
		logger.debug("Service getAllNonManagers()");
		List<User> users = dao.getAllNonManagers();
		return users.toArray(new User[users.size()]);
	}
	
	
	public boolean doesUserExist(String email) {
		logger.debug("Service doesUserExist()");
		return dao.doesUserExistByEmail(email);
	}
	
	public Reimbursement getReimbursementById(int id) {
		logger.debug("Service doesReimbursementExist()");
		return dao.getReimbursementById(id);
	}
	
	public void addNewReimbursement(Reimbursement reimbursement) {
		dao.addNewReimbursement(reimbursement);
	}
}