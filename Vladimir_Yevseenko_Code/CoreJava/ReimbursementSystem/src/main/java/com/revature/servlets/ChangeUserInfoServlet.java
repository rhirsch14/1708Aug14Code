package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.revature.logging.Logging;
import com.revature.pojos.User;
import com.revature.service.Service;

public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = -2203144656220389003L;

	private static Logger logger = Logging.getLogger();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("ChangeUserInfoServlet doPost()");
		
		Service service = Service.getFromSession(req.getSession());
		User curUser = service.getCurUser();
		
		String first = req.getParameter("first");
		logger.debug("ChangeUserInfoServlet received first: " + first);
		if (first.equals(""))
			first = curUser.getFirst();
		
		String last = req.getParameter("last");
		logger.debug("ChangeUserInfoServlet received last: " + last);
		if (last.equals(""))
			last = curUser.getLast();
		
		String email = req.getParameter("email");
		logger.debug("ChangeUserInfoServlet received email: " + email);
		if(email.equals(""))
			email = curUser.getEmail();
		
		String password = req.getParameter("password");
		logger.debug("ChangeUserInfoServlet received password: " + password);
		if (password.equals(""))
			password = curUser.getPassword();
		
		
		resp.setContentType("application/json");
		
		JSONObject obj = new JSONObject();
		
		obj.put("success", service.updateUserInfo(first, last, email, password));
		
		logger.debug("ChangeUserInfoServlet final first: " + first);
		logger.debug("ChangeUserInfoServlet final last: " + last);
		logger.debug("ChangeUserInfoServlet final email: " + email);
		logger.debug("ChangeUserInfoServlet final password: " + password);
		
		resp.getWriter().println(obj);
	}
}
