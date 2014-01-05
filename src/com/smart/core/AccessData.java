package com.smart.core;


import java.util.*;

public class AccessData {
	private MetaData metaData;

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	
	private boolean success;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	private List results;

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String timeGrain;
	private String timeFormat;
	
	
	
	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getTimeGrain() {
		return timeGrain;
	}

	public void setTimeGrain(String timeGrain) {
		this.timeGrain = timeGrain;
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
