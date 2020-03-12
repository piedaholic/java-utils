package com.utilities.files;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/** The Class FileTest. */
public class FileTest {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    File oldFile = new File("D:\\Temp\\temp\\Exception.log");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    String date = null;
    date = dateFormat.format(Calendar.getInstance().getTime()).toUpperCase();
    File newFile = new File("D:\\Temp\\temp\\" + "Exception" + "_" + date + ".log");
    // System.out.println(oldFile.canExecute());
    // System.out.println(oldFile.canRead());
    // System.out.println(oldFile.canWrite());
    try {
      // System.out.println(oldFile.getAbsolutePath() + " " + oldFile.getName());
      // System.out.println(oldFile.getCanonicalPath() + " " + oldFile.getName());
      // System.out.println(newFile.getAbsolutePath() + " " + newFile.getName());
      // System.out.println(newFile.getCanonicalPath() + " " + newFile.getName());
      FileUtilities.backUpFile(oldFile, newFile);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
