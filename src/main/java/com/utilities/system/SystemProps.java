package com.utilities.system;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class SystemProps {
	public static void main(String args[]) {
		Properties appProps = new Properties();
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream("D:\\Temp\\RABOELCM\\runtime\\Properties\\application\\fcubs.properties"));
			appProps.load(bis);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Before Regularizing the Path" + "\n" + "****************");
		System.out.println(appProps.get("APPLICATION_WORK_AREA"));
		System.out.println("");
		updateEnvValWithPath("APPLICATION_WORK_AREA", System.getProperties(), appProps);
		System.out.println("After Regularizing the Path" + "\n" + "****************");
		System.out.println(appProps.get("APPLICATION_WORK_AREA"));
	}

	public static Properties updateEnvValWithPath(String key, Properties sysProps, Properties appProps) {
		String value = "";
		System.out.println("Isnide updateEnvValWithPath");
		if (appProps.containsKey(key)) {
			System.out.println("Inside If#1");
			value = appProps.getProperty(key);
			System.out.println("Value is "+value);
			String retValue = value;
			if (value != null && value.contains("{") && value.contains("}")) {
				System.out.println("Inside If#2");
				int beginIndex = value.indexOf("{");
				int endIndex = value.indexOf("}");
				String envVar = value.substring(beginIndex + 1, endIndex);
				String target = "{" + envVar + "}";
				String replacement = sysProps.getProperty(envVar);
				if (replacement != null) {
					retValue = value.replace(target, replacement);
				}
			}

			appProps.setProperty(key, retValue);
		}
		return appProps;
	}
}
