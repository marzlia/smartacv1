package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Cycle implements Serializable {
	public Cycle() {}

	private Integer cycleId; // 序号
	private String cycleName; // 循环名称
	
	public Integer getCycleId() {
		return cycleId;
	}
	public void setCycleId(Integer cycleId) {
		this.cycleId = cycleId;
	}
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
}
