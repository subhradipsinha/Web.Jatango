package demo.pageObject;

import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SendEmailReportByTakingScreenShot {

	public static void mail() throws IOException {
		// TODO Auto-generated method stub

		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");


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
			msg.addHeader("My Reports", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("subhradip.sinha@ncompasbusiness.com", "subhradip.sinha@ncompasbusiness.com"));

			msg.setReplyTo(InternetAddress.parse("subhradip.sinha@ncompasbusiness.com", false));

			msg.setSubject("Test Report by Automation", "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("subhradip.sinha@gmail.com"));

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1>Please find embedded Report in mail</H1><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/HTML");
			// add it
			multipart.addBodyPart(messageBodyPart);
			
           // Second part is taking screen shot 
			
			String filename1 = System.getProperty("user.dir")
					+ "\\Reports\\HTMLReports\\Jatango.html";
			
			WebDriverManager.chromedriver().setup();
			WebDriver driver= new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(filename1);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//take screenshot
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(System.getProperty("user.dir")+"\\Reports\\HTMLReports\\Jatango.html"));
			driver.close();
	
			//Next add the image by creating a Datahandler as follows:
			String filename2 = System.getProperty("user.dir")+""
					+ "\\Reports\\HTMLReports\\Jatango.html";
			String filename3 = System.getProperty("user.dir")+""
					+ "\\Reports\\PdfReport\\Jatango.pdf";
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(
					filename2);
			DataSource fds1= new FileDataSource(filename3);

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setDataHandler(new DataHandler(fds1));
			messageBodyPart.setHeader("Content-ID", "image");
			

			// Set the multipart message to the email message
			multipart.addBodyPart(messageBodyPart);
			
			msg.setContent(multipart);
			

			// Send message
			Transport.send(msg);
			
			System.out.println("Email Sent Successfully with image!!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}


}
