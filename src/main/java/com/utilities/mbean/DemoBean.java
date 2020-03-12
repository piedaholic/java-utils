package com.utilities.mbean;

// TODO: Auto-generated Javadoc
/** The Class DemoBean. */
public class DemoBean implements AppBean {

  /** Instantiates a new demo bean. */
  private DemoBean() {
    super();
  }

  /**
   * Instantiates a new demo bean.
   *
   * @param beanIdentifier the bean identifier
   */
  private DemoBean(String beanIdentifier) {
    this.beanIdentifier = beanIdentifier;
  }

  /** The bean identifier. */
  private String beanIdentifier = "";

  /** The count. */
  private int _count = 0;

  /** The minresp. */
  private double _minresp = 0.0D;

  /** The maxresp. */
  private double _maxresp = 0.0D;

  /** The avgresp. */
  private double _avgresp = 0.0D;

  /** The elapsedtime. */
  private double _elapsedtime = 0.0D;

  /** The Constant attributes. */
  private static final String attributes = "COUNT~MINRESP~MAXRESP~AVGRESP~";

  /** The Constant attributeDataType. */
  private static final String attributeDataType = "int~double~double~double~";

  /**
   * Gets the count.
   *
   * @return the count
   */
  public int getCount() {
    return this._count;
  }

  /**
   * Gets the min resp.
   *
   * @return the min resp
   */
  public double getMinResp() {
    return this._minresp;
  }

  /**
   * Gets the max resp.
   *
   * @return the max resp
   */
  public double getMaxResp() {
    return this._maxresp;
  }

  /**
   * Gets the avg resp.
   *
   * @return the avg resp
   */
  public double getAvgResp() {
    return this._avgresp;
  }

  /**
   * Gets the elapsed time.
   *
   * @return the elapsed time
   */
  public double getElapsedTime() {
    return this._elapsedtime;
  }

  /**
   * Sets the count.
   *
   * @param count the new count
   */
  public void setCount(int count) {
    this._count = count;
  }

  /**
   * Sets the elapsed time.
   *
   * @param elapsedtime the new elapsed time
   */
  public void setElapsedTime(int elapsedtime) {
    this._elapsedtime = (double) elapsedtime;
  }

  /**
   * Decrement.
   *
   * @return the int
   */
  public int decrement() {
    --this._count;
    return this._count;
  }

  /**
   * Increment.
   *
   * @return the int
   */
  public int increment() {
    ++this._count;
    return this._count;
  }

  /**
   * Update bean.
   *
   * @param mintime the mintime
   * @param maxtime the maxtime
   * @param avgtime the avgtime
   * @param count the count
   */
  public void updateBean(double mintime, double maxtime, double avgtime, int count) {
    if (this._minresp == 0.0D && this._maxresp == 0.0D && this._avgresp == 0.0D) {
      this._minresp = mintime;
      this._maxresp = maxtime;
      this._avgresp = avgtime;
      this._count = count;
    } else {
      this._avgresp =
          (this._avgresp * (double) this.getCount() + avgtime * (double) count)
              / (double) (this.getCount() + count);
      if (mintime < this._minresp) {
        this._minresp = mintime;
      }

      if (maxtime > this._maxresp) {
        this._maxresp = maxtime;
      }

      this._count = this.getCount() + count;
    }
  }

  /**
   * Reset.
   *
   * @return the int
   */
  public int reset() {
    this._count = 0;
    this._minresp = 0.0D;
    this._maxresp = 0.0D;
    this._avgresp = 0.0D;
    return this._count;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.utilities.mbean.AppBean#getBeanAttributes()
   */
  @Override
  public String getBeanAttributes() {
    return DemoBean.attributes;
  }

  /**
   * Sets the bean identifier.
   *
   * @param beanIdentifier the beanIdentifier to set
   */
  public void setBeanIdentifier(String beanIdentifier) {
    this.beanIdentifier = beanIdentifier;
  }

  /**
   * Gets the bean identifier.
   *
   * @return the beanIdentifier
   */
  @Override
  public String getBeanIdentifier() {
    return beanIdentifier;
  }

  /**
   * Gets the attribute data type.
   *
   * @return the attributedatatype
   */
  @Override
  public String getAttributeDataType() {
    return attributeDataType;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.utilities.mbean.AppBean#updateBean(java.lang.Object[])
   */
  @Override
  public void updateBean(Object[] args) {
    this.updateBean((Double) args[0], (Double) args[1], (Double) args[2], (Integer) args[3]);
  }
}
