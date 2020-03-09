package com.utilities.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteToFile {

	public static void main(String[] args) {
		String filePath = "D:\\Temp\\MyLogs";
		File file = new File(filePath + File.separator + "Harsh.log");
		String timeStamp = timeStampStr();
		StringBuffer strb = new StringBuffer();
		strb.append("[" + timeStamp + "]" + "HelloWorld");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(strb.toString());
			osw.close();
		} catch (Exception e) {

		}
	}

	public static String timeStampStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");
		String milliSecDate = sdf.format(Calendar.getInstance().getTime());

		return milliSecDate;
	}

}
