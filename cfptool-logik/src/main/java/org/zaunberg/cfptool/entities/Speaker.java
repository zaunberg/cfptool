package org.zaunberg.cfptool.entities;

public class Speaker {

	private String firstname;
	private String lastname;
	private String email;
	private String twitter;
	private String xing;
	private String company;
	private String about;
	
	public Speaker() {
		this("", "", "", "", "", "", "");
	}

	public Speaker(String firstname, String lastname, String email, String twitter,
			String xing, String company, String about) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.twitter = twitter;
		this.xing = xing;
		this.company = company;
		this.about = about;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getXing() {
		return xing;
	}

	public void setXing(String xing) {
		this.xing = xing;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public String toString() {
		return "Speaker [firstname=" + firstname + ", lastname=" + lastname
				+ "]";
	}
}
