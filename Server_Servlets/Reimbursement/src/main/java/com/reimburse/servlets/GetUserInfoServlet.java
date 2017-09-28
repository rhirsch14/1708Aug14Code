package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.pojos.Worker;
import com.reimburse.service.Service;

@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {

	final static Logger logger = Logger.getLogger(GetUserInfoServlet.class);
	
	private final int STATUS_CODE_FAILED = 418;
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet");
		
		Service service = new Service();
		
		HttpSession session = request.getSession();
		Worker sessionUser = (Worker)session.getAttribute("user");
		
		if (sessionUser != null) {
			
			logger.info("Converting our user and accounts to a DTO");	// DEBUG
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(sessionUser);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		} else {
			response.setStatus(STATUS_CODE_FAILED);
		}
		
	}
}