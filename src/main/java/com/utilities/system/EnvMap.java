package com.utilities.system;

import java.util.Map;

// TODO: Auto-generated Javadoc
/** The Class EnvMap. */
public class EnvMap {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    Map<String, String> env = System.getenv();
    for (String envName : env.keySet()) {
      System.out.format("%s=%s%n", envName, env.get(envName));
    }
  }
}
