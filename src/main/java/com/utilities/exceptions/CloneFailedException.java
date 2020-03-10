/**
 * 
 */
package com.utilities.exceptions;

/**
 * @author hpsingh
 *
 */
public class CloneFailedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3678681626360024381L;

    /**
     * 
     */
    public CloneFailedException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public CloneFailedException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public CloneFailedException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public CloneFailedException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CloneFailedException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

}
