package com.ex.test;

import com.ex.dao.UserDaoImpl;
import com.ex.domain.User;

public class MainTest {

	public static void main(String[] args) {
		UserDaoImpl dao = new UserDaoImpl();
		User u = new User();
		u.setUsername("test");
		u.setPassword("test");
		
		dao.createUser(u);
	}
	
}