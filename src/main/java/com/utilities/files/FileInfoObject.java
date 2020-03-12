package com.utilities.files;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/** The Class FileInfoObject. */
public class FileInfoObject {

  /** The interface id. */
  private String interfaceId = null;

  /** The header. */
  private HashMap<?, ?> header = null;

  /** The header values. */
  private String[] headerValues = null;

  /** The detail. */
  private HashMap<?, ?> detail = null;

  /** The detail values. */
  private String[] detailValues = null;

  /** The defn. */
  private HashMap<?, ?> defn = null;

  /** Instantiates a new file info object. */
  public FileInfoObject() {}

  /**
   * Instantiates a new file info object.
   *
   * @param header the header
   * @param detail the detail
   * @param defn the defn
   */
  public FileInfoObject(HashMap<?, ?> header, HashMap<?, ?> detail, HashMap<?, ?> defn) {
    this.header = header;
    this.interfaceId = (String) header.keySet().iterator().next();
    this.headerValues = ((String) header.get(this.interfaceId)).split("~");
    this.detail = detail;
    this.defn = defn;
  }

  /**
   * Sets the header.
   *
   * @param header the header
   */
  public void setHeader(HashMap<?, ?> header) {
    this.header = header;
    this.interfaceId = (String) header.keySet().iterator().next();
    this.headerValues = ((String) header.get(this.interfaceId)).split("~");
  }

  /**
   * Sets the detail.
   *
   * @param detail the detail
   */
  public void setDetail(HashMap<?, ?> detail) {
    this.detail = detail;
  }

  /**
   * Gets the header.
   *
   * @return the header
   */
  public HashMap<?, ?> getHeader() {
    return this.header;
  }

  /**
   * Gets the detail.
   *
   * @return the detail
   */
  public HashMap<?, ?> getDetail() {
    return this.detail;
  }

  /**
   * Gets the defn.
   *
   * @return the defn
   */
  public HashMap<?, ?> getDefn() {
    return this.defn;
  }

  /**
   * Sets the defn.
   *
   * @param defn the defn
   */
  public void setDefn(HashMap<?, ?> defn) {
    this.defn = defn;
  }

  /**
   * Gets the interface id.
   *
   * @return the interface id
   */
  public String getInterfaceId() {
    return this.interfaceId;
  }

  /**
   * Gets the auth stat.
   *
   * @return the auth stat
   */
  public String getAuthStat() {
    return this.headerValues[27];
  }

  /**
   * Gets the file num.
   *
   * @return the file num
   */
  public String getFileNum() {
    return this.headerValues[0];
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return this.getDetails(1)[7];
  }

  /**
   * Gets the criteria.
   *
   * @return the criteria
   */
  public String getCriteria() {
    return this.headerValues[2];
  }

  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return this.headerValues[3];
  }

  /**
   * Gets the file path.
   *
   * @return the file path
   */
  public String getFilePath() {
    return this.headerValues[4];
  }

  /**
   * Gets the arch dir.
   *
   * @return the arch dir
   */
  public String getArchDir() {
    return this.headerValues[5];
  }

  /**
   * Gets the unique file name.
   *
   * @return the unique file name
   */
  public String getUniqueFileName() {
    return this.headerValues[6];
  }

  /**
   * Gets the file type.
   *
   * @return the file type
   */
  public String getFileType() {
    return this.headerValues[7];
  }

  /**
   * Gets the ascii file format.
   *
   * @return the ascii file format
   */
  public String getAsciiFileFormat() {
    return this.headerValues[8];
  }

  /**
   * Gets the row delimiter.
   *
   * @return the row delimiter
   */
  public String getRowDelimiter() {
    return this.headerValues[9];
  }

  /**
   * Gets the field delimiter.
   *
   * @return the field delimiter
   */
  public String getFieldDelimiter() {
    return this.headerValues[10];
  }

  /**
   * Gets the string delimiter.
   *
   * @return the string delimiter
   */
  public String getStringDelimiter() {
    return this.headerValues[11];
  }

  /**
   * Gets the date format.
   *
   * @return the date format
   */
  public String getDateFormat() {
    return this.headerValues[12];
  }

  /**
   * Gets the date delimiter.
   *
   * @return the date delimiter
   */
  public String getDateDelimiter() {
    return this.headerValues[13];
  }

  /**
   * Gets the four digit year.
   *
   * @return the four digit year
   */
  public String getFourDigitYear() {
    return this.headerValues[14];
  }

  /**
   * Gets the decimal symbol.
   *
   * @return the decimal symbol
   */
  public String getDecimalSymbol() {
    return this.headerValues[15];
  }

  /**
   * Gets the digit grup symbol.
   *
   * @return the digit grup symbol
   */
  public String getDigitGrupSymbol() {
    return this.headerValues[16];
  }

  /**
   * Gets the time delimiter.
   *
   * @return the time delimiter
   */
  public String getTimeDelimiter() {
    return this.headerValues[17];
  }

  /**
   * Gets the col hdr on first line.
   *
   * @return the col hdr on first line
   */
  public String getColHdrOnFirstLine() {
    return this.headerValues[18];
  }

  /**
   * Gets the date time option.
   *
   * @return the date time option
   */
  public String getDateTimeOption() {
    return this.headerValues[19];
  }

  /**
   * Gets the segment id.
   *
   * @return the segment id
   */
  public String getSegmentId() {
    return this.headerValues[20];
  }

  /**
   * Gets the no of segments.
   *
   * @return the no of segments
   */
  public String getNoOfSegments() {
    return this.headerValues[21];
  }

  /**
   * Gets the segment delimiter.
   *
   * @return the segment delimiter
   */
  public String getSegmentDelimiter() {
    return this.headerValues[22];
  }

  /**
   * Gets the segment occur del.
   *
   * @return the segment occur del
   */
  public String getSegmentOccurDel() {
    return this.headerValues[23];
  }

  /**
   * Gets the segment occur field.
   *
   * @return the segment occur field
   */
  public String getSegmentOccurField() {
    return this.headerValues[24];
  }

  /**
   * Gets the segment field len.
   *
   * @return the segment field len
   */
  public String getSegmentFieldLen() {
    return this.headerValues[25];
  }

  /**
   * Gets the segment field type.
   *
   * @return the segment field type
   */
  public String getSegmentFieldType() {
    return this.headerValues[26];
  }

  /**
   * Gets the details.
   *
   * @param fileColSeq the file col seq
   * @return the details
   */
  public String[] getDetails(int fileColSeq) {
    this.detailValues = ((String) this.detail.get(String.valueOf(fileColSeq))).split("~");
    return this.detailValues;
  }

  /**
   * Gets the interface module id.
   *
   * @return the interface module id
   */
  public String getInterfaceModuleId() {
    return (String) this.defn.get("1");
  }

  /**
   * Gets the interface proc id.
   *
   * @return the interface proc id
   */
  public String getInterfaceProcId() {
    return (String) this.defn.get("0");
  }

  /**
   * Gets the import or export.
   *
   * @return the import or export
   */
  public String getImportOrExport() {
    return (String) this.defn.get("2");
  }

  /**
   * Gets the segmentation.
   *
   * @return the segmentation
   */
  public String getSegmentation() {
    return (String) this.defn.get("3");
  }

  /**
   * Gets the split column.
   *
   * @return the split column
   */
  public String getSplitColumn() {
    return (String) this.defn.get("4");
  }

  /**
   * Gets the number of files.
   *
   * @return the number of files
   */
  public String getNumberOfFiles() {
    return (String) this.defn.get("5");
  }

  /**
   * Gets the file spec reqd for export.
   *
   * @return the file spec reqd for export
   */
  public String getFileSpecReqdForExport() {
    return (String) this.defn.get("6");
  }

  /**
   * Gets the file info.
   *
   * @return the file info
   */
  public FileInfoObject getFileInfo() {
    return this;
  }
}
