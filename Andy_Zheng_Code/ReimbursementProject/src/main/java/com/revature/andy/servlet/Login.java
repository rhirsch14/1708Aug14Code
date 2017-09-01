package com.revature.andy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.andy.service.Service;
import com.revature.andy.session.PseudoSession;

public class Login extends HttpServlet{

	Service s = new Service();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter pr = resp.getWriter();
		
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		if(PseudoSession.login(email, pass) == 1) {
			pr.println(PseudoSession.getCurrentUser().getFName() + " " + PseudoSession.getCurrentUser().getLName());
			//resp.sendRedirect("success.html");
		}
		else if(PseudoSession.login(email, pass) == 2){
			resp.sendRedirect("failurepassword.html");
		}else {
			resp.sendRedirect("failure.html");
		}
	}
}