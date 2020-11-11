package com.utilities.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/** The Class ProcessExecutor. */
public class ProcessExecutor {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    try {
      // execute("D:\\Patchsets\\\\_12.3.0.9.5\\INSTALLER\\SOFT\\ROFCEarRun.bat");
      // execute("D:\\Development\\Windows\\HelloWorld.bat");
      // executeBatchFile("D:\\Development\\Windows\\HelloWorld.bat");
      executeBatchFile("D:\\Patchsets\\\\_12.3.0.9.5\\INSTALLER\\SOFT\\ROFCEarRun.bat");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * Execute batch file.
   *
   * @param cmd the cmd
   */
  public static void executeBatchFile(String cmd) {
    final StringBuffer sb = new StringBuffer();
    int processComplete = -1;
    ProcessBuilder processBuilder = new ProcessBuilder(cmd);
    File dir = new File(new File(cmd).getParentFile().getAbsolutePath());
    processBuilder.directory(dir);
    processBuilder.redirectErrorStream(true);
    try {
      final Process process = processBuilder.start();
      final InputStream is = process.getInputStream();
      // the background thread watches the output from the process
      new Thread(
              new Runnable() {
                public void run() {
                  try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                      sb.append(line).append('\n');
                      System.out.println(line);
                    }
                  } catch (IOException e) {
                    System.out.println("Java ProcessBuilder: IOException occured.");
                    e.printStackTrace();
                  } finally {
                    try {
                      is.close();
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                }
              })
          .start();
      // Wait to get exit value
      // the outer thread waits for the process to finish
      processComplete = process.waitFor();
      System.out.println("Java ProcessBuilder result:" + processComplete);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
