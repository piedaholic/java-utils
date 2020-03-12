package com.utilities.test;

// TODO: Auto-generated Javadoc
/** The Class TestString. */
public class TestString {

  /**
   * Gets the class name.
   *
   * @param blockName the block name
   * @return the class name
   */
  public static String getClassName(String blockName) {
    String res = "";
    if ((blockName != null) && (blockName != "")) {
      blockName = blockName.toLowerCase();
      String[] resAr = blockName.split("_", -1);
      for (int i = 0; i < resAr.length; i++) {
        res =
            res
                + resAr[i].replaceFirst(
                    new StringBuilder().append(resAr[i].charAt(0)).append("").toString(),
                    new StringBuilder()
                        .append(resAr[i].charAt(0))
                        .append("")
                        .toString()
                        .toUpperCase());
      }
    }
    return res;
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String master = "BLK_FACILITY";
    master = master.substring(4, master.length());
    System.out.println(master);
    System.out.println(getClassName(master));
  }
}
