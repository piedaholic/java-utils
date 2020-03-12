package com.utilities.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/** The Class SendHttpClient. */
public class SendHttpClient {

  /**
   * Dbg.
   *
   * @param msg the msg
   */
  private void dbg(String msg) {}

  /**
   * Send http request.
   *
   * @param payLoad the pay load
   * @param replicationServletUrl the replication servlet url
   * @return the string
   */
  public String sendHttpRequest(String payLoad, String replicationServletUrl) {
    URL url = null;
    HttpURLConnection httpURLConnection = null;
    String responseMessage = "";
    OutputStream outputStream = null;
    BufferedReader reader = null;
    InputStreamReader inputStreamReader = null;
    InputStream instream = null;
    this.dbg("sendHttpRequest-->Sending request to the servlet");

    try {
      url = new URL(replicationServletUrl);
      httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setDoOutput(true);
      outputStream = httpURLConnection.getOutputStream();
      DataOutputStream payloadWriter = new DataOutputStream(outputStream);
      payloadWriter.write(payLoad.getBytes());
      int responseCode = httpURLConnection.getResponseCode();
      if (responseCode == 200) {
        instream = httpURLConnection.getInputStream();
        inputStreamReader = new InputStreamReader(instream);
        reader = new BufferedReader(inputStreamReader);

        for (String str = null;
            (str = reader.readLine()) != null;
            responseMessage = responseMessage + str) {}
      }
    } catch (MalformedURLException var45) {
      this.dbg("sendHttpRequest-->MalformedURLExceptio:n" + var45.getMessage());
    } catch (IOException var46) {
      this.dbg("sendHttpRequest-->IOException:" + var46.getMessage());
    } catch (Exception var47) {
      this.dbg("sendHttpRequest-->General Exception:" + var47.getMessage());
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
      } catch (IOException var44) {
        this.dbg("sendHttpRequest-->Exception Occured:" + var44.getMessage());
      }

      try {
        if (instream != null) {
          instream.close();
        }
      } catch (IOException var43) {
        this.dbg("sendHttpRequest-->Exception Occured:" + var43.getMessage());
      }

      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
      } catch (IOException var42) {
        this.dbg("sendHttpRequest-->Exception Occured:" + var42.getMessage());
      }

      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException var41) {
        this.dbg("sendHttpRequest-->Exception Occured:" + var41.getMessage());
      }
    }

    return responseMessage;
  }

  /**
   * Send http request.
   *
   * @param payLoad the pay load
   * @param replicationServletUrl the replication servlet url
   * @return the string
   */
  public String sendHttpRequest(Clob payLoad, String replicationServletUrl) {
    URL url = null;
    HttpURLConnection httpURLConnection = null;
    String responseMessage = "";
    OutputStream outputStream = null;
    DataOutputStream payloadWriter = null;
    BufferedReader reader = null;
    InputStreamReader inputStreamReader = null;
    InputStream instream = null;
    this.dbg("sendHttpRequest-->Sending request to the servlet");

    try {
      url = new URL(replicationServletUrl);
      httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
      httpURLConnection.setRequestProperty("actionType", "REPLICATION");
      httpURLConnection.setDoOutput(true);
      outputStream = httpURLConnection.getOutputStream();
      payloadWriter = new DataOutputStream(outputStream);
      this.dbg("sendHttpRequest-->Payload Lenght is:" + payLoad.length());
      String myClob = payLoad.getSubString(1L, (int) payLoad.length());
      this.dbg("sendHttpRequest-->Request message before Encoding is:" + myClob);
      myClob = URLEncoder.encode(myClob, "UTF-8");
      this.dbg("sendHttpRequest-->Request message is:" + myClob);
      payloadWriter.write(myClob.getBytes());
      this.dbg("sendHttpRequest-->Request stream Lenght is:" + payloadWriter.size());
      int responseCode = httpURLConnection.getResponseCode();
      if (responseCode == 200) {
        instream = httpURLConnection.getInputStream();
        inputStreamReader = new InputStreamReader(instream);
        reader = new BufferedReader(inputStreamReader);

        for (String str = null;
            (str = reader.readLine()) != null;
            responseMessage = responseMessage + str) {}
      }
    } catch (ConnectException var64) {
      this.dbg("sendHttpRequest-->ConnectException:" + var64.getMessage());
    } catch (MalformedURLException var65) {
      this.dbg("sendHttpRequest-->MalformedURLException:" + var65.getMessage());
    } catch (IOException var66) {
      this.dbg("sendHttpRequest-->IOException:" + var66.getMessage());
    } catch (SQLException var67) {
      this.dbg("sendHttpRequest-->SQLException:" + var67.getMessage());
    } catch (Exception var68) {
      this.dbg("sendHttpRequest-->General Exception:" + var68.getMessage());
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
      } catch (IOException var63) {
        this.dbg("sendHttpRequest-->Exception:" + var63.getMessage());
      }

      try {
        if (instream != null) {
          instream.close();
        }
      } catch (IOException var62) {
        this.dbg("sendHttpRequest-->Exception:" + var62.getMessage());
      }

      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
      } catch (IOException var61) {
        this.dbg("sendHttpRequest-->Exception:" + var61.getMessage());
      }

      try {
        if (payloadWriter != null) {
          payloadWriter.close();
        }
      } catch (IOException var60) {
        this.dbg("sendHttpRequest-->Exception:" + var60.getMessage());
      }

      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
      } catch (IOException var59) {
        this.dbg("sendHttpRequest-->Exception:" + var59.getMessage());
      }
    }

    return responseMessage;
  }

  /**
   * Check url reachable.
   *
   * @param replicationServletUrl the replication servlet url
   * @return true, if successful
   */
  public boolean checkUrlReachable(String replicationServletUrl) {
    URL url = null;
    HttpURLConnection httpURLConnection = null;
    boolean urlReachable = false;

    try {
      url = new URL(replicationServletUrl);
      httpURLConnection = (HttpURLConnection) url.openConnection();
      this.dbg("checkUrlReachable-->Trying to connect to the URL");
      int responseCode = httpURLConnection.getResponseCode();
      this.dbg("checkUrlReachable-->responseCode is:" + responseCode);
      if (responseCode == 200 || responseCode == 500) {
        urlReachable = true;
      }
    } catch (MalformedURLException var7) {
      this.dbg("checkUrlReachable-->MalformedURLException:" + var7.getMessage());
    } catch (IOException var8) {
      this.dbg("checkUrlReachable-->IOException:" + var8.getMessage());
    } catch (Exception var9) {
      this.dbg("checkUrlReachable-->Exception:" + var9.getMessage());
    }

    return urlReachable;
  }
}
