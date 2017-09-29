package com.revature.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Ensures one connection to one database.
 * @author Will Underwood
 *
 */
public class ConnectionSingleton {
	
	private static ConnectionSingleton singleton = null;
	private static Boolean hasInstance = false;
	
	private ConnectionSingleton() {
		hasInstance = true;
	}
	
	/**
	 * Returns the sole instance of this class.
	 * @precondition None
	 * @param None
	 * @return The sole instance of this class
	 */
	public static synchronized ConnectionSingleton getInstance() {
		if (!hasInstance) {
			singleton = new ConnectionSingleton();
		}
		return singleton;
	}
	
	/**
	 * Returns the connection to the database.
	 * @precondition None
	 * @param None
	 * @return The connection to the database.
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties props = new Properties();
			props.load(new FileReader("C:/Users/RevatureAdmin/my_git_repos/1708Aug14Code/eclipse-workspace/LoginJDBC/src/main/java/com/revature/util/database.properties"));
			Class.forName(props.getProperty("driver"));
			conn = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("usr"),
					props.getProperty("pwd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}