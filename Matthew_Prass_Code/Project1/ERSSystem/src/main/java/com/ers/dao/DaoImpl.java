package com.ers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.ers.pojos.Employee;
import com.ers.pojos.Reimbursement;
import com.ers.pojos.ReimbursementStatus;
import com.ers.util.ConnectionFactory;

public class DaoImpl implements EmployeeDao,ReimburseDao,ReimbursementStatusDao {

	@Override
	public ArrayList<ReimbursementStatus> getAllStatuses() {
		ArrayList<ReimbursementStatus> list = new ArrayList<ReimbursementStatus>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select *" + 
					"from reimbursestatus";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt(1);
				String name = rs.getString(2);
				
				ReimbursementStatus ex = new ReimbursementStatus(id,name);
				
				list.add(ex);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ReimbursementStatus getStatus(int id) {
		ArrayList<ReimbursementStatus> list = new ArrayList<ReimbursementStatus>();
		ReimbursementStatus result = null;
		for(ReimbursementStatus r: list)
		{
			if(r.getId() == id)
			{
				result = r;
			}
		}
		return result;
	}

	@Override
	public Reimbursement createReimbursement(Employee submit, Timestamp submitdate, String desc, double amt) {
		Reimbursement a = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			conn.setAutoCommit(false);
			String sql = "insert into reimbursement(submitterid, submitdate, description, amount)" + 
					"values(?,?,?,?)";
			
			String[] key = new String[1];
			key[0]= "reid";
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, submit.getId());
			ps.setTimestamp(2, submitdate);
			ps.setString(3, desc);
			ps.setDouble(4, amt);
			
			int numRows = ps.executeUpdate();
			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
			{
				id = pk.getInt(1);
			}
			
			conn.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public ArrayList<Reimbursement> getAllReimbursements() {
		ArrayList<Reimbursement> list = new ArrayList<Reimbursement>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select *" + 
					"from reimbursement";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt(1);
				int subid = rs.getInt(2);
				int resolvid = rs.getInt(3);
				Timestamp submitdate = rs.getTimestamp(4);
				Timestamp resolvedate = rs.getTimestamp(5);
				int status = rs.getInt(6);
				String desc = rs.getString(7);
				String notes = rs.getString(8);
				double amt = rs.getDouble(9);
				
				Reimbursement ex = new Reimbursement(getEmployee(subid),getEmployee(resolvid),submitdate,resolvedate,getStatus(status),desc,notes,amt);
				ex.setId(id);
				list.add(ex);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ArrayList<Reimbursement> getAllReimbursementsFromEmployee(int empid) {
		ArrayList<Reimbursement> list = new ArrayList<Reimbursement>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select *" + 
					"from reimbursement" +
					"where empid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empid);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt(1);
				int subid = rs.getInt(2);
				int resolvid = rs.getInt(3);
				Timestamp submitdate = rs.getTimestamp(4);
				Timestamp resolvedate = rs.getTimestamp(5);
				int status = rs.getInt(6);
				String desc = rs.getString(7);
				String notes = rs.getString(8);
				double amt = rs.getDouble(9);
				
				Reimbursement ex = new Reimbursement(getEmployee(subid),getEmployee(resolvid),submitdate,resolvedate,getStatus(status),desc,notes,amt);
				ex.setId(id);
				list.add(ex);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ArrayList<Reimbursement> getAllResolved() {
		//might not work?
		ArrayList<Reimbursement> relist = getAllReimbursements();
		ArrayList<Reimbursement> result = new ArrayList<Reimbursement>();
		for(Reimbursement r:relist)
		{
			if(r.getType().getId() == 1)
			{
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public ArrayList<Reimbursement> getAllResolvedByEmployee(int empid) {
		//might not work
		ArrayList<Reimbursement> relist = getAllReimbursementsFromEmployee(empid);
		ArrayList<Reimbursement> result = new ArrayList<Reimbursement>();
		for(Reimbursement r:relist)
		{
			if(r.getType().getId() == 1)
			{
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public void updateReimbursement(Reimbursement r) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "{ call updateReimbursement(?,?,?,?,?) }";
			
			
			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1, r.getId());
			cs.setInt(2,r.getResolver().getId());
			cs.setTimestamp(3, r.getResolveddate());
			cs.setInt(4,r.getType().getId());
			cs.setString(5, r.getNotes());
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
	}

	@Override
	public void deleteReimbursement(Reimbursement r) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "{ call deleteReimbursement(?) }";
			
			
			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1,r.getId());
			cs.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int addEmployee(String fn, String ln, String email, String pass, String usrnm,int ismnger) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection();)
		{
			conn.setAutoCommit(false);
			String sql = "insert into employee(firstname,lastname,email,password,username,ismanager)" + 
					"values(?,?,?,?,?,?)";
			String[] keys = new String[1];
			keys[0] = "empid";
			PreparedStatement ps = conn.prepareStatement(sql,keys);
			ps.setString(1, fn);
			ps.setString(2, ln);
			ps.setString(3, email);
			ps.setString(4, pass);
			ps.setString(5, usrnm);
			ps.setInt(6, ismnger);
			
			int numRows = ps.executeUpdate();
			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
			{
				id = pk.getInt(1);
			}
			
			conn.commit();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> list = new ArrayList<Employee>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select *" + 
					"from employee";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt(1);
				String fn = rs.getString(2);
				String ln = rs.getString(3);
				String em = rs.getString(4);
				String pw = rs.getString(5);
				String usrnm = rs.getString(6);
				int ismngr = rs.getInt(7);
				
				Employee ex = new Employee(fn,ln,em,pw,usrnm,ismngr);
				ex.setId(id);
				list.add(ex);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Employee getEmployee(int id) {
		//might not work
		ArrayList<Employee> list = getAllEmployees();
		Employee emp = null;
		for(Employee e:list)
		{
			if(e.getId() == id)
			{
				emp = e;
				break;
			}
		}
		return emp;
	}

	@Override
	public void updateEmployeeInfo(Employee e) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "{ call updateEmployee(?,?,?,?,?,?) }";
			
			
			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1, e.getId());
			cs.setString(2,e.getFirstname());
			cs.setString(3,e.getLastname());
			cs.setString(4,e.getEmail());
			cs.setString(5,e.getPassword());
			cs.setString(6, e.getUsername());
			cs.execute();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

/*	@Override
	public HashMap<Integer, String> getEmployeeEmails() {
		HashMap<Integer, String> emails = new HashMap<Integer, String>();
		try(Connection conn = ConnectionFactory
				.getInstance().getConnection();){
			String sql = "select empid, email from employee";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()){
				int id = rs.getInt(1);
				String email = rs.getString(2);
				emails.put(id, email);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}*/

}