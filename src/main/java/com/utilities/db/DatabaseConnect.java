package com.utilities.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public final String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    public final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public final String POSTGRESQL_DRIVER_CLASS = "org.postgresql.Driver";

    public Connection connect(String connectString, String user, String password) {
	Connection conn = null;
	try {
	    conn = DriverManager.getConnection(connectString, user, password);
	} catch (SQLException e) {
	    System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    conn = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    conn = null;
	}
	return conn;
    }

    public boolean canConnect(String connectString, String user, String password) {
	boolean result = false;
	try {
	    DriverManager.getConnection(connectString, user, password);
	    result = true;
	} catch (SQLException e) {
	    System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    public Connection connect(String db, String connectString, String user, String password) { // jdbc:oracle:thin:@10.184.155.191:1521:RABODBD
											       // // Oracle
	Connection conn = null;
	if (isDbDriverAvailable(db)) {
	    try {
		conn = DriverManager.getConnection(connectString, user, password);
	    } catch (SQLException e) {
		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else
	    System.out.println("Driver Not Available");
	return conn;
    }

    public boolean isDbDriverAvailable(String db) {
	boolean result = false;
	switch (db) {
	case "oracle":
	    try {
		Class.forName(ORACLE_DRIVER_CLASS);
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	    break;
	case "mysql":
	    try {
		Class.forName(MYSQL_DRIVER_CLASS);
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	    break;
	case "postgresql":
	    try {
		Class.forName(POSTGRESQL_DRIVER_CLASS);
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	    break;
	default:
	    result = false;
	}
	return result;
    }

}
