package com.revature.q07;

import java.util.Comparator;

/*
 *  Could also be implemented using a simple Lambda
 *  Comparator<Employee> departmentComparator = (e1, e2) -> 
 *  		e1.getDepartment().compareTo(e2.getDepartment());
 */
public class EmployeeDepartmentComparator implements Comparator<Employee> {
	public int compare(Employee e1, Employee e2) {
		return e1.getDepartment().compareTo(e2.getDepartment());
	}
}
