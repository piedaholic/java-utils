package com.utilities.datatypes;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/** The Class TwoDSort. */
public class TwoDSort {

  /** The Class ColumnSorter. */
  public static class ColumnSorter implements Comparator<Object> {

    /** The column index. */
    int columnIndex;

    /** The ascndg. */
    int ascndg;

    /** The data type. */
    String dataType;

    /**
     * Instantiates a new column sorter.
     *
     * @param columnIndex the column index
     * @param ascndg the ascndg
     * @param dataType the data type
     */
    public ColumnSorter(int columnIndex, int ascndg, String dataType) {
      this.ascndg = ascndg;
      this.columnIndex = columnIndex;
      this.dataType = dataType.toUpperCase();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2) {
      try {
        BigDecimal gndata1;
        BigDecimal gndata2;
        String sdt2;
        Date dt2;
        String sdt1;
        Date dt1;
        if (this.ascndg == 1) {
          if (this.dataType.equals("STRING")) {
            return ((String[]) ((String[]) o1))
                [this.columnIndex].compareTo(((String[]) ((String[]) o2))[this.columnIndex]);
          } else if (this.dataType.equals("DATE")) {
            sdt1 = ((String[]) ((String[]) o1))[this.columnIndex];
            dt1 = Date.valueOf(sdt1);
            sdt2 = ((String[]) ((String[]) o2))[this.columnIndex];
            dt2 = Date.valueOf(sdt2);
            return dt1.compareTo(dt2);
          } else if (this.dataType.equals("INT")) {
            return Integer.parseInt(((String[]) ((String[]) o1))[this.columnIndex])
                - Integer.parseInt(((String[]) ((String[]) o2))[this.columnIndex]);
          } else {
            gndata1 = new BigDecimal(((String[]) ((String[]) o1))[this.columnIndex]);
            gndata2 = new BigDecimal(((String[]) ((String[]) o2))[this.columnIndex]);
            return gndata1.compareTo(gndata2);
          }
        } else if (this.dataType.equals("STRING")) {
          return ((String[]) ((String[]) o2))
              [this.columnIndex].compareTo(((String[]) ((String[]) o1))[this.columnIndex]);
        } else if (this.dataType.equals("DATE")) {
          sdt1 = ((String[]) ((String[]) o1))[this.columnIndex];
          dt1 = Date.valueOf(sdt1);
          sdt2 = ((String[]) ((String[]) o2))[this.columnIndex];
          dt2 = Date.valueOf(sdt2);
          return dt2.compareTo(dt1);
        } else if (this.dataType.equals("INT")) {
          return Integer.parseInt(((String[]) ((String[]) o2))[this.columnIndex])
              - Integer.parseInt(((String[]) ((String[]) o1))[this.columnIndex]);
        } else {
          gndata1 = new BigDecimal(((String[]) ((String[]) o1))[this.columnIndex]);
          gndata2 = new BigDecimal(((String[]) ((String[]) o2))[this.columnIndex]);
          return gndata2.compareTo(gndata1);
        }
      } catch (Exception var7) {
        return 0;
      }
    }
  }
}
