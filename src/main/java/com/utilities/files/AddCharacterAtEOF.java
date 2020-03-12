package com.utilities.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/** The Class AddCharacterAtEOF. */
public class AddCharacterAtEOF {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    AddCharacterAtEOF fw = new AddCharacterAtEOF();
    try {
      fw.addCharacter("D:\\Temp\\BADEODFE\\", "/");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Adds the character.
   *
   * @param path the path
   * @param character the character
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void addCharacter(String path, String character) throws IOException {
    File root = new File(path);
    File[] list = root.listFiles();

    if (list == null) return;

    for (File f : list) {
      if (f.isDirectory()) {
        walk(f.getAbsolutePath());
        System.out.println("Dir:" + f.getAbsoluteFile());
      } else {
        System.out.println("File:" + f.getAbsoluteFile());
        // Open given file in append mode.
        BufferedWriter out = new BufferedWriter(new FileWriter(f.getAbsoluteFile(), true));
        out.write("\n/");
        out.close();
        /*
         * FileChannel src = new FileInputStream(temp).getChannel(); FileChannel dest = new
         * FileOutputStream(f.getAbsoluteFile()).getChannel(); dest.transferFrom(src, 0,
         * src.size()); if(temp.delete()) { System.out.println("File deleted successfully"); } else
         * { System.out.println("Failed to delete the file"); }
         */
      }
    }
  }

  /**
   * Walk.
   *
   * @param path the path
   */
  public void walk(String path) {

    File root = new File(path);
    File[] list = root.listFiles();

    if (list == null) return;

    for (File f : list) {
      if (f.isDirectory()) {
        walk(f.getAbsolutePath());
        System.out.println("Dir:" + f.getAbsoluteFile());
      } else {
        System.out.println("File:" + f.getAbsoluteFile());
      }
    }
  }
}
