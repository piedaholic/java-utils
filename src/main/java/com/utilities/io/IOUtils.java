/**
 * 
 */
package com.utilities.io;

import java.util.Scanner;

/**
 * @author hpsingh
 *
 */
public class IOUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void removeWhitespaces() {
	scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
	// \u2028 is Unicode for Line Separator
	// \u2029 is Unicode for Paragraph Separator
	// \u0085 is Unicode for Next Line
    }
}
