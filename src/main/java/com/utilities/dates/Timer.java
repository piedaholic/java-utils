package com.utilities.dates;

// TODO: Auto-generated Javadoc
/** The Class Timer. */
public class Timer {

  /** The lg start. */
  long lgStart = 0L;

  /** The lg end. */
  long lgEnd = 0L;

  /** Start. */
  public void start() {
    this.lgStart = System.currentTimeMillis();
  }

  /** End. */
  public void end() {
    this.lgEnd = System.currentTimeMillis();
  }

  /** Reset. */
  public void reset() {
    this.lgStart = 0L;
    this.lgEnd = 0L;
  }
}
