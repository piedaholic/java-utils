package com.utilities.silentODT;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SilentODTEncryptDecrypt 
{
  public static void main(String args[])
    throws Exception
  {
	String arg0 = "oraclefinancials";
	String arg1 = "RABOUPG1";
	String arg2 = "jdbc:oracle:thin:@10.184.155.191:1521:RABODBD";
	String arg3 = "RABOUPG1";
	String arg4 = "hp7ejgc9K6sJv5QlICmQPJMmAEGoPtoFNO56p4UBXc/8ozzzeK/KkJt0ySCDLLy3";
    System.out.println("Encrypted DB Password Value:" + encryptPass(arg1, arg0));
    System.out.println("Encrypted DB URL Value:" + encryptPass(arg2, arg0));
    System.out.println("Encrypted ODT Login Password Value:" + encryptPass(arg3, arg0));
    System.out.println("Decrypted Value: " + decryptPass(arg4, arg0));
  }
  
  public static String encryptPass(String pass, String key)
    throws Exception
  {
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(1, aesKey);
    byte[] encrypted = cipher.doFinal(pass.getBytes());
    String string = new String(Base64.getEncoder().encode(encrypted));
    return string;
  }
  
  public static String decryptPass(String encrypted, String key)
    throws Exception
  {
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(2, aesKey);
    String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes())));
    return decrypted;
  }
}
