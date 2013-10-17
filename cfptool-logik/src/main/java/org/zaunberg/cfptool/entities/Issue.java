package org.zaunberg.cfptool.entities;


public class Issue {

	private String projectId;
	private String summary;
	private String issuetypeId;
	private String assigneeName;
	private String reporterName;
	private String priorityId;
	private String label;
	private String description;
	
	private Submission submission;
	private String issueKey;
	
	public Issue() {
		this("", "", "", "", "", "", "", "", null, null);
	}
	
	public Issue(String projectId, String issuetypeId, String priorityId, String assigneeName, String reporterName, String summary, 
			String description, String label) {
		this(projectId, issuetypeId, priorityId, assigneeName, reporterName, summary, description, label, null, null);
	}
	
	public Issue(String projectId, String issuetypeId, String priorityId, String assigneeName, String reporterName, String summary, 
			String description, String label, Submission submission) {
		this(projectId, issuetypeId, priorityId, assigneeName, reporterName, summary, description, label, submission, null);
	}
	
	public Issue(String projectId, String issuetypeId, String priorityId, String assigneeName, String reporterName, String summary, 
			String description, String label, Submission submission, String issueKey) {
		this.projectId = projectId;
		this.issuetypeId = issuetypeId;
		this.priorityId = priorityId;
		this.assigneeName = assigneeName;
		this.reporterName = reporterName;
		this.summary = summary;
		this.description = description;
		this.label = label;
		this.submission = submission;
		this.issueKey = issueKey;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIssuetypeId() {
		return issuetypeId;
	}

	public void setIssuetypeId(String issuetypeId) {
		this.issuetypeId = issuetypeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getJSON() {
		return "{\"fields\": {\"project\": {\"id\": \""+ this.getProjectId() +"\"},\"summary\": \""+ this.getSummary() +"\"," +
		        "\"issuetype\": {\"id\": \""+ this.getIssuetypeId() +"\" }, \"assignee\": { \"name\": \""+ this.getAssigneeName() +"\" }, \"reporter\": { \"name\": \""+ this.getReporterName() +"\"" +
		        "}, \"priority\": { \"id\": \""+ this.getPriorityId() +"\" }, \"labels\": [ \""+ this.getLabel() +"\" ], \"description\": \""+ this.getDescription() +"\" } }";	
	}
	
	public static Issue convertFromSubmission(Submission submission, Issue defaultIssue) {	
		// String projectId, String issuetypeId, String priorityId, String assigneeName, String reporterName, String summary, String description, String label
		return new Issue(defaultIssue.getProjectId(), defaultIssue.getIssuetypeId(), defaultIssue.getPriorityId(), defaultIssue.getAssigneeName(), defaultIssue.getReporterName(),
				submission.getTitle(), submission.toString(), defaultIssue.getLabel(), submission, null);
	}

	@Override
	public String toString() {
		return "Issue [projectId=" + projectId + ", summary=" + summary
				+ ", issuetypeId=" + issuetypeId + ", assigneeName="
				+ assigneeName + ", reporterName=" + reporterName
				+ ", priorityId=" + priorityId + ", label=" + label
				+ ", description=" + description + ", submission=" + submission
				+ ", issueKey=" + issueKey + "]";
	}
}
