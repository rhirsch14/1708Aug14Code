package com.pone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pone.dto.DTO;
import com.pone.pojos.AUser;
import com.pone.pojos.RStatus;
import com.pone.pojos.Reimbursement;
import com.pone.service.Service;


@WebServlet("/getReimbursements")
public class GetReimbursementsServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		Service service = new Service();
		
		HttpSession session = request.getSession();
		AUser sessionUser = (AUser)session.getAttribute("auser");
		if(sessionUser != null){
			ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			reimbursements = service.getAllReimbursements();
			
			ArrayList<RStatus> allStatuses = service.getReimbursementStatuses();
			
			ArrayList<AUser> allUsers = service.getAllUsers();
			DTO adto = new DTO(sessionUser, reimbursements,allStatuses,allUsers);
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(adto);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		}
		else{
			response.setStatus(418);
		}
		
		
		
		
		
	}
	
}
