package com.utilities.xml;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser extends DefaultHandler {

    private StringBuffer strbfrValue;

    public XmlParser() {
	System.out.println("Buffer Initialized");
	strbfrValue = new StringBuffer();
    }

    public void parseXML(String pXML) throws Exception {
	SAXParserFactory spFactory = null;
	SAXParser parser = null;
	InputSource source = new InputSource(new StringReader(pXML));
	spFactory = SAXParserFactory.newInstance();
	spFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
	parser = spFactory.newSAXParser();
	parser.parse(source, this);
    }

    public void startElement(String uri, String localName, String qName, Attributes attribs) throws SAXException {
	System.out.println("Inside startElement");
	System.out.println(uri);
	System.out.println(localName);
	System.out.println(qName);
    }

    public void characters(char[] data, int start, int length) {
	this.strbfrValue.append(data, start, length);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
	System.out.println("Inside endElement");
    }
}
