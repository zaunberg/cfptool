package org.zaunberg.cfptool;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zaunberg.cfptool.entities.Issue;
import org.zaunberg.cfptool.entities.Speaker;
import org.zaunberg.cfptool.entities.Submission;

public class ConfluenceTest {

	public final static String JIRA_LINK = "https://link.to.jira.com";

    private final Submission getSubmission() {
    	List<Speaker> speakerList = new ArrayList<Speaker>();
    	speakerList.add(new Speaker("firstname", "lastname", "email", "twitter", "xing", "company", "about"));
    	return new Submission(speakerList, "title", "talkAbstract", "lang", "type");
    }	
	
    private final List<Issue> getIssueReportList() {
    	List<Issue> issueReport = new ArrayList<Issue>();
    	issueReport.add(new Issue("projectId", "issuetypeId", "priorityId", "assigneeName", "reporterName", "summary", 
			"description", "label", getSubmission(), "1"));
 
    	return issueReport;
    }
    
	@Test
	public void testCreateSubmissionTableWithHead() {
		Confluence confluence = new Confluence(JIRA_LINK);
		String table = confluence.generateSubmissionTable(getIssueReportList(), true);
		final String expected = "|| Title || Speaker || Sprache || Typ || Jira-Link ||\n| title | firstname lastname | lang | type | "+ JIRA_LINK +"/browse/1 |\n";
		Assert.assertEquals(expected, table);
	}
	
	@Test
	public void testCreateSubmissionTable() {
		Confluence confluence = new Confluence(JIRA_LINK);
		String table = confluence.generateSubmissionTable(getIssueReportList(), false);
		
		final String expected = "| title | firstname lastname | lang | type | "+ JIRA_LINK +"/browse/1 |\n";
		Assert.assertEquals(expected, table);
	}
	
	@Test
	public void testCreateSpeakerTableWithHead() {
		Confluence confluence = new Confluence(JIRA_LINK);
		String table = confluence.generateSpeakerTable(getIssueReportList(), true);
		final String expected = "|| Speaker/Jira-Link || Email || Twitter || Xing|| Firma  || Bild ||\n| [firstname lastname|"+ JIRA_LINK +"/browse/1] | [mailto:email] | twitter | xing | company |  |\n";
		Assert.assertEquals(expected, table);
	}
	
	@Test
	public void testCreateSpeakerTable() {
		Confluence confluence = new Confluence(JIRA_LINK);
		String table = confluence.generateSpeakerTable(getIssueReportList(), false);
		
		final String expected = "| [firstname lastname|"+ JIRA_LINK +"/browse/1] | [mailto:email] | twitter | xing | company |  |\n";
		Assert.assertEquals(expected, table);
	}
}
