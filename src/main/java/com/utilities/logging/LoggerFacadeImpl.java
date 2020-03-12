package com.utilities.logging;

import org.apache.commons.transaction.util.LoggerFacade;

// TODO: Auto-generated Javadoc
/** The Class LoggerFacadeImpl. */
public class LoggerFacadeImpl {

  /** The name. */
  public String name = "";

  /**
   * Creates the logger.
   *
   * @param p1 the p 1
   * @return the logger facade
   */
  public LoggerFacade createLogger(String p1) {
    return (LoggerFacade) new LoggerFacadeImpl();
  }

  /**
   * Log info.
   *
   * @param p1 the p 1
   */
  public void logInfo(String p1) {}

  /**
   * Log fine.
   *
   * @param p1 the p 1
   */
  public void logFine(String p1) {}

  /**
   * Checks if is fine enabled.
   *
   * @return true, if is fine enabled
   */
  public boolean isFineEnabled() {
    return false;
  }

  /**
   * Log finer.
   *
   * @param p1 the p 1
   */
  public void logFiner(String p1) {}

  /**
   * Checks if is finer enabled.
   *
   * @return true, if is finer enabled
   */
  public boolean isFinerEnabled() {
    return false;
  }

  /**
   * Log finest.
   *
   * @param p1 the p 1
   */
  public void logFinest(String p1) {}

  /**
   * Checks if is finest enabled.
   *
   * @return true, if is finest enabled
   */
  public boolean isFinestEnabled() {
    return false;
  }

  /**
   * Log warning.
   *
   * @param p1 the p 1
   */
  public void logWarning(String p1) {}

  /**
   * Log warning.
   *
   * @param p1 the p 1
   * @param p2 the p 2
   */
  public void logWarning(String p1, Throwable p2) {}

  /**
   * Log severe.
   *
   * @param p1 the p 1
   */
  public void logSevere(String p1) {}

  /**
   * Log severe.
   *
   * @param p1 the p 1
   * @param p2 the p 2
   */
  public void logSevere(String p1, Throwable p2) {}
}
