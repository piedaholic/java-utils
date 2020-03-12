/** */
package com.utilities.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class CloneFailedException.
 *
 * @author hpsingh
 */
public class CloneFailedException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3678681626360024381L;

  /** Instantiates a new clone failed exception. */
  public CloneFailedException() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Instantiates a new clone failed exception.
   *
   * @param message the message
   */
  public CloneFailedException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * Instantiates a new clone failed exception.
   *
   * @param cause the cause
   */
  public CloneFailedException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * Instantiates a new clone failed exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public CloneFailedException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * Instantiates a new clone failed exception.
   *
   * @param message the message
   * @param cause the cause
   * @param enableSuppression the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public CloneFailedException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }
}
