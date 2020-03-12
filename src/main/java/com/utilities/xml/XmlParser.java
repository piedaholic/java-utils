package com.utilities.xml;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// TODO: Auto-generated Javadoc
/** The Class XmlParser. */
public class XmlParser extends DefaultHandler {

  /** The strbfr value. */
  private StringBuffer strbfrValue;

  /** Instantiates a new xml parser. */
  public XmlParser() {
    System.out.println("Buffer Initialized");
    strbfrValue = new StringBuffer();
  }

  /**
   * Parses the XML.
   *
   * @param pXML the xml
   * @throws Exception the exception
   */
  public void parseXML(String pXML) throws Exception {
    SAXParserFactory spFactory = null;
    SAXParser parser = null;
    InputSource source = new InputSource(new StringReader(pXML));
    spFactory = SAXParserFactory.newInstance();
    spFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
    parser = spFactory.newSAXParser();
    parser.parse(source, this);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
   * java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes attribs)
      throws SAXException {
    System.out.println("Inside startElement");
    System.out.println(uri);
    System.out.println(localName);
    System.out.println(qName);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
   */
  public void characters(char[] data, int start, int length) {
    this.strbfrValue.append(data, start, length);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.xml.sax.helpers.DefaultHandler#ignorableWhitespace(char[], int, int)
   */
  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}

  /*
   * (non-Javadoc)
   *
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) throws SAXException {
    System.out.println("Inside endElement");
  }
}
