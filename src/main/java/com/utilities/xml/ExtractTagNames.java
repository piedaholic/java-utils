package com.utilities.xml;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/** The Class ExtractTagNames. */
public class ExtractTagNames {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    try {
      new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Enter XML File name: ");
      // String xmlFile = bf.readLine();
      String xmlFile = "C:\\Users\\hpsingh\\Desktop\\LENRL78162150005_OL_ROLL_ADV.xml";
      File file = new File(xmlFile);
      if (file.exists()) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(xmlFile);

        NodeList list = doc.getElementsByTagName("*");
        System.out.println("XML Elements: ");
        for (int i = 0; i < list.getLength(); i++) {

          Element element = (Element) list.item(i);
          System.out.println(element.getNodeName());
        }
      } else {
        System.out.print("File not found!");
      }
    } catch (Exception e) {
      System.exit(1);
    }
  }
}
