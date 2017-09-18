package com.revature.email;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;
import com.sun.mail.smtp.SMTPTransport;

public class EmailSender {

	public final static String FROM = "yevseenko.vladimir@gmail.com";
	public final static String HOST = "localhost";

	private static Logger logger = Logging.getLogger();

	public void sendEmail(String to, String password) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);
        try {
	        // -- Set the FROM and TO fields --
	        msg.setFrom(new InternetAddress(FROM));
	        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	     
	
	        msg.setSubject("Welcome to Vlad's Reimbursement System");
	        msg.setText("Your temporary password is: " + password, "utf-8");
	        msg.setSentDate(new Date());
	
	        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
	
	        t.connect("smtp.gmail.com", FROM, "vova1542");
	        t.sendMessage(msg, msg.getAllRecipients());      
	        t.close();
        } catch (MessagingException ex) {
        	logger.fatal("EmailSender MessagingException");
        	logger.fatal(ex.getMessage());
        }
    }
}