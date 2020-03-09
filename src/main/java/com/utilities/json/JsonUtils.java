/**
 * 
 */
package com.utilities.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.dto.AppRootDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.util.ObjectUtils;
import com.util.StringUtils;

/**
 * @author hpsingh
 *
 */
public class JsonUtils {
    public static void main(String[] args) {
	JsonUtils jsonUtils = new JsonUtils();
	File file = jsonUtils.getFileFromResources("CreateUser.json");
	JSONObject jsonObject = JsonUtils.readJsonFile(file.getPath());
	JsonUtils.populateBodyDto(jsonObject);
    }

    public static AppRootDto populateBodyDto(JSONObject req) {
	AppRootDto appRootDto = null;
	try {
	    JSONObject body = (JSONObject) req.get("body");
	    JSONArray names = body.names();

	    appRootDto = (AppRootDto) Class
		    .forName("com.dto." + StringUtils.toCamelCase(names.get(0).toString()) + "Dto").getConstructor()
		    .newInstance();
	    JSONObject dataBlock = (JSONObject) body.get(names.get(0).toString());
	    convertJsonFormat(dataBlock, appRootDto);
	    /*
	     * try { ObjectMapper mapper = new ObjectMapper(); appRootDto =
	     * mapper.readValue(new TreeTraversingParser(jsonNode), appRootDto.getClass());
	     * } catch (Exception e) { }
	     */
	} catch (Exception e) {
	    System.out.println(e);
	}
	return appRootDto;
    }

    @SuppressWarnings("deprecation")
    static JsonNode convertJsonFormat(JSONObject json) {
	ObjectNode ret = JsonNodeFactory.instance.objectNode();

	Iterator<String> iterator = json.keys();
	for (; iterator.hasNext();) {
	    String key = iterator.next();
	    Object value;
	    try {
		value = json.get(key);
	    } catch (JSONException e) {
		throw new RuntimeException(e);
	    }
	    if (json.isNull(key))
		ret.putNull(key);
	    else if (value instanceof String)
		ret.put(key, (String) value);
	    else if (value instanceof Integer)
		ret.put(key, (Integer) value);
	    else if (value instanceof Long)
		ret.put(key, (Long) value);
	    else if (value instanceof Double)
		ret.put(key, (Double) value);
	    else if (value instanceof Boolean)
		ret.put(key, (Boolean) value);
	    else if (value instanceof JSONObject)
		ret.put(key, convertJsonFormat((JSONObject) value));
	    else if (value instanceof JSONArray)
		ret.put(key, convertJsonFormat((JSONArray) value));
	    else
		throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	}
	return ret;
    }

    static JsonNode convertJsonFormat(JSONArray json) {
	ArrayNode ret = JsonNodeFactory.instance.arrayNode();
	for (int i = 0; i < json.length(); i++) {
	    Object value;
	    try {
		value = json.get(i);
	    } catch (JSONException e) {
		throw new RuntimeException(e);
	    }
	    if (json.isNull(i))
		ret.addNull();
	    else if (value instanceof String)
		ret.add((String) value);
	    else if (value instanceof Integer)
		ret.add((Integer) value);
	    else if (value instanceof Long)
		ret.add((Long) value);
	    else if (value instanceof Double)
		ret.add((Double) value);
	    else if (value instanceof Boolean)
		ret.add((Boolean) value);
	    else if (value instanceof JSONObject)
		ret.add(convertJsonFormat((JSONObject) value));
	    else if (value instanceof JSONArray)
		ret.add(convertJsonFormat((JSONArray) value));
	    else
		throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	}
	return ret;
    }

    @SuppressWarnings("deprecation")
    static JsonNode convertJsonFormat(JSONObject json, AppRootDto appRootDto) {
	ObjectNode ret = JsonNodeFactory.instance.objectNode();
	Method method = null;
	Iterator<String> iterator = json.keys();
	for (; iterator.hasNext();) {
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
		if (!ObjectUtils.doesMethodExist(appRootDto, methodName)) {
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
	     * else if (value instanceof String) ret.add((String) value); else if (value
	     * instanceof Integer) ret.add((Integer) value); else if (value instanceof Long)
	     * ret.add((Long) value); else if (value instanceof Double) ret.add((Double)
	     * value); else if (value instanceof Boolean) ret.add((Boolean) value);
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
	     * else if (value instanceof JSONObject) ret.put(key,
	     * convertJsonFormat((JSONObject) value)); else if (value instanceof JSONArray)
	     * ret.put(key, convertJsonFormat((JSONArray) value));
	     */
	    else if (value instanceof JSONObject) {
		if (ObjectUtils.doesMethodExist(appRootDto, methodName + "Dto")) {
		    methodName = methodName + "Dto";
		}

		try {
		    Method objMethod = appRootDto.getClass().getDeclaredMethod(methodName.replaceFirst("set", "get"));
		    Class<?> returnParam = objMethod.getReturnType();
		    Object childObj = returnParam.getConstructor().newInstance();
		    ret.put(key, convertJsonFormat((JSONObject) value, (AppRootDto) childObj));
		    method = appRootDto.getClass().getDeclaredMethod(methodName, returnParam);
		    method.invoke(appRootDto, childObj);
		} catch (Exception e) {
		    System.out.println(e);
		}
	    } else if (value instanceof JSONArray) {
		ret.put(key, convertJsonFormat((JSONArray) value, appRootDto, key));
	    } else
		throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	}
	return ret;
    }

    static JsonNode convertJsonFormat(JSONArray json, AppRootDto appRootDto, String key) {
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
	     * else if (value instanceof String) ret.add((String) value); else if (value
	     * instanceof Integer) ret.add((Integer) value); else if (value instanceof Long)
	     * ret.add((Long) value); else if (value instanceof Double) ret.add((Double)
	     * value); else if (value instanceof Boolean) ret.add((Boolean) value);
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
	     * else if (value instanceof JSONObject) ret.add(convertJsonFormat((JSONObject)
	     * value)); else if (value instanceof JSONArray)
	     * ret.add(convertJsonFormat((JSONArray) value));
	     */
	    else if (value instanceof JSONObject) {
		String objClassName = key.substring(0, key.length() - 1);
		objClassName = "com.dto." + objClassName.substring(0, 1).toUpperCase() + objClassName.substring(1)
			+ "Dto";
		Object obj = null;
		try {
		    obj = Class.forName(objClassName).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | NoSuchMethodException | SecurityException
			| ClassNotFoundException e) {
		}
		ret.add(convertJsonFormat((JSONObject) value, (AppRootDto) obj));
		objList.add(obj);

	    } else if (value instanceof JSONArray) {
		ret.add(convertJsonFormat((JSONArray) value));
	    } else
		throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	}
	Method method;
	try {
	    method = appRootDto.getClass().getDeclaredMethod(
		    "set" + key.substring(0, 1).toUpperCase() + key.substring(1) + "List", List.class);
	    method.invoke(appRootDto, objList);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	}

	return ret;
    }

    /*
     * public static org.json.simple.JSONObject readJsonFile(String file) {
     * org.json.simple.JSONObject jsonObject = null; try {
     * org.json.simple.parser.JSONParser parser = new JSONParser(); Object obj =
     * parser.parse(new FileReader(file)); jsonObject = (org.json.simple.JSONObject)
     * obj; } catch (FileNotFoundException e) {
     * System.out.println("File Not Found"); } catch (IOException e) {
     * System.out.println("I/O Exception Occured"); } catch (ParseException e) {
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

    public static void replaceNodeNames(JSONObject jsonObject) {
	Iterator<String> iterator = jsonObject.keys();
	for (; iterator.hasNext();) {
	    String key = iterator.next();
	    Object value;
	    try {
		value = jsonObject.get(key);
	    } catch (JSONException e) {
		throw new RuntimeException(e);
	    }
	    if (value instanceof JSONObject) {
		jsonObject.put(key.replace("Dto", ""), jsonObject.remove(key));
		replaceNodeNames((JSONObject) value);
	    } else if (value instanceof JSONArray) {
		jsonObject.put(key.replace("List", ""), jsonObject.remove(key));
		replaceNodeNames((JSONObject) value);
	    } else
		throw new RuntimeException("Unknown Element/Class/Instance Exception " + value.getClass());
	}
    }
}
