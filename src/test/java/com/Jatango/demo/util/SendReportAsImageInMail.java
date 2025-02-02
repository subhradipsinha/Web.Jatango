package com.Jatango.demo.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static com.github.dockerjava.api.model.PortConfig.PublishMode.host;
import static io.restassured.RestAssured.port;

public class SendReportAsImageInMail {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");



		
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("subhradip.sinha@ncompasbusiness.com", "IceCream@2024");
			}
		});
		// compose message
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("subhradip.sinha@ncompasbusiness.com", "subhradip.sinha@ncompasbusiness.com"));

			msg.setReplyTo(InternetAddress.parse("subhradip.sinha@ncompasbusiness.com", false));

			msg.setSubject("Test Report by Automation", "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("subhradip.sinha@ncompasbusiness.com"));

			// Create the message body part
			/*
			 * BodyPart messageBodyPart = new MimeBodyPart();
			 * 
			 * messageBodyPart.setText("Find mail as report");
			 */

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			//multipart.addBodyPart(messageBodyPart);

		    
			BodyPart  messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1>Please find embedded Report in mail</H1><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);
			
           // Second part is image  embed
			
			String filename = System.getProperty("user.dir")
					+ "\\Reports\\HTMLReports\\Jatango.html";
			//Next add the image by creating a Datahandler as follows:

			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(
					filename);

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "image");
			

			// Set the multipart message to the email message
			multipart.addBodyPart(messageBodyPart);
			
			msg.setContent(multipart);
			

			// Send message
			Transport.send(msg);
			
			System.out.println("EMail Sent Successfully with image!!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
