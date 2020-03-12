package com.utilities.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class TraverseNodes.
 */
public class TraverseNodes {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    String inpXML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<QUERY_REQ>\r\n" + "   <HEADER>\r\n"
            + "      <SOURCE>SYSTEM</SOURCE>\r\n" + "      <COMPONENT>LINUX</COMPONENT>\r\n"
            + "      <ACTION>NEW</ACTION>\r\n" + "   </HEADER>\r\n" + "   <BODY>\r\n"
            + "      <User-Details>\r\n" + "         <ID>1</ID>\r\n" + "      </User-Details>\r\n"
            + "   </BODY>\r\n" + "</QUERY_REQ>\r\n" + "";
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    String blkName = null;
    try {
      DocumentBuilder builder = docFactory.newDocumentBuilder();
      org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(inpXML)));
      doc.getDocumentElement().normalize();
      Node body = doc.getElementsByTagName("BODY").item(0);
      NodeList list = body.getChildNodes();
      System.out.println("XML Elements: ");
      for (int i = 0; i < list.getLength(); i++) {
        // Element element = (Element) list.item(i);
        if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
          // System.out.println(list.item(i).getNodeName());
          blkName = list.item(i).getNodeName();
        }
      }
      // System.out.println(blkName);
      NodeList blockNode = doc.getElementsByTagName(blkName).item(0).getChildNodes();
      for (int i = 0; i < blockNode.getLength(); i++) {
        // Element element = (Element) list.item(i);
        if (blockNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
          System.out.println(blockNode.item(i).getNodeName());
          System.out.println(blockNode.item(i).getTextContent());
        }
        // blkName = list.item(i).getNodeName();
      }
    } catch (SAXException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
