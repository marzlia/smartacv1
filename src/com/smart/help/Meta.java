package com.smart.help;

public class Meta {
	public Meta(){};
	private String eName; 		// 字段英文名
	private String isNull; 		// 
	private String dataType;	//
	private Integer maxLength;
	private String colType;
	private String cName;		// 中文名
	
	public String getEName() {
		return eName;
	}
	public void setEName(String name) {
		eName = name;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getCName() {
		return cName;
	}
	public void setCName(String name) {
		cName = name;
	}
	
	
}
