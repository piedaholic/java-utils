package com.utilities.files;

import java.util.HashMap;

public class FileInfoObject {
    private String interfaceId = null;
    private HashMap<?, ?> header = null;
    private String[] headerValues = null;
    private HashMap<?, ?> detail = null;
    private String[] detailValues = null;
    private HashMap<?, ?> defn = null;

    public FileInfoObject() {
    }

    public FileInfoObject(HashMap<?, ?> header, HashMap<?, ?> detail, HashMap<?, ?> defn) {
	this.header = header;
	this.interfaceId = (String) header.keySet().iterator().next();
	this.headerValues = ((String) header.get(this.interfaceId)).split("~");
	this.detail = detail;
	this.defn = defn;
    }

    public void setHeader(HashMap<?, ?> header) {
	this.header = header;
	this.interfaceId = (String) header.keySet().iterator().next();
	this.headerValues = ((String) header.get(this.interfaceId)).split("~");
    }

    public void setDetail(HashMap<?, ?> detail) {
	this.detail = detail;
    }

    public HashMap<?, ?> getHeader() {
	return this.header;
    }

    public HashMap<?, ?> getDetail() {
	return this.detail;
    }

    public HashMap<?, ?> getDefn() {
	return this.defn;
    }

    public void setDefn(HashMap<?, ?> defn) {
	this.defn = defn;
    }

    public String getInterfaceId() {
	return this.interfaceId;
    }

    public String getAuthStat() {
	return this.headerValues[27];
    }

    public String getFileNum() {
	return this.headerValues[0];
    }

    public String getTableName() {
	return this.getDetails(1)[7];
    }

    public String getCriteria() {
	return this.headerValues[2];
    }

    public String getFileName() {
	return this.headerValues[3];
    }

    public String getFilePath() {
	return this.headerValues[4];
    }

    public String getArchDir() {
	return this.headerValues[5];
    }

    public String getUniqueFileName() {
	return this.headerValues[6];
    }

    public String getFileType() {
	return this.headerValues[7];
    }

    public String getAsciiFileFormat() {
	return this.headerValues[8];
    }

    public String getRowDelimiter() {
	return this.headerValues[9];
    }

    public String getFieldDelimiter() {
	return this.headerValues[10];
    }

    public String getStringDelimiter() {
	return this.headerValues[11];
    }

    public String getDateFormat() {
	return this.headerValues[12];
    }

    public String getDateDelimiter() {
	return this.headerValues[13];
    }

    public String getFourDigitYear() {
	return this.headerValues[14];
    }

    public String getDecimalSymbol() {
	return this.headerValues[15];
    }

    public String getDigitGrupSymbol() {
	return this.headerValues[16];
    }

    public String getTimeDelimiter() {
	return this.headerValues[17];
    }

    public String getColHdrOnFirstLine() {
	return this.headerValues[18];
    }

    public String getDateTimeOption() {
	return this.headerValues[19];
    }

    public String getSegmentId() {
	return this.headerValues[20];
    }

    public String getNoOfSegments() {
	return this.headerValues[21];
    }

    public String getSegmentDelimiter() {
	return this.headerValues[22];
    }

    public String getSegmentOccurDel() {
	return this.headerValues[23];
    }

    public String getSegmentOccurField() {
	return this.headerValues[24];
    }

    public String getSegmentFieldLen() {
	return this.headerValues[25];
    }

    public String getSegmentFieldType() {
	return this.headerValues[26];
    }

    public String[] getDetails(int fileColSeq) {
	this.detailValues = ((String) this.detail.get(String.valueOf(fileColSeq))).split("~");
	return this.detailValues;
    }

    public String getInterfaceModuleId() {
	return (String) this.defn.get("1");
    }

    public String getInterfaceProcId() {
	return (String) this.defn.get("0");
    }

    public String getImportOrExport() {
	return (String) this.defn.get("2");
    }

    public String getSegmentation() {
	return (String) this.defn.get("3");
    }

    public String getSplitColumn() {
	return (String) this.defn.get("4");
    }

    public String getNumberOfFiles() {
	return (String) this.defn.get("5");
    }

    public String getFileSpecReqdForExport() {
	return (String) this.defn.get("6");
    }

    public FileInfoObject getFileInfo() {
	return this;
    }
}
