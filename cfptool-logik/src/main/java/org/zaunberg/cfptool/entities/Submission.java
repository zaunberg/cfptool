package org.zaunberg.cfptool.entities;

import java.util.ArrayList;
import java.util.List;


public class Submission {

	private List<Speaker> speakerList;
	private String title;
	private String talkAbstract;
	private String lang;
	private String type;
	
	public Submission() {
		this(new ArrayList<Speaker>(), "", "", "", "");
	}

	public Submission(List<Speaker> speakerList, String title, String talkAbstract,
			String lang, String type) {
		this.speakerList = speakerList;
		this.title = title;
		this.talkAbstract = talkAbstract;
		this.lang = lang;
		this.type = type;
	}

	public List<Speaker> getSpeakerList() {
		return speakerList;
	}

	public void setSpeakerList(List<Speaker> speakerList) {
		this.speakerList = speakerList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTalkAbstract() {
		return talkAbstract;
	}

	public void setTalkAbstract(String talkAbstract) {
		this.talkAbstract = talkAbstract;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Submission [speakerList=" + speakerList + ", title=" + title
				+ ", talkAbstract=" + talkAbstract + ", lang=" + lang
				+ ", type=" + type + "]";
	}
}
