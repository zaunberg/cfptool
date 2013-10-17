package org.zaunberg.cfptool;

import javax.mail.PasswordAuthentication;

public class MyMailAuthenticator extends javax.mail.Authenticator {
	private String username;
	private String password;
	
	public MyMailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
    public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(username, password);
    }
}
