package com.utilities.db.oracle;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/** The Class OracleUtility. */
public class OracleUtility {

  /**
   * Gets the param.
   *
   * @param con the con
   * @param rs the rs
   * @param pParamCount the param count
   * @param pTableName the table name
   * @param pFieldName the field name
   * @return the param
   * @throws SQLException the SQL exception
   */
  public static Object getParam(
      Connection con, ResultSet rs, int pParamCount, String pTableName, String pFieldName)
      throws SQLException {
    Object value = null;
    int intType = DBMetaData.getInstance().getDataType(con, pTableName, pFieldName);
    if (intType != 4 && intType != 5) {
      if (intType == -5) {
        value = rs.getLong(pParamCount) + "";
      } else if (intType != 3 && intType != 2 && intType != 8) {
        if (intType == 91) {
          value = rs.getDate(pParamCount);
        } else if (intType == 93) {
          value = rs.getTimestamp(pParamCount);
        } else if (intType == 2005) {
          value = rs.getClob(pParamCount);
        } else if (intType == 2004) {
          value = rs.getBlob(pParamCount);
        } else {
          value = rs.getString(pParamCount);
        }
      } else {
        value = rs.getDouble(pParamCount) + "";
      }
    } else {
      value = rs.getInt(pParamCount) + "";
    }
    return value;
  }

  /**
   * Sets the param.
   *
   * @param con the con
   * @param pPrdStmt the prd stmt
   * @param pParamCount the param count
   * @param pTableName the table name
   * @param pFieldName the field name
   * @param pFieldValue the field value
   * @throws SQLException the SQL exception
   */
  public static void setParam(
      Connection con,
      PreparedStatement pPrdStmt,
      int pParamCount,
      String pTableName,
      String pFieldName,
      String pFieldValue)
      throws SQLException {
    int intType = DBMetaData.getInstance().getDataType(con, pTableName, pFieldName);
    if ("%".equals(pFieldValue)) {
      pPrdStmt.setString(pParamCount, pFieldValue);
    } else if (pFieldValue != null
        && !"null".equalsIgnoreCase(pFieldValue)
        && !"NULL".equalsIgnoreCase(pFieldValue)
        && pFieldValue.length() != 0) {
      if (intType == 4) {
        pPrdStmt.setInt(pParamCount, Integer.parseInt(pFieldValue));
      } else if (intType == -5) {
        pPrdStmt.setLong(pParamCount, Long.parseLong(pFieldValue));
      } else if (intType != 5 && intType != 8 && intType != 2 && intType != 3) {
        if (intType == 91) {
          if (pFieldValue.length() > 10) {
            pPrdStmt.setTimestamp(pParamCount, Timestamp.valueOf(pFieldValue));
          } else {
            pPrdStmt.setDate(pParamCount, Date.valueOf(pFieldValue));
          }
        } else if (intType == 93) {
          if (pFieldValue.length() > 10) {
            pPrdStmt.setTimestamp(pParamCount, Timestamp.valueOf(pFieldValue));
          } else {
            pPrdStmt.setDate(pParamCount, Date.valueOf(pFieldValue));
          }
        } else if (intType == 2005) {
          StringReader reader = new StringReader(pFieldValue);
          pPrdStmt.setCharacterStream(pParamCount, reader, pFieldValue.length());
        } else if (intType == 2004) {
          /*
           * FileAttachment attch = (FileAttachment) (new HashMap<>().get(pFieldValue)); if (attch
           * != null) { ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream
           * oos = null;
           *
           * try { oos = new ObjectOutputStream(baos); oos.writeObject(attch); } catch (IOException
           * var11) { }
           *
           * InputStream is = new ByteArrayInputStream(baos.toByteArray());
           * pPrdStmt.setBinaryStream(pParamCount, is, baos.size()); }
           */
        } else {
          pPrdStmt.setString(pParamCount, pFieldValue);
        }
      } else {
        pPrdStmt.setDouble(pParamCount, Double.parseDouble(pFieldValue));
      }

    } else {
      pPrdStmt.setNull(pParamCount, intType);
    }
  }

  /**
   * Gets the DB server time.
   *
   * @param con the con
   * @return the DB server time
   * @throws SQLException the SQL exception
   */
  public static String getDBServerTime(Connection con) throws SQLException {
    String strTime = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      stmt = con.createStatement();

      for (rs =
              stmt.executeQuery(
                  "select to_char(SYSTIMESTAMP, 'DD-MON-RRRR HH24:MI:SS TZR') from dual");
          rs.next();
          strTime = rs.getString(1)) {}

      stmt.close();
      rs.close();
      return strTime;
    } catch (SQLException var8) {
      throw var8;
    } finally {
      rs.close();
      stmt.close();
    }
  }

  /**
   * Gets the DB timestamp.
   *
   * @param con the con
   * @return the DB timestamp
   * @throws SQLException the SQL exception
   */
  public static String getDBTimestamp(Connection con) throws SQLException {
    String strTime = null;
    strTime = getDBServerTime(con);
    StringBuffer sb = new StringBuffer(30);
    sb.append(strTime);
    return sb.toString();
  }
}
