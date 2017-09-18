package com.bank.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.service.BankService;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 5285277464221949899L;
	private static BankService service = new BankService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String e = (String) req.getAttribute("email");
		String p = (String) req.getAttribute("password");
		if (e != null && p != null) {
			System.out.println("e != null && p != null");
			if (service.attemptLogin(e, p))
				resp.sendRedirect("/login_success.html");
			else
				resp.sendRedirect("/login_failure.html");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}