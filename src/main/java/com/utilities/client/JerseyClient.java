package com.utilities.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

// TODO: Auto-generated Javadoc
/** The Class JerseyClient. */
public class JerseyClient {

  /**
   * Post.
   *
   * @param url the url
   * @param request the request
   * @return the string
   */
  public static String post(String url, Object request) {
    try {
      Client client = Client.create();

      WebResource webResource = client.resource(url);

      ClientResponse response =
          webResource.accept("application/xml").post(ClientResponse.class, request);

      if (response.getStatus() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
      }

      String output = response.getEntity(String.class);

      System.out.println("Server response : \n");
      System.out.println(output);
      return output;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
