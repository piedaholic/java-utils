package com.utilities.mbean;

// TODO: Auto-generated Javadoc
/** The Interface AppBean. */
public interface AppBean {

  /**
   * Gets the bean attributes.
   *
   * @return the bean attributes
   */
  public String getBeanAttributes();

  /**
   * Gets the bean identifier.
   *
   * @return the bean identifier
   */
  public String getBeanIdentifier();

  /**
   * Gets the attribute data type.
   *
   * @return the attribute data type
   */
  public String getAttributeDataType();

  /**
   * Update bean.
   *
   * @param args the args
   */
  public void updateBean(Object[] args);
}
