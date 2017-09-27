package com.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ex.model.User;
import com.ex.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.getAll();
		if(users == null || users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.I_AM_A_TEAPOT);
		}
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		User user = userService.findById(id);
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody User u,
		UriComponentsBuilder ucBuilder){
			if(userService.exists(u)) {
				System.out.println("already exists!");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			userService.create(u);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/user/id").buildAndExpand(u.getId()).toUri());
			return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id){
		User user = userService.findById(id);
			if(user == null) {
				System.out.println("does not exist!");
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			userService.delete(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody User u){
		User user = userService.findById(id);
			if(user == null) {
				System.out.println("does not exist!");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			//user.setId(u.getId());
			user.setUsername(u.getUsername());
			userService.update(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
