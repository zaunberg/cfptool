package org.zaunberg.cfptool;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.zaunberg.cfptool.entities.Speaker;
import org.zaunberg.cfptool.entities.Submission;
import org.zaunberg.cfptool.exceptions.NoSubmissionException;
import org.zaunberg.cfptool.exceptions.XMLException;


public class MessageToSubmissionConverter {

	public MessageToSubmissionConverter() {}
	
	public List<Submission> readAndConvert(Message[] messages) throws MessagingException {
		List<Submission> submissionList = new ArrayList<Submission>();	
		
		for(Message message : messages) {
			try {	
				submissionList.add(parseXML(message.getContent().toString()));
			} catch (NoSubmissionException e) {
				// if no submission => skip message
			} catch (XMLException e) {
				// xml cannot read => skip message
			} catch (IOException e) {
				// message cannot read => skip message
			}
		}
		
		return submissionList;
	}

    private Document loadXMLFromString(String xml) throws Exception {
    	InputSource is = new InputSource(new StringReader(xml));
    	
    	return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
    }
    
    public Submission parseXML(String xmlString) throws NoSubmissionException, XMLException {
    	Document doc = null;
		try {
			doc = loadXMLFromString(xmlString);
		} catch (Exception e) {
			throw new XMLException();
		}

    	doc.getDocumentElement().normalize();
    	
    	return parseSubmission(doc);
    }
    
    private Submission parseSubmission(Document submissionCoc) throws NoSubmissionException {
    	// check if Submission?
    	if(submissionCoc.getDocumentElement().getNodeName().equalsIgnoreCase("submission")) {
        	
	    	List<Speaker> speakerList = getSpeaker(submissionCoc);
	    	String talkAbstract = getAbstract(submissionCoc);
	    	String talkTitle = getTitle(submissionCoc);
	    	String language = getLanguage(submissionCoc);
	    	String type = getType(submissionCoc);
	    	
	    	return new Submission(speakerList, talkTitle, talkAbstract, language, type);
    	}
    	
    	throw new NoSubmissionException();    	
    }
    
    private List<Speaker> getSpeaker(Document doc) {
    	NodeList nList = doc.getElementsByTagName("speaker");
    	List<Speaker> speakerList = new ArrayList<Speaker>();
    	
    	for (int i = 0; i < nList.getLength(); i++) {
    		Node nNode = nList.item(i);

    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    			Element element = (Element) nNode;
    			String firstname = getTagValue(element, "firstname");
    			String lastname = getTagValue(element, "lastname");
    			String email = getTagValue(element, "email");
    			String twitter = getTagValue(element, "twitter");
    			String xing = getTagValue(element, "xing");
    			String company = getTagValue(element, "company");
    			String about = getTagValue(element, "about");
    			
    			speakerList.add(new Speaker(firstname, lastname, email, twitter, xing, company, about));
    		}
    	}   
    	
    	return speakerList;
    }
    
    private String getLanguage(Document doc) {
    	return getTagValue(doc, "language");
    }
    
    private String getType(Document doc) {
    	return getTagValue(doc, "type");
    }
    
    private String getAbstract(Document doc) {
    	return getTagValue(doc, "abstract");    	
    }

    private String getTitle(Document doc) {
    	return getTagValue(doc, "title");    	
    }   
    
    private String getTagValue(Document doc, String tag) {
    	String value = "";
		if(doc.getElementsByTagName(tag).getLength() == 1) {
			value = doc.getElementsByTagName(tag).item(0).getTextContent();
		}
		
		return value;
    }    
    
    private String getTagValue(Element eElement, String tag) {
    	String value = "";
		if(eElement.getElementsByTagName(tag).getLength() == 1) {
			value = eElement.getElementsByTagName(tag).item(0).getTextContent();
		}
		
		return value;
    }
}
