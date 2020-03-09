package com.utilities.files;

import java.io.File;

public class Filewalker {

    public void walk(String path, StringBuilder strb) {

	File root = new File(path);
	File[] list = root.listFiles();

	if (list == null)
	    return;

	for (File f : list) {
	    if (f.isDirectory()) {
		walk(f.getAbsolutePath(), strb);
		strb.append("Dir:" + f.getAbsoluteFile() + "\n");
		System.out.println("Dir:" + f.getAbsoluteFile());
	    } else {
		strb.append("File:" + f.getAbsoluteFile() + "\n");
		System.out.println("File:" + f.getAbsoluteFile());
	    }
	}
    }
}
