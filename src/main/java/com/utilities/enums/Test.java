package com.utilities.enums;

// TODO: Auto-generated Javadoc
/** The Class Test. */
public class Test {

  /** The os enum. */
  private OSEnum osEnum;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    System.out.println(OSEnum.contains("wind"));
    Test test = new Test();
    test.setEnum(OSEnum.valueOf("J"));
    System.out.println(test.getEnum());
  }

  /**
   * Sets the enum.
   *
   * @param osEnum the new enum
   */
  public void setEnum(OSEnum osEnum) {
    this.osEnum = osEnum;
  }

  /**
   * Gets the enum.
   *
   * @return the enum
   */
  public OSEnum getEnum() {
    return this.osEnum;
  }
}
