package com.bank.pojos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Account {
	
	public static final Map<accountLevel, Double> rewardsRateMap = new TreeMap<accountLevel, Double>();
	public static final Map<accountLevel, Double> savingsRateMap = new TreeMap<accountLevel, Double>();
	public static final Map<accountLevel, Double> creditRateMap = new TreeMap<accountLevel, Double>();
	
	// Initialization block
	static {
		rewardsRateMap.put(accountLevel.BRONZE, (double)1/100);
		rewardsRateMap.put(accountLevel.SILVER, (double)2/100);
		rewardsRateMap.put(accountLevel.GOLD, (double)3/100);
		rewardsRateMap.put(accountLevel.PLATINUM, (double)5/100);
		rewardsRateMap.put(accountLevel.DOUBLE_PLATINUM, (double)10/100);
		
		savingsRateMap.put(accountLevel.BRONZE, (double)3/100);
		savingsRateMap.put(accountLevel.SILVER, (double)4/100);
		savingsRateMap.put(accountLevel.GOLD, (double)5/100);
		savingsRateMap.put(accountLevel.PLATINUM, (double)6/100);
		savingsRateMap.put(accountLevel.DOUBLE_PLATINUM, (double)8/100);
		
		creditRateMap.put(accountLevel.BRONZE, (double)25/100);
		creditRateMap.put(accountLevel.SILVER, (double)23/100);
		creditRateMap.put(accountLevel.GOLD, (double)20/100);
		creditRateMap.put(accountLevel.PLATINUM, (double)17/100);
		creditRateMap.put(accountLevel.DOUBLE_PLATINUM, (double)12/100);
		
	}
	
	// Account id number is initialized when the account is created
	private final int accountId;
	
	// The date the account was opened
	private final LocalDate accountOpenedDate;

	// BigDecimal: Immutable, arbitrary-precision signed decimal number
	private BigDecimal balance = BigDecimal.ZERO;
	
	// Marks whether an account was deleted
	// When a user deletes their account all of their information is persisted
	// The only change is that the account is marked as deleted and afterwards
	// can only be accessed by bank personel.
	private boolean deleted = false;
	
	private accountType type;
	
	private accountLevel level;
	
	// ID of associated user who owns this account
	private int userId;
	
	/*
	 * Account type determines interest rate for savings account and
	 * the percentage of rewards accrued per purchase, and the interest
	 * rate for a credit account
	 */
	public enum accountLevel {
		NULL,		// The database is 1-indexed so I'm throwing out the index 0 element
		BRONZE,
		SILVER,
		GOLD,
		PLATINUM,
		DOUBLE_PLATINUM
	};
	public static void printAccountLevels() {
		String result = "";
		
		for (accountLevel level : accountLevel.values())
			if (level != accountLevel.NULL)
				result += level.toString() + ", ";
		result = result.substring(0, result.length()-2);	// Delete the last ", " from the end
		
		System.out.println(result);
	}
	public enum accountType {
		NULL,		// The database is 1-indexed so I'm throwing out the index 0 element
		CHECKING,
		SAVINGS,
		CREDIT,
		REWARD
	}
	public static void printAccountTypes() {
		String result = "";
		
		for (accountType type : accountType.values())
			if (type != accountType.NULL)
				result += type.toString() + ", ";
		result = result.substring(0, result.length()-2);	// Delete the last ", " from the end
		
		System.out.println(result);
	}
	
	public Account(int accountId, LocalDate accountOpenedDate, BigDecimal balance,
			boolean deleted, accountType type, accountLevel level, int userId) {
		super();
		this.accountId = accountId;
		this.accountOpenedDate = accountOpenedDate;
		this.balance = balance;
		this.deleted = deleted;
		this.type = type;
		this.level = level;
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public accountType getType() {
		return type;
	}
	public boolean setType(accountType type) {
		if (type != accountType.NULL)
			this.type = type;
		else return false;
		return true;
	}
	public accountLevel getLevel() {
		return level;
	}
	public boolean setLevel(accountLevel level) {
		if (level != accountLevel.NULL)
			this.level = level;
		else return false;
		return true;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public LocalDate getAccountOpenedDate() {
		return accountOpenedDate;
	}
	public int getAccountId() {
		return accountId;
	}
	public double getCreditRate() {
		return creditRateMap.get(level);
	}
	public double getRewardsRate() {
		return rewardsRateMap.get(level);
	}
	public double getSavingsRate() {
		return savingsRateMap.get(level);
	}
	
	
	@Override
	public String toString() {
		return "Account [accountOpenedDate=" + accountOpenedDate + ", accountId=" + accountId + ", balance=" + balance
				+ ", deleted=" + deleted + ", type=" + type + ", level=" + level + ", userId=" + userId + "]";
	}

}
