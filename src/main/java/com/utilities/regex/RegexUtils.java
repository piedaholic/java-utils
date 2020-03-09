/**
 * 
 */
package com.utilities.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hpsingh
 *
 */
public class RegexUtils {

    public static boolean matches(String patternString, String text) {
	boolean result = true;
	Pattern pattern = Pattern.compile(patternString);
	Matcher matcher = pattern.matcher(text);
	result = matcher.matches();
	return result;
    }
}
