package com.utilities.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {
    public String request(String urlString, String requestType, Map<String, String> requestParams,
	    int timeOutInterval) {
	String strResponseXml = "";
	try {
	    URL httpURL = null;
	    HttpURLConnection httpURLConnection = null;
	    try {
		httpURL = new URL(urlString);
		// timeOutInterval = 30000;
		httpURLConnection = (HttpURLConnection) httpURL.openConnection();
		httpURLConnection.setRequestMethod(requestType);
		httpURLConnection.setConnectTimeout(timeOutInterval);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		for (Map.Entry<String, String> entry : requestParams.entrySet())
		    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
		httpURLConnection.setChunkedStreamingMode(0);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		DataOutputStream dataOutStream = new DataOutputStream(httpURLConnection.getOutputStream());
		Throwable localThrowable3 = null;
		try {
		    byte[] reqByte = "".getBytes();
		    dataOutStream.write(reqByte);
		    dataOutStream.flush();
		    int responseCode = httpURLConnection.getResponseCode();
		    if (responseCode == 200) {
			strResponseXml = getResponse(httpURLConnection);
		    } else {
			throw new Exception("Response Code not 200");
		    }
		} catch (Throwable localThrowable1) {
		    localThrowable3 = localThrowable1;
		    throw localThrowable1;
		} finally {
		    if (dataOutStream != null) {
			if (localThrowable3 != null) {
			    try {
			    } catch (Throwable localThrowable2) {
				localThrowable3.addSuppressed(localThrowable2);
			    }
			}
		    }
		}
	    } catch (Exception e) {
		throw new Exception("Exception:" + e.getMessage());
	    } finally {
		httpURLConnection.disconnect();
	    }
	} catch (Exception localException1) {
	}
	return strResponseXml;
    }

    private static String getResponse(HttpURLConnection httpURLConnection) {
	String inputLine = null;
	try {
	    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	    Throwable localThrowable3 = null;
	    try {
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
	    } catch (Throwable localThrowable1) {
		localThrowable3 = localThrowable1;
		throw localThrowable1;
	    } finally {
		if (in != null) {
		    if (localThrowable3 != null) {
			try {
			    in.close();
			} catch (Throwable localThrowable2) {
			    localThrowable3.addSuppressed(localThrowable2);
			}
		    } else {
			in.close();
		    }
		}
	    }
	} catch (IOException localIOException) {
	}
	return inputLine;
    }
}
