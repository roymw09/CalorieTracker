package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	
	private PasswordAuthentication authentication;

	public SMTPAuthenticator(String login, String password) {
		authentication = new PasswordAuthentication(login, password);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
