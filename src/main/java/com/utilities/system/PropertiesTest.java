package com.utilities.system;

import java.io.FileInputStream;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/** The Class PropertiesTest. */
public class PropertiesTest {

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {

    // set up new properties object
    // from file "myProperties.txt"
    FileInputStream propFile =
        new FileInputStream(
            "D:\\Temp\\RABOELCM\\runtime\\Properties\\application\\fcubs.properties");
    Properties p = new Properties(System.getProperties());
    p.load(propFile);

    // set the system properties
    System.setProperties(p);
    // display new properties
    System.getProperties().list(System.out);
  }
}
