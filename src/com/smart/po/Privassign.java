package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Privassign implements Serializable {
	public Privassign() {}

	private Long privassignId; // 编号
	private Integer groupId; // 用户组
	private Integer privId; // 权限
	private String privName; // 名称
	private String privDesc; // 描述
	
	public Long getPrivassignId() {
		return privassignId;
	}
	public void setPrivassignId(Long privassignId) {
		this.privassignId = privassignId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
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
