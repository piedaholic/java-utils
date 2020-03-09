package com.utilities.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final int SUCCESS = 1;
    public static final int NOT_FOUND = 0;
    public static final int EXCEPTION = -1;
    public static final String WS_XML_HEADER_TAG = "HEADER";
    public static final String WS_XML_BODY_TAG = "BODY";
    public static final String WS_XML_SYSTEM_RESP_TAG = "System-Response";
    public static final String WS_XML_ECODE_TAG = "ERROR_CODE";
    public static final String WS_XML_EDESC_TAG = "ERROR_DESC";
    public static final String WS_XML_EPARAM_TAG = "ERROR_PARAM";
    public static final String WS_XML_ERR_NODE = "Error";

    public static final String WS_JSON_HEADER_TAG = "header";
    public static final String WS_JSON_BODY_TAG = "body";
    public static final String WS_JSON_SYSTEM_RESP_TAG = "systemResponse";

    public final static String CONTENT_TYPE_XML = "XML";
    public final static String CONTENT_TYPE_JSON = "JSON";
    public final static String CONTENT_TYPE_TEXT = "TEXT";
    public final static String CONTENT_TYPE_PARAMS = "PARAMS";
    public final static char INCOMING_REQUEST = 'I';
    public final static char OUTGOING_REQUEST = 'O';
    public final static String REMARKS_NODE_NAME = "REMARKS";

    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PATCH = "PATCH";
    public static final String METHOD_POST = "POST";

    public static final List<String> REQUEST_METHODS = Arrays.asList(METHOD_DELETE, METHOD_GET, METHOD_PATCH,
	    METHOD_POST);
    public static final List<String> REQUEST_METHODS_WITH_PAYLOAD = Arrays.asList(METHOD_PATCH, METHOD_POST);
    public static final String[] PROTOCOL_LISTS = new String[] { "http://", "https://" };
    public static final List<String> PROTOCOLS = Arrays.asList(PROTOCOL_LISTS);
}
