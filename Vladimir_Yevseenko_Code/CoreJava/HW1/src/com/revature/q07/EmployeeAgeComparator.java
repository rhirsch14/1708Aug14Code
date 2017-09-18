package com.revature.q07;

import java.util.Comparator;

/*
 *  Could also be implemented using a simple Lambda
 *  ie: Comparator<Employee> ageComparator = (e1, e2) ->
 *  			((Integer) e1.getName()).compareTo(e2.getAge());
 */
public class EmployeeAgeComparator implements Comparator<Employee> {
	public int compare(Employee e1, Employee e2) {
		return ((Integer)e1.getAge()).compareTo(e2.getAge());
	}
}