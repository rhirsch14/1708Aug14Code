package com.revature.q07;

import java.util.Comparator;

/*
 * Could also be implemented with a simple Lambda
 * Comparator<Employee> nameComparator = (e1, e2) -> 
 * 					e1.getName().compareTo(e2.getName());
 */
public class EmployeeNameComparator implements Comparator<Employee> {
	public int compare(Employee e1, Employee e2) {
		return e1.getName().compareTo(e2.getName());
	}
}