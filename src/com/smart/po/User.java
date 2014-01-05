package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class User implements Serializable {
	public User() {}

	private Integer userId; // 编号
	private String userName; // 姓名
	private String userPassword; // 密码
	private Integer groupId; // 用户组
	private String groupName; // 用户组名
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
