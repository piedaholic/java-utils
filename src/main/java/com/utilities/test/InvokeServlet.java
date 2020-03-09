package com.utilities.test;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InvokeServlet {
	public static void main(String args[]) {
		String url = "http://localhost:7001/JAXWS/QueryUserServlet";
		String charset = "UTF-8";
		String reqXMLContents = "<QUERYUSER_REQ>\r\n" + 
				"	<HEADER>\r\n" + 
				"		<SOURCE>SYSTEM</SOURCE>\r\n" + 
				"		<COMPONENT>LINUX</COMPONENT>\r\n" + 
				"	</HEADER>\r\n" + 
				"	<BODY>\r\n" + 
				"		<!--Optional:-->\r\n" + 
				"		<User-Details>\r\n" + 
				"			<!--Optional:-->\r\n" + 
				"			<NAME>HPSINGH</NAME>\r\n" + 
				"		</User-Details>\r\n" + 
				"	</BODY>\r\n" + 
				"</QUERYUSER_REQ>\r\n" + 
				"";
		System.out.println(reqXMLContents);
		try {
			reqXMLContents = URLEncoder.encode(reqXMLContents, charset);
			String query = String.format("reqXMLContents=%s", reqXMLContents);
			System.out.println(query);

			URLConnection urlConnection = new URL(url).openConnection();

			// URL url = new
			// URL("http://localhost:7001/JAXWS/QueryUserServlet?reqXMLContents=" +
			// reqXMLContents);
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(url.openStream()));
			// line = in.readLine();

			urlConnection.setUseCaches(false);
			urlConnection.setDoOutput(true); // Triggers POST.
			urlConnection.setRequestProperty("accept-charset", charset);
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			OutputStreamWriter writer = null;
			try {
				writer = new OutputStreamWriter(urlConnection.getOutputStream(), charset);
				writer.write(query); // Write POST query string (if any needed).
			} finally {
				if (writer != null)
					try {
						writer.close();
					} catch (IOException logOrIgnore) {
					}
			}

			InputStream result = urlConnection.getInputStream();
			System.out.println(result);
			//System.out.println(urlConnection.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
