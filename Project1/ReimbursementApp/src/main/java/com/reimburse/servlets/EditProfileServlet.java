package com.reimburse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.reimburse.pojos.User;
import com.reimburse.service.Service;

@WebServlet("/edit_profile")
public class EditProfileServlet extends HttpServlet{
	final static Logger logger = Logger.getLogger(EditProfileServlet.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		
		HttpSession session = req.getSession();
		Service bankService = new Service();
		
		User user = bankService.getUser((int)session.getAttribute("userid"));
		user.setFirstname((String)req.getParameter("inputfn"));
		user.setLastname((String)req.getParameter("inputln"));
		user.setEmail((String)req.getParameter("inputemail"));
		user.setPwd((String)req.getParameter("inputpass1"));
		
		bankService.updateUser(user);
		
		logger.info("Edited info of user "+user.getUserId()+":" + user.toString());

		
	}
}