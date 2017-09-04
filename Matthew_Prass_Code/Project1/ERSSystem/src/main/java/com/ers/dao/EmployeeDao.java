package com.ers.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.ers.pojos.Employee;

public interface EmployeeDao 
{
	public int addEmployee(String fn, String ln, String email, String pass, String usrnm);
	public ArrayList<Employee> getAllEmployees();
	public Employee getEmployee(int id);
	public void updateEmployeeInfo(Employee e);
	//public HashMap<Integer,String> getEmployeeEmails();
}
