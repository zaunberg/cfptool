package org.zaunberg.cfptool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;



public class ImapMailClient {
	
	private static final boolean DEBUG = false;
	
	public static final String MAIL_STORE = "imap";
	public static final String MAIN_FOLDER = "INBOX";
	private String host;
	private Authenticator authenticator;
	private Properties props;
	private Store store;
	private Folder folder;
	
	public ImapMailClient(String host, String username, String password) {
		this.host = host;
		this.authenticator = new MyMailAuthenticator(username, password);
		setProperties();
	}
	
	private void setProperties() {
		this.props = System.getProperties();
		this.props.put("mail.imap.host", host);
		this.props.put("mail.imap.auth", "true");
	}
	
	public void conntect() throws MessagingException {
		Session session = Session.getDefaultInstance(props, authenticator);
		session.setDebug(DEBUG);

		this.store = session.getStore(MAIL_STORE);
		this.store.connect();	
	}
	
	public void disconnect() throws MessagingException {	
		this.folder.close(false);
		this.store.close();		
	}
	
	public boolean isConnected() {
		return this.store.isConnected();
	}
	
	// Öffnet einen Ordner relativ zum aktiven Ordner.
	private Folder openFolder(Folder aktivFolder, String relativFolderPath) throws MessagingException {
		String[] folderNames = relativFolderPath.split("/");
		for(String str : folderNames) {
			if(!str.isEmpty()) {
				aktivFolder = aktivFolder.getFolder(str);
			}
		}	
		return aktivFolder;
	}
	
	public Message[] getMessages(int mode, String folderPath) throws MessagingException {
		this.folder = this.store.getFolder(MAIN_FOLDER);
		
		if(folderPath != null) {
			this.folder = openFolder(this.folder, folderPath);
		}
		
		this.folder.open(mode);
			
		Message[] messages = this.folder.getMessages();
		
		return messages;		
	}
	
	public Message[] getMessages() throws MessagingException {
		return getMessages(Folder.READ_ONLY, MAIN_FOLDER);
	}
	
	public void moveMailsFromTo(String from, String to) throws MessagingException {
		Folder toFolder = openFolder(this.store.getFolder(MAIN_FOLDER), to);
		
		Message[] messages = getMessages(Folder.READ_WRITE, from);
		
		// Nachrichten in neuen Ordner kopieren
		this.folder.copyMessages(messages, toFolder);
		
		// kopierte nachrichten löschen
		this.folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
		this.folder.expunge();
		
	}

	public static void listMassages(Message[] messages) throws MessagingException, IOException {
		for (int i = 0; i < messages.length; i++) {
			Message m = messages[i];

			System.out.println("-----------------------\nNachricht: " + i);
			System.out.println("Von: " + Arrays.toString(m.getFrom()));
			System.out.println("Betreff: " + m.getSubject());
			System.out.println("Gesendet am: " + m.getSentDate());
			System.out.println("ContentType: " + new ContentType(m.getContentType()));
			System.out.println("Content: " + m.getContent());

			// Nachricht ist eine einfache Text- bzw. HTML-Nachricht
			if (m.isMimeType("text/plain")) {
				System.out.println(m.getContent());
			}

			// Nachricht ist eine Multipart-Nachricht (besteht aus mehreren
			// Teilen)
			if (m.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) m.getContent();
				for (int j = 0; j < mp.getCount(); j++) {
					Part part = mp.getBodyPart(j);
					String disposition = part.getDisposition();

					if (disposition == null) {
						MimeBodyPart mimePart = (MimeBodyPart) part;

						if (mimePart.isMimeType("text/plain")) {
							BufferedReader br = new BufferedReader(new InputStreamReader(mimePart.getInputStream()));
							String line = null;
							while((line = br.readLine()) != null ) {
								System.out.println(line);
							}
						}
					}
				}
			}
		}
	}
}
