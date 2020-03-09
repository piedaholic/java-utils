package com.utilities.string;

import java.util.ArrayList;

public class StringUtils {
    public static String[] Split(String main, String cmp) {
	Object[] obj = null;

	try {
	    String str = main;
	    ArrayList<String> arr = new ArrayList<String>();
	    if (main != null && !main.equals("")) {
		String[] out;
		if (main.indexOf(cmp) == -1) {
		    out = new String[] { main };
		    return out;
		} else {
		    int k;
		    for (k = 0; str != null; ++k) {
			if (str.indexOf(cmp) == str.lastIndexOf(cmp)) {
			    if (str.indexOf(cmp) == 0) {
				arr.add("");
			    } else {
				arr.add(str.substring(0, str.indexOf(cmp)));
			    }

			    if (str.indexOf(cmp) == str.length() - 1) {
				arr.add("");
			    } else {
				arr.add(str.substring(str.indexOf(cmp) + cmp.length(), str.length()));
			    }

			    obj = arr.toArray();
			    out = new String[arr.size()];

			    for (int j = 0; j < arr.size(); ++j) {
				out[j] = obj[j].toString();
			    }

			    return out;
			}

			if (str.indexOf(cmp) == 0) {
			    arr.add("");
			} else {
			    arr.add(str.substring(0, str.indexOf(cmp)));
			}

			str = str.substring(str.indexOf(cmp) + 1, str.length());
		    }

		    obj = arr.toArray();
		    out = new String[arr.size()];

		    for (k = 0; k < arr.size(); ++k) {
			out[k] = obj[k].toString();
		    }

		    return out;
		}
	    } else {
		return null;
	    }
	} catch (Exception var8) {
	    return null;
	}
    }

    public static String replaceStr(String inputStr, String tobeReplaced, String replaceWith) {
	try {
	    StringBuffer sb = new StringBuffer(inputStr);

	    for (int index = inputStr.indexOf(tobeReplaced); index != -1; index = inputStr.indexOf(tobeReplaced,
		    index + replaceWith.length())) {
		inputStr = sb.replace(index, index + tobeReplaced.length(), replaceWith).toString();
		sb = new StringBuffer(inputStr);
	    }

	    return inputStr;
	} catch (Exception var5) {
	    return inputStr;
	}
    }

    public static String setString(int inp1, char inp2) {
	String retStr = "";

	for (int i = 0; i < inp1; ++i) {
	    retStr = retStr + inp2;
	}

	return retStr;
    }

    private static final String[] HEXCHARS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
	    "C", "D", "E", "F" };

    public static String byteToHex(byte[] input) {
	StringBuilder builder = new StringBuilder();

	for (int ctr = 0; ctr < input.length; ++ctr) {
	    builder.append(HEXCHARS[input[ctr] >> 4 & 15]);
	    builder.append(HEXCHARS[input[ctr] & 15]);
	}

	String output = builder.toString();
	return output;
    }

    public static byte[] hexToByte(String input) {
	int len = input.length();
	if (len % 2 != 0) {
	    throw new IllegalArgumentException("Input string size should be a multiple of 2 bytes.");
	} else {
	    byte[] bytes = new byte[len / 2];

	    for (int ctr = 0; ctr < len; ctr += 2) {
		bytes[ctr / 2] = (byte) ((Character.digit(input.charAt(ctr), 16) << 4)
			+ Character.digit(input.charAt(ctr + 1), 16));
	    }

	    return bytes;
	}
    }

    public static final String toHexString(byte[] bytes) {
	String[] HexChars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
		"F" };
	StringBuffer sb = new StringBuffer();

	for (int i = 0; i < bytes.length; ++i) {
	    sb.append(HexChars[bytes[i] >> 4 & 15]);
	    sb.append(HexChars[bytes[i] & 15]);
	}

	return new String(sb);
    }

    public static int stringtoInt(String sInput) {
	int num;
	sInput = checkForNull(sInput);
	if (sInput.equals("")) {
	    num = 0;
	} else {
	    num = Integer.parseInt(sInput);
	}

	return num;
    }

    public static String checkForNull(String sInput) {
	String sOutput = "";
	if (sInput != null) {
	    sOutput = sInput.trim();
	}

	return sOutput;
    }
}
