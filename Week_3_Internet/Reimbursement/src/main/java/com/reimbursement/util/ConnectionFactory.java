package com.reimbursement.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class ConnectionFactory {
	private static ConnectionFactory cf = null;
	private static Boolean build = true;
	
	private ConnectionFactory() {
		build = false;
	}
	
	/*
	 * provides method callers with the CF object
	 * and synchronizes to prevent 2 threads from creating connection object simultaneously
	 */
	public static synchronized ConnectionFactory getInstance() {
		if(build == true) {
			cf = new ConnectionFactory();
		}
		return cf;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("C:\\Users\\apbon\\my_git_repos\\1708Aug14Code\\Week_3_Internet\\Reimbursement\\src\\main\\java\\com\\reimbursement\\util\\database.properties"));
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), //URL, USERNAME, AND PASSWORD. Get from the database.properties
					prop.getProperty("usr"), prop.getProperty("pwd"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
}
