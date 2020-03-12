/** */
package com.utilities.io;

import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class IOUtils.
 *
 * @author hpsingh
 */
public class IOUtils {

  /** The Constant scanner. */
  private static final Scanner scanner = new Scanner(System.in);

  /** Removes the whitespaces. */
  public static void removeWhitespaces() {
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
    // \u2028 is Unicode for Line Separator
    // \u2029 is Unicode for Paragraph Separator
    // \u0085 is Unicode for Next Line
  }
}
