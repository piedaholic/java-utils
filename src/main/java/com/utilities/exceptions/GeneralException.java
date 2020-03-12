package com.utilities.exceptions;

// TODO: Auto-generated Javadoc
/** The Class GeneralException. */
public class GeneralException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** Instantiates a new general exception. */
  public GeneralException() {}

  /**
   * Instantiates a new general exception.
   *
   * @param message the message
   */
  public GeneralException(String message) {
    super(message);
  }

  /**
   * Instantiates a new general exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public GeneralException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new general exception.
   *
   * @param cause the cause
   */
  public GeneralException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new general exception.
   *
   * @param message the message
   * @param cause the cause
   * @param enableSuppression the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public GeneralException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {
    return this.toString();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Throwable#toString()
   */
  @Override
  public String toString() {
    return super.getMessage();
  }
}
