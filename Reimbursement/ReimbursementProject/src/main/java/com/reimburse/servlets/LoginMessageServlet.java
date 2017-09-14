package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.service.Services;
import com.reimburse.pojos.User;

@WebServlet("/loginmessagetest")
public class LoginMessageServlet extends HttpServlet{

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test -- POST");
		//console.log("djjdf");
		
		System.out.println("are you working");
		Map<String, String[]> myMap = request.getParameterMap();
		
		
		Set<String> txObject = myMap.keySet();
		
	
		ObjectMapper jackson = new ObjectMapper();
		
		Object obj = txObject.toArray()[0];
		ArrayList<String> tx = jackson.readValue((String)obj, ArrayList.class);
		
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		Services service = new Services();
		
		String email = tx.get(0);
		String pwd = tx.get(1);
		System.out.println("this also work");
		int id = service.validateUser(email);
		System.out.println("id = " + id);
		System.out.println("email:" + email);
		System.out.println("but does this work?");
		String json = "";
		if(id<0){
			json = "Invalid user. try again";
			System.out.println("invalid user");
		} 
		else {
			
		 User u = service.login(id, pwd);
		 
			if(u == null){
				json = "Incorrect Password. try again";
				System.out.println("wrong password");
			}
			else{
				json = "success";
				System.out.println("success");
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}
	
}
