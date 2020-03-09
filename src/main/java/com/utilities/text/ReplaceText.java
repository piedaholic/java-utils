package com.utilities.text;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;


public class ReplaceText {

private static String g_SourceFileName = "D:/Temp/AEG_F01_FLEXCUBE_20180704.txt";
private static String g_DestFileName = "D:/Temp/AEG_F01_FLEXCUBE_20180704_5.txt";
//private static String g_ReplaceFileName = "D:/Temp/AEG_F01_FLEXCUBE_20180704_5.txt";

	public static void main(String[] args) throws IOException {

		StringBuilder strb = new StringBuilder();
		String line1;
		try (BufferedReader br = new BufferedReader(new FileReader(g_SourceFileName))) {
			while ((line1 = br.readLine()) != null) {
				strb.append(line1);
				strb.append("\n");
			}
		}
		String content = strb.toString();
		try (BufferedReader br = new BufferedReader(new FileReader("D:/Temp/AEG_Records_to_delete.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				content = content.replace(line, "");
			}
		}
		content = content.replaceAll("(?m)^[ \t]*\r?\n", "");
		IOUtils.write(content, new FileOutputStream(g_DestFileName), "UTF-8");
	}

}
