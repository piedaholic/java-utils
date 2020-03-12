package com.utilities.string;

// TODO: Auto-generated Javadoc
/** The Class Test. */
public class Test {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    String regex = "^(.*)[java|py]$";
    System.out.println("C:\\Harsh.py".matches(regex));
    String file = "C:/Desk/Test.py";
    String artifactName = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
    System.out.println(artifactName);
    String fileName = "FCUBSAccServiceear";
    System.out.println(fileName.matches("FCUBS([A-Za-z0-9]*)Service\\.(.*)"));
  }
}
