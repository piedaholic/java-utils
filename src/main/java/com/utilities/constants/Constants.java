package com.utilities.constants;

import java.util.Arrays;
import java.util.List;

// TODO: Auto-generated Javadoc
/** The Class Constants. */
public class Constants {

  /** The Constant SUCCESS. */
  public static final int SUCCESS = 1;

  /** The Constant NOT_FOUND. */
  public static final int NOT_FOUND = 0;

  /** The Constant EXCEPTION. */
  public static final int EXCEPTION = -1;

  /** The Constant WS_XML_HEADER_TAG. */
  public static final String WS_XML_HEADER_TAG = "HEADER";

  /** The Constant WS_XML_BODY_TAG. */
  public static final String WS_XML_BODY_TAG = "BODY";

  /** The Constant WS_XML_SYSTEM_RESP_TAG. */
  public static final String WS_XML_SYSTEM_RESP_TAG = "System-Response";

  /** The Constant WS_XML_ECODE_TAG. */
  public static final String WS_XML_ECODE_TAG = "ERROR_CODE";

  /** The Constant WS_XML_EDESC_TAG. */
  public static final String WS_XML_EDESC_TAG = "ERROR_DESC";

  /** The Constant WS_XML_EPARAM_TAG. */
  public static final String WS_XML_EPARAM_TAG = "ERROR_PARAM";

  /** The Constant WS_XML_ERR_NODE. */
  public static final String WS_XML_ERR_NODE = "Error";

  /** The Constant WS_JSON_HEADER_TAG. */
  public static final String WS_JSON_HEADER_TAG = "header";

  /** The Constant WS_JSON_BODY_TAG. */
  public static final String WS_JSON_BODY_TAG = "body";

  /** The Constant WS_JSON_SYSTEM_RESP_TAG. */
  public static final String WS_JSON_SYSTEM_RESP_TAG = "systemResponse";

  /** The Constant CONTENT_TYPE_XML. */
  public static final String CONTENT_TYPE_XML = "XML";

  /** The Constant CONTENT_TYPE_JSON. */
  public static final String CONTENT_TYPE_JSON = "JSON";

  /** The Constant CONTENT_TYPE_TEXT. */
  public static final String CONTENT_TYPE_TEXT = "TEXT";

  /** The Constant CONTENT_TYPE_PARAMS. */
  public static final String CONTENT_TYPE_PARAMS = "PARAMS";

  /** The Constant INCOMING_REQUEST. */
  public static final char INCOMING_REQUEST = 'I';

  /** The Constant OUTGOING_REQUEST. */
  public static final char OUTGOING_REQUEST = 'O';

  /** The Constant REMARKS_NODE_NAME. */
  public static final String REMARKS_NODE_NAME = "REMARKS";

  /** The Constant METHOD_DELETE. */
  public static final String METHOD_DELETE = "DELETE";

  /** The Constant METHOD_GET. */
  public static final String METHOD_GET = "GET";

  /** The Constant METHOD_PATCH. */
  public static final String METHOD_PATCH = "PATCH";

  /** The Constant METHOD_POST. */
  public static final String METHOD_POST = "POST";

  /** The Constant REQUEST_METHODS. */
  public static final List<String> REQUEST_METHODS =
      Arrays.asList(METHOD_DELETE, METHOD_GET, METHOD_PATCH, METHOD_POST);

  /** The Constant REQUEST_METHODS_WITH_PAYLOAD. */
  public static final List<String> REQUEST_METHODS_WITH_PAYLOAD =
      Arrays.asList(METHOD_PATCH, METHOD_POST);

  /** The Constant PROTOCOL_LISTS. */
  public static final String[] PROTOCOL_LISTS = new String[] {"http://", "https://"};

  /** The Constant PROTOCOLS. */
  public static final List<String> PROTOCOLS = Arrays.asList(PROTOCOL_LISTS);
}
