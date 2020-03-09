package com.utilities.logging;

import org.apache.commons.transaction.util.LoggerFacade;

public class LoggerFacadeImpl {
    public String name = "";

    public LoggerFacade createLogger(String p1) {
	return (LoggerFacade) new LoggerFacadeImpl();
    }

    public void logInfo(String p1) {
    }

    public void logFine(String p1) {
    }

    public boolean isFineEnabled() {
	return false;
    }

    public void logFiner(String p1) {
    }

    public boolean isFinerEnabled() {
	return false;
    }

    public void logFinest(String p1) {
    }

    public boolean isFinestEnabled() {
	return false;
    }

    public void logWarning(String p1) {
    }

    public void logWarning(String p1, Throwable p2) {
    }

    public void logSevere(String p1) {
    }

    public void logSevere(String p1, Throwable p2) {
    }
}
