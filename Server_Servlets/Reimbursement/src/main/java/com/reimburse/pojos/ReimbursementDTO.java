package com.reimburse.pojos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Same as a Reimbursement but also has fields for the submitter/resolver usernames
public class ReimbursementDTO extends Reimbursement {

	private String submitterUsername;
	private String resolverUsername;

	public ReimbursementDTO(int reimbursementId, int submitterId, reimbursementStatus status, LocalDateTime submitDate,
			String description, BigDecimal ammount, String submitterUsername, String resolverUsername) {
		super(reimbursementId, submitterId, status, submitDate, description, ammount);
		
		this.submitterUsername = submitterUsername;
		this.resolverUsername = resolverUsername;
	}

	public ReimbursementDTO(Reimbursement reimburse, String submitterUsername, String resolverUsername) {
		super(reimburse.getReimbursementId(), reimburse.getSubmitterId(), reimburse.getStatus(),
				reimburse.getSubmitDate(), reimburse.getDescription(), reimburse.getAmmount());
		this.resolverId = reimburse.getResolverId();
		this.resolvedDate = reimburse.getResolvedDate();
		this.resolveNotes = reimburse.getResolveNotes();
		
		this.submitterUsername = submitterUsername;
		this.resolverUsername = resolverUsername;
	}

	public String getSubmitterUsername() {
		return submitterUsername;
	}

	public void setSubmitterUsername(String submitterUsername) {
		this.submitterUsername = submitterUsername;
	}

	public String getResolverUsername() {
		return resolverUsername;
	}

	public void setResolverUsername(String resolverUsername) {
		this.resolverUsername = resolverUsername;
	}

	@Override
	public String toString() {
		return "ReimbursementDTO [" + super.toString() + " submitterUsername=" + submitterUsername + ", resolverUsername=" + resolverUsername
				+ "]";
	}

}