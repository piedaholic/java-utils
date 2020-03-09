package com.utilities.server;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServerUtils {
    public static InitialContext getInitContext(String provider_url, String initCtx_factory) throws NamingException {
	Properties props = new Properties();
	props.put("java.naming.provider.url", provider_url);
	props.put("java.naming.factory.initial", initCtx_factory);
	InitialContext initContext = new InitialContext(props);
	return initContext;
    }
}
