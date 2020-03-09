package com.utilities.files;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter {
    private String filenameWithoutExtn = null;
    private String fileTypeForFilter = null;

    public FileFilter(String filenameWithoutExtn, String fileTypeForFilter) {
	this.filenameWithoutExtn = filenameWithoutExtn;
	this.fileTypeForFilter = fileTypeForFilter;
    }

    public File[] getFileArray(String fileName) {
	File[] fileArr = null;
	File directory = new File(fileName);
	FileFilter.CustomFileNameFilter filter = new FileFilter.CustomFileNameFilter(this.filenameWithoutExtn,
		this.fileTypeForFilter);
	fileArr = directory.listFiles(filter);
	return fileArr;
    }

    class CustomFileNameFilter implements FilenameFilter {
	private String filenameWithoutExtn = null;
	private String fileTypeForFilter = null;

	public CustomFileNameFilter(String filenameWithoutExtn, String fileTypeForFilter) {
	    this.filenameWithoutExtn = filenameWithoutExtn;
	    this.fileTypeForFilter = fileTypeForFilter;
	}

	public boolean accept(File file, String name) {
	    return name.startsWith(this.filenameWithoutExtn) && name.endsWith(this.fileTypeForFilter);
	}
    }
}
