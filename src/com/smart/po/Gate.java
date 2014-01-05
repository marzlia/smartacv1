package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Gate implements Serializable {
	public Gate() {}

	private Long gateId; // 编号
	private String gateIp; // 地址
	private String gateMac; // 物理地址
	
	public String getGateMac() {
		return gateMac;
	}
	public void setGateMac(String gateMac) {
		this.gateMac = gateMac;
	}

	private String gateCampus; // 校区
	private String gateRoom; // 房间
	private String gateStatus; // 状态
	private Integer gateErrcode; // 错误码
	private Date gateRefresh; // 更新时间
	
	public Long getGateId() {
		return gateId;
	}
	public void setGateId(Long gateId) {
		this.gateId = gateId;
	}
	public String getGateIp() {
		return gateIp;
	}
	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}
	public String getGateCampus() {
		return gateCampus;
	}
	public void setGateCampus(String gateCampus) {
		this.gateCampus = gateCampus;
	}
	public String getGateRoom() {
		return gateRoom;
	}
	public void setGateRoom(String gateRoom) {
		this.gateRoom = gateRoom;
	}
	public String getGateStatus() {
		return gateStatus;
	}
	public void setGateStatus(String gateStatus) {
		this.gateStatus = gateStatus;
	}
	public Integer getGateErrcode() {
		return gateErrcode;
	}
	public void setGateErrcode(Integer gateErrcode) {
		this.gateErrcode = gateErrcode;
	}
	public Date getGateRefresh() {
		return gateRefresh;
	}
	public void setGateRefresh(Date gateRefresh) {
		this.gateRefresh = gateRefresh;
	}
}
