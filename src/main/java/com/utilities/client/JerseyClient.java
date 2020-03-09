package com.utilities.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.ws.schema.user.AppWsHeader;
import com.ws.schema.user.QueryUserByPkReq;
import com.ws.schema.user.UserPkType;
import com.ws.schema.user.QueryUserByPkReq.AppWsBody;

public class JerseyClient {

	public static void main(String[] args) {
		try {
			QueryUserByPkReq reqMsg = new QueryUserByPkReq();
			AppWsHeader wsheader = new AppWsHeader();
			// wsheader.setCOMPONENT(COMPType.LINUX);
			wsheader.setSource("SYSTEM");
			reqMsg.setWsHeader(wsheader);
			AppWsBody wsbody = new AppWsBody();
			UserPkType userDetails = new UserPkType();
			userDetails.setName("Harsh");
			wsbody.setUser(userDetails);
			reqMsg.setWsBody(wsbody);
			Client client = Client.create();

			WebResource webResource = client.resource("http://localhost:7001/JAXWS/resources/QueryUserResource");

			ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, reqMsg);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Server response : \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
