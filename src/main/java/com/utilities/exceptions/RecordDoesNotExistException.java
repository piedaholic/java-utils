package com.utilities.exceptions;

public class RecordDoesNotExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 523743708170081291L;

	public RecordDoesNotExistException(String s) {
		// Call constructor of parent Exception
		super(s);
	}
}
