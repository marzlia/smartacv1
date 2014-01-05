package com.smart.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Aggregation implements Serializable {
	public Aggregation (){}
	
	private String aggName;
	private Long aggCount;
	
	public String getAggName() {
		return aggName;
	}
	public void setAggName(String aggName) {
		this.aggName = aggName;
	}
	public Long getAggCount() {
		return aggCount;
	}
	public void setAggCount(Long aggCount) {
		this.aggCount = aggCount;
	}
	
	
}
