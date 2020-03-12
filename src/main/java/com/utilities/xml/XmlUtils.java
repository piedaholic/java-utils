package com.utilities.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.validation.Validation;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/** The Class XmlUtils. */
public class XmlUtils {

  /**
   * Validates XML against XSD schema.
   *
   * @param xml XML in which the element is being searched
   * @param schemas XSD schemas against which the XML is validated
   * @throws IOException if the XML at the specified path is missing
   * @throws SAXException if the XSD schema is invalid
   */
  public static void validateWithXMLSchema(InputStream xml, InputStream[] schemas)
      throws IOException, SAXException {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    Source[] sources = new Source[schemas.length];
    for (int i = 0; i < schemas.length; i++) {
      sources[i] = new StreamSource(schemas[i]);
    }

    Schema schema = factory.newSchema(sources);
    Validator validator = schema.newValidator();

    try {
      validator.validate(new StreamSource(xml));
    } catch (SAXException e) {
      throw e;
    }
  }

  /**
   * Searches XML element with XPath and returns list of nodes found.
   *
   * @param xml XML in which the element is being searched
   * @param expression XPath expression used in search
   * @return {@link NodeList} of elements matching the XPath in the XML
   * @throws XPathExpressionException if there is an error in the XPath expression
   * @throws IOException if the XML at the specified path is missing
   * @throws SAXException if the XML cannot be parsed
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static NodeList findWithXPath(InputStream xml, String expression)
      throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;

    dBuilder = dbFactory.newDocumentBuilder();

    Document doc = dBuilder.parse(xml);
    doc.getDocumentElement().normalize();

    XPath xPath = XPathFactory.newInstance().newXPath();

    return (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
  }

  /**
   * Validation.
   *
   * @param xsdFile xsdFile
   * @param xmlInput xmlInput
   * @throws SAXException SAXException
   * @throws IOException IOException
   */
  public static void validation(String xsdFile, InputStream xmlInput)
      throws SAXException, IOException {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    URL xsdURL = Validation.class.getClassLoader().getResource(xsdFile);
    if (xsdURL != null) {
      Schema schema = factory.newSchema(xsdURL);
      Validator validator = schema.newValidator();
      // validator.setErrorHandler(new AutoErrorHandler());

      Source source = new StreamSource(xmlInput);

      try (OutputStream resultOut = new FileOutputStream(new File(xsdFile + ".xml"))) {
        Result result = new StreamResult(resultOut);
        validator.validate(source, result);
      }
    } else {
      throw new FileNotFoundException(
          String.format("can not found xsd file [%s] from classpath.", xsdFile));
    }
  }

  /**
   * Convert document to string.
   *
   * @param doc the doc
   * @return the string
   */
  public static String convertDocumentToString(Document doc) {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer;
    try {
      transformer = tf.newTransformer();
      // below code to remove XML declaration
      // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(doc), new StreamResult(writer));
      String output = writer.getBuffer().toString();
      return output;
    } catch (TransformerException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Convert string to document.
   *
   * @param xmlStr the xml str
   * @return the document
   */
  public static Document convertStringToDocument(String xmlStr) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    try {
      builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
      return doc;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Checks if is xml.
   *
   * @param xmlStr the xml str
   * @return true, if is xml
   */
  public static boolean isXML(String xmlStr) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    try {
      builder = factory.newDocumentBuilder();
      builder.parse(new InputSource(new StringReader(xmlStr)));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Erase tags from header.
   *
   * @param inpXML the inp XML
   * @param tagList the tag list
   */
  public static void eraseTagsFromHeader(String inpXML, String[] tagList) {
    ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(tagList));
    for (String str : arrayList) {
      inpXML = inpXML.replaceFirst("<" + str + ">", "").replaceAll("</" + str + ">", "");
      inpXML.replaceFirst("<" + str + ">[\\s\\S]*?</" + str + ">", "");
    }
    inpXML = inpXML.replaceAll("\\s\\r\\n", "");
  }

  /**
   * Delete element.
   *
   * @param someNode the some node
   * @param nodeName the node name
   */
  public static void deleteElement(Node someNode, String nodeName) {
    NodeList childs = someNode.getChildNodes();
    for (int i = 0; i < childs.getLength(); ) {
      Node child = childs.item(i);
      if (child.getNodeType() == Document.ELEMENT_NODE) {
        if (child.getNodeName().equalsIgnoreCase(nodeName)) {
          child.getParentNode().removeChild(child);
          continue;
        } else {
          deleteElement(child, nodeName);
        }
      }
      i++;
    }
  }

  /**
   * Validate req XML for DTD.
   *
   * @param reqXML the req XML
   * @return true, if successful
   */
  public static boolean validateReqXMLForDTD(String reqXML) {
    boolean dtdFlag = false;
    String testString = "";
    if (reqXML.indexOf("<FCJMSG") >= 0) {
      testString = reqXML.substring(0, reqXML.indexOf("<FCJMSG"));
    } else if (reqXML.indexOf("<FCUBS_REQ_ENV>") >= 0) {
      testString = reqXML.substring(0, reqXML.indexOf("<FCUBS_REQ_ENV>"));
    } else {
      testString = reqXML;
    }
    testString = testString.trim().toUpperCase();
    if ((testString.contains("<!DOCTYPE")) || (testString.contains("<!ELEMENT"))) {
      dtdFlag = true;
    }
    return dtdFlag;
  }

  /**
   * Checks for child elements.
   *
   * @param el the el
   * @return true, if successful
   */
  public static boolean hasChildElements(Element el) {
    NodeList children = el.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      if (children.item(i).getNodeType() == Node.ELEMENT_NODE) return true;
    }
    return false;
  }
}
