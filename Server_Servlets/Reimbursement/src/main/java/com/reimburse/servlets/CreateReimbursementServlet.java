package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.dao.DaoImpl;
import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Worker;
import com.reimburse.service.Service;

@WebServlet("/createReimbursement")
public class CreateReimbursementServlet extends HttpServlet {
	
	// For submitting recipts
	private static String bucketName = "myfirstbucket1708";
	private static String key = "2";
	private static String uploadFileName = "temp";

	final static Logger logger = Logger.getLogger(CreateReimbursementServlet.class);
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPost");

		ArrayList<String> tx = readValuesFromRequest(request);

		Service service = new Service();
		
		// Parameters in the following order: [ description, ammount ]
		String description = tx.get(0);
		BigDecimal ammount = new BigDecimal(tx.get(1));
		
		HttpSession session = request.getSession();
		Worker user = (Worker)session.getAttribute("user");
		int userId = user.getWorkerId();

		String json = "";
		Reimbursement reimburse = service.tryCreateReimbursement(userId, description, ammount);
		
		if (reimburse == null)
			json = "false";
		else 
			json = "true";

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
