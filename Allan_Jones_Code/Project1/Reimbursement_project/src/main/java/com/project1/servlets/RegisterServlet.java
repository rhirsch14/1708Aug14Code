package com.project1.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project1.service.Service;

//@WebServlet("/logout")
public class RegisterServlet extends HttpServlet {
	static Service service = new Service();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstname = req.getParameter("fn");
		String lastname = req.getParameter("ln");
		String username = req.getParameter("un");
		String email = req.getParameter("em");
		
		boolean success = service.registerEmp(firstname, lastname, username, email);
		if(success) {
			// output successful register to register.html
			// and clear out form input fields
		} else {
			// output unsuccessful register to register.html
			// log unsuccessful attempt to logger
		}
		RequestDispatcher rd = req.getRequestDispatcher("register.html");
		rd.forward(req, resp); // successful login
	}
}
