package com.smart.po;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public class Category implements Serializable {
	private String parentKey;
	private String parentValue;
	private List parentList;
	
	public List getParentList() {
		return parentList;
	}
	public void setParentList(List parentList) {
		this.parentList = parentList;
	}
	private String key;
	
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	public String getParentValue() {
		return parentValue;
	}
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
