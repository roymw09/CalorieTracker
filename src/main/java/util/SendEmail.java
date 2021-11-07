package util;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
	private String message;
	private String subject;
	
	public SendEmail(String subject, String message) {
		this.subject = subject;
		this.message = message;
	}
	
    // Generate random verification code
    public String generateRandomCode() {
    	Random random = new Random();
    	int number = random.nextInt(999999);
    	return String.format("%06d", number);
    }
	
	public boolean sendEmail(String emailAddress, String verificationCode) {
		boolean wasEmailSent = false;
		String from = "anrcalorietracker@gmail.com";
		String to = emailAddress;
		String login = from;
		String password = "xxsmranpbsltbirk"; // google app password
		message += verificationCode;

		Properties props = new Properties();
		props.setProperty("mail.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");

		Authenticator auth = new SMTPAuthenticator(login, password);

		Session session = Session.getInstance(props, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setText(message);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);
			wasEmailSent = true;
		} catch (MessagingException ex) {
			Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
		return wasEmailSent;
	}
}
