package com.utilities.logging;

import java.io.FileWriter;
import java.io.PrintWriter;

public class LogUtils {
    public static void jLog(String logMessage, String logFile) {
	try {
	    PrintWriter pwLog = new PrintWriter(new FileWriter(logFile, true));
	    pwLog.println(logMessage);
	    pwLog.close();
	} catch (Exception var3) {
	}

    }
}
