package com.utilities.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// TODO: Auto-generated Javadoc
/** The Class FCReadFile. */
public class FCReadFile {

  /**
   * Read flat file for segment.
   *
   * @param fo the fo
   * @param fileName the file name
   * @param segmentSeq the segment seq
   * @return the linked hash map
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   */
  @SuppressWarnings("unchecked")
  public static LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject> readFlatFileForSegment(
      FileInfoObject fo, String fileName, HashMap<String, String> segmentSeq)
      throws FileNotFoundException, IOException, SQLException {
    LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject> fileInfoMap =
        new LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject>();
    BufferedReader buffer = null;
    InputStreamReader isr = null;
    FileInputStream fis = null;
    String data = null;
    String[] recordsInRow = null;
    String KeyString = null;

    try {
      fis = new FileInputStream(fileName);
      isr = new InputStreamReader(fis, "UTF-8");
      buffer = new BufferedReader(isr);
      int line_no_temp = 0;

      while ((data = buffer.readLine()) != null) {
        FileInfoObject fio = null;
        LinkedHashMap<String, String> columnValueMap = null;
        if (data.trim().length() != 0) {
          if (line_no_temp == 0 && !"0".equals(fo.getColHdrOnFirstLine())) {
            ++line_no_temp;
          } else if (!data.trim().equals("HDR") && !data.trim().equals("FTR")) {
            if (!"".equals(fo.getRowDelimiter()) && fo.getRowDelimiter().length() != 0) {
              recordsInRow = data.split(fo.getRowDelimiter());
            } else {
              recordsInRow = data.split("#####");
            }

            for (int rowCnt = 0; rowCnt < recordsInRow.length; ++rowCnt) {
              String singleRecordData = recordsInRow[rowCnt];
              fio = new FileInfoObject();
              String[] columnArr = null;
              fio.setHeader(fo.getHeader());
              fio.setDefn(fo.getDefn());
              columnValueMap = new LinkedHashMap<String, String>();
              String segmentId;
              HashMap<String, String> l_DetInfo;
              if ("D".equals(fo.getAsciiFileFormat())) {
                segmentId = null;
                String[] segmentsData;
                if (!"".equals(fo.getSegmentDelimiter())
                    && fo.getSegmentDelimiter().length() != 0) {
                  segmentsData = singleRecordData.split(fo.getSegmentDelimiter());
                } else {
                  segmentsData = singleRecordData.split("!!!!!");
                }

                int columnIndex;
                for (columnIndex = 0; columnIndex < segmentsData.length; ++columnIndex) {
                  l_DetInfo = null;
                  segmentId = segmentsData[columnIndex];
                  if (segmentSeq.containsKey(segmentId)) {
                    ((String) segmentSeq.get(segmentId)).split("~");
                    // l_DetInfo = getIfDetInfo(fio, segmentTable);
                    fio.setDetail(l_DetInfo);
                    break;
                  }
                }

                columnArr = new String[fio.getDetail().size() + 1];

                for (columnIndex = 0; columnIndex < columnArr.length; ++columnIndex) {
                  columnArr[columnIndex] = "";
                }

                if (null == KeyString) {
                  // generate key string
                }

                columnIndex = 1;

                for (int columnCnt = 0; columnCnt < segmentsData.length; ++columnCnt) {
                  if ("KEYSTRING".equals(fio.getDetails(columnIndex)[0])) {
                    columnArr[columnIndex] = columnArr[columnIndex] + KeyString + "~";
                    ++columnIndex;
                  }

                  if (!"VARCHAR2".equals(fio.getDetails(columnIndex)[1])
                      && !"VARCHAR".equals(fio.getDetails(columnIndex)[1])
                      && !"CHAR".equals(fio.getDetails(columnIndex)[1])
                      && !"DATE".equals(fio.getDetails(columnIndex)[1])) {
                    if ("NUMBER".equals(fio.getDetails(columnIndex)[1])) {
                      segmentsData[columnCnt] =
                          segmentsData[columnCnt].replaceAll(fio.getDigitGrupSymbol(), "");
                      columnArr[columnIndex] =
                          columnArr[columnIndex] + segmentsData[columnCnt] + "~";
                    } else {
                      columnArr[columnIndex] =
                          columnArr[columnIndex] + segmentsData[columnCnt] + "~";
                    }
                  } else {
                    columnArr[columnIndex] = columnArr[columnIndex] + segmentsData[columnCnt] + "~";
                  }

                  ++columnIndex;
                }
              } else if ("F".equals(fo.getAsciiFileFormat())) {
                l_DetInfo = (HashMap<String, String>) segmentSeq.keySet();
                Iterator<String> segmentKeyIterator = ((Iterable<String>) l_DetInfo).iterator();

                while (segmentKeyIterator.hasNext()) {
                  segmentId = (String) segmentKeyIterator.next();
                  if (singleRecordData.contains(segmentId)) {
                    // String segmentTable = ((String) segmentSeq.get(segmentId)).split("~")[1];
                    // l_DetInfo = getIfDetInfo(context, fio, segmentTable, currUser);
                    fio.setDetail(l_DetInfo);
                    break;
                  }
                }

                if (null == KeyString) {
                  // generate key string
                }

                columnArr = new String[fio.getDetail().size() + 1];

                for (int columnCnt = 0; columnCnt < columnArr.length; ++columnCnt) {
                  columnArr[columnCnt] = "";
                }

                String temp = null;

                for (int columnIndex = 1; columnIndex <= fio.getDetail().size(); ++columnIndex) {
                  int fieldLength = Integer.parseInt(fio.getDetails(columnIndex)[2]);
                  int decimalLength = Integer.parseInt(fio.getDetails(columnIndex)[6]);
                  if ("KEYSTRING".equals(fio.getDetails(columnIndex)[0])) {
                    temp = KeyString;
                  } else if (!"VARCHAR2".equals(fio.getDetails(columnIndex)[1])
                      && !"VARCHAR".equals(fio.getDetails(columnIndex)[1])
                      && !"CHAR".equals(fio.getDetails(columnIndex)[1])
                      && !"DATE".equals(fio.getDetails(columnIndex)[1])) {
                    if ("NUMBER".equals(fio.getDetails(columnIndex)[1])) {
                      if ("0".equals(fio.getDetails(columnIndex)[6])) {
                        temp = singleRecordData.substring(0, fieldLength);
                        singleRecordData = singleRecordData.substring(temp.length());
                      } else if (!"".equals(fio.getDecimalSymbol())
                          && !"F".equals(fio.getDecimalSymbol())
                          && !"null".equals(fio.getDecimalSymbol())) {
                        temp = singleRecordData.substring(0, fieldLength + 1);
                        singleRecordData = singleRecordData.substring(temp.length());
                      } else {
                        temp = singleRecordData.substring(0, fieldLength);
                        temp =
                            temp.substring(0, fieldLength - decimalLength)
                                + "."
                                + temp.substring(fieldLength - decimalLength);
                        singleRecordData = singleRecordData.substring(temp.length() - 1);
                      }
                    } else {
                      temp = singleRecordData.substring(0, fieldLength);
                      singleRecordData = singleRecordData.substring(temp.length());
                    }
                  } else {
                    temp = singleRecordData.substring(0, fieldLength);
                    singleRecordData = singleRecordData.substring(temp.length());
                  }

                  columnArr[columnIndex] = columnArr[columnIndex] + temp + "~";
                }
              }

              for (int columnIndex = 1; columnIndex <= fio.getDetail().size(); ++columnIndex) {
                String[] detailArr = fio.getDetails(columnIndex);
                if (columnArr[columnIndex].endsWith("~")) {
                  columnArr[columnIndex] =
                      columnArr[columnIndex].substring(0, columnArr[columnIndex].length() - 1);
                }

                if (columnArr[columnIndex].endsWith("~")) {
                  columnArr[columnIndex] = columnArr[columnIndex] + "null";
                }

                columnValueMap.put(detailArr[0], columnArr[columnIndex]);
              }

              fileInfoMap.put(columnValueMap, fio);
            }
          } else {
            ++line_no_temp;
          }
        } else {
          buffer.skip((long) data.trim().length());
        }
      }
    } catch (IndexOutOfBoundsException var40) {
      // handle exception
    } catch (FileNotFoundException var41) {

    } catch (IOException var42) {

    } catch (Exception var43) {

    } finally {
      try {
        if (buffer != null) {
          buffer.close();
        }
      } catch (IOException var39) {
      }

      try {
        if (fis != null) {
          fis.close();
        }
      } catch (IOException var38) {
      }
    }

    return fileInfoMap;
  }

  /**
   * Read flat file.
   *
   * @param fo the fo
   * @param fileName the file name
   * @return the linked hash map
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static LinkedHashMap<String, String> readFlatFile(FileInfoObject fo, String fileName)
      throws FileNotFoundException, IOException {
    BufferedReader buffer = null;
    Boolean hasKeyString = false;
    LinkedHashMap<String, String> columnValueMap = new LinkedHashMap<String, String>();
    String data = null;
    String[] columnArr = null;
    int LINE_NO = 0;
    String[] recordsInRow = null;
    String singleRecordData = null;
    // generate key string
    String KeyString = "";
    InputStreamReader inputStreamReader = null;
    FileInputStream fileInputStream = null;

    int columnIndex;
    try {
      fileInputStream = new FileInputStream(fileName);
      inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      buffer = new BufferedReader(inputStreamReader);
      columnIndex = 0;

      while ((data = buffer.readLine()) != null) {
        if (data.trim().length() != 0) {
          if (columnIndex == 0 && !"0".equals(fo.getColHdrOnFirstLine())) {
            ++columnIndex;
            continue;
          }

          if (!"".equals(fo.getRowDelimiter()) && fo.getRowDelimiter().length() != 0) {
            recordsInRow = data.split(fo.getRowDelimiter());
          } else {
            recordsInRow = data.split("#####");
          }

          for (int rowCnt = 0; rowCnt < recordsInRow.length; ++rowCnt) {
            singleRecordData = recordsInRow[rowCnt];
            int columnCnt;
            int fieldLength;
            int arrIndex;
            if ("D".equals(fo.getAsciiFileFormat())) {
              if (singleRecordData.endsWith(fo.getFieldDelimiter())
                  || singleRecordData.endsWith("|")) {
                singleRecordData = singleRecordData + "null";
              }

              if (LINE_NO == 0 && rowCnt == 0) {
                columnArr = new String[fo.getDetail().size() + 1];

                for (columnCnt = 0; columnCnt < columnArr.length; ++columnCnt) {
                  columnArr[columnCnt] = "";
                }
              }

              String[] dataArr = singleRecordData.split(fo.getFieldDelimiter());
              fieldLength = 1;

              for (arrIndex = 0; arrIndex < dataArr.length; ++arrIndex) {
                if (!"VARCHAR2".equals(fo.getDetails(fieldLength)[1])
                    && !"VARCHAR".equals(fo.getDetails(fieldLength)[1])
                    && !"CHAR".equals(fo.getDetails(fieldLength)[1])
                    && !"DATE".equals(fo.getDetails(fieldLength)[1])) {
                  if ("NUMBER".equals(fo.getDetails(fieldLength)[1])) {
                    dataArr[arrIndex] = dataArr[arrIndex].replaceAll(fo.getDigitGrupSymbol(), "");
                    columnArr[fieldLength] = columnArr[fieldLength] + dataArr[arrIndex] + "~";
                  } else {
                    columnArr[fieldLength] = columnArr[fieldLength] + dataArr[arrIndex] + "~";
                  }
                } else {
                  columnArr[fieldLength] = columnArr[fieldLength] + dataArr[arrIndex] + "~";
                }

                ++fieldLength;
              }

              for (arrIndex = 1; arrIndex <= fo.getDetail().size(); ++arrIndex) {
                String[] detailArr = fo.getDetails(arrIndex);

                try {
                  if (!"KEYSTRING".equals(fo.getDetails(arrIndex)[0])) {
                    if (hasKeyString) {}
                  } else {
                    String ColArrValue = columnArr[arrIndex];
                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue.substring(0, ColArrValue.length() - 1);
                    }

                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue + "null";
                    }

                    String[] ColArrValues = ColArrValue.split("~");
                    String temp = KeyString;

                    for (int i = 1; i < ColArrValues.length; ++i) {
                      temp = temp + "~" + KeyString;
                    }

                    columnArr[arrIndex] = columnArr[arrIndex] + KeyString + "~";
                    hasKeyString = true;
                  }
                } catch (ArrayIndexOutOfBoundsException var42) {
                  columnValueMap.put(detailArr[0], "null");
                }
              }
            } else if ("F".equals(fo.getAsciiFileFormat())) {
              if (LINE_NO == 0 && rowCnt == 0) {
                columnArr = new String[fo.getDetail().size() + 1];

                for (columnCnt = 0; columnCnt < columnArr.length; ++columnCnt) {
                  columnArr[columnCnt] = "";
                }
              }

              String temp = null;

              for (columnIndex = 1; columnIndex <= fo.getDetail().size(); ++columnIndex) {
                fieldLength = Integer.parseInt(fo.getDetails(columnIndex)[2]);
                arrIndex = Integer.parseInt(fo.getDetails(columnIndex)[6]);
                if ("KEYSTRING".equals(fo.getDetails(columnIndex)[0])) {
                  temp = KeyString;
                } else if (!"VARCHAR2".equals(fo.getDetails(columnIndex)[1])
                    && !"VARCHAR".equals(fo.getDetails(columnIndex)[1])
                    && !"CHAR".equals(fo.getDetails(columnIndex)[1])
                    && !"DATE".equals(fo.getDetails(columnIndex)[1])) {
                  if ("NUMBER".equals(fo.getDetails(columnIndex)[1])) {
                    if ("0".equals(fo.getDetails(columnIndex)[6])) {
                      temp = singleRecordData.substring(0, fieldLength);
                      singleRecordData = singleRecordData.substring(temp.length());
                    } else if (!"".equals(fo.getDecimalSymbol())
                        && !"F".equals(fo.getDecimalSymbol())
                        && !"null".equals(fo.getDecimalSymbol())) {
                      temp = singleRecordData.substring(0, fieldLength + 1);
                      singleRecordData = singleRecordData.substring(temp.length());
                    } else {
                      temp = singleRecordData.substring(0, fieldLength);
                      temp =
                          temp.substring(0, fieldLength - arrIndex)
                              + "."
                              + temp.substring(fieldLength - arrIndex);
                      singleRecordData = singleRecordData.substring(temp.length() - 1);
                    }
                  } else {
                    temp = singleRecordData.substring(0, fieldLength);
                    singleRecordData = singleRecordData.substring(temp.length());
                  }
                } else {
                  temp = singleRecordData.substring(0, fieldLength);
                  singleRecordData = singleRecordData.substring(temp.length());
                }

                columnArr[columnIndex] = columnArr[columnIndex] + temp + "~";
              }
            }
          }
        } else {
          buffer.skip((long) data.trim().length());
        }

        ++LINE_NO;
      }
    } catch (IndexOutOfBoundsException var43) {
      // handle or throw exception
    } catch (FileNotFoundException var44) {
      // handle or throw exception
    } catch (IOException var45) {
      // handle or throw exception
    } catch (Exception var46) {
      // handle or throw exception
    } finally {
      try {
        if (buffer != null) {
          buffer.close();
        }
      } catch (IOException var41) {
        // handle or throw exception
      }

      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
      } catch (IOException var40) {
        // handle or throw exception
      }

      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
      } catch (IOException var39) {
        // handle or throw exception
      }
    }

    for (columnIndex = 1; columnIndex <= fo.getDetail().size(); ++columnIndex) {
      String[] detailArr = fo.getDetails(columnIndex);
      if (columnArr[columnIndex].endsWith("~")) {
        columnArr[columnIndex] =
            columnArr[columnIndex].substring(0, columnArr[columnIndex].length() - 1);
      }

      if (columnArr[columnIndex].endsWith("~")) {
        columnArr[columnIndex] = columnArr[columnIndex] + "null";
      }

      columnValueMap.put(detailArr[0], columnArr[columnIndex]);
    }

    return columnValueMap;
  }

  /**
   * Read excel for segment.
   *
   * @param fo the fo
   * @param fileName the file name
   * @param segmentSeq the segment seq
   * @return the linked hash map
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   */
  public static LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject> readExcelForSegment(
      FileInfoObject fo, String fileName, HashMap<?, ?> segmentSeq)
      throws FileNotFoundException, IOException, SQLException {
    LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject> fileInfoMap =
        new LinkedHashMap<LinkedHashMap<String, String>, FileInfoObject>();
    FileInputStream myxls = null;
    String KeyString = null;

    try {
      myxls = new FileInputStream(fileName);
      Workbook wb = WorkbookFactory.create(myxls);
      int numSheets = wb.getNumberOfSheets();
      int sheetCnt = 0;

      while (sheetCnt < numSheets) {
        Sheet sheet = wb.getSheetAt(sheetCnt);
        Iterator<?> rowIterator = sheet.rowIterator();
        int rowCnt_temp = 0;

        while (true) {
          while (rowIterator.hasNext()) {
            FileInfoObject fio =
                new FileInfoObject(fo.getHeader(), (HashMap<?, ?>) null, fo.getDefn());
            fo.setDetail((HashMap<?, ?>) null);
            String[] columnArr = null;
            LinkedHashMap<String, String> columnValueMap = new LinkedHashMap<String, String>();
            Row row = (Row) rowIterator.next();
            if (rowCnt_temp == 0 && !"0".equals(fo.getColHdrOnFirstLine())) {
              ++rowCnt_temp;
            } else {
              columnArr = new String[row.getLastCellNum() + 1];

              for (int columnCnt = 1; columnCnt < columnArr.length; ++columnCnt) {
                columnArr[columnCnt] = "";
              }

              row.cellIterator();
              String segmentId = null;
              int columnIndex = 1;
              HashMap<?, ?> l_DetInfo = null;

              for (int cellCnt = 0; cellCnt < row.getLastCellNum(); ++cellCnt) {
                Cell cell = row.getCell((short) cellCnt);
                if (cell != null) {
                  if (cell.getCellType().equals(CellType.NUMERIC)) {
                    columnArr[columnIndex] =
                        (new Double(cell.getNumericCellValue())).intValue() + "~";
                  } else if (cell.getCellType().equals(CellType.STRING)) {
                    columnArr[columnIndex] = cell.getStringCellValue() + "~";
                  } else if (cell.getCellType().equals(CellType.BLANK)) {
                    columnArr[columnIndex] = "null~";
                  } else {
                    columnArr[columnIndex] = cell.getStringCellValue() + "~";
                  }

                  segmentId =
                      columnArr[columnIndex].substring(0, columnArr[columnIndex].length() - 1);
                  if (segmentSeq.containsKey(segmentId)) {
                    ((String) segmentSeq.get(segmentId)).split("~");
                    // l_DetInfo = getIfDetInfo(context, fio, segmentTable, currUser);
                    fio.setDetail(l_DetInfo);
                  }
                } else {
                  columnArr[columnIndex] = "null~";
                }

                ++columnIndex;
              }

              if (null == KeyString) {
                // generate excel
              }

              boolean hasKeyString = false;

              for (int arrIndex = 1; arrIndex <= fio.getDetail().size(); ++arrIndex) {
                String[] detailArr = fio.getDetails(arrIndex);

                try {
                  if ("KEYSTRING".equals(fio.getDetails(arrIndex)[0])) {
                    String ColArrValue = columnArr[arrIndex];
                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue.substring(0, ColArrValue.length() - 1);
                    }

                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue + "null";
                    }

                    String[] ColArrValues = ColArrValue.split("~");
                    String temp = KeyString;

                    for (int i = 1; i < ColArrValues.length; ++i) {
                      temp = temp + "~" + KeyString;
                    }

                    columnValueMap.put(detailArr[0], temp);
                    hasKeyString = true;
                  } else {
                    int colIndex = arrIndex;
                    if (hasKeyString) {
                      colIndex = arrIndex - 1;
                    }

                    if ("NUMBER".equals(detailArr[1])) {
                      columnArr[colIndex] =
                          columnArr[colIndex].replaceAll(fio.getDigitGrupSymbol(), "");
                    }

                    if (columnArr[colIndex].endsWith("~")) {
                      columnArr[colIndex] =
                          columnArr[colIndex].substring(0, columnArr[colIndex].length() - 1);
                    }

                    if (columnArr[colIndex].endsWith("~")) {
                      columnArr[colIndex] = columnArr[colIndex] + "null";
                    }

                    columnValueMap.put(detailArr[0], columnArr[colIndex]);
                  }
                } catch (ArrayIndexOutOfBoundsException var40) {
                  columnValueMap.put(detailArr[0], "null");
                }
              }

              fileInfoMap.put(columnValueMap, fio);
            }
          }

          ++sheetCnt;
          break;
        }
      }
    } catch (IndexOutOfBoundsException var41) {
      // handle or throw exception
    } catch (FileNotFoundException var42) {
      // handle or throw exception
    } catch (IOException var43) {
      // handle or throw exception
    } finally {
      try {
        if (myxls != null) {
          myxls.close();
        }
      } catch (IOException var39) {
      }
    }

    return fileInfoMap;
  }

  /**
   * Read excel.
   *
   * @param fo the fo
   * @param fileName the file name
   * @return the linked hash map
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static LinkedHashMap<String, String> readExcel(FileInfoObject fo, String fileName)
      throws FileNotFoundException, IOException {
    String[] columnArr = null;
    FileInputStream myxls = null;
    Boolean hasKeyString = false;
    LinkedHashMap<String, String> columnValueMap = new LinkedHashMap<String, String>();
    String KeyString = null; // generate key string

    try {
      myxls = new FileInputStream(fileName);
      Workbook wb = WorkbookFactory.create(myxls);
      int numSheets = wb.getNumberOfSheets();

      label297:
      for (int sheetCnt = 0; sheetCnt < numSheets; ++sheetCnt) {
        Sheet sheet = wb.getSheetAt(sheetCnt);
        Iterator<?> rowIterator = sheet.rowIterator();
        int rowIndex = 0;
        int rowCnt_temp = 0;

        while (true) {
          while (true) {
            if (!rowIterator.hasNext()) {
              continue label297;
            }

            Row row = (Row) rowIterator.next();
            if (rowCnt_temp == 0 && !"0".equals(fo.getColHdrOnFirstLine())) {
              ++rowCnt_temp;
            } else {
              if (rowIndex == 0) {
                columnArr = new String[row.getLastCellNum() + 1];
                columnArr = new String[fo.getDetail().size() + 1];

                for (int columnCnt = 0; columnCnt < columnArr.length; ++columnCnt) {
                  columnArr[columnCnt] = "";
                }
              }

              row.cellIterator();
              int columnIndex = 1;

              int arrIndex;
              for (arrIndex = 0; arrIndex < row.getLastCellNum(); ++arrIndex) {
                Cell cell = row.getCell((short) arrIndex);
                if (cell != null) {
                  if (cell.getCellType().equals(CellType.NUMERIC)) {
                    columnArr[columnIndex] =
                        columnArr[columnIndex] + String.valueOf(cell.getNumericCellValue()) + "~";
                  } else if (cell.getCellType().equals(CellType.STRING)) {
                    columnArr[columnIndex] =
                        columnArr[columnIndex] + cell.getStringCellValue() + "~";
                  } else if (cell.getCellType().equals(CellType.BLANK)) {
                    columnArr[columnIndex] = columnArr[columnIndex] + "null~";
                  } else {
                    columnArr[columnIndex] =
                        columnArr[columnIndex] + cell.getStringCellValue() + "~";
                  }
                } else {
                  columnArr[columnIndex] = columnArr[columnIndex] + "null~";
                }

                ++columnIndex;
              }

              for (arrIndex = 1; arrIndex <= fo.getDetail().size(); ++arrIndex) {
                String[] detailArr = fo.getDetails(arrIndex);

                try {
                  if ("KEYSTRING".equals(fo.getDetails(arrIndex)[0])) {
                    String ColArrValue = columnArr[arrIndex];
                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue.substring(0, ColArrValue.length() - 1);
                    }

                    if (ColArrValue.endsWith("~")) {
                      ColArrValue = ColArrValue + "null";
                    }

                    String[] ColArrValues = ColArrValue.split("~");
                    String temp = KeyString;

                    for (int i = 1; i < ColArrValues.length; ++i) {
                      temp = temp + "~" + KeyString;
                    }

                    columnArr[arrIndex] = columnArr[arrIndex] + KeyString + "~";
                    hasKeyString = true;
                  } else if (hasKeyString) {
                  }
                } catch (ArrayIndexOutOfBoundsException var34) {
                  columnValueMap.put(detailArr[0], "null");
                }
              }

              ++rowIndex;
            }
          }
        }
      }
    } catch (IndexOutOfBoundsException var35) {
      // handle or throw exception
    } catch (FileNotFoundException var36) {
      // handle or throw exception
    } catch (IOException var37) {
      // handle or throw exception
    } finally {
      try {
        if (myxls != null) {
          myxls.close();
        }
      } catch (IOException var33) {
      }
    }

    for (int columnIndex = 1; columnIndex <= fo.getDetail().size(); ++columnIndex) {
      String[] detailArr = fo.getDetails(columnIndex);
      if ("NUMBER".equals(detailArr[1])) {
        columnArr[columnIndex] = columnArr[columnIndex].replaceAll(fo.getDigitGrupSymbol(), "");
      }

      if (columnArr[columnIndex].endsWith("~")) {
        columnArr[columnIndex] =
            columnArr[columnIndex].substring(0, columnArr[columnIndex].length() - 1);
      }

      if (columnArr[columnIndex].endsWith("~")) {
        columnArr[columnIndex] = columnArr[columnIndex] + "null";
      }

      columnValueMap.put(detailArr[0], columnArr[columnIndex]);
    }

    return columnValueMap;
  }

  /**
   * Write excel.
   *
   * @param fo the fo
   * @param data the data
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeExcel(FileInfoObject fo, LinkedList<?> data, String fileName)
      throws IOException {
    SimpleDateFormat sdf = null;
    String now = null;
    FileOutputStream fileout = null;
    int noOfColumns = fo.getDetail().size();

    try {
      if (fileName.contains("*.")) {
        sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        now = sdf.format(Calendar.getInstance().getTime());
        fileName =
            fileName.substring(0, fileName.indexOf("*"))
                + now
                + fileName.substring(fileName.indexOf("*") + 1);
        fileout = new FileOutputStream(fileName);
      } else {
        fileout = new FileOutputStream(fileName);
      }

      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet();
      int rowCnt = 0;
      if (!fo.getColHdrOnFirstLine().equals("0")) {
        HSSFRow row = sheet.createRow((short) rowCnt);

        for (int colCnt = 0; colCnt < noOfColumns; ++colCnt) {
          HSSFCell cell = row.createCell(colCnt);
          cell.setCellValue(fo.getDetails(colCnt + 1)[0]);
        }

        ++rowCnt;
      }

      for (ListIterator<?> dataIterator = data.listIterator(); dataIterator.hasNext(); ++rowCnt) {
        HSSFRow row = sheet.createRow((short) rowCnt);
        LinkedList<?> rowData = (LinkedList<?>) dataIterator.next();
        ListIterator<?> rowIterator = rowData.listIterator();

        while (rowIterator.hasNext()) {
          HSSFCell cell = row.createCell(rowIterator.nextIndex());
          cell.setCellType(CellType.BLANK);
          String columnData = (String) rowIterator.next();
          Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
          if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
              && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {}

          if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
              && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
              && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
              && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
            if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
              if ("".equals(columnData.trim())) {
                cell.setCellValue(columnData);
              } else {
                columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                cell.setCellValue(columnData);
              }
            } else {
              cell.setCellValue(columnData);
            }
          } else {
            cell.setCellValue(columnData);
          }
        }
      }

      wb.write(fileout);
      wb.close();
    } catch (IOException var26) {
      // handle or throw exception
    } catch (Exception var27) {
      // handle or throw exception
    } finally {
      try {
        if (fileout != null) {
          fileout.close();
        }
      } catch (IOException var25) {
      }
    }
  }

  /**
   * Write flat file.
   *
   * @param fo the fo
   * @param data the data
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeFlatFile(FileInfoObject fo, LinkedList<?> data, String fileName)
      throws IOException {
    SimpleDateFormat sdf = null;
    String now = null;
    BufferedWriter outfile = null;
    int noOfColumns = fo.getDetail().size();
    StringBuffer colHead = new StringBuffer();
    String delimiter = "!@#$".equals(fo.getFieldDelimiter()) ? "~" : fo.getFieldDelimiter();

    try {
      if (fileName.contains("*.")) {
        sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        now = sdf.format(Calendar.getInstance().getTime());
        fileName =
            fileName.substring(0, fileName.indexOf("*"))
                + now
                + fileName.substring(fileName.indexOf("*") + 1);
        outfile = new BufferedWriter(new FileWriter(fileName));
      } else {
        outfile = new BufferedWriter(new FileWriter(fileName));
      }

      if (!fo.getColHdrOnFirstLine().equals("0")) {
        for (int colCnt = 1; colCnt <= noOfColumns; ++colCnt) {
          String columnHeading = fo.getDetails(colCnt)[8];
          StringBuffer colDataBuffer = new StringBuffer(columnHeading);
          if ("D".equals(fo.getAsciiFileFormat())) {
            colHead.append(columnHeading);
            if (colCnt != noOfColumns) {
              colHead.append(delimiter);
            }
          } else {
            int columnLength = Integer.parseInt(fo.getDetails(colCnt)[2]);
            if (columnHeading.length() >= columnLength) {
              columnHeading = columnHeading + " ";
            }

            if (columnLength > columnHeading.length()) {
              colDataBuffer =
                  colDataBuffer.insert(
                      columnHeading.length(),
                      createEmptyStr(columnLength - columnHeading.length(), "S"));
              outfile.write(colDataBuffer.toString());
            } else {
              outfile.write(columnHeading);
            }
          }
        }

        colHead.append(System.getProperty("line.separator"));
      }

      outfile.write(colHead.toString());
      ListIterator<?> dataIterator = data.listIterator();

      while (dataIterator.hasNext()) {
        LinkedList<?> rowData = (LinkedList<?>) dataIterator.next();
        ListIterator<?> rowIterator = rowData.listIterator();

        while (true) {
          while (rowIterator.hasNext()) {
            String columnData = (String) rowIterator.next();
            StringBuffer colDataBuffer = new StringBuffer();
            int columnLength;
            if ("D".equals(fo.getAsciiFileFormat())) {
              columnLength = Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
              if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {
                ++columnLength;
              }

              if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("".equals(columnData.trim())) {
                    outfile.write(columnData);
                  } else {
                    columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                    outfile.write(columnData);
                  }
                }
              } else {
                outfile.write(columnData);
              }

              if (rowIterator.nextIndex() != rowData.size()) {
                outfile.write(delimiter);
              }
            } else {
              columnLength = Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
              if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {
                ++columnLength;
              }

              if (columnLength <= columnData.length()) {
                if (columnLength < columnData.length()) {
                  outfile.write(columnData.substring(0, columnLength));
                } else if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                    if ("".equals(columnData.trim())) {
                      columnData = columnData.trim();
                      outfile.write(columnData);
                    } else {
                      columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                      outfile.write(columnData);
                    }
                  }
                } else {
                  outfile.write(columnData);
                }
              } else if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("".equals(columnData.trim())) {
                    columnData = columnData.trim();
                    colDataBuffer = colDataBuffer.insert(0, createEmptyStr(columnLength, "S"));
                    outfile.write(colDataBuffer.toString());
                  } else {
                    try {
                      if (Long.parseLong(columnData) >= 0L) {
                        columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                        colDataBuffer =
                            colDataBuffer
                                .insert(0, createEmptyStr(columnLength - columnData.length(), "N"))
                                .append(columnData);
                        outfile.write(colDataBuffer.toString());
                      } else {
                        columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                        colDataBuffer =
                            colDataBuffer
                                .insert(0, createEmptyStr(columnLength - columnData.length(), "N"))
                                .append("0")
                                .append(columnData.substring(1));
                        colDataBuffer.replace(0, 1, "-");
                        outfile.write(colDataBuffer.toString());
                      }
                    } catch (NumberFormatException var26) {
                      if (Double.parseDouble(columnData) >= 0.0D) {
                        columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                        colDataBuffer =
                            colDataBuffer
                                .insert(0, createEmptyStr(columnLength - columnData.length(), "N"))
                                .append(columnData);
                        outfile.write(colDataBuffer.toString());
                      } else {
                        columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                        colDataBuffer =
                            colDataBuffer
                                .insert(0, createEmptyStr(columnLength - columnData.length(), "N"))
                                .append("0")
                                .append(columnData.substring(1));
                        colDataBuffer.replace(0, 1, "-");
                        outfile.write(colDataBuffer.toString());
                      }
                    }
                  }
                }
              } else {
                colDataBuffer =
                    colDataBuffer
                        .append(columnData)
                        .insert(
                            columnData.length(),
                            createEmptyStr(columnLength - columnData.length(), "S"));
                outfile.write(colDataBuffer.toString());
              }
            }
          }

          outfile.write(System.getProperty("line.separator"));
          break;
        }
      }
    } catch (IOException var27) {
      // handle or throw exception
    } catch (Exception var28) {
      // handle or throw exception
    } finally {
      try {
        if (outfile != null) {
          outfile.close();
        }
      } catch (IOException var25) {
      }
    }
  }

  /**
   * Write flat file with segments.
   *
   * @param fileInfoList the file info list
   * @param segmentList the segment list
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeFlatFileWithSegments(
      LinkedList<?> fileInfoList, LinkedList<?> segmentList, String fileName) throws IOException {
    SimpleDateFormat sdf = null;
    String now = null;
    BufferedWriter outfile = null;
    FileInfoObject fio = (FileInfoObject) fileInfoList.get(0);
    FileInfoObject fo = null;
    String delimiter = "!@#$".equals(fio.getFieldDelimiter()) ? "~" : fio.getFieldDelimiter();

    try {
      if (fileName.contains("*.")) {
        sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        now = sdf.format(Calendar.getInstance().getTime());
        fileName =
            fileName.substring(0, fileName.indexOf("*"))
                + now
                + fileName.substring(fileName.indexOf("*") + 1);
        outfile = new BufferedWriter(new FileWriter(fileName));
      } else {
        outfile = new BufferedWriter(new FileWriter(fileName));
      }

      ListIterator<?> segmentIterator = segmentList.listIterator();
      ListIterator<?> fileInfoIterator = fileInfoList.listIterator();
      Vector<String> header = new Vector<String>(1, 1);
      Vector<String> detail = new Vector<String>(1, 1);
      String rowdata = null;
      int segmentno = 1;
      String columnData;
      for (int m = 0; segmentIterator.hasNext(); ++segmentno) {
        LinkedList<?> segmentData = (LinkedList<?>) segmentIterator.next();
        fo = (FileInfoObject) fileInfoIterator.next();
        ListIterator<?> segmentDataIterator = segmentData.listIterator();

        for (int i = 0; segmentDataIterator.hasNext(); ++i) {
          LinkedList<?> rowData = (LinkedList<?>) segmentDataIterator.next();
          ListIterator<?> rowIterator = rowData.listIterator();

          for (int k = 0; rowIterator.hasNext(); ++k) {
            columnData = (String) rowIterator.next();
            StringBuffer colDataBuffer = new StringBuffer();
            int columnLength;
            if ("D".equals(fo.getAsciiFileFormat())) {
              columnLength = Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
              if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {
                ++columnLength;
              }

              if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("".equals(columnData.trim())) {
                    if (k == 0) {
                      rowdata = columnData;
                    } else {
                      rowdata = rowdata + columnData;
                    }
                  } else {
                    columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                    if (k == 0) {
                      rowdata = columnData;
                    } else {
                      rowdata = rowdata + columnData;
                    }
                  }
                }
              } else if (k == 0) {
                rowdata = columnData;
              } else {
                rowdata = rowdata + columnData;
              }

              if (rowIterator.nextIndex() != rowData.size()) {
                rowdata = rowdata + delimiter;
              }
            } else {
              columnLength = Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
              if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {
                ++columnLength;
              }

              if (columnLength > columnData.length()) {
                if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                    && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                    if ("".equals(columnData.trim())) {
                      columnData = columnData.trim();
                      colDataBuffer = colDataBuffer.insert(0, createEmptyStr(columnLength, "S"));
                      outfile.write(colDataBuffer.toString());
                    } else {
                      try {
                        if (Long.parseLong(columnData) >= 0L) {
                          columnData =
                              formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                          colDataBuffer =
                              colDataBuffer
                                  .insert(
                                      0, createEmptyStr(columnLength - columnData.length(), "N"))
                                  .append(columnData);
                          outfile.write(colDataBuffer.toString());
                        } else {
                          columnData =
                              formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                          colDataBuffer =
                              colDataBuffer
                                  .insert(
                                      0, createEmptyStr(columnLength - columnData.length(), "N"))
                                  .append("0")
                                  .append(columnData.substring(1));
                          colDataBuffer.replace(0, 1, "-");
                          outfile.write(colDataBuffer.toString());
                        }
                      } catch (NumberFormatException var40) {
                        if (Double.parseDouble(columnData) >= 0.0D) {
                          columnData =
                              formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                          colDataBuffer =
                              colDataBuffer
                                  .insert(
                                      0, createEmptyStr(columnLength - columnData.length(), "N"))
                                  .append(columnData);
                          outfile.write(colDataBuffer.toString());
                        } else {
                          columnData =
                              formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                          colDataBuffer =
                              colDataBuffer
                                  .insert(
                                      0, createEmptyStr(columnLength - columnData.length(), "N"))
                                  .append("0")
                                  .append(columnData.substring(1));
                          colDataBuffer.replace(0, 1, "-");
                          outfile.write(colDataBuffer.toString());
                        }
                      }
                    }
                  }
                } else {
                  colDataBuffer =
                      colDataBuffer
                          .append(columnData)
                          .insert(
                              columnData.length(),
                              createEmptyStr(columnLength - columnData.length(), "S"));
                  outfile.write(colDataBuffer.toString());
                }
              } else if (columnLength < columnData.length()) {
                outfile.write(columnData.substring(0, columnLength));
              } else if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("".equals(columnData.trim())) {
                    columnData = columnData.trim();
                    outfile.write(columnData);
                  } else {
                    columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                    outfile.write(columnData);
                  }
                }
              } else {
                outfile.write(columnData);
              }
            }
          }

          if ("F".equals(fo.getAsciiFileFormat())) {
            outfile.write(System.getProperty("line.separator"));
          }

          if (segmentno == 1) {
            header.add(i, rowdata);
          } else {
            detail.add(m, rowdata);
            ++m;
          }
        }
      }

      if ("D".equals(fo.getAsciiFileFormat())) {
        Enumeration<String> hdEnum = header.elements();
        Enumeration<String> dtlEnum = detail.elements();
        String tempstr1 = null;

        while (hdEnum.hasMoreElements()) {
          columnData = (String) hdEnum.nextElement();
          outfile.write(columnData);
          outfile.write(System.getProperty("line.separator"));
        }

        while (dtlEnum.hasMoreElements()) {
          tempstr1 = (String) dtlEnum.nextElement();
          outfile.write(tempstr1);
          outfile.write(System.getProperty("line.separator"));
        }
      }
    } catch (IOException var41) {

    } catch (Exception var42) {

    } finally {
      try {
        if (outfile != null) {
          outfile.close();
        }
      } catch (IOException var39) {
      }
    }
  }

  /**
   * Write excel with segments.
   *
   * @param fileInfoList the file info list
   * @param segmentList the segment list
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeExcelWithSegments(
      LinkedList<?> fileInfoList, LinkedList<?> segmentList, String fileName) throws IOException {
    SimpleDateFormat sdf = null;
    String now = null;
    FileOutputStream fileout = null;

    try {
      if (fileName.contains("*.")) {
        sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        now = sdf.format(Calendar.getInstance().getTime());
        fileName =
            fileName.substring(0, fileName.indexOf("*"))
                + now
                + fileName.substring(fileName.indexOf("*") + 1);
        fileout = new FileOutputStream(fileName);
      } else {
        fileout = new FileOutputStream(fileName);
      }

      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet();
      int rowCnt = 0;
      ListIterator<?> segmentIterator = segmentList.listIterator();
      ListIterator<?> fileInfoIterator = fileInfoList.listIterator();

      while (segmentIterator.hasNext()) {
        LinkedList<?> segmentData = (LinkedList<?>) segmentIterator.next();
        FileInfoObject fo = (FileInfoObject) fileInfoIterator.next();

        label164:
        for (ListIterator<?> segmentDataIterator = segmentData.listIterator();
            segmentDataIterator.hasNext();
            ++rowCnt) {
          HSSFRow row = sheet.createRow((short) rowCnt);
          LinkedList<?> rowData = (LinkedList<?>) segmentDataIterator.next();
          ListIterator<?> rowIterator = rowData.listIterator();

          while (true) {
            while (true) {
              if (!rowIterator.hasNext()) {
                continue label164;
              }

              HSSFCell cell = row.createCell(rowIterator.nextIndex());
              CellType cp = CellType._NONE;
              cell.setCellType(cp);
              String columnData = (String) rowIterator.next();
              Integer.parseInt(fo.getDetails(rowIterator.previousIndex() + 1)[2]);
              if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"0".equals(fo.getDetails(rowIterator.previousIndex() + 1)[6])) {}

              if (!"VARCHAR2".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"DATE".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"VARCHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])
                  && !"CHAR".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                if ("NUMBER".equals(fo.getDetails(rowIterator.previousIndex() + 1)[1])) {
                  if ("".equals(columnData.trim())) {
                    cell.setCellValue(columnData);
                  } else {
                    columnData = formatNumber(fo, columnData, rowIterator.previousIndex() + 1);
                    cell.setCellValue(columnData);
                  }
                } else {
                  cell.setCellValue(columnData);
                }
              } else {
                cell.setCellValue(columnData);
              }
            }
          }
        }
      }

      wb.write(fileout);
      wb.close();
    } catch (IOException var30) {
      // handle or throw exception
    } catch (Exception var31) {
      // handle or throw exception
    } finally {
      try {
        if (fileout != null) {
          fileout.close();
        }
      } catch (IOException var29) {
      }
    }
  }

  /**
   * Creates the empty str.
   *
   * @param len the len
   * @param type the type
   * @return the string
   */
  public static String createEmptyStr(int len, String type) {
    char[] ch = new char[len];

    for (int i = 0; i < ch.length; ++i) {
      if ("S".equals(type)) {
        ch[i] = ' ';
      } else {
        ch[i] = '0';
      }
    }

    String returnStr = new String(ch);
    return returnStr;
  }

  /**
   * Move file to archive.
   *
   * @param fo the fo
   * @param srcFile the src file
   * @param archiveFile the archive file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void moveFileToArchive(FileInfoObject fo, String srcFile, String archiveFile)
      throws IOException {
    try {
      String archiveDir = fo.getArchDir();
      File source = new File(srcFile);
      File destination = new File(archiveDir);
      boolean result = false;
      if (!destination.exists()) {
        destination.mkdir();
        copyFile(source, destination, archiveFile);
        result = true;
      } else {
        copyFile(source, destination, archiveFile);
        result = true;
      }

      if (result && !delete(source)) {
        throw new IOException("Unable to delete original folder");
      }
    } catch (FileNotFoundException var9) {
      // handle or throw exception
    } catch (IOException var10) {
      // handle or throw exception
    }
  }

  /**
   * Copy file.
   *
   * @param source the source
   * @param dest the dest
   * @param archFile the arch file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void copyFile(File source, File dest, String archFile) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    String destinationArchiveFile = null;

    try {
      if (!archFile.contains("*.")) {
        destinationArchiveFile =
            dest.toString().contains("\\") ? dest + "\\" + archFile : dest + "/" + archFile;
      } else {
        // String sourceFileString = source.toString();
        String temp = source.getName();
        destinationArchiveFile =
            dest.toString().contains("\\") ? dest + "\\" + temp : dest + "/" + temp;
      }

      File destArchFile = new File(destinationArchiveFile);
      if (dest.isDirectory()) {
        destArchFile.createNewFile();
      }

      in = new FileInputStream(source);
      out = new FileOutputStream(destArchFile);
      byte[] buf = new byte[1024];

      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
    } catch (FileNotFoundException var22) {
      // handle or throw exception
    } catch (IOException var23) {
      // handle or throw exception
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException var21) {
      }

      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException var20) {
      }
    }
  }

  /**
   * Delete.
   *
   * @param resource the resource
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static boolean delete(File resource) throws IOException {
    try {
      if (resource.isDirectory()) {
        File[] childFiles = resource.listFiles();

        for (int i = 0; i < childFiles.length - 1; ++i) {
          delete(childFiles[i]);
        }
      }

      return resource.delete();
    } catch (IOException var5) {
      // handle or throw exception
      return false;
    } catch (Exception var6) {
      // handle or throw exception
      return false;
    }
  }

  /**
   * Format number.
   *
   * @param fo the fo
   * @param columnData the column data
   * @param fileColSeq the file col seq
   * @return the string
   */
  public static String formatNumber(FileInfoObject fo, String columnData, int fileColSeq) {
    DecimalFormat df = new DecimalFormat();
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    String formattedNum = null;
    char lDigitGroup;
    char lDecimalSymbol;
    if (!"".equals(fo.getDecimalSymbol())
        && !"null".equals(fo.getDecimalSymbol())
        && !"F".equals(fo.getDecimalSymbol())) {
      lDecimalSymbol = fo.getDecimalSymbol().charAt(0);
    } else {
      lDecimalSymbol = '#';
    }

    if (!"".equals(fo.getDigitGrupSymbol())
        && !"null".equals(fo.getDigitGrupSymbol())
        && !"F".equals(fo.getDigitGrupSymbol())) {
      lDigitGroup = fo.getDigitGrupSymbol().charAt(0);
    } else {
      lDigitGroup = '#';
    }

    dfs.setGroupingSeparator(lDigitGroup);
    if ("0".equals(fo.getDetails(fileColSeq)[6])) {
      df.setDecimalSeparatorAlwaysShown(false);
      df.setDecimalFormatSymbols(dfs);
      formattedNum = df.format(Long.parseLong(columnData));
    } else {
      df.setDecimalSeparatorAlwaysShown(true);
      df.setMinimumFractionDigits(Integer.parseInt(fo.getDetails(fileColSeq)[6]));
      df.setMaximumFractionDigits(Integer.parseInt(fo.getDetails(fileColSeq)[6]));
      dfs.setDecimalSeparator(lDecimalSymbol);
      df.setDecimalFormatSymbols(dfs);
      formattedNum = df.format(Double.parseDouble(columnData));
    }

    return formattedNum.replaceAll("#", "");
  }

  /**
   * Move file to archive 1.
   *
   * @param fo the fo
   * @param srcFile the src file
   * @param archiveFile the archive file
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws Exception the exception
   */
  public static void moveFileToArchive1(FileInfoObject fo, String srcFile, String archiveFile)
      throws IOException, Exception {

    try {
      String archiveDir = fo.getArchDir();
      File source = new File(srcFile);
      File destination = new File(archiveDir);
      boolean result = false;
      if (!destination.exists()) {
        destination.mkdir();
        copyFile(source, destination, archiveFile);
        result = true;
      } else {
        copyFile(source, destination, archiveFile);
        result = true;
      }

      if (result) {
        if (!delete(source)) {
          throw new IOException("Unable to delete original folder");
        }
      } else if (!result) {
        System.exit(0);
      }

    } catch (FileNotFoundException var8) {
      throw new Exception();
    } catch (IOException var9) {
      throw new Exception();
    }
  }

  /**
   * Copy file 1.
   *
   * @param source the source
   * @param dest the dest
   * @param archFile the arch file
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws Exception the exception
   */
  public static void copyFile1(File source, File dest, String archFile)
      throws IOException, Exception {
    InputStream in = null;
    OutputStream out = null;
    String destinationArchiveFile = null;

    try {
      if (!archFile.contains("*.")) {
        destinationArchiveFile =
            dest.toString().contains("\\") ? dest + "\\" + archFile : dest + "/" + archFile;
      } else {
        String sourceFileString = source.toString();
        String temp = sourceFileString.substring(sourceFileString.lastIndexOf("\\") + 1);
        destinationArchiveFile =
            dest.toString().contains("\\") ? dest + "\\" + temp : dest + "/" + temp;
      }

      File destArchFile = new File(destinationArchiveFile);
      if (dest.isDirectory()) {
        destArchFile.createNewFile();
      }

      in = new FileInputStream(source);
      out = new FileOutputStream(destArchFile);
      byte[] buf = new byte[1024];

      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
    } catch (FileNotFoundException var21) {
      throw new Exception();
    } catch (IOException var22) {
      throw new Exception();
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException var20) {
      }

      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException var19) {
      }
    }
  }

  /**
   * Delete 1.
   *
   * @param resource the resource
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws Exception the exception
   */
  public static boolean delete1(File resource) throws IOException, Exception {

    try {
      if (resource.isDirectory()) {
        File[] childFiles = resource.listFiles();
        File[] var3 = childFiles;
        int var4 = childFiles.length;

        for (int var5 = 0; var5 < var4; ++var5) {
          File child = var3[var5];
          delete(child);
        }
      }

      return resource.delete();
    } catch (IOException var7) {
      throw new Exception();
    } catch (Exception var8) {
      throw new Exception();
    }
  }
}
