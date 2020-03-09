package com.utilities.datatypes;

public class BooleanUtility {
   public static boolean getBoolean(String inString) {
      if (inString != null) {
         return inString.equalsIgnoreCase("Y");
      } else {
         return false;
      }
   }

   public static String getString(boolean flag) {
      return flag ? "Y" : "N";
   }
}
