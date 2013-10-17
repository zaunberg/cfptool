package org.zaunberg.cfptool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.naming.AuthenticationException;

import org.zaunberg.cfptool.entities.Issue;
import org.zaunberg.cfptool.entities.Submission;
import org.zaunberg.cfptool.utils.FileUtils;


public class CfpTool {

	private String emailHost;
	private String emailUsername;
	private String emailPassword;
	private String emailReadFolderPath;
	private String emailMoveToFolderPath;
	private String moveEmails;
	
	private String defaultIssueProjectId;
	private String defaultIssueTypeId;
	private String defaultPriorityId;
	private String defaultAssigneeName;
	private String defaultReporterName;
	private String defaultSummary;
	private String defaultDescription;
	private String defaultLabel;
	
	private String jiraUsername;
	private String jiraPassword;
	private String jiraUrl;
	private String jiraRestAPI;
	
	private String submissionTableFileName;
	private String speakerTableFileName;
	
	private String writeMode;
	
	private boolean propertiesLoaded;
	
	public CfpTool() {
		propertiesLoaded = false;
	}
	
	public CfpTool loadProperties() {
		try {
			readProperties();
			propertiesLoaded = checkProperties();
			if(!propertiesLoaded) {
				System.out.println("some properties are empty");
			}
			
		} catch (Exception e) {
			propertiesLoaded = false;
			System.out.println("cannot load properties");
			try {
				createProperties();
				System.out.println("properties created. Please fill out.");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return this;
	}
	
	private void createProperties() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		
		prop.setProperty("emailHost", "");
		prop.setProperty("emailUsername", "");
		prop.setProperty("emailPassword", "");
		prop.setProperty("emailReadFolderPath", "");
		prop.setProperty("emailMoveToFolderPath", "");
		prop.setProperty("moveEmails", "");
		
		prop.setProperty("defaultIssueProjectId", "");
		prop.setProperty("defaultIssueTypeId", "");
		prop.setProperty("defaultPriorityId", "");
		prop.setProperty("defaultAssigneeName", "");
		prop.setProperty("defaultReporterName", "");
		prop.setProperty("defaultSummary", "");
		prop.setProperty("defaultDescription", "");
		prop.setProperty("defaultLabel", "");
		
		prop.setProperty("jiraUsername", "");
		prop.setProperty("jiraPassword", "");
		prop.setProperty("jiraUrl", "");
		prop.setProperty("jiraRestAPI", "");
		prop.setProperty("submissionTableFileName", "");
		prop.setProperty("speakerTableFileName", "");
		
		prop.setProperty("writeMode", "");
		
		prop.store(new FileOutputStream("config.properties"), null);
	}
	
	private void readProperties() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		
		emailHost = prop.getProperty("emailHost");
		emailUsername = prop.getProperty("emailUsername");
		emailPassword = prop.getProperty("emailPassword");
		emailReadFolderPath = prop.getProperty("emailReadFolderPath");
		emailMoveToFolderPath = prop.getProperty("emailMoveToFolderPath");
		moveEmails = prop.getProperty("moveEmails");
		
		defaultIssueProjectId = prop.getProperty("defaultIssueProjectId");
		defaultIssueTypeId = prop.getProperty("defaultIssueTypeId");
		defaultPriorityId = prop.getProperty("defaultPriorityId");
		defaultAssigneeName = prop.getProperty("defaultAssigneeName");
		defaultReporterName = prop.getProperty("defaultReporterName");
		defaultSummary = prop.getProperty("defaultSummary");
		defaultDescription = prop.getProperty("defaultDescription");
		defaultLabel = prop.getProperty("defaultLabel");
		
		jiraUsername = prop.getProperty("jiraUsername");
		jiraPassword = prop.getProperty("jiraPassword");
		jiraUrl = prop.getProperty("jiraUrl");
		jiraRestAPI = prop.getProperty("jiraRestAPI");
		submissionTableFileName = prop.getProperty("submissionTableFileName");
		speakerTableFileName = prop.getProperty("speakerTableFileName");
		
		writeMode = prop.getProperty("writeMode");
	}
	
	private boolean checkProperties() {
		return notEmtpy(emailHost) &&
				notEmtpy(emailUsername) &&
				notEmtpy(emailPassword) &&
				notEmtpy(emailReadFolderPath) &&
				notEmtpy(emailMoveToFolderPath) &&
				notEmtpy(moveEmails) &&
		
				notEmtpy(defaultIssueProjectId) &&
				notEmtpy(defaultIssueTypeId) &&
				notEmtpy(defaultPriorityId) &&
				notEmtpy(defaultAssigneeName) &&
				notEmtpy(defaultReporterName) &&
				notEmtpy(defaultSummary) &&
				notEmtpy(defaultDescription) &&
				notEmtpy(defaultLabel) &&
		
				notEmtpy(jiraUsername) &&
				notEmtpy(jiraPassword) &&
				notEmtpy(jiraUrl) &&
				notEmtpy(jiraRestAPI) &&
				notEmtpy(submissionTableFileName) &&
				notEmtpy(speakerTableFileName) &&
				
				notEmtpy(writeMode);		
	}
	
	private boolean notEmtpy(String s) {
		return !s.isEmpty() && !s.equals(" ");
	}

	public void run() {
		if(propertiesLoaded) {
			ImapMailClient imapMailClient = new ImapMailClient(emailHost, emailUsername, emailPassword);
			
			Issue defaultIssue = new Issue(defaultIssueProjectId, defaultIssueTypeId, defaultPriorityId, defaultAssigneeName, 
					defaultReporterName, defaultSummary, defaultDescription, defaultLabel);
			Jira jira = new Jira(jiraUsername, jiraPassword, jiraUrl, jiraRestAPI);
			Confluence confluence = new Confluence(jira.getJiraUrl());
			
			MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
				
			List<Issue> issueReport = new ArrayList<Issue>();
			
			try {
				System.out.println("Connect to e-mail-account: "+ emailUsername +" on: "+ emailHost);
				imapMailClient.conntect();
				
				System.out.println("Read from dir: "+ emailReadFolderPath);
				Message[] messageArray = imapMailClient.getMessages(Folder.READ_ONLY, emailReadFolderPath);
				
				System.out.println(messageArray.length +" E-Mails found");
				//ImapMailClient.listMassages(messageArray);
	
				System.out.println("read submissions");
				List<Submission> submissionList = messageToSubmissionConverter.readAndConvert(messageArray);
				
				try {
					
					if(writeMode.equalsIgnoreCase("true")) {
						System.out.println("create issues");
						issueReport = jira.createIssueList(submissionList, defaultIssue);
						System.out.println("Issues created");
					} else {
						System.out.println("create issues (DUMMY-Mode active)");
						issueReport = jira.createIssueListDUMMY(submissionList, defaultIssue);
						System.out.println("Issues created (DUMMY-Mode active)");						
					}
					
					System.out.println("create submissiontable: "+ submissionTableFileName);
					String submissionTable = confluence.generateSubmissionTable(issueReport, !FileUtils.fileExists(submissionTableFileName));
					FileUtils.writeToFile(submissionTable, submissionTableFileName);			
					
					System.out.println("create speakertable: "+ speakerTableFileName);
					String speakerTable = confluence.generateSpeakerTable(issueReport, !FileUtils.fileExists(speakerTableFileName));
					FileUtils.writeToFile(speakerTable, speakerTableFileName);
					
					
					if(moveEmails.equalsIgnoreCase("true")) {
						if(writeMode.equalsIgnoreCase("true")) {
							System.out.println("move e-mails from: "+ emailReadFolderPath +" to: "+ emailMoveToFolderPath);
							imapMailClient.moveMailsFromTo(emailReadFolderPath, emailMoveToFolderPath);
						} else {
							System.out.println("ignore moveEmails from: "+ emailReadFolderPath +" to: "+ emailMoveToFolderPath +" (DUMMY-Mode active)");
						}
					}
					
				} catch (AuthenticationException ae) {
					System.out.println("Jira-authentication-error, cannot create issues");
				}			

				imapMailClient.disconnect();
				System.out.println("disconnect");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!issueReport.isEmpty()) {
				System.out.println("Report: ");
				for(Issue rep : issueReport) {
					System.out.println(" - "+ rep);
				}	
			} else {
				System.out.println("Report: ");
				System.out.println(" - no issues created");
			}
		} else {
			System.out.println("Properties not loaded!");
		}
	}
}
