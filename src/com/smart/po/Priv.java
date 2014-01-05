package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Priv implements Serializable {
	public Priv() {}

	private Integer privId; // 编号
	private String privName; // 名称
	private String privDesc; // 描述
	public Integer getPrivId() {
		return privId;
	}
	public void setPrivId(Integer privId) {
		this.privId = privId;
	}
	public String getPrivName() {
		return privName;
	}
	public void setPrivName(String privName) {
		this.privName = privName;
	}
	public String getPrivDesc() {
		return privDesc;
	}
	public void setPrivDesc(String privDesc) {
		this.privDesc = privDesc;
	}
	
	
}
