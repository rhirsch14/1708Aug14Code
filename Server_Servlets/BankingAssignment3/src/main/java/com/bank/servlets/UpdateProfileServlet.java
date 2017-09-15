package com.bank.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
// FIXME
	private final int STATUS_CODE_FAILED = 418;
	
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost in UpdateProfileServlet");	// DEBUG

		// Grab all params, even though in this case it is 1 JSON String
		// name values
		Map<String, String[]> myMap = request.getParameterMap();

		// Get the keyset from the map
		Set<String> dtoObject = myMap.keySet();

		// use Jackson. API for converting JSON to java
		ObjectMapper jackson = new ObjectMapper();

		// Convert our keyset into an array, then get what we need
		Object obj = dtoObject.toArray()[0];
		ArrayList<String> tx = jackson.readValue((String) obj, ArrayList.class);

		Service bankService = new Service();

		// Parameters in the following order: [userId, fistName, lastName, password, email]
		int userId = Integer.parseInt(tx.get(0));
		String firstName = tx.get(1);
		String lastName = tx.get(2);
		String password = tx.get(3);
		String email = tx.get(4);

		HttpSession session = request.getSession();
		BankUser user = bankService.getBankUser(userId);

		String json = "";
		if (user == null) {
			// userId does not correspond to a Bank User.
			// This should never happen
			System.out.println("User is not in database");
			json = "false";
		} else {
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setEmail(email);
			if (bankService.updateBankUser(userId, user)) {
				session.setAttribute("user", user);	// Update the session's copy of the User
				json = "true";
			}
			else {
				System.out.println("Update failed");
				json = "false";
			}

		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}
}