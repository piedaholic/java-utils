package com.utilities.datatypes;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/** The Class TargetClass. */
public class TargetClass {

  /** The name. */
  private String name;

  /** The age. */
  private int age;

  /** The id. */
  private BigDecimal id;

  /**
   * Instantiates a new target class.
   *
   * @param id the id
   * @param age the age
   * @param name the name
   */
  public TargetClass(BigDecimal id, Integer age, String name) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  /** Instantiates a new target class. */
  public TargetClass() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the age.
   *
   * @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the age.
   *
   * @param age the new age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public BigDecimal getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(BigDecimal id) {
    this.id = id;
  }
}
