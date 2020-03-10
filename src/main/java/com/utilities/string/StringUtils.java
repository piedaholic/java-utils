package com.utilities.string;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.utilities.constants.Constants;
import com.utilities.dates.GregorianDateMatcher;
import com.utilities.json.JsonUtils;
import com.utilities.object.ObjectUtils;
import com.utilities.xml.XmlUtils;

public class StringUtils {
    public static String[] split(String main, String cmp) {
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

    public static String validateParameter(String input) {
	if (input == null) {
	    return null;
	}
	if ("".equalsIgnoreCase(input)) {
	    return "";
	}
	StringWriter writer = new StringWriter((int) (input.length() * 1.5D));
	int length = input.length();
	if (length > 4000) {
	    return "";
	}
	for (int ctr = 0; ctr < length; ctr++) {
	    char token = input.charAt(ctr);
	    if ((token == '&') || (token == '(') || (token == ')') || (token == '+') || (token == ',') || (token == '/')
		    || (token == '<') || (token == '\006') || (token == '\\')) {
		return "";
	    }
	    writer.write(token);
	}
	return writer.toString();
    }

    public String validateString(String input) {
	if (input == null) {
	    return null;
	}
	if ("".equalsIgnoreCase(input)) {
	    return "";
	}
	StringWriter writer = new StringWriter((int) (input.length() * 1.5D));
	int length = input.length();
	for (int ctr = 0; ctr < length; ctr++) {
	    char token = input.charAt(ctr);
	    if (((token >= '0') && (token <= '9')) || ((token >= 'A') && (token <= 'Z'))
		    || ((token >= 'a') && (token <= 'z')) || (token == '_')) {
		writer.write(token);
	    } else {
		return "";
	    }
	}
	return writer.toString();
    }

    /*
     * private static final String[] HEXCHARS = { "0", "1", "2", "3", "4", "5", "6",
     * "7", "8", "9", "A", "B", "C", "D", "E", "F" };
     * 
     * public static String byteToHex(byte[] input) { StringBuilder builder = new
     * StringBuilder(); for (int ctr = 0; ctr < input.length; ctr++) {
     * builder.append(HEXCHARS[(input[ctr] >> 4 & 0xF)]);
     * builder.append(HEXCHARS[(input[ctr] & 0xF)]); } String output =
     * builder.toString(); return output; }
     * 
     * public static byte[] hexToByte(String input) { int len = input.length(); if
     * (len % 2 != 0) { throw new
     * IllegalArgumentException("Input string size should be a multiple of 2 bytes."
     * ); } byte[] bytes = new byte[len / 2]; for (int ctr = 0; ctr < len; ctr += 2)
     * { bytes[(ctr / 2)] = ((byte) ((Character.digit(input.charAt(ctr), 16) << 4) +
     * Character.digit(input.charAt(ctr + 1), 16))); } return bytes; }
     */
    public static String toCamelCase(String init) {
	if (init == null)
	    return null;

	final StringBuilder ret = new StringBuilder(init.length());

	for (final String word : init.split(" ")) {
	    if (!word.isEmpty()) {
		ret.append(word.substring(0, 1).toUpperCase());
		ret.append(word.substring(1).toLowerCase());
	    }
	    if (!(ret.length() == init.length()))
		ret.append(" ");
	}

	return ret.toString();
    }

    public static String toCamelCase(String init, String regexPattern) {
	if (init == null)
	    return null;

	final StringBuilder ret = new StringBuilder(init.length());

	for (String word : init.split(regexPattern)) {
	    if (!word.isEmpty()) {
		ret.append(word.substring(0, 1).toUpperCase());
		ret.append(word.substring(1).toLowerCase());
	    }
	    // if (!(ret.length() == init.length()))
	    // ret.append(" ");
	}

	return ret.toString();
    }

    public static boolean isInteger(String input) {

	try {
	    Integer.parseInt(input);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    public static boolean isDouble(String str) {

	try {
	    Double.parseDouble(str);
	    return true;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

    public static boolean isFloat(String str) {

	try {
	    Float.parseFloat(str);
	    return true;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

    public static boolean isValidDate(String inDate) {
	// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");
	// dateFormat.setLenient(false);
	// try {
	// dateFormat.parse(inDate.trim());
	// } catch (Exception pe) {
	// return false;
	// }
	// return true;
	return GregorianDateMatcher.dateMatches(inDate);
    }

    public static boolean isValidTimestamp(String str) {
	return GregorianDateMatcher.tsMatches(str);
    }

    public static boolean isDateTimestampValid(String str) {
	String[] dateTime = str.split(" ");
	switch (dateTime.length) {
	case 1:
	    return GregorianDateMatcher.dateMatches(dateTime[0]);
	case 2:
	    return GregorianDateMatcher.dateMatches(dateTime[0]) && GregorianDateMatcher.tsMatches(dateTime[1]);
	default:
	    return false;
	}
    }

    public static String convertDocumentToString(Document doc) {
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer transformer;
	try {
	    transformer = tf.newTransformer();
	    // below code to remove XML declaration
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	    StringWriter writer = new StringWriter();
	    transformer.transform(new DOMSource(doc), new StreamResult(writer));
	    String output = writer.getBuffer().toString();
	    return output;
	} catch (TransformerException e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static Document convertStringToXMLDocument(String xmlString) {
	// Parser that produces DOM object trees from XML content
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	// API to obtain DOM Document instance
	DocumentBuilder builder = null;
	try {
	    // Create DocumentBuilder with default configuration
	    builder = factory.newDocumentBuilder();

	    // Parse the content to Document object
	    Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
	    return doc;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static String getVarName(String colName) {
	String res = "";
	if ((colName != null) && (colName != "")) {
	    colName = colName.toLowerCase();
	    String[] resAr = colName.split("_", -1);
	    for (int i = 0; i < resAr.length; i++) {
		if (i == 0) {
		    res = res + resAr[i];
		} else {
		    res = res + resAr[i].replaceFirst(
			    new StringBuilder().append(resAr[i].charAt(0)).append("").toString(),
			    new StringBuilder().append(resAr[i].charAt(0)).append("").toString().toUpperCase());
		}
	    }
	}
	return res;
    }

    public static String generateId() {
	String id = null;
	try {
	    // Initialize SecureRandom
	    // This is a lengthy operation, to be done only upon
	    // initialization of the application
	    SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

	    // generate a random number
	    String randomNum = Integer.valueOf(prng.nextInt()).toString();

	    // get its digest
	    MessageDigest sha = MessageDigest.getInstance("SHA-1");
	    byte[] result = sha.digest(randomNum.getBytes());

	    id = hexEncode(result);
	} catch (NoSuchAlgorithmException ex) {
	    System.err.println(ex);
	}
	return id;
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice textual
     * representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book "Java In A
     * Nutshell", and converts a byte array into a String of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    static private String hexEncode(byte[] input) {
	StringBuilder result = new StringBuilder();
	char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	for (int idx = 0; idx < input.length; ++idx) {
	    byte b = input[idx];
	    result.append(digits[(b & 0xf0) >> 4]);
	    result.append(digits[b & 0x0f]);
	}
	return result.toString();
    }

    public static boolean containsSpecialChar() {
	Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher("I am a string");
	boolean b = m.find();
	return b;
    }

    public static void toString(String[] args, String startWith, String endWith, String dataSeparator) {
	String result = startWith;
	for (int i = 0; i < args.length; i++) {
	    if (i == (args.length - 1))
		result = result + args[i];
	    else
		result = result + args[i] + dataSeparator;
	}
	result = result + endWith;
    }

    public static String getBodyContent(InputStream objReqInpStream, int contentLength) throws IOException {
	int offset = 0;
	char[] requestCharacters = new char[contentLength];
	InputStreamReader reader = new InputStreamReader(objReqInpStream, "utf-8");
	for (;;) {
	    int noOfReadChars = reader.read(requestCharacters, offset, contentLength - offset);
	    if ((noOfReadChars == -1) || (noOfReadChars == 0)) {
		break;
	    }
	    offset += noOfReadChars;
	}
	if (objReqInpStream != null) {
	    objReqInpStream.close();
	}
	StringBuffer sb = new StringBuffer();
	sb.append(requestCharacters, 0, offset);

	return sb.toString();
    }

    public static String generateJaxbRespClassName(String reqClassName, String serviceName) {
	String pkg = reqClassName;
	String[] strArr = reqClassName.split("\\.");
	reqClassName = strArr[strArr.length - 1];
	pkg = pkg.replace(reqClassName, "");
	int idx = reqClassName.indexOf("By");

	if (idx > 0) {
	    reqClassName = reqClassName.substring(0, idx);
	    reqClassName = reqClassName + "Resp";
	} else {
	    idx = reqClassName.indexOf("All");
	    if (idx > 0) {
		reqClassName = reqClassName.substring(0, idx);
		reqClassName = reqClassName + serviceName.replace("Service", "") + "ListResp";
	    } else {
		idx = reqClassName.indexOf("Req");
		if (idx > 0 && reqClassName.startsWith("Query")) {
		    reqClassName = reqClassName.substring(0, idx);
		    reqClassName = reqClassName + "ListResp";
		}
	    }
	}
	if (reqClassName.startsWith("Delete")) {
	    reqClassName = "DeleteResp";
	}
	reqClassName = pkg + reqClassName.replace("Req", "Resp");
	return reqClassName;
    }

    public static String stringify(String str) {
	try {
	    if (!str.equals(null) && !str.equals("")) {
		return str;
	    } else
		return "";
	} catch (NullPointerException e) {
	    return "";
	}
    }

    public static String stringify(String str, String ifNullString) {
	if (!ObjectUtils.isNull(str)) {
	    return str;
	} else {
	    if (!ObjectUtils.isNull(ifNullString)) {
		return ifNullString;
	    } else
		return "";
	}
    }

    public static String getContentType(String req) {
	if (XmlUtils.isXML(req)) {
	    return Constants.CONTENT_TYPE_XML;
	} else if (JsonUtils.jacksonJSON(req)) {
	    return Constants.CONTENT_TYPE_JSON;
	} else {
	    try {
		// Must be unrecognized text format
		return Constants.CONTENT_TYPE_TEXT;
	    } catch (Exception e) {
		return null;
	    }

	}
    }

    public static String toString(Object obj) {
	StringBuilder result = new StringBuilder();
	String newLine = System.getProperty("line.separator");

	result.append(obj.getClass().getName());
	result.append(" Object {");
	result.append(newLine);

	Method[] methods = obj.getClass().getDeclaredMethods();
	for (Method method : methods) {
	    if (method.getName().startsWith("get")) {
		Object output;
		try {
		    output = method.invoke(new Object[] {});
		    System.out.println(method.getName().replaceFirst("get", "") + ":" + output);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}

	// determine fields declared in this class only (no fields of superclass)
	Field[] fields = obj.getClass().getDeclaredFields();

	// print field names paired with their values
	for (Field field : fields) {
	    result.append("  ");
	    try {
		result.append(field.getName());
		result.append(": ");
		// requires access to private field:
		result.append(field.get(obj));
	    } catch (IllegalAccessException ex) {
		System.out.println(ex);
	    }
	    result.append(newLine);
	}
	result.append("}");

	return result.toString();
    }

    public static String listToString(List<?> list) {
	String result = "";
	if (list != null) {
	    Iterator<?> iterator = list.iterator();
	    while (iterator.hasNext()) {
		result = "[" + iterator.next().toString() + "]";
	    }
	}
	return result;
    }

}
