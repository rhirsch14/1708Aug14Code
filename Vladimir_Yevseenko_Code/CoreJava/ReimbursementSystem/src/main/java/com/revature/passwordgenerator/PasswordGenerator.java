package com.revature.passwordgenerator;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
	
	/*
	 * Length of temporary password to generate
	 */
	private final static int PASSWORD_LENGTH = 8;
	
	/*
	 * A String containing all the acceptable characters to be used in a temporary password
	 */
	private final static String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	/*
	 * Random instance to be used, SecureRandom makes for better RNG than just Random
	 */
	private final static Random RANDOM = new SecureRandom();
	
	/*
	 * Returns a String of length PASSWORD_LENGTH that is a temporary password
	 */
	public String generatePassword() {
		String pw = "";
		
		for (int i=0; i<PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * CHARACTERS.length());
			pw += CHARACTERS.charAt(index);
		}
		return pw;
	}
}