package com.utilities.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/** The Class FileSearch. */
public class FileSearch {

  /** The file name to search. */
  private String fileNameToSearch;

  /** The result. */
  private List<String> result = new ArrayList<String>();

  /**
   * Gets the file name to search.
   *
   * @return the file name to search
   */
  public String getFileNameToSearch() {
    return fileNameToSearch;
  }

  /**
   * Sets the file name to search.
   *
   * @param fileNameToSearch the new file name to search
   */
  public void setFileNameToSearch(String fileNameToSearch) {
    this.fileNameToSearch = fileNameToSearch;
  }

  /**
   * Gets the result.
   *
   * @return the result
   */
  public List<String> getResult() {
    return result;
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    FileSearch fileSearch = new FileSearch();
    String propPath =
        "D:\\_Patchsets\\_12.3.0.0.11\\_12.3.0.0.11\\_12.3.0.0.11\\INSTALLER\\SOFT\\logs\\env.properties";
    Properties prop = new Properties();
    try {
      FileInputStream input = new FileInputStream(propPath);
      prop.load(input);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    // try different directory and filename :)
    fileSearch.searchDirectory(new File(prop.getProperty("SOURCE_PATH")), "CLDACCNT_SYS.js");

    int count = fileSearch.getResult().size();
    if (count == 0) {
      System.out.println("\nNo result found!");
    } else {
      System.out.println("\nFound " + count + " result!\n");
      for (String matched : fileSearch.getResult()) {
        System.out.println("Found : " + matched);
      }
    }
  }

  /**
   * Search directory.
   *
   * @param directory the directory
   * @param fileNameToSearch the file name to search
   */
  public void searchDirectory(File directory, String fileNameToSearch) {

    setFileNameToSearch(fileNameToSearch);

    if (directory.isDirectory()) {
      search(directory);
    } else {
      System.out.println(directory.getAbsoluteFile() + " is not a directory!");
    }
  }

  /**
   * Search.
   *
   * @param file the file
   */
  private void search(File file) {

    if (file.isDirectory()) {
      System.out.println("Searching directory ... " + file.getAbsoluteFile());

      // do you have permission to read this directory?
      if (file.canRead()) {
        for (File temp : file.listFiles()) {
          if (temp.isDirectory()) {
            search(temp);
          } else {
            if (getFileNameToSearch().equals(temp.getName().toLowerCase())) {
              result.add(temp.getAbsoluteFile().toString());
            }
          }
        }

      } else {
        System.out.println(file.getAbsoluteFile() + "Permission Denied");
      }
    }
  }
}
