package com.bank.dao;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.domain.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

	
	public UserDaoImpl() {}
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public User findUserByUsername(String username) {
		ArrayList<User> list = (ArrayList<User>) sessionFactory
				.getCurrentSession()
				.createQuery("from User where lower(username)=?")
				.setParameter(0, username.toLowerCase()).list();
		if(list.size()==0)return null;
		else { return list.get(0);}
	}


	public User createUser(User user) {
		sessionFactory.getCurrentSession().save(user);
		return user;
	}

	
}
