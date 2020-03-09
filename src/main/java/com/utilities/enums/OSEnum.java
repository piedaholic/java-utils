/**
 * 
 */
package com.utilities.enums;

/**
 * @author hpsingh
 *
 */
public enum OSEnum {
	WINDOWS, LINUX;

	public static OSEnum fromValue(String v) {
		return valueOf(v);
	}
	public static boolean contains(String test) {

	    for (OSEnum c : OSEnum.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}

}
