package com.utilities.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.utilities.files.FileUtilities;
import com.util.JAXBContextFactory;
import com.ws.schema.user.Error;
import com.ws.schema.user.ErrorList;
import com.ws.schema.user.QueryUserResp;
import com.ws.schema.user.QueryUserResp.AppWsBody;
import com.ws.schema.user.SysResponse;

public class Test {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	File file = new File(
		"D:\\Development\\Eclipse\\OEPEOxygen\\workspace\\MyApplication\\resources\\testXMLs\\CreateUser.xml");
	try {
	    String inpXml = FileUtilities.readFile(file.getPath(), Charset.defaultCharset());
	    XmlParser xmlParser = new XmlParser();
	    xmlParser.parseXML(inpXml);
	    QueryUserResp queryUserResp = new QueryUserResp();
	    SysResponse sysResponse = new SysResponse();
	    List<Error> errorList = new ArrayList<Error>();
	    ErrorList errors = new ErrorList();
	    errors.setErrors(errorList);
	    Error error = new Error();
	    error.setErrorCode("404");
	    error.setErrorDesc("Not Found");
	    errors.getErrors().add(error);
	    error.setErrorCode("500");
	    error.setErrorDesc("Ok");
	    errors.getErrors().add(error);
	    sysResponse.setStatus("Failed");
	    sysResponse.setErrorList(errors);
	    AppWsBody wsBody = new AppWsBody();
	    wsBody.setSysResponse(sysResponse);
	    queryUserResp.setWsBody(wsBody);
	    JAXBContext context = JAXBContextFactory.getInstance().getContext("com.ws.schema.user");
	    Marshaller marsh = context.createMarshaller();
	    marsh.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    marsh.marshal(queryUserResp, output);
	    System.out.println(output.toString("UTF-8"));
	    BigDecimal num = new BigDecimal(2);
	    System.out.println(num.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
