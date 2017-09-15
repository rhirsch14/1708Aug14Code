package com.bank.pojos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Account {
	
	public static final Map<accountType, Double> rewardsRateMap = new TreeMap<accountType, Double>();
	public static final Map<accountType, Double> savingsRateMap = new TreeMap<accountType, Double>();
	
	// Initialization block
	static {
		rewardsRateMap.put(accountType.BRONZE, (double)1/100);
		rewardsRateMap.put(accountType.SILVER, (double)2/100);
		rewardsRateMap.put(accountType.GOLD, (double)3/100);
		rewardsRateMap.put(accountType.PLATINUM, (double)5/100);
		rewardsRateMap.put(accountType.DOUBLE_PLATINUM, (double)10/100);
		
		savingsRateMap.put(accountType.BRONZE, (double)3/100);
		savingsRateMap.put(accountType.SILVER, (double)4/100);
		savingsRateMap.put(accountType.GOLD, (double)5/100);
		savingsRateMap.put(accountType.PLATINUM, (double)6/100);
		savingsRateMap.put(accountType.DOUBLE_PLATINUM, (double)8/100);
	}
	
	// This is used to delimit fields in the toString and fromString methods
	public static final String delimit = "::";
	
	// The date the account was opened
	private final String accountOpenedDate;

	// Account id number is initialized when the account is created
	private final int accountId;
	
	// Every account must have a unique username
	private final String username;
	private String password;
	
	// This comes from the User and is used to link a user to their account
	private final String SSN;
	
	// Marks whether an account was deleted
	// When a user deletes their account all of their information is persisted
	// The only change is that the account is marked as deleted and afterwards
	// can only be accessed by bank personel.
	private boolean deleted = false;
	
	// BigDecimal: Immutable, arbitrary-precision signed decimal number
	private BigDecimal checkingBalance = BigDecimal.ZERO;
	private BigDecimal savingsBalance = BigDecimal.ZERO;
	private BigDecimal rewardsBalance = BigDecimal.ZERO;
	
	// The interest rate is yearly and based only on the savings account
	private double interestRate;
	// The rewards rate is applied by purchase and accrues only with purchases
	private double rewardsRate;

	private accountType type;
	
	/*
	 * Account type determines interest rate for savings account and
	 * the percentage of rewards accrued per purchase
	 */
	public enum accountType {
		BRONZE,
		SILVER,
		GOLD,
		PLATINUM,
		DOUBLE_PLATINUM
	};

	public Account(Person person, String username, String password, accountType type, int accountId) {
		this.accountId = accountId;
		accountOpenedDate = new Date().toString();
		this.type = type;
		this.username = username;
		this.password = password;
		this.SSN = person.getSSN();
		
		this.interestRate = savingsRateMap.get(type);
		this.rewardsRate = rewardsRateMap.get(type);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public BigDecimal getCheckingBalance() {
		return checkingBalance;
	}

	public void setCheckingBalance(BigDecimal checkingBalance) {
		this.checkingBalance = checkingBalance;
	}

	public BigDecimal getSavingsBalance() {
		return savingsBalance;
	}

	public void setSavingsBalance(BigDecimal savingsBalance) {
		this.savingsBalance = savingsBalance;
	}

	public BigDecimal getRewardsBalance() {
		return rewardsBalance;
	}

	public void setRewardsBalance(BigDecimal rewardsBalance) {
		this.rewardsBalance = rewardsBalance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public double getRewardsRate() {
		return rewardsRate;
	}

	public accountType getType() {
		return type;
	}

	public void setType(accountType type) {
		this.type = type;
	}

	public static Map<accountType, Double> getRewardsRateMap() {
		return rewardsRateMap;
	}

	public static Map<accountType, Double> getSavingsRateMap() {
		return savingsRateMap;
	}

	public String getAccountOpenedDate() {
		return accountOpenedDate;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getUsername() {
		return username;
	}

	public String getSSN() {
		return SSN;
	}
	
	private Account(String date, int id, String username, String password, String SSN,
			boolean deleted, BigDecimal checkingBalance, BigDecimal savingsBalance, BigDecimal rewardsBalance,
			double interestRate, double rewardsRate, accountType type) {
		
		this.accountOpenedDate = date;
		this.accountId = id;
		this.username = username;
		this.password = password;
		this.SSN = SSN;
		this.deleted = deleted;
		this.checkingBalance = checkingBalance;
		this.savingsBalance = savingsBalance;
		this.rewardsBalance = rewardsBalance;
		this.interestRate = interestRate;
		this.rewardsRate = rewardsRate;
		this.type = type;
	}
	
	// NOTE: Since the date.toString() returns single colons, this delimited must
	// be different so I chose delimit
	public String toString() {
		return accountOpenedDate + delimit + accountId + delimit + username + delimit + password + delimit + SSN + delimit +
				deleted + delimit + checkingBalance + delimit + savingsBalance + delimit + rewardsBalance + delimit + 
				interestRate + delimit + rewardsRate + delimit + type;
	}
	public static Account fromString(String str) throws NumberFormatException {
		String[] splitStr = str.split(delimit);
		
		return new Account(splitStr[0], Integer.parseInt(splitStr[1]), splitStr[2], splitStr[3], 
				splitStr[4], Boolean.parseBoolean(splitStr[5]),
				new BigDecimal(splitStr[6]), new BigDecimal(splitStr[7]),
				new BigDecimal(splitStr[8]), Double.parseDouble(splitStr[9]),
				Double.parseDouble(splitStr[10]), (accountType.valueOf(splitStr[11])));
	}
	
	// Validaes whether these strings can be used to make this object. Uses delimit
	public boolean validateStrings() {
		
		return !(
				(SSN.contains(delimit) || SSN.contains(User.delimit)) ||
				(accountOpenedDate.contains(delimit) || accountOpenedDate.contains(User.delimit)) ||
				(username.contains(delimit) || username.contains(User.delimit)) ||
				(password.contains(delimit) || password.contains(User.delimit)));
	}
	
}