package com.utilities.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils {
    public byte[] serializeObject(Object object) {
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	try {
	    ObjectOutputStream stream = new ObjectOutputStream(bos);
	    stream.writeObject(object);
	} catch (IOException localIOException) {
	}
	return bos.toByteArray();
    }

    public Object getObject(byte[] byteArray) throws Exception {
	ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));

	Object obj = ois.readObject();
	ois.close();
	return obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T checkForNull(T input) {
	T output = null;
	if (input != null) {
	    if (input instanceof String)
		output = (T) ((String) input).trim();
	}
	return output;
    }
}
