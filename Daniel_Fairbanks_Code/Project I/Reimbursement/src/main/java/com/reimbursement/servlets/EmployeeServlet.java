package com.reimbursement.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reimbursement.pojos.User;
import com.reimbursement.service.Service;
import com.reimbursement.pojos.Reimbursement;

public class EmployeeServlet  extends HttpServlet {

	private Service runApp = new Service();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuilder sb = new StringBuilder("");
		HttpSession session = request.getSession(false);
		if (session == null) {
			sb.append("Invalid"); // response.setStatus(418);
		}
		else {
			User tmp = (User)session.getAttribute("user");
			HashMap<Integer, Reimbursement> requests = runApp.getRequestsByEmployee(tmp);

			for (HashMap.Entry<Integer, Reimbursement> entry : requests.entrySet()) {
			    Integer id = entry.getKey();
			    Reimbursement rq = entry.getValue();
			    sb.append("<tr>\n");
			    switch (rq.getStatus()) {
		    	case 1:
		    		sb.append("\t<th style=color:gray>"+rq.getStatusName()+"</th>\n");
		    		break;
		    	case 2:
		    		sb.append("\t<th style=color:green>"+rq.getStatusName()+"</th>\n");
		    		break;
		    	case 3:
		    		sb.append("\t<th style=color:red>"+rq.getStatusName()+"</th>\n");
		    		break;
			    }
			    sb.append("\t<th>$"+rq.getAmount()+"</th>\n");
			    sb.append("\t<th>"+rq.getSubmit_date()+"</th>\n");
			    if (rq.getResolve_date() == null) {
				    sb.append("\t<th>-</th>\n");
				    sb.append("\t<th>-</th>\n");
			    }
			    else {
				    sb.append("\t<th>"+rq.getResolve_date()+"</th>\n");
				    sb.append("\t<th>"+rq.getResolver().getFirstname()+" "+rq.getResolver().getLastname()+"</th>\n");
			    }
			    sb.append("\t<th><button type=\"button\" class=\"btn btn-secondary\" data-container=\"body\" data-toggle=\"popover\" "
			    		+ "data-placement=\"left\" data-content=\""+rq.getDescription()+"\">Info</button></th>\n");
			    sb.append("</tr>\n");
			}
		}
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}