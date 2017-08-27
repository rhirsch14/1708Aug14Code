package com.bank.dao;

import com.bank.pojos.User;

public interface DAO {
	void createNewUser(User user);
	void updateUserInfo(User user);
	
}
