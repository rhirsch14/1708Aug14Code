package com.bank.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bank.beans.*;
import com.bank.util.ConnectionUtil;

public class HibernateDao {

	// CREATE
	public <T> Integer create(Class<T> obj) {
		Session session = ConnectionUtil.getSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();

			id = (Integer) session.save(obj);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return id;
	}
	
	/* UPDATE */
	public <T> void update(Class <T> obj) {
		Session session = ConnectionUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// TODO Update the below code to work for generics as opposed to Employees
	// TODO Make a READ operation with uniqueResult() instead of list()
	/* DELETE */
	public <T> void deleteEmployee(Integer id) {
		Session session = ConnectionUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Object obj = session.get(<T>.getClass(), id);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// READ ALL
	public void listEmployees() {
		Session session = ConnectionUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List employees = session.createQuery("FROM Employee").list();
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print("  Last Name: " + employee.getLastName());
				System.out.println("  Salary: " + employee.getSalary());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}