package com.utilities.test;

public class Test {

	public static void main(String args[]) {
		System.out.println("HARSH".substring(4));
		String reqd = "";
		String msgTerminator = "";
		String msgTerminatorFlag = "Y";
		do {
			System.out.println("Hello World");
		} while (reqd == "Y");
		if (!msgTerminator.equals("") || msgTerminator != null)
			System.out.println("GG");
		if ((msgTerminator != null || !msgTerminator.equals("") || !msgTerminator.equals("null"))
				&& msgTerminatorFlag.equals("Y") || msgTerminatorFlag.equals("N"))

		{
			System.out.println("Harsh");
		}
		String blockName = "recordStatus";
		int idx = blockName.lastIndexOf("_CUSTOM");
		if (idx > 0) {
			blockName = blockName.substring(0, idx);
		}
		System.out.println(blockName);
		blockName = addDelimiter(blockName, '_', "(?=[A-Z])").toUpperCase();
		System.out.println(blockName);
		String respXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + "<QUERYUSER_RES>\r\n"
				+ "  <WSHEADER>\r\n" + "    <Request-Header>\r\n" + "      <source>SYSTEM</source>\r\n"
				+ "      <component>LINUX</component>\r\n" + "      <action>NEW</action>\r\n"
				+ "    </Request-Header>\r\n" + "  </WSHEADER>\r\n" + "  <WSBODY>\r\n" + "    <User-Details>\r\n"
				+ "      <id>2</id>\r\n" + "      <name>ANIKAN SKYWALKER</name>\r\n"
				+ "      <address>BANGALORE</address>\r\n" + "      <nationality>INDIAN</nationality>\r\n"
				+ "      <dob>1997-03-12</dob>\r\n" + "      <recordStatus>O</recordStatus>\r\n"
				+ "      <gender>M</gender>\r\n" + "    </User-Details>\r\n" + "    <Sys-Response>\r\n"
				+ "      <status>SUCCESS</status>\r\n" + "      <err_Dto_List>\r\n" + "        <Error>\r\n"
				+ "          <ecode>200</ecode>\r\n" + "          <edesc>Success</edesc>\r\n" + "        </Error>\r\n"
				+ "      </err_Dto_List>\r\n" + "    </Sys-Response>\r\n" + "  </WSBODY>\r\n" + "</QUERYUSER_RES>";
		//respXML = respXML.replaceAll("\\s", "");
		respXML = respXML.replaceAll("<Request-Header>", "");
		respXML = respXML.replaceAll("(xml)", "BULK");
		System.out.println(respXML);
		String str = "ModifyUserByIdReqModify";
		str = str.replaceFirst("Query|Create|Modify|Update|Delete", "");
		System.out.println(str.substring(0,1).toLowerCase());
		String testString = "helloWorld";
		testString.replaceFirst("\\w","\\u\\1");
	}

	public static String addDelimiter(String str, Character delimiter, String regexPattern) {
		String newStr = "";
		String[] strArr = str.split(regexPattern);// (?=\\p{Upper})
		System.out.println("Arr Length is " + strArr.length);
		for (int i = 0; i < strArr.length; i++) {
			System.out.println(i + "-->" + strArr[i] + ".." + newStr);
			if (i != strArr.length - 1) {
				newStr = newStr + strArr[i] + delimiter;
			} else {
				newStr = newStr + strArr[i];
			}

		}
		return newStr;
	}
}
