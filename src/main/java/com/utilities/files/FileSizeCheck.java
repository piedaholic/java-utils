package com.utilities.files;

import java.io.File;
// TODO: Auto-generated Javadoc
// import java.nio.channels.FileChannel;

/** The Class FileSizeCheck. */
public class FileSizeCheck {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    File file = new File("D:\\Temp\\MyLogs" + File.separator + "Exception.log");
    String path = "D:\\Temp\\MyLogs";
    // path.replaceAll("\\", "");
    System.out.println(path);
    if (!file.exists() || !file.isFile()) return;
    System.out.println(File.separatorChar);
    long sizeInBytes = file.length();
    long sizeInMb = sizeInBytes / 1048576L;
    System.out.println(sizeInBytes);
    System.out.println(sizeInMb);
  }
}
