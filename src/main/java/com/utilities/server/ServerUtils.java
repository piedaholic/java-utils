package com.utilities.server;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

// TODO: Auto-generated Javadoc
/** The Class ServerUtils. */
public class ServerUtils {

  /**
   * Gets the inits the context.
   *
   * @param provider_url the provider url
   * @param initCtx_factory the init ctx factory
   * @return the inits the context
   * @throws NamingException the naming exception
   */
  public static InitialContext getInitContext(String provider_url, String initCtx_factory)
      throws NamingException {
    Properties props = new Properties();
    props.put("java.naming.provider.url", provider_url);
    props.put("java.naming.factory.initial", initCtx_factory);
    InitialContext initContext = new InitialContext(props);
    return initContext;
  }
}
