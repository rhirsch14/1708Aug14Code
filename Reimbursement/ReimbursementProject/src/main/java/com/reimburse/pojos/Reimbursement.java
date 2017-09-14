package com.reimburse.pojos;

import java.sql.Date;

public class Reimbursement {
	private int reimburseid;
	private String desc;
	private double amount;
	private Status statusid;
	private User submitterid;
	private User resolverid;
	private Date submitDate;
	private Date resolveDate;
	private String notes;
	
	public Reimbursement(){}
	
	
	public Reimbursement(int reimburseid, String desc, double amount, Status statusid, User submitterid, User resolverid,
			Date submitDate, Date resolveDate, String notes) {
		super();
		this.reimburseid = reimburseid;
		this.desc = desc;
		this.amount = amount;
		this.statusid = statusid;
		this.submitterid = submitterid;
		this.resolverid = resolverid;
		this.submitDate = submitDate;
		this.resolveDate = resolveDate;
		this.notes = notes;
	}


	public int getReimburseid() {
		return reimburseid;
	}


	public void setReimburseid(int reimburseid) {
		this.reimburseid = reimburseid;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Status getStatusid() {
		return statusid;
	}


	public void setStatusid(Status statusid) {
		this.statusid = statusid;
	}


	public User getSubmitterid() {
		return submitterid;
	}


	public void setSubmitterid(User submitterid) {
		this.submitterid = submitterid;
	}



	public User getResolverid() {
		return resolverid;
	}

	public void setResolverid(User resolverid) {
		this.resolverid = resolverid;
	}


	public Date getSubmitDate() {
		return submitDate;
	}



	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getResolveDate() {
		return resolveDate;
	}


	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}



	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
