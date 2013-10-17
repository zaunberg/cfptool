package org.zaunberg.cfptool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zaunberg.cfptool.entities.Issue;
import org.zaunberg.cfptool.entities.Speaker;
import org.zaunberg.cfptool.entities.SpeakerWithIssue;
import org.zaunberg.cfptool.entities.Submission;


public class Confluence {

	private String jiraUrl;
	
	public Confluence(String jiraUrl) {
		this.jiraUrl = jiraUrl;
	}
	
	public String generateSubmissionTable(List<Issue> issueReport, boolean withTableHead) {
		StringBuffer sb = new StringBuffer();
		
		if(withTableHead) {
			sb.append("|| Title || Speaker || Sprache || Typ || Jira-Link ||\n");
		}
		
		for (Issue issue : issueReport) {
			Submission submission = issue.getSubmission();
			String issueId = issue.getIssueKey();
			String jiraLink = "";
			
			if(issueId != null) {
				jiraLink = this.jiraUrl +"/browse/"+ issueId;
			}
			
			sb.append("| " + submission.getTitle() + " | "
					+ speaker(submission) + " | " + submission.getLang() + " | " + submission.getType()
					+ " | " + jiraLink + " |\n");
		}

		return sb.toString();		
	}
	
	public Map<String, SpeakerWithIssue> generateSpeakerMap(List<Issue> issueReport) {
		Map<String, SpeakerWithIssue> speakerMap = new HashMap<String, SpeakerWithIssue>();
		
		for(Issue issue : issueReport) {
			if(issue.getSubmission() != null) {
				for(Speaker speaker : issue.getSubmission().getSpeakerList()) {
					speakerMap.put(speaker.getFirstname() +" "+ speaker.getLastname(), new SpeakerWithIssue(speaker, issue));
				}
			}
		}
		
		return speakerMap;
	}
	
	public String generateSpeakerTable(List<Issue> issueReport, boolean withTableHead) {
		Map<String, SpeakerWithIssue> speakerMap = generateSpeakerMap(issueReport);
		StringBuffer sb = new StringBuffer();
		
		if(withTableHead) {
			sb.append("|| Speaker/Jira-Link || Email || Twitter || Xing|| Firma  || Bild ||\n");
		}
		
		for(SpeakerWithIssue speakerWithIssue : speakerMap.values()) {
			Speaker speaker = speakerWithIssue.getSpeaker();
			
			String name = speaker.getFirstname() +" "+ speaker.getLastname();
			String email = "[mailto:"+ speaker.getEmail() +"]";
			
			String jiraLink = "";
			
			String issueId = speakerWithIssue.getIssue().getIssueKey();
			if(issueId != null) {
				jiraLink = "["+ name +"|"+ this.jiraUrl +"/browse/"+ issueId +"]";
			}
			
			sb.append("| "+ jiraLink +" | "+ email +" | "+ speaker.getTwitter() +" | "+ speaker.getXing() +" | "+ speaker.getCompany() +" |  |\n");			
			
		}

		return sb.toString();		
	}

	private String speaker(Submission submission) {
		StringBuffer sb = new StringBuffer();
		for (Speaker speaker : submission.getSpeakerList()) {
			sb.append(speaker.getFirstname() + " " + speaker.getLastname());
			sb.append(", ");
		}

		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}


}
