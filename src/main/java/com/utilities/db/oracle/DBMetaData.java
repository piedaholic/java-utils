package com.utilities.db.oracle;

import com.datastructures.array.ArrayUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/** The Class DBMetaData. */
public class DBMetaData {

  /** The select pk cols. */
  private static String SELECT_PK_COLS =
      "SELECT /*+ result_cache */ column_list FROM "
          + "USER_CONS_COLUMNS WHERE "
          + "CONSTRAINT_NAME IN (SELECT CONSTRAINT_NAME FROM USER_CONSTRAINTS WHERE CONSTRAINT_TYPE = 'P')"
          + " AND table_name = ? ";

  /** The select user synonyms. */
  private static String SELECT_USER_SYNONYMS =
      "SELECT TABLE_NAME FROM user_synonyms WHERE SYNONYM_name = ? ";

  /** The select user tab columns. */
  private static String SELECT_USER_TAB_COLUMNS =
      "SELECT DISTINCT COLUMN_NAME,DATA_TYPE,NULLABLE FROM ALL_TAB_COLS WHERE table_name = ? ";

  /** The dbmt data. */
  private static DBMetaData dbmtData = new DBMetaData();

  /** The hm data type. */
  private static Map<String, HashMap<String, Integer>> hmDataType;

  /** The hm primary keys. */
  private static Map<String, String[]> hmPrimaryKeys;

  /** The hm not nulls. */
  private static Map<String, StringBuffer> hmNotNulls;

  /** The hm non primary columns. */
  private static Map<?, ?> hmNonPrimaryColumns;

  /** The hm table columns. */
  private static Map<?, ?> hmTableColumns;

  /** Instantiates a new DB meta data. */
  private DBMetaData() {
    hmDataType = new HashMap<String, HashMap<String, Integer>>(20);
    hmNotNulls = new HashMap<String, StringBuffer>(20);
    hmPrimaryKeys = new HashMap<String, String[]>(20);
    hmNonPrimaryColumns = new HashMap<Object, Object>(20);
    hmTableColumns = new HashMap<Object, Object>(20);
  }

  /**
   * Gets the single instance of DBMetaData.
   *
   * @return single instance of DBMetaData
   */
  public static DBMetaData getInstance() {
    return dbmtData;
  }

  /**
   * Gets the data type.
   *
   * @param con the con
   * @param pTableName the table name
   * @param pFieldName the field name
   * @return the data type
   * @throws SQLException the SQL exception
   */
  public int getDataType(Connection con, String pTableName, String pFieldName) throws SQLException {
    HashMap<String, Integer> hmTypes = null;
    hmTypes = (HashMap<String, Integer>) hmDataType.get(pTableName);
    if (hmTypes == null || hmTypes.size() == 0) {
      hmTypes = new HashMap<String, Integer>(20);
      StringBuffer strbfrNotNullColumns = new StringBuffer();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      PreparedStatement pstmnt = null;
      String tablename = pTableName;
      ResultSet res = null;

      try {
        pstmnt = con.prepareStatement(SELECT_USER_SYNONYMS);
        pstmnt.setString(1, pTableName);

        for (res = pstmnt.executeQuery(); res.next(); tablename = res.getString("TABLE_NAME")) {}

        pstmt = con.prepareStatement(SELECT_USER_TAB_COLUMNS);
        pstmt.setString(1, tablename);
        rs = pstmt.executeQuery();
        String columnName = "";
        String columnType = "";
        String nullable = "Y";

        while (rs.next()) {
          columnName = rs.getString("COLUMN_NAME");
          columnType = rs.getString("DATA_TYPE");
          nullable = rs.getString("NULLABLE");
          short i;
          if ("DATE".equalsIgnoreCase(columnType)) {
            i = 91;
          } else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {
            i = 93;
          } else if ("INTEGER".equalsIgnoreCase(columnType)) {
            i = 4;
          } else if ("NUMBER".equalsIgnoreCase(columnType)) {
            i = 2;
          } else if ("CHAR".equalsIgnoreCase(columnType)) {
            i = 1;
          } else if ("VARCHAR2".equalsIgnoreCase(columnType)) {
            i = 12;
          } else {
            i = 1111;
          }

          hmTypes.put(columnName, new Integer(i));
          if ("N".equals(nullable)) {
            strbfrNotNullColumns.append(columnName).append("~");
          }
        }
      } catch (SQLException var18) {
        throw var18;
      } finally {
        if (rs != null) {
          rs.close();
        }

        if (pstmt != null) {
          pstmt.close();
        }

        if (res != null) {
          rs.close();
        }

        if (pstmnt != null) {
          pstmnt.close();
        }
      }

      hmDataType.put(pTableName, hmTypes);
      hmNotNulls.put(pTableName, strbfrNotNullColumns);
    }

    Integer iType = (Integer) hmTypes.get(pFieldName.toUpperCase());
    int type = iType != null ? iType : 0;
    return type;
  }

  /**
   * Gets the not null columns.
   *
   * @param con the con
   * @param pTableName the table name
   * @return the not null columns
   * @throws SQLException the SQL exception
   */
  public String getNotNullColumns(Connection con, String pTableName) throws SQLException {
    StringBuffer strbfrNotNullCols = (StringBuffer) hmNotNulls.get(pTableName);
    if (strbfrNotNullCols == null) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      HashMap<String, Integer> hmTypes = new HashMap<String, Integer>(20);

      try {
        StringBuffer sbQuery = new StringBuffer(128);
        sbQuery.append("SELECT * FROM ").append(pTableName).append(" WHERE ROWNUM < 0 ");
        pstmt = con.prepareStatement(sbQuery.toString());
        rs = pstmt.executeQuery();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        strbfrNotNullCols = new StringBuffer("~");
        String columnName = "";
        int nullable;
        int columnCount = rsMetaData.getColumnCount();

        for (int count = 1; count <= columnCount; ++count) {
          columnName = rsMetaData.getColumnName(count);
          int i = rsMetaData.getColumnType(count);
          nullable = rsMetaData.isNullable(count);
          String strIsNullable = "";
          if (nullable == 0) {
            strIsNullable = "NO";
          } else {
            strIsNullable = "YES";
          }

          hmTypes.put(columnName, new Integer(i));
          if (strIsNullable.equalsIgnoreCase("NO")) {
            strbfrNotNullCols.append(columnName).append("~");
          }
        }
      } catch (SQLException var19) {
        throw var19;
      } finally {
        if (rs != null) {
          rs.close();
        }

        if (pstmt != null) {
          pstmt.close();
        }
      }

      hmDataType.put(pTableName, hmTypes);
      hmNotNulls.put(pTableName, strbfrNotNullCols);
    }

    return strbfrNotNullCols.toString();
  }

  /**
   * Gets the primary keys.
   *
   * @param con the con
   * @param pTableName the table name
   * @return the primary keys
   * @throws SQLException the SQL exception
   */
  public String[] getPrimaryKeys(Connection con, String pTableName) throws SQLException {

    String[] strPK = (String[]) ((String[]) hmPrimaryKeys.get(pTableName));
    if (strPK == null) {
      strPK = new String[0];
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
        pstmt = con.prepareStatement(SELECT_PK_COLS);
        pstmt.setString(1, pTableName);
        rs = pstmt.executeQuery();
        ArrayList<String> result = new ArrayList<String>();
        if (rs.next()) {
          // StringTokenizer strtkzPK = new StringTokenizer(rs.getString(1), delimiter);
          // strPK = new String[strtkzPK.countTokens()];
          result.add(rs.getString(1));
        }
        hmPrimaryKeys.put(pTableName, (String[]) result.toArray());
      } catch (SQLException var11) {
        throw var11;
      } finally {
        if (rs != null) {
          rs.close();
        }

        if (pstmt != null) {
          pstmt.close();
        }
      }
    }

    return strPK;
  }

  /**
   * Gets the columns.
   *
   * @param con the con
   * @param pTableName the table name
   * @return the columns
   * @throws SQLException the SQL exception
   */
  public String getColumns(Connection con, String pTableName) throws SQLException {
    String strCols = (String) hmTableColumns.get(pTableName);
    if (strCols == null) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ResultSetMetaData rsMetaData = null;

      try {
        StringBuffer sbQuery = new StringBuffer(128);
        sbQuery.append("SELECT * FROM ").append(pTableName).append(" WHERE ROWNUM <0 ");
        pstmt = con.prepareStatement(sbQuery.toString());
        rs = pstmt.executeQuery();
        rsMetaData = rs.getMetaData();
        StringBuffer sb = new StringBuffer(1024);
        String columnName = "";
        int columnCount = rsMetaData.getColumnCount();

        for (int count = 1; count <= columnCount; ++count) {
          columnName = rsMetaData.getColumnName(count);
          sb.append(columnName);
          sb.append("~");
        }

        strCols = sb.toString();
      } catch (SQLException var19) {
        throw var19;
      } finally {
        try {
          if (rs != null) {
            rs.close();
          }

          if (pstmt != null) {
            pstmt.close();
          }
        } catch (SQLException var18) {
        }
      }
    }

    return strCols;
  }

  /**
   * Gets the non primary columns.
   *
   * @param con the con
   * @param pTableName the table name
   * @return the non primary columns
   * @throws SQLException the SQL exception
   */
  public String[] getNonPrimaryColumns(Connection con, String pTableName) throws SQLException {
    String[] strNonPKCols = (String[]) ((String[]) hmNonPrimaryColumns.get(pTableName));
    if (strNonPKCols == null) {
      String[] pkCols = this.getPrimaryKeys(con, pTableName);
      String[] allCols = this.getColumns(con, pTableName).split("~");
      StringBuffer sb = new StringBuffer(1024);
      String columnName = "";

      for (int index = 0; index < allCols.length; ++index) {
        columnName = allCols[index];
        if (!ArrayUtils.isElementInArray(pkCols, columnName)) {
          sb.append(columnName);
          sb.append("~");
        }
      }

      StringTokenizer stNonPk = new StringTokenizer(sb.toString(), "~");
      strNonPKCols = new String[stNonPk.countTokens()];

      for (int var9 = 0; stNonPk.hasMoreTokens(); strNonPKCols[var9++] = stNonPk.nextToken()) {}
    }

    return strNonPKCols;
  }
}
