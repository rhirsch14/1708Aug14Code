package com.revature.tests;



import org.junit.Test;

import com.revature.email.EmailSender;

public class EmailSenderTests {

	@Test
	public void test() {
		EmailSender es = new EmailSender();
		es.sendEmail("yevseenko.vladimir@gmail.com", "password");
	}
	
}