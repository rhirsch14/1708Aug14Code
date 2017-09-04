package com.project1.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project1.service.Service;

public class LoginServlet extends HttpServlet {

	static Service service = new Service();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username + " " + password);

		int id = service.validateUser(username, password);
		if (id == -1) {
			RequestDispatcher rd = request.getRequestDispatcher("error.html");
			rd.forward(request, response); // invalid user
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("success.html");
			rd.forward(request, response); // successful login
		}

	}
}