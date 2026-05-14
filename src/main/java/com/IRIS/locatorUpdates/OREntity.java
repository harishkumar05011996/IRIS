package com.IRIS.locatorUpdates;

public class OREntity {

    private String attributeType;
    private String value;
    private String type;
    private boolean isShadow;
    private String recommendedValue;
    private String logicalName;
    private String fileName;

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public boolean getIsShadow() {
        return isShadow;
    }

    public void setIsShadow(boolean isShadow) {
        this.isShadow = isShadow;
    }

    public enum Attribute {
        ID, CSS, NAME, TAGNAME, LINKTEXT, PARTIALLINKTEXT, XPATH, CLASSNAME
    }

    public String getRecommendedValue() {
        return recommendedValue;
    }

    public void setRecommendedValue(String recommendedValue) {
        this.recommendedValue = recommendedValue;
    }

    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
