package com.revature.pojos;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.revature.logging.Logging;

public class Reimbursement {
	private final int id;
	private final User submitter, resolver;
	private final Date submissionDate, resolutionDate;
	private final Status status;
	private final String description;
	private final double amount;
	
	private static Logger log = Logging.getLogger();
	
	
	public Reimbursement(User submitter, User resolver, Date submissionDate,
			Date resolutionDate, Status reimbursementStatus, String description, double amount) {
		this.id = -1;
		this.submitter = submitter;
		this.resolver = resolver;
		this.submissionDate = submissionDate;
		this.resolutionDate = resolutionDate;
		this.status = reimbursementStatus;
		this.description = description;
		this.amount = amount;
		log.debug("Reimbursement(...) created");
	}
	
	public Reimbursement(int id, User submitter, User resolver, Date submissionDate,
			Date resolutionDate, Status reimbursementStatus, String description, double amount) {
		this.id = id;
		this.submitter = submitter;
		this.resolver = resolver;
		this.submissionDate = submissionDate;
		this.resolutionDate = resolutionDate;
		this.status = reimbursementStatus;
		this.description = description;
		this.amount = amount;
		log.debug("Reimbursement(id, ...) created");
	}
	
	public int getId() {
		return id;
	}
	
	public User getSubmitter() {
		return submitter;
	}
	
	public User getResolver() {
		return resolver;
	}
	
	public Date getSubmissionDate() {
		return submissionDate;
	}
	
	public Date getResolutionDate() {
		return resolutionDate;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("submitter", new JSONObject(submitter.toMap()));
		map.put("resolver", new JSONObject(resolver.toMap()));
		map.put("submissionDate", submissionDate.toString());
		map.put("resolutionDate", resolutionDate.toString());
		map.put("status", status.getStr());
		map.put("description", description);
		map.put("amount", amount);
		return map;
	}
}