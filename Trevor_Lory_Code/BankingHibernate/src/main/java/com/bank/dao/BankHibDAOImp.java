package com.bank.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bank.beans.User;

@Transactional
public class BankHibDAOImp implements DAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public User addUserNew(User u) {
		Session s = sessionFactory.getCurrentSession();
		int id = (Integer) s.save(u);
		System.out.println(id);
		u.setUSERID(id);
		return u;
	}

	public List<String> getAllEmails() {
		List<String> things;
		Session s = sessionFactory.getCurrentSession();
		return s.createQuery("select EMAIL from User").list();
	}

//	public List<User> getAllUsers() {
//		List<User> list = null;
//		Session session = ConnectionUtil.getSession();
//		try {
//			Query query = session.createQuery("from User");
//			list = query.list();
//		}
//		finally {
//			session.close();
//		}
//		return list;
//	}
//	
//	public void addUser(User u) {
//		Session session = ConnectionUtil.getSession();
//		try {
//			Transaction tx = (Transaction)session.beginTransaction();
//			u.setUSERID((Integer) session.save(u));
//			tx.commit();
//		}
//		finally {
//			session.close();
//		}
//	}
//	
//	public List<Account> getAllAccounts() {
//		List<Account> list = null;
//		Session session = ConnectionUtil.getSession();
//		try {
//			Query query = session.createQuery("from Account");
//			list = query.list();
//		}
//		finally {
//			session.close();
//		}
//		return list;
//	}
//	
//	public List<AccountType> getAllAccountTypes() {
//		List<AccountType> list = null;
//		Session session = ConnectionUtil.getSession();
//		try {
//			Query query = session.createQuery("from AccountType");
//			list = query.list();
//		}
//		finally {
//			session.close();
//		}
//		return list;
//	}
	
}