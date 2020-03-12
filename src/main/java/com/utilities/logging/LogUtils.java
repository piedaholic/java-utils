package com.utilities.logging;

import java.io.FileWriter;
import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/** The Class LogUtils. */
public class LogUtils {

  /**
   * J log.
   *
   * @param logMessage the log message
   * @param logFile the log file
   */
  public static void jLog(String logMessage, String logFile) {
    try {
      PrintWriter pwLog = new PrintWriter(new FileWriter(logFile, true));
      pwLog.println(logMessage);
      pwLog.close();
    } catch (Exception var3) {
    }
  }
}
