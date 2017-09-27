package com.revature.andy.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.revature.andy.model.User;

@Service
public class UserImpl implements UserService{

	private static final AtomicInteger counter = new AtomicInteger();
	
	static List<User> users = new ArrayList<User>(Arrays.asList(
			new User(counter.incrementAndGet(),"Jon Snow"),
			new User(counter.incrementAndGet(),"Arya Stark"),
			new User(counter.incrementAndGet(),"Cersei L.")
			));
	
	@Override
	public void create(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	@Override
	public void update(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	@Override
	public void delete(User user) {
		users.remove(user);
	}

	@Override
	public List<User> getAll() {
		return users;
	}

	@Override
	public User findById(int id) {
		for(User u : users) {
			if(u.getId() == id) {
				return u;
			}
		}
		return null;
	}

	@Override
	public User findByName(String name) {
		for(User u : users) {
			if(u.getName() == name) {
				return u;
			}
		}
		return null;
	}

	@Override
	public boolean exists(User user) {
		return findByName(user.getName()) == null;
	}
}
