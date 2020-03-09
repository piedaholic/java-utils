package com.utilities.mbean;

public class DemoBean implements AppBean {
    private DemoBean() {
	super();
    }

    private DemoBean(String beanIdentifier) {
	this.beanIdentifier = beanIdentifier;
    }

    private String beanIdentifier = "";
    private int _count = 0;
    private double _minresp = 0.0D;
    private double _maxresp = 0.0D;
    private double _avgresp = 0.0D;
    private double _elapsedtime = 0.0D;
    private static final String attributes = "COUNT~MINRESP~MAXRESP~AVGRESP~";
    private static final String attributeDataType = "int~double~double~double~";

    public int getCount() {
	return this._count;
    }

    public double getMinResp() {
	return this._minresp;
    }

    public double getMaxResp() {
	return this._maxresp;
    }

    public double getAvgResp() {
	return this._avgresp;
    }

    public double getElapsedTime() {
	return this._elapsedtime;
    }

    public void setCount(int count) {
	this._count = count;
    }

    public void setElapsedTime(int elapsedtime) {
	this._elapsedtime = (double) elapsedtime;
    }

    public int decrement() {
	--this._count;
	return this._count;
    }

    public int increment() {
	++this._count;
	return this._count;
    }

    public void updateBean(double mintime, double maxtime, double avgtime, int count) {
	if (this._minresp == 0.0D && this._maxresp == 0.0D && this._avgresp == 0.0D) {
	    this._minresp = mintime;
	    this._maxresp = maxtime;
	    this._avgresp = avgtime;
	    this._count = count;
	} else {
	    this._avgresp = (this._avgresp * (double) this.getCount() + avgtime * (double) count)
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

    public int reset() {
	this._count = 0;
	this._minresp = 0.0D;
	this._maxresp = 0.0D;
	this._avgresp = 0.0D;
	return this._count;
    }

    @Override
    public String getBeanAttributes() {
	return DemoBean.attributes;
    }

    /**
     * @param beanIdentifier
     *            the beanIdentifier to set
     */
    public void setBeanIdentifier(String beanIdentifier) {
	this.beanIdentifier = beanIdentifier;
    }

    /**
     * @return the beanIdentifier
     */
    @Override
    public String getBeanIdentifier() {
	return beanIdentifier;
    }

    /**
     * @return the attributedatatype
     */
    @Override
    public String getAttributeDataType() {
	return attributeDataType;
    }

    @Override
    public void updateBean(Object[] args) {
	this.updateBean((Double) args[0], (Double) args[1], (Double) args[2], (Integer) args[3]);
    }
}
