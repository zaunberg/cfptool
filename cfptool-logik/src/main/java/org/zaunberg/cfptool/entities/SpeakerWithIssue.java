package org.zaunberg.cfptool.entities;


public class SpeakerWithIssue {

	private Speaker speaker;
	private Issue issue;
	
	public SpeakerWithIssue(Speaker speaker, Issue issue) {
		this.speaker = speaker;
		this.issue = issue;
	}	
	
	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
}
