package com.utilities.string;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/** The Class StringEscapeUtils. */
public class StringEscapeUtils {

  /** The Constant lookupMap. */
  private static final Map<Integer, String> lookupMap = new HashMap<Integer, String>();

  /** The Constant MARKUP_ENTITIES. */
  private static final String[][] MARKUP_ENTITIES =
      new String[][] {
        {"34", "quot"},
        {"38", "amp"},
        {"39", "#x27"},
        {"47", "#x2F"},
        {"60", "lt"},
        {"62", "gt"},
        {"40", "#x28"},
        {"41", "#x29"}
      };

  /** The immune javascript chars. */
  private static char[] IMMUNE_JAVASCRIPT_CHARS = new char[] {',', '.', '_'};

  /**
   * Escape HTML.
   *
   * @param input the input
   * @return the string
   */
  public static String escapeHTML(String input) {
    if (input == null) {
      return null;
    } else if (input.equals("")) {
      return "";
    } else {
      StringWriter writer = new StringWriter((int) ((double) input.length() * 1.5D));
      escapeCharacters(writer, input);
      return writer.toString();
    }
  }

  /**
   * Escape characters.
   *
   * @param writer the writer
   * @param input the input
   */
  private static void escapeCharacters(StringWriter writer, String input) {
    int length = input.length();

    for (int ctr = 0; ctr < length; ++ctr) {
      char token = input.charAt(ctr);
      String entity = (String) lookupMap.get(Integer.valueOf(token));
      if (entity == null) {
        writer.write(token);
      } else {
        writer.write(38);
        writer.write(entity);
        writer.write(59);
      }
    }
  }

  /**
   * Escape URL.
   *
   * @param input the input
   * @return the string
   */
  public static String escapeURL(String input) {
    if (input == null) {
      return null;
    } else if (input.equals("")) {
      return "";
    } else {
      try {
        return URLEncoder.encode(input, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
        return "";
      }
    }
  }

  /**
   * Escape java script.
   *
   * @param input the input
   * @return the string
   */
  public static String escapeJavaScript(String input) {
    if (input == null) {
      return null;
    } else if (input.equals("")) {
      return "";
    } else {
      StringWriter writer = new StringWriter(input.length() * 2);
      escapeJavaScriptCharacters(writer, input);
      return writer.toString();
    }
  }

  /**
   * Escape java script characters.
   *
   * @param writer the writer
   * @param input the input
   */
  private static void escapeJavaScriptCharacters(StringWriter writer, String input) {
    int length = input.length();

    for (int ctr = 0; ctr < length; ++ctr) {
      char token = input.charAt(ctr);
      if (isImmuneCharacter(token, IMMUNE_JAVASCRIPT_CHARS)) {
        writer.write(token);
      } else if (token >= '0' && token <= '9'
          || token >= 'A' && token <= 'Z'
          || token >= 'a' && token <= 'z') {
        writer.write(token);
      } else {
        String hexString;
        String pad;
        if (token < 256) {
          hexString = Integer.toHexString(token).toUpperCase();
          pad = "00".substring(hexString.length());
          writer.write("\\x" + pad + hexString);
        } else {
          hexString = Integer.toHexString(token).toUpperCase();
          pad = "0000".substring(hexString.length());
          writer.write("\\u" + pad + hexString);
        }
      }
    }
  }

  /**
   * Checks if is immune character.
   *
   * @param token the token
   * @param immuneCharacters the immune characters
   * @return true, if is immune character
   */
  private static boolean isImmuneCharacter(char token, char[] immuneCharacters) {
    char[] var2 = immuneCharacters;
    int var3 = immuneCharacters.length;

    for (int var4 = 0; var4 < var3; ++var4) {
      char immuneChar = var2[var4];
      if (immuneChar == token) {
        return true;
      }
    }

    return false;
  }

  static {
    for (int ctr = 0; ctr < MARKUP_ENTITIES.length; ++ctr) {
      lookupMap.put(Integer.parseInt(MARKUP_ENTITIES[ctr][0]), MARKUP_ENTITIES[ctr][1]);
    }
  }
}
