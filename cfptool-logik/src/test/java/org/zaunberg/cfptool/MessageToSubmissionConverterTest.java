package org.zaunberg.cfptool;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.zaunberg.cfptool.entities.Speaker;
import org.zaunberg.cfptool.entities.Submission;
import org.zaunberg.cfptool.exceptions.NoSubmissionException;
import org.zaunberg.cfptool.exceptions.XMLException;

public class MessageToSubmissionConverterTest extends TestUtil {

	@Test(expected = NoSubmissionException.class)
	public void testParseNoSubmission() throws NoSubmissionException, XMLException, IOException {
		String xmlString = loadFileContent("xmlfiles/noSubmission.xml");

		MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
		messageToSubmissionConverter.parseXML(xmlString);
	}

	@Test(expected = XMLException.class)
	public void testParseNoXML() throws NoSubmissionException, XMLException, IOException {
		String xmlString = loadFileContent("xmlfiles/noXml.xml");

		MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
		messageToSubmissionConverter.parseXML(xmlString);
	}
	
	@Test
	public void testParseSubmission() throws NoSubmissionException, XMLException, IOException {
		String xmlString = loadFileContent("xmlfiles/submission.xml");

		MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
		Submission submission = messageToSubmissionConverter.parseXML(xmlString);
		
		Assert.assertNotNull(submission);
		Assert.assertEquals(1, submission.getSpeakerList().size());
		
		Speaker speaker = submission.getSpeakerList().get(0);
		Assert.assertNotNull(speaker);
		
		Assert.assertEquals("Jack", speaker.getFirstname());
		Assert.assertEquals("Testmaster", speaker.getLastname());		
		Assert.assertEquals("jack@test.com", speaker.getEmail());
		Assert.assertEquals("myTwitterLink", speaker.getTwitter());
		Assert.assertEquals("myXingLink", speaker.getXing());
		Assert.assertEquals("Testmaster", speaker.getCompany());
		Assert.assertEquals("I am a testmaster!", speaker.getAbout());
		
		Assert.assertEquals("JUnit Tests", submission.getTitle());
		Assert.assertEquals("Test your App with JUnit.", submission.getTalkAbstract());
		Assert.assertEquals("de", submission.getLang());
		Assert.assertEquals("short", submission.getType());
	}
	
	@Test
	public void testParseSubmissionWithTwoSpeaker() throws NoSubmissionException, XMLException, IOException {
		String xmlString = loadFileContent("xmlfiles/submissionWithTwoSpeaker.xml");

		MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
		Submission submission = messageToSubmissionConverter.parseXML(xmlString);
		
		Assert.assertNotNull(submission);
		Assert.assertEquals(2, submission.getSpeakerList().size());
		
		Speaker speaker = submission.getSpeakerList().get(0);
		Assert.assertNotNull(speaker);
		
		Assert.assertEquals("Jack", speaker.getFirstname());
		Assert.assertEquals("Testmaster", speaker.getLastname());		
		Assert.assertEquals("jack@test.com", speaker.getEmail());
		Assert.assertEquals("myTwitterLink", speaker.getTwitter());
		Assert.assertEquals("myXingLink", speaker.getXing());
		Assert.assertEquals("Testmaster", speaker.getCompany());
		Assert.assertEquals("I am a testmaster!", speaker.getAbout());
		
		Speaker speaker2 = submission.getSpeakerList().get(1);
		Assert.assertNotNull(speaker2);
		
		Assert.assertEquals("Max", speaker2.getFirstname());
		Assert.assertEquals("Testmaxer", speaker2.getLastname());		
		Assert.assertEquals("max@test.com", speaker2.getEmail());
		Assert.assertEquals("maxTwitterLink", speaker2.getTwitter());
		Assert.assertEquals("maxXingLink", speaker2.getXing());
		Assert.assertEquals("Testmaster", speaker2.getCompany());
		Assert.assertEquals("I am a maxmaster!", speaker2.getAbout());
		
		Assert.assertEquals("JUnit Tests", submission.getTitle());
		Assert.assertEquals("Test your App with JUnit.", submission.getTalkAbstract());
		Assert.assertEquals("de", submission.getLang());
		Assert.assertEquals("short", submission.getType());
	}
	
	@Test
	public void testParseSubmissionWithoutSpeaker() throws NoSubmissionException, XMLException, IOException {
		String xmlString = loadFileContent("xmlfiles/submissionWithoutSpeaker.xml");

		MessageToSubmissionConverter messageToSubmissionConverter = new MessageToSubmissionConverter();
		Submission submission = messageToSubmissionConverter.parseXML(xmlString);
		
		Assert.assertNotNull(submission);
		
		Assert.assertEquals(0, submission.getSpeakerList().size());
	}
}
