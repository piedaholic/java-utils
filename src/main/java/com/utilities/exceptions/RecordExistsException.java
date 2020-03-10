package com.utilities.exceptions;

public class RecordExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2991516649916663614L;

	public RecordExistsException(String s) {
		// Call constructor of parent Exception
		super(s);
	}
}
