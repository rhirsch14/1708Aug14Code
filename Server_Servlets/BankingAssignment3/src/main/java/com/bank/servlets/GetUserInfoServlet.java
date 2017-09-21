package com.bank.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dto.DTO;
import com.bank.pojos.Account;
import com.bank.pojos.BankUser;
import com.bank.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {

	private final int STATUS_CODE_FAILED = 418;
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet in GetUserInfoServlet");	// DEBUG
		
		Service bankService = new Service();
		
		HttpSession session = request.getSession();
		BankUser sessionUser = (BankUser)session.getAttribute("user");
		
		if (sessionUser != null) {
			ArrayList<Account> accountList = bankService.getAccounts(sessionUser.getUserId());
			
			System.out.println("Converting our user and accounts to a DTO");	// DEBUG
			DTO dto = new DTO(sessionUser, accountList);
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(dto);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		} else {
			response.setStatus(STATUS_CODE_FAILED);
		}
		
	}
}