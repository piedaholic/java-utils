package com.utilities.string;

public class Test {

    public static void main(String[] args) {
	String regex = "^(.*)[java|py]$";
	System.out.println("C:\\Harsh.py".matches(regex));
	String file = "C:/Desk/Test.py";
	String artifactName = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
	System.out.println(artifactName);
	String fileName = "FCUBSAccServiceear";
	System.out.println(fileName.matches("FCUBS([A-Za-z0-9]*)Service\\.(.*)"));
    }
}
