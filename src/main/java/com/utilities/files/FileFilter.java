package com.utilities.files;

import java.io.File;
import java.io.FilenameFilter;

// TODO: Auto-generated Javadoc
/** The Class FileFilter. */
public class FileFilter {

  /** The filename without extn. */
  private String filenameWithoutExtn = null;

  /** The file type for filter. */
  private String fileTypeForFilter = null;

  /**
   * Instantiates a new file filter.
   *
   * @param filenameWithoutExtn the filename without extn
   * @param fileTypeForFilter the file type for filter
   */
  public FileFilter(String filenameWithoutExtn, String fileTypeForFilter) {
    this.filenameWithoutExtn = filenameWithoutExtn;
    this.fileTypeForFilter = fileTypeForFilter;
  }

  /**
   * Gets the file array.
   *
   * @param fileName the file name
   * @return the file array
   */
  public File[] getFileArray(String fileName) {
    File[] fileArr = null;
    File directory = new File(fileName);
    FileFilter.CustomFileNameFilter filter =
        new FileFilter.CustomFileNameFilter(this.filenameWithoutExtn, this.fileTypeForFilter);
    fileArr = directory.listFiles(filter);
    return fileArr;
  }

  /** The Class CustomFileNameFilter. */
  class CustomFileNameFilter implements FilenameFilter {

    /** The filename without extn. */
    private String filenameWithoutExtn = null;

    /** The file type for filter. */
    private String fileTypeForFilter = null;

    /**
     * Instantiates a new custom file name filter.
     *
     * @param filenameWithoutExtn the filename without extn
     * @param fileTypeForFilter the file type for filter
     */
    public CustomFileNameFilter(String filenameWithoutExtn, String fileTypeForFilter) {
      this.filenameWithoutExtn = filenameWithoutExtn;
      this.fileTypeForFilter = fileTypeForFilter;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File file, String name) {
      return name.startsWith(this.filenameWithoutExtn) && name.endsWith(this.fileTypeForFilter);
    }
  }
}
