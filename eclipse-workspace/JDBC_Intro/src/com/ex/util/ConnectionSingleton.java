package com.ex.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionSingleton {
	
	private static ConnectionSingleton cs = null;
	private static Boolean build = true;
	
	private ConnectionSingleton() {
		build = false;
	}
	
	public static synchronized ConnectionSingleton getInstance() {
		if (build) {
			cs = new ConnectionSingleton();
		}
		return cs;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties props = new Properties();
			props.load(new FileReader("C:/Users/will/my_git_repos/1708Aug14Code/eclipse-workspace/JDBC_Intro/src/com/ex/util/database.properties"));
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