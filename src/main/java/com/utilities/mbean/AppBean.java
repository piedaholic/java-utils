package com.utilities.mbean;

public interface AppBean {

    public String getBeanAttributes();

    public String getBeanIdentifier();

    public String getAttributeDataType();

    public void updateBean(Object[] args);
}
