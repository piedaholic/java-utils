/** */
package com.utilities.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.utilities.constants.Constants;
import com.utilities.object.ClassUtils;
import com.utilities.object.ObjectUtils;
import com.utilities.string.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtils.
 *
 * @author hpsingh
 */
public class JsonUtils {

  /** The Constant JACKSON_PARSER. */
  public static final int JACKSON_PARSER = 1;

  /** The Constant GSON_PARSER. */
  public static final int GSON_PARSER = 2;

  /** The Constant JAVA_PARSER. */
  public static final int JAVA_PARSER = 3;

  /** The Constant JSONP_PARSER. */
  public static final int JSONP_PARSER = 4;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    JsonUtils jsonUtils = new JsonUtils();
    File file = jsonUtils.getFileFromResources("CreateUser.json");
    JSONObject jsonObject = JsonUtils.readJsonFile(file.getPath());
    JsonUtils.populateBodyDto(jsonObject);
  }

  /**
   * Populate body dto.
   *
   * @param req the req
   * @return the object
   */
  public static Object populateBodyDto(JSONObject req) {
    Object obj = null;
    try {
      JSONObject body = (JSONObject) req.get("body");
      JSONArray names = body.names();

      obj =
          Class.forName("com.dto." + StringUtils.toCamelCase(names.get(0).toString()) + "Dto")
              .getConstructor()
              .newInstance();
      JSONObject dataBlock = (JSONObject) body.get(names.get(0).toString());
      convertJsonFormat(dataBlock, obj);
      /*
       * try { ObjectMapper mapper = new ObjectMapper(); appRootDto = mapper.readValue(new
       * TreeTraversingParser(jsonNode), appRootDto.getClass()); } catch (Exception e) { }
       */
    } catch (Exception e) {
      System.out.println(e);
    }
    return obj;
  }

  /**
   * Convert json format.
   *
   * @param json the json
   * @return the json node
   */
  @SuppressWarnings("deprecation")
  static JsonNode convertJsonFormat(JSONObject json) {
    ObjectNode ret = JsonNodeFactory.instance.objectNode();

    Iterator<String> iterator = json.keys();
    for (; iterator.hasNext(); ) {
      String key = iterator.next();
      Object value;
      try {
        value = json.get(key);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
      if (json.isNull(key)) ret.putNull(key);
      else if (value instanceof String) ret.put(key, (String) value);
      else if (value instanceof Integer) ret.put(key, (Integer) value);
      else if (value instanceof Long) ret.put(key, (Long) value);
      else if (value instanceof Double) ret.put(key, (Double) value);
      else if (value instanceof Boolean) ret.put(key, (Boolean) value);
      else if (value instanceof JSONObject) ret.put(key, convertJsonFormat((JSONObject) value));
      else if (value instanceof JSONArray) ret.put(key, convertJsonFormat((JSONArray) value));
      else
        throw new RuntimeException(
            "not prepared for converting instance of class " + value.getClass());
    }
    return ret;
  }

  /**
   * Convert json format.
   *
   * @param json the json
   * @return the json node
   */
  static JsonNode convertJsonFormat(JSONArray json) {
    ArrayNode ret = JsonNodeFactory.instance.arrayNode();
    for (int i = 0; i < json.length(); i++) {
      Object value;
      try {
        value = json.get(i);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
      if (json.isNull(i)) ret.addNull();
      else if (value instanceof String) ret.add((String) value);
      else if (value instanceof Integer) ret.add((Integer) value);
      else if (value instanceof Long) ret.add((Long) value);
      else if (value instanceof Double) ret.add((Double) value);
      else if (value instanceof Boolean) ret.add((Boolean) value);
      else if (value instanceof JSONObject) ret.add(convertJsonFormat((JSONObject) value));
      else if (value instanceof JSONArray) ret.add(convertJsonFormat((JSONArray) value));
      else
        throw new RuntimeException(
            "not prepared for converting instance of class " + value.getClass());
    }
    return ret;
  }

  /**
   * Convert json format.
   *
   * @param json the json
   * @param appRootDto the app root dto
   * @return the json node
   */
  @SuppressWarnings("deprecation")
  static JsonNode convertJsonFormat(JSONObject json, Object appRootDto) {
    ObjectNode ret = JsonNodeFactory.instance.objectNode();
    Method method = null;
    Iterator<String> iterator = json.keys();
    for (; iterator.hasNext(); ) {
      String key = iterator.next();
      String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
      Object value;
      try {
        value = json.get(key);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
      if (json.isNull(key)) {
        ret.putNull(key);
        if (!ClassUtils.doesMethodExist(appRootDto, methodName)) {
          methodName = methodName + "Dto";
        }

        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName.replaceFirst("set", "get"));
          Object arg = method.invoke(appRootDto);
          method = appRootDto.getClass().getDeclaredMethod(methodName, arg.getClass());
          method.invoke(appRootDto, new Object[] {});
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      /*
       * else if (value instanceof String) ret.add((String) value); else if (value instanceof
       * Integer) ret.add((Integer) value); else if (value instanceof Long) ret.add((Long) value);
       * else if (value instanceof Double) ret.add((Double) value); else if (value instanceof
       * Boolean) ret.add((Boolean) value);
       */
      else if (value instanceof String) {
        ret.put(key, (String) value);
        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName, String.class);
          method.invoke(appRootDto, value);
        } catch (Exception e) {
          System.out.println(e);
        }
      } else if (value instanceof Integer) {
        ret.put(key, (Integer) value);
        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName, BigDecimal.class);
          method.invoke(appRootDto, value);
        } catch (Exception e) {
          try {
            method = appRootDto.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(appRootDto, value);
          } catch (Exception e1) {
            System.out.println(e1);
          }
        }
      } else if (value instanceof Long) {
        ret.put(key, (Long) value);
        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName, BigDecimal.class);
          method.invoke(appRootDto, value);
        } catch (Exception e) {
          try {
            method = appRootDto.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(appRootDto, value);
          } catch (Exception e1) {
            System.out.println(e1);
          }
        }
      } else if (value instanceof Double) {
        ret.put(key, (Double) value);
        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName, BigDecimal.class);
          method.invoke(appRootDto, value);
        } catch (Exception e) {
          try {
            method = appRootDto.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(appRootDto, value);
          } catch (Exception e1) {
            System.out.println(e1);
          }
        }
      } else if (value instanceof Boolean) {
        ret.put(key, (Boolean) value);
        try {
          method = appRootDto.getClass().getDeclaredMethod(methodName, BigDecimal.class);
          method.invoke(appRootDto, value);
        } catch (Exception e) {
          try {
            method = appRootDto.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(appRootDto, value);
          } catch (Exception e1) {
            System.out.println(e1);
          }
        }
      }
      /*
       * else if (value instanceof JSONObject) ret.put(key, convertJsonFormat((JSONObject) value));
       * else if (value instanceof JSONArray) ret.put(key, convertJsonFormat((JSONArray) value));
       */
      else if (value instanceof JSONObject) {
        if (ClassUtils.doesMethodExist(appRootDto, methodName + "Dto")) {
          methodName = methodName + "Dto";
        }

        try {
          Method objMethod =
              appRootDto.getClass().getDeclaredMethod(methodName.replaceFirst("set", "get"));
          Class<?> returnParam = objMethod.getReturnType();
          Object childObj = returnParam.getConstructor().newInstance();
          ret.put(key, convertJsonFormat((JSONObject) value, childObj));
          method = appRootDto.getClass().getDeclaredMethod(methodName, returnParam);
          method.invoke(appRootDto, childObj);
        } catch (Exception e) {
          System.out.println(e);
        }
      } else if (value instanceof JSONArray) {
        ret.put(key, convertJsonFormat((JSONArray) value, appRootDto, key));
      } else
        throw new RuntimeException(
            "not prepared for converting instance of class " + value.getClass());
    }
    return ret;
  }

  /**
   * Convert json format.
   *
   * @param json the json
   * @param appRootDto the app root dto
   * @param key the key
   * @return the json node
   */
  static JsonNode convertJsonFormat(JSONArray json, Object appRootDto, String key) {
    List<Object> objList = new ArrayList<>();
    ArrayNode ret = JsonNodeFactory.instance.arrayNode();
    for (int i = 0; i < json.length(); i++) {
      Object value;
      try {
        value = json.get(i);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
      if (json.isNull(i)) {
        ret.addNull();
      }
      /*
       * else if (value instanceof String) ret.add((String) value); else if (value instanceof
       * Integer) ret.add((Integer) value); else if (value instanceof Long) ret.add((Long) value);
       * else if (value instanceof Double) ret.add((Double) value); else if (value instanceof
       * Boolean) ret.add((Boolean) value);
       */
      else if (value instanceof String) {
        ret.add((String) value);
        objList.add(value);
      } else if (value instanceof Integer) {
        ret.add((Integer) value);
        objList.add(BigDecimal.valueOf((Integer) value));
      } else if (value instanceof Long) {
        ret.add((Long) value);
        objList.add(BigDecimal.valueOf((Long) value));
      } else if (value instanceof Double) {
        ret.add((Double) value);
        objList.add(BigDecimal.valueOf((Double) value));
      } else if (value instanceof Boolean) {
        ret.add((Boolean) value);
        objList.add((Boolean) value);
      }
      /*
       * else if (value instanceof JSONObject) ret.add(convertJsonFormat((JSONObject) value)); else
       * if (value instanceof JSONArray) ret.add(convertJsonFormat((JSONArray) value));
       */
      else if (value instanceof JSONObject) {
        String objClassName = key.substring(0, key.length() - 1);
        objClassName =
            "com.dto."
                + objClassName.substring(0, 1).toUpperCase()
                + objClassName.substring(1)
                + "Dto";
        Object obj = null;
        try {
          obj = Class.forName(objClassName).getConstructor().newInstance();
        } catch (InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException
            | NoSuchMethodException
            | SecurityException
            | ClassNotFoundException e) {
        }
        ret.add(convertJsonFormat((JSONObject) value, obj));
        objList.add(obj);

      } else if (value instanceof JSONArray) {
        ret.add(convertJsonFormat((JSONArray) value));
      } else
        throw new RuntimeException(
            "not prepared for converting instance of class " + value.getClass());
    }
    Method method;
    try {
      method =
          appRootDto
              .getClass()
              .getDeclaredMethod(
                  "set" + key.substring(0, 1).toUpperCase() + key.substring(1) + "List",
                  List.class);
      method.invoke(appRootDto, objList);
    } catch (NoSuchMethodException
        | SecurityException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException e) {
    }

    return ret;
  }

  /**
   * Read json file.
   *
   * @param file the file
   * @return the JSON object
   */
  /*
   * public static org.json.simple.JSONObject readJsonFile(String file) { org.json.simple.JSONObject
   * jsonObject = null; try { org.json.simple.parser.JSONParser parser = new JSONParser(); Object
   * obj = parser.parse(new FileReader(file)); jsonObject = (org.json.simple.JSONObject) obj; }
   * catch (FileNotFoundException e) { System.out.println("File Not Found"); } catch (IOException e)
   * { System.out.println("I/O Exception Occured"); } catch (ParseException e) {
   * System.out.println("Parse Exception Occured"); } return jsonObject; }
   */
  public static JSONObject readJsonFile(String file) {
    JSONObject jsonObject = null;
    try {
      // JsonParser jsonParser = new JsonFactory().createParser(new File(file));
      String jsonString = new String(Files.readAllBytes(Paths.get(file)));
      jsonObject = new JSONObject(jsonString);
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
    } catch (IOException e) {
      System.out.println("I/O Exception Occured");
    }
    return jsonObject;
  }

  /**
   * Gets the file from resources.
   *
   * @param fileName the file name
   * @return the file from resources
   */
  // get file from classpath, resources folder
  File getFileFromResources(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();

    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file is not found!");
    } else {
      return new File(resource.getFile());
    }
  }

  /**
   * Checks if is JSON valid.
   *
   * @param jsonString the json string
   * @param jsonParser the json parser
   * @return true, if is JSON valid
   */
  /*
   * public static void replaceNodeNames(JSONObject jsonObject) { Iterator<String> iterator =
   * jsonObject.keys(); for (; iterator.hasNext();) { String key = iterator.next(); Object value;
   * try { value = jsonObject.get(key); } catch (JSONException e) { throw new RuntimeException(e); }
   * if (value instanceof JSONObject) { jsonObject.put(key.replace("Dto", ""),
   * jsonObject.remove(key)); replaceNodeNames((JSONObject) value); } else if (value instanceof
   * JSONArray) { jsonObject.put(key.replace("List", ""), jsonObject.remove(key));
   * replaceNodeNames((JSONObject) value); } else throw new
   * RuntimeException("Unknown Element/Class/Instance Exception " + value.getClass()); } }
   */
  public boolean isJSONValid(String jsonString, int jsonParser) {
    switch (jsonParser) {
      case JACKSON_PARSER:
        return jacksonJSON(jsonString);
      case GSON_PARSER:
        return gsonJSON(jsonString);
      case JAVA_PARSER:
        return javaJSON(jsonString);
      case JSONP_PARSER:
        return jacksonJSON(jsonString);
      default:
        return false;
    }
  }

  /**
   * Jackson JSON.
   *
   * @param jsonInString the json in string
   * @return true, if successful
   */
  public static boolean jacksonJSON(String jsonInString) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(jsonInString);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Gson JSON.
   *
   * @param jsonInString the json in string
   * @return true, if successful
   */
  public static boolean gsonJSON(String jsonInString) {
    try {
      Gson gson = new Gson();
      gson.fromJson(jsonInString, Object.class);
      return true;
    } catch (JsonSyntaxException ex) {
      return false;
    }
  }

  /**
   * Java JSON.
   *
   * @param jsonInString the json in string
   * @return true, if successful
   */
  public static boolean javaJSON(String jsonInString) {
    try {
      new JSONObject(jsonInString);
    } catch (JSONException ex) {
      try {
        new JSONArray(jsonInString);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Jsonp JSON.
   *
   * @param jsonInString the json in string
   * @return true, if successful
   */
  public static boolean jsonpJSON(String jsonInString) {
    try {
      JsonParser parser = Json.createParser(new StringReader(jsonInString));
      String key = null;
      String value = null;
      while (parser.hasNext()) {
        Event event = parser.next();
        switch (event) {
          case KEY_NAME:
            key = parser.getString();
            System.out.println(key);
            break;
          case VALUE_STRING:
            value = parser.getString();
            System.out.println(value);
            break;
          default:
            break;
        }
      }
    } catch (JSONException ex) {
      try {
        new JSONArray(jsonInString);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validate against JSO N schema.
   *
   * @param jsonSchemaFile the json schema file
   * @param jsonFile the json file
   * @throws ValidationException the validation exception
   */
  public void validateAgainstJSON_Schema(String jsonSchemaFile, String jsonFile)
      throws ValidationException {
    JSONObject jsonSchema =
        new JSONObject(new JSONTokener(JsonUtils.class.getResourceAsStream(jsonSchemaFile)));
    JSONObject jsonSubject =
        new JSONObject(new JSONTokener(JsonUtils.class.getResourceAsStream(jsonFile)));
    Schema schema = SchemaLoader.load(jsonSchema);
    schema.validate(jsonSubject);
  }

  /*
   * @SuppressWarnings("deprecation") public static JsonNode convertJsonFormat(JSONObject json) {
   * ObjectNode ret = JsonNodeFactory.instance.objectNode(); Iterator<String> iterator =
   * json.keys(); for (; iterator.hasNext();) { String key = iterator.next(); Object value; try {
   * value = json.get(key); } catch (JSONException e) { throw new RuntimeException(e); } if
   * (json.isNull(key)) ret.putNull(key); else if (value instanceof String) ret.put(key, (String)
   * value); else if (value instanceof Integer) ret.put(key, (Integer) value); else if (value
   * instanceof Long) ret.put(key, (Long) value); else if (value instanceof Double) ret.put(key,
   * (Double) value); else if (value instanceof Boolean) ret.put(key, (Boolean) value); else if
   * (value instanceof JSONObject) ret.put(key, convertJsonFormat((JSONObject) value)); else if
   * (value instanceof JSONArray) ret.put(key, convertJsonFormat((JSONArray) value)); else throw new
   * RuntimeException("not prepared for converting instance of class " + value.getClass()); } return
   * ret; }
   */
  /*
   * public static JsonNode convertJsonFormat(JSONArray json) { ArrayNode ret =
   * JsonNodeFactory.instance.arrayNode(); for (int i = 0; i < json.length(); i++) { Object value;
   * try { value = json.get(i); } catch (JSONException e) { throw new RuntimeException(e); } if
   * (json.isNull(i)) ret.addNull(); else if (value instanceof String) ret.add((String) value); else
   * if (value instanceof Integer) ret.add((Integer) value); else if (value instanceof Long)
   * ret.add((Long) value); else if (value instanceof Double) ret.add((Double) value); else if
   * (value instanceof Boolean) ret.add((Boolean) value); else if (value instanceof JSONObject)
   * ret.add(convertJsonFormat((JSONObject) value)); else if (value instanceof JSONArray)
   * ret.add(convertJsonFormat((JSONArray) value)); else throw new RuntimeException("not prepared
   * for converting instance of class " + value.getClass()); } return ret; }
   *
   * @param jsonObject the json object
   *
   * @return the JSON object
   */
  public static JSONObject replaceNodeNames(JSONObject jsonObject) {
    JSONObject resultObj = new JSONObject();
    Iterator<String> iterator = jsonObject.keys();
    for (; iterator.hasNext(); ) {
      String key = iterator.next();
      Object value;
      try {
        value = jsonObject.get(key);
      } catch (JSONException e) {
        // throw new RuntimeException(e);
        value = null;
      }
      if (value instanceof JSONObject) {
        JSONObject childObj = new JSONObject();
        childObj = (JSONObject) value;
        if (!ObjectUtils.isNull(childObj)) childObj = replaceNodeNames(childObj);
        resultObj.put(key.replace("Dto", ""), childObj);
      } else if (value instanceof JSONArray) {
        JSONArray jsonArray = new JSONArray();
        jsonArray = (JSONArray) value;
        if (!ObjectUtils.isNull(jsonArray)) replaceNodeNames(jsonArray);
        resultObj.put(key.replace("List", ""), jsonArray);

      } else
        // throw new RuntimeException("Unknown Element/Class/Instance Exception " +
        // value.getClass());
        resultObj.put(key.replace("Dto", "").replace("List", ""), value);
    }
    return resultObj;
  }

  /**
   * Replace node names.
   *
   * @param jsonArray the json array
   * @return the JSON array
   */
  public static JSONArray replaceNodeNames(JSONArray jsonArray) {
    JSONArray resultArray = new JSONArray();
    for (int i = 0; i < jsonArray.length(); i++) {
      Object value;
      try {
        value = jsonArray.get(i);
      } catch (JSONException e) {
        throw new RuntimeException(e);
      }
      if (value instanceof JSONObject) {
        value = replaceNodeNames((JSONObject) value);
        // jsonArray.remove(i);
        resultArray.put(i, value);
      } else if (value instanceof JSONArray) {
        value = replaceNodeNames((JSONArray) value);
        // jsonArray.remove(i);
        resultArray.put(i, value);
      } else
        // throw new RuntimeException("Unknown Element/Class/Instance Exception " +
        // value.getClass());
        resultArray.put(i, value);
    }
    return resultArray;
  }

  /**
   * Replace node names.
   *
   * @param jsonObject the json object
   * @return the json object
   * @throws JsonProcessingException the json processing exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static JsonObject replaceNodeNames(com.google.gson.JsonObject jsonObject)
      throws JsonProcessingException, IOException {
    // ObjectMapper objectMapper = new ObjectMapper();
    // JsonNode rootNode = objectMapper.readTree(jsonData);
    // Iterator<JsonNode> elements = rootNode.elements();
    // while (elements.hasNext()) {
    // JsonNode childNode = elements.next();
    // }
    // com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
    // JsonElement jsonTree = jsonParser.parse(jsonData).get;
    com.google.gson.JsonObject resultObj = new JsonObject();
    Iterator<String> iterator = jsonObject.keySet().iterator();
    for (; iterator.hasNext(); ) {
      String key = iterator.next();
      JsonElement value;
      try {
        value = jsonObject.get(key);
      } catch (JSONException e) {
        // throw new RuntimeException(e);
        value = null;
      }
      if (value.isJsonObject()) {
        com.google.gson.JsonObject childObj = new JsonObject();
        childObj = value.getAsJsonObject();
        if (!ObjectUtils.isNull(childObj)) childObj = replaceNodeNames(childObj);
        resultObj.add(key.replace("Dto", ""), childObj);
      } else if (value.isJsonArray()) {
        JsonArray jsonArray = new JsonArray();
        jsonArray = value.getAsJsonArray();
        if (!ObjectUtils.isNull(jsonArray)) replaceNodeNames(jsonArray);
        resultObj.add(key.replace("List", ""), jsonArray);

      } else
        // throw new RuntimeException("Unknown Element/Class/Instance Exception " +
        // value.getClass());
        resultObj.add(key.replace("Dto", "").replace("List", ""), value);
    }
    return resultObj;
  }

  /**
   * Replace node names.
   *
   * @param jsonArray the json array
   * @return the json array
   * @throws JsonProcessingException the json processing exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static JsonArray replaceNodeNames(com.google.gson.JsonArray jsonArray)
      throws JsonProcessingException, IOException {
    // ObjectMapper objectMapper = new ObjectMapper();
    // JsonNode rootNode = objectMapper.readTree(jsonData);
    // Iterator<JsonNode> elements = rootNode.elements();
    // while (elements.hasNext()) {
    // JsonNode childNode = elements.next();
    // }
    com.google.gson.JsonArray resultObj = new JsonArray();
    Iterator<JsonElement> iterator = jsonArray.iterator();
    for (; iterator.hasNext(); ) {
      JsonElement value = null;
      try {
        value = iterator.next();
      } catch (JSONException e) {
        // throw new RuntimeException(e);
        // value = null;
      }
      if (value.isJsonObject()) {
        com.google.gson.JsonObject childObj = new JsonObject();
        childObj = value.getAsJsonObject();
        if (!ObjectUtils.isNull(childObj)) childObj = replaceNodeNames(childObj);
        resultObj.add(childObj);
      } else if (value.isJsonArray()) {
        JsonArray childJsonArray = new JsonArray();
        childJsonArray = value.getAsJsonArray();
        if (!ObjectUtils.isNull(jsonArray)) replaceNodeNames(jsonArray);
        resultObj.add(childJsonArray);

      } else
        // throw new RuntimeException("Unknown Element/Class/Instance Exception " +
        // value.getClass());
        resultObj.add(value);
    }
    return resultObj;
  }

  /**
   * Translate json tag.
   *
   * @param key the key
   * @return the string
   */
  public static String translateJsonTag(String key) {
    switch (key) {
      case "headerDto":
        return Constants.WS_JSON_HEADER_TAG;
      case "bodyDto":
        return Constants.WS_JSON_BODY_TAG;
      case "systemResponseDto":
        return Constants.WS_JSON_SYSTEM_RESP_TAG;
      default:
        return "";
    }
  }

  /**
   * Convert json to hash map.
   *
   * @param jsonString the json string
   * @return the linked hash map
   */
  public static LinkedHashMap<?, ?> convertJsonToHashMap(String jsonString) {
    LinkedHashMap<?, ?> map =
        (LinkedHashMap<?, ?>) new Gson().fromJson(jsonString, LinkedHashMap.class);
    return map;
  }
}
