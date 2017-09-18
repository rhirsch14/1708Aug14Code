package com.revature.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;

public class Hasher {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private final static String SALT = "5ebe2294ecd0e0f08eab7690d2a6ee69";

	private static Logger logger = Logging.getLogger();

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public String hashPassword(String in) {
		logger.debug("Hasher hashPassword()");
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(SALT.getBytes()); // <-- Prepend SALT.
			md.update(in.getBytes());
			byte[] out = md.digest();
			return bytesToHex(out); // <-- Return the Hex Hash.
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}