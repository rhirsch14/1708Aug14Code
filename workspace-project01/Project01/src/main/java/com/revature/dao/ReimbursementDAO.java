/**
 * 
 */
package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.model.Reimbursement;
import com.revature.util.ConnectionSingleton;

// TODO: Auto-generated Javadoc
/**
 * Defines CRUD operations for Remibursement entities.
 * @author Will Underwood
 */
public class ReimbursementDAO implements IReimbursementDAO {

	/* (non-Javadoc)
	 * @see com.revature.dao.IReimbursementDAO#create(com.revature.model.Reimbursement)
	 */
	@Override
	public int create(Reimbursement reimbursement) {
		if (reimbursement == null) {
			throw new IllegalArgumentException("Reimbursement cannot be null");
		}
		int numberOfRowsInserted = 0;
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO reimbursement(r_id, submitter_id, resolver_id, status_id, date_submitted, date_resolved, description, resolution_notes, amount) VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, reimbursement.getSubmitterID());
			statement.setInt(2, reimbursement.getResolverID());
			statement.setInt(3, reimbursement.getStatusID());
			statement.setDate(4, reimbursement.getDateSubmitted());
			statement.setDate(5, reimbursement.getDateResolved());
			statement.setString(6, reimbursement.getDescription());
			statement.setString(7, reimbursement.getResolutionNotes());
			statement.setDouble(8, reimbursement.getAmount());
			numberOfRowsInserted = statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfRowsInserted;
	}

	/**
	 * Reads a single Reimbursement by its natural key.
	 *
	 * @param submitterID the submitter ID
	 * @param dateSubmitted the date submitted
	 * @return the reimbursement
	 * @precondition submitterID > 0, dateSubmitted != null
	 */
	@Override
	public Reimbursement read(int rID) {
		if (rID < 1) {
			throw new IllegalArgumentException("Reimbursement ID cannot be less than 1");
		}
		Reimbursement reimbursement = null;
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			String sql = "SELECT * FROM reimbursement WHERE r_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, rID);
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				reimbursement = getReimbursementFromQuery(results);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimbursement;
	}

	private Reimbursement getReimbursementFromQuery(ResultSet results) throws SQLException {
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setrID(results.getInt("r_id"));
		reimbursement.setSubmitterID(results.getInt("submitter_id"));
		reimbursement.setResolverID(results.getInt("resolver_id"));
		reimbursement.setStatusID(results.getInt("status_id"));
		reimbursement.setDateSubmitted(results.getDate("date_submitted"));
		reimbursement.setDateResolved(results.getDate("date_resolved"));
		reimbursement.setDescription(results.getString("description"));
		reimbursement.setResolutionNotes(results.getString("resolution_notes"));
		reimbursement.setAmount(results.getDouble("amount"));
		return reimbursement;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.dao.IReimbursementDAO#readAll()
	 */
	@Override
	public ArrayList<Reimbursement> readAll() {
		ArrayList<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			String sql = "SELECT * FROM reimbursement";
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			while(results.next()) {
				Reimbursement reimbursement = this.getReimbursementFromQuery(results);
				allReimbursements.add(reimbursement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allReimbursements;
	}

	/* (non-Javadoc)
	 * @see com.revature.dao.IReimbursementDAO#update(com.revature.model.Reimbursement)
	 */
	@Override
	public int update(Reimbursement reimbursement) {
		if (reimbursement == null) {
			throw new IllegalArgumentException("Reimbursement cannot be null");
		}
		int rowsAffected = 0;
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			String sql = "UPDATE reimbursement SET status_id = ?, date_resolved = ?, description = ?, resolution_notes = ?, amount = ? WHERE r_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, reimbursement.getStatusID());
			statement.setDate(2, reimbursement.getDateResolved());
			statement.setString(3, reimbursement.getDescription());
			statement.setString(4, reimbursement.getResolutionNotes());
			statement.setDouble(5, reimbursement.getAmount());
			statement.setInt(6, reimbursement.getReimbursementID());
			rowsAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

}