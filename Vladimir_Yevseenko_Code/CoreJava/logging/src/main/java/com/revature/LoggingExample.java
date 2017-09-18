package com.revature;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggingExample {
	private static Logger logger = Logger.getRootLogger();
	public static void main(String[] args) {
		logger.setLevel(Level.INFO);
		logger.trace("logging");
	}
}