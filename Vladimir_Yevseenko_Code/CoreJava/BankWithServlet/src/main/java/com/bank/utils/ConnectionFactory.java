package com.bank.utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	private static ConnectionFactory instance;
	
	
	private static final String DB_PROP_PATH = 
			"C:/Users/vlad/my_git_repos/1708Aug14Code/Vladimir_Yevseenko_Code/CoreJava/BankWithServlet/src/main/java/com/bank/utils/database.properties";
	
	private ConnectionFactory() {}
	
	public synchronized static ConnectionFactory getInstance() {
		if (instance == null)
			instance = new ConnectionFactory();
		return instance;
	}
	
	public Connection getConnection() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader(DB_PROP_PATH));
			Class.forName(prop.getProperty("driver"));
			return DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("usr"), prop.getProperty("pw"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}