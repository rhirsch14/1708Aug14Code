package com.reimburse.servlets;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.pojos.Worker;
import com.reimburse.service.Service;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {

	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost in UpdateProfileServlet"); // DEBUG

		ArrayList<String> tx = readValuesFromRequest(request);

		Service service = new Service();

		// Parameters in the following order: [userId, firstName, lastName, email, username, password, managerId, isManager]
		int userId = Integer.parseInt(tx.get(0));
		String firstName = tx.get(1);
		String lastName = tx.get(2);
		String email = tx.get(3);
		String username = tx.get(4);
		String password = tx.get(5);
		int managerId = Integer.parseInt(tx.get(6));
		boolean isManager = tx.get(7).equals("true")? true : false; 

		HttpSession session = request.getSession();
		Worker user = service.getWorker(userId);

		String json = "";
		if (userId == -1) {
			// Create a new worker
			
			service.tryCreateWorker(managerId, firstName, lastName, email, username, password, isManager);
		}
		if (user == null) {
			// userId does not correspond to a Bank User.
			// This should never happen
			System.out.println("User is not in database");
			json = "false";
		} else {

			if (service.updateWorker(userId, firstName, lastName, email, username, password)) {
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setUsername(username);
				user.setPassword(password);
				session.setAttribute("user", user); // Update the session's copy
													// of the User
				json = "true";
			} else {
				System.out.println("Update failed");
				json = "false";
			}

		}

		writeValueToResponse(response, json);
	}
	
	private ArrayList<String> readValuesFromRequest(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		// Grab all params, even though in this case it is 1 JSON String
		// name values
		Map<String, String[]> myMap = req.getParameterMap();

		// Get the keyset from the map
		Set<String> dtoObject = myMap.keySet();

		// use Jackson. API for converting JSON to java
		ObjectMapper jackson = new ObjectMapper();

		// Convert our keyset into an array, then get what we need
		Object obj = dtoObject.toArray()[0];
		return jackson.readValue((String) obj, ArrayList.class);
	}
	
	private void writeValueToResponse(HttpServletResponse resp, String json) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		out.write(json);
	}
}