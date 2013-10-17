package org.zaunberg.cfptool;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.zaunberg.cfptool.entities.Issue;
import org.zaunberg.cfptool.entities.ReturnIssue;
import org.zaunberg.cfptool.entities.Submission;

import com.google.gson.Gson;


public class Jira {

	private RestClient restClient;
	private String jiraRestUri;
	private String jiraUrl;

	public Jira(String user, String password, String jiraUrl, String jiraRestUri) {
		this.restClient = new RestClient(user, password);
		this.jiraRestUri = jiraRestUri;
		this.jiraUrl = jiraUrl;
	}

	public Issue createIssue(Issue issue) throws AuthenticationException {
		String result = this.restClient.postRequest(this.jiraUrl + this.jiraRestUri + "/issue",issue.getJSON()).replaceAll("<.*?>","");

		Gson gson = new Gson();
		ReturnIssue returnIssue = gson.fromJson(result, ReturnIssue.class);
		if (returnIssue.getKey() != null) {
			issue.setIssueKey(returnIssue.getKey());
		}

		return issue;
	}

	public List<Issue> createIssueList(List<Submission> submissionList, Issue defaultIssue) throws AuthenticationException {
		return createIssueList(submissionListToIssueList(submissionList, defaultIssue));
	}

	public List<Issue> createIssueList(List<Issue> issueList) throws AuthenticationException { List<Issue> issueReport = new ArrayList<Issue>();
		for (Issue issue : issueList) {
			issueReport.add(createIssue(issue));
		}

		return issueReport;
	}

	public List<Issue> submissionListToIssueList(List<Submission> submissionList, Issue defaultIssue) {
		List<Issue> issueList = new ArrayList<Issue>();

		for (Submission submission : submissionList) {
			issueList.add(Issue.convertFromSubmission(submission, defaultIssue));
		}

		return issueList;
	}

	public String getJiraUrl() {
		return jiraUrl;
	}

	/* *********************** DUMY ******** */

	public Issue createIssueDUMMY(Issue issue) {

		String id = "-ID-";
		String key = "-KEY-";
		
		String result = "{\"id\":\""+ id +"\",\"key\":\""+ key +"\",\"self\":\""+ jiraUrl + this.jiraRestUri +"/issue/"+ id +"\"}";

		Gson gson = new Gson();
		ReturnIssue returnIssue = gson.fromJson(result, ReturnIssue.class);
		if (returnIssue.getKey() != null) {
			issue.setIssueKey(returnIssue.getKey());
		}

		return issue;
	}
	
	public List<Issue> createIssueListDUMMY(List<Submission> submissionList, Issue defaultIssue) {
		return createIssueListDUMMY(submissionListToIssueList(submissionList, defaultIssue));
	}

	public List<Issue> createIssueListDUMMY(List<Issue> issueList) { List<Issue> issueReport = new ArrayList<Issue>();
		for (Issue issue : issueList) {
			issueReport.add(createIssueDUMMY(issue));
		}

		return issueReport;
	}
}
