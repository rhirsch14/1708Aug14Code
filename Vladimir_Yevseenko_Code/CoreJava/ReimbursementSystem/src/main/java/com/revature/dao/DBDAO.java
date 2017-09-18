package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.Status;
import com.revature.pojos.User;
import com.revature.utils.ConnectionFactory;

public class DBDAO {
	private Connection dbConn;
	private static Logger log = Logging.getLogger();
	
	public DBDAO() {
		dbConn = ConnectionFactory.getInstance().getConnection();
		try {
			dbConn.setAutoCommit(false);
		} catch (SQLException ex) {
			log.fatal("dbConn.setAutoCommit() SQLException");
		}
		log.debug("DBDAO() instance created");
	}
	
	public List<User> getAllNonManagers() {
		log.debug("DBDAO getAllNonManagers()");
		final String sql = "SELECT * FROM users WHERE is_manager = 0";
		try (Statement s = dbConn.createStatement()) {
			List<User> users = new ArrayList<>();
			ResultSet res = s.executeQuery(sql);
			while (res.next()) {		
				users.add(new User(res.getInt("id"), res.getString("first_name"),
						res.getString("last_name"), res.getString("email"),
						res.getString("password"), res.getInt("is_manager") == 1));
			}
			res.close();
			return users;
		} catch (SQLException ex) {
			log.fatal("DBDAO getAllNonManagers() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	public List<Reimbursement> getAllReimbursements() {
		log.debug("DBDAO getAllReimbursements()");
		final String sql = "SELECT * FROM reimbursements";
		try (Statement s = dbConn.createStatement()){
			List<Reimbursement> reimbs = new ArrayList<>();
			try (ResultSet rs = s.executeQuery(sql)) {
				while (rs.next()) {
					reimbs.add(new Reimbursement(rs.getInt("id"), 
							getUserById(rs.getInt("submitter_id")), 
							getUserById(rs.getInt("resolver_id")),
							rs.getDate("submission_date"), 
							rs.getDate("resolution_date"),
							Status.getStatus(rs.getInt("reimbursement_status_id")),
							rs.getString("description"),
							rs.getDouble("amount")));
				}
				return reimbs;
			}
		} catch (SQLException ex) {
			log.fatal("DBDAO getAllReimbursements() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	
	public List<Reimbursement> getUsersReimbursements(User u) {
		log.debug("DBDAO getUsersReimbursements()");
		final String sql = "SELECT * FROM reimbursements WHERE submitter_id = ?";
		try (PreparedStatement s = dbConn.prepareStatement(sql)){
			List<Reimbursement> reimbs = new ArrayList<>();
			s.setInt(1, u.getId());
			try (ResultSet rs = s.executeQuery()) {
				while (rs.next()) {
					reimbs.add(new Reimbursement(rs.getInt("id"), 
							u, getUserById(rs.getInt("resolver_id")),
							rs.getDate("submission_date"), 
							rs.getDate("resolution_date"),
							Status.getStatus(rs.getInt("reimbursement_status_id")),
							rs.getString("description"),
							rs.getDouble("amount")));
				}
				return reimbs;
			}
		} catch (SQLException ex) {
			log.fatal("DBDAO getUsersReimbursements() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	public User getUserById(int id) {
		log.debug("DBDAO getUserById()");
		final String sql = "SELECT * FROM users WHERE id = ?";
		try (PreparedStatement ps = dbConn.prepareCall(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(id, rs.getString("first_name"), rs.getString("last_name"),
								rs.getString("email"), rs.getString("password"),
								rs.getInt("is_manager") == 1);
				} else {
					log.warn("getUserById() user not found");
				}
			}
			return null;
		} catch (SQLException ex) {
			log.fatal("DBDAO getUserById() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	public User getUserByEmail(String email) {
		log.debug("DBDAO getUserById()");
		final String sql = "SELECT * FROM users WHERE email = ?";
		try (PreparedStatement ps = dbConn.prepareCall(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("id"), rs.getString("first_name"), 
								rs.getString("last_name"),
								email, rs.getString("password"),
								rs.getInt("is_manager") == 1);
				}
					log.warn("getUserById() user not found");
					return null;
			}
		} catch (SQLException ex) {
			log.fatal("DBDAO getUserById() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	/*
	 * Returns false if user unable to be added - email is not unique
	 */
	public boolean addUser(User u) {
		log.debug("DBDAO addUser()");
		final String sql = "INSERT INTO users (first_name, last_name, email, password, is_manager)"
				+ " VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
		 	ps.setString(1, u.getFirst());
		 	ps.setString(2, u.getLast());
		 	ps.setString(3, u.getEmail());
		 	ps.setString(4, u.getPassword());
		 	ps.setInt(5, u.getIsManager() ? 1 : 0);
		 	ps.execute();
		 	dbConn.commit();
		 	return true;
		} catch (SQLException ex) {
			return false;
		}
	}
	
	
	public boolean doesUserExistByEmail(String email) {
		log.debug("DBDAO doesUserExistByEmail()");
		final String sql = "SELECT * FROM users WHERE email = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException ex) {
			log.fatal("doesUserExistByEmail() SQLException");
			log.fatal(ex.getMessage());
			return false;
		}
		
	}
	
	public void addNewReimbursement(Reimbursement r) {
		log.debug("DBDAO addNewReimbursement()");
		final String sql = "INSERT INTO reimbursements (submitter_id, resolver_id,"
				+ " submission_date, resolution_date, reimbursement_status_id, description,"
				+ " amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, r.getSubmitter().getId());
			ps.setInt(2, r.getResolver().getId());
			ps.setDate(3, r.getSubmissionDate());
			ps.setDate(4, r.getResolutionDate());
			ps.setInt(5, r.getStatus().getId());
			ps.setString(6, r.getDescription());
			ps.setDouble(7, r.getAmount());
			ps.execute();
			dbConn.commit();
		} catch (SQLException ex) {
			log.fatal("DBDAO addReimbursement() SQLException");
			log.fatal(ex.getMessage());
		}
	}
	
	public User attemptLogin(String email, String password) {
		log.debug("DBDAO attemptLogin()");
		final String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("id"),
									rs.getString("first_name"),
									rs.getString("last_name"),
									email, password, rs.getInt("is_manager") == 1);
				}
				log.debug("DBDAO attemptLogin() unsuccessful");
				return null;
			}
		} catch (SQLException ex) {
			log.fatal("DBDAO attemptLogin() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
	
	
	public boolean updateUserInfo(int id, String first, String last, String email, String password) {
		log.debug("DBDAO() updateUserInfo()");
		final String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ?, email = ? WHERE id = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, first);
			ps.setString(2, last);
			ps.setString(3, password);
			ps.setString(4, email);
			ps.setInt(5, id);
			ps.execute();
			dbConn.commit();
			return true;
		} catch (SQLException ex) {
			log.fatal("DBDAO updateUserInfo() SQLException");
			log.fatal(ex.getMessage());
			return false;
		}
	}
	
	public boolean updateUserInfoNoEmail(int id, String first, String last, String email, String password) {
		log.debug("DBDAO() updateUserInfoNoEmail()");
		final String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE id = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, first);
			ps.setString(2, last);
			ps.setString(3, password);
			ps.setInt(4, id);
			ps.execute();
			dbConn.commit();
			return true;
		} catch (SQLException ex) {
			log.fatal("DBDAO updateUserInfo() SQLException");
			log.fatal(ex.getMessage());
			return false;
		}
	}

	public boolean resolveReimbursement(int reimbId, int resolverId, boolean approved) {
		log.debug("DBDAO resolveReimbursement()");
		final String sql = "UPDATE reimbursements SET reimbursement_status_id = ?, resolver_id = ?, resolution_date = ? WHERE id = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			Date resolveDate =  new Date(Calendar.getInstance().getTime().getTime());
			ps.setInt(1, approved ? 2 : 3);
			ps.setInt(2, resolverId);
			ps.setDate(3, resolveDate);
			ps.setInt(4, reimbId);
			ps.execute();
			dbConn.commit();
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}
	
	public Reimbursement getReimbursementById(int id) {
		final String sql = "SELECT * FROM reimbursements WHERE id = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Reimbursement(id,
								getUserById(rs.getInt("submitter_id")),
								getUserById(rs.getInt("resolver_id")),
								rs.getDate("submission_date"),
								rs.getDate("resolution_date"),
								Status.getStatus(rs.getInt("reimbursement_status_id")),
								rs.getString("description"),
								rs.getDouble("amount"));
			}
			return null;
 		} catch (SQLException ex) {
			log.fatal("DBDAO doesReimbursementExistById() SQLException");
			log.fatal(ex.getMessage());
			return null;
		}
	}
}
