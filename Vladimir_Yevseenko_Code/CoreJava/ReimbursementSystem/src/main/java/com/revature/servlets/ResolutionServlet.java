package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.revature.logging.Logging;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.Status;
import com.revature.service.Service;

public class ResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = -8499667970000249726L;

	private static Logger logger = Logging.getLogger();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("ResolutionServlet doPost()");
		
		Service service = Service.getFromSession(req.getSession());
		
		int id = Integer.parseInt(req.getParameter("id"));
		String approved = req.getParameter("approved");
		
		JSONObject obj = new JSONObject();
		
		Reimbursement reimb = service.getReimbursementById(id);
		
		if (reimb == null) {
			obj.put("success", "noSuchId");
		} else {
			if (reimb.getStatus() == Status.PENDING) {
				service.resolveReimbursement(id, approved.equals("approved"));
				obj.put("success", "success");
			} else {
				obj.put("success", "notPending");
			}
		}
		
		resp.getWriter().println(obj);
	}
}
