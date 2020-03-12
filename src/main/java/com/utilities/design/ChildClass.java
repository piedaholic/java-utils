package com.utilities.design;

// TODO: Auto-generated Javadoc
/** The Class ChildClass. */
public class ChildClass extends ParentClass implements RootInterface {

  /** The id. */
  private String id;

  /*
   * (non-Javadoc)
   *
   * @see com.utilities.design.ParentClass#getId()
   */
  public String getId() {
    return id;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.utilities.design.ParentClass#setId(java.lang.String)
   */
  public void setId(String id) {
    // this.id = id;
    this.id = this.id == null ? id : this.id;
  }
}
