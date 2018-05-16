package com.oleksa.model.service;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class MailTest {

//	@Test
	public void testSendMail() {
			final String username = "beautysalon9689aac76739245d@gmail.com";
	        final String password = "4e8df24ba7ad6caa";

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("beautysalon9689aac76739245d@gmail.com"));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("atpt34@gmail.com"));
	            message.setSubject("Testing Subject");
	            message.setText("Dear Mail Crawler,"
	                + "\n\n No spam to my email, please!");

	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	
}
