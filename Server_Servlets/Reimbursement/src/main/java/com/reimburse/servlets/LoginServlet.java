package com.reimburse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.reimburse.service.Service;

/**
 * Servlet implementation class TesterServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doGet");
		System.out.println(request.getParameter("usernameText"));
		/*
		// to create/read the session in order to preserve information between connections
		request.getSession();
		
		String username = request.getParameter("usernameText");
		String password = request.getParameter("passwordText");
		
		Service bankService = new Service();
		if (bankService.isAWorker(username))
			if (bankService.validateWorker(username, password) != null)
				if(bankService.isAManager(username))
					response.sendRedirect("../home_tab/manager_home.html");	// Logged in as manager
				else response.sendRedirect("../home_tab/home.html");	// Loged in as employee
			else System.out.println("Bad password");	// Bad password
		else System.out.println("Bad username");	// Bad username
*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		doGet(request, response);
	}

}