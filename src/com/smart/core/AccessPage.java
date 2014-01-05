package com.smart.core;

import java.util.List;

public class AccessPage extends Page {
	
	private List filter;
	
	
	public List getFilter() {
		return filter;
	}
	public void setFilter(List filter) {
		this.filter = filter;
	}
	
	private String startDate;
	private String endDate;
	
	private String key;
	private List valueList;
	private List parentList;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List getValueList() {
		return valueList;
	}
	public void setValueList(List valueList) {
		this.valueList = valueList;
	}
	public List getParentList() {
		return parentList;
	}
	public void setParentList(List parentList) {
		this.parentList = parentList;
	}
}
