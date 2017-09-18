package com.revature.pojos;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;

public enum Status {
	PENDING(1, "pending"), APPROVED(2, "approved"), DENIED(3, "denied");
	
	
	private int id;
	private String str;
	private static Logger log = Logging.getLogger();
	
	private Status(int id, String str) {
		this.id = id;
		this.str = str;
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getStr() {
		return str;
	}
	
	public static Status getStatus(int id) {
		switch(id) {
			case 1: return PENDING;
			case 2: return APPROVED;
			case 3: return DENIED;
			default: log.warn("getStatus() unknown id, returning null"); return null;
		}
	}
}