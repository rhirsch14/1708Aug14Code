package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 3979568826540168433L;

//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) 
//					throws ServletException, IOException{
//		
//	response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		
//		String name = request.getParameter("username");
//		String pass = request.getParameter("pass");
//		
//		out.print("<h1> Welcome " + name + "!</h1>");
//		
//		// get param names 
//		Enumeration<String> paramNames = 
//				request.getParameterNames();
//		while(paramNames.hasMoreElements()){
//			out.println("<br> Get parameter names <br>");
//			String param = paramNames.nextElement();
//			String value = request.getParameter(param);
//			out.println(param +": " + value);
//		}
//		
//	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) 
					throws ServletException, IOException{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("username");
		String pass = request.getParameter("pass");
		
		out.print("<h1> Welcome " + name + "!</h1>");
		
	}

}