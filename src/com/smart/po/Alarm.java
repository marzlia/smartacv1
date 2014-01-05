package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Alarm implements Serializable {
	public Alarm() {}

	private Long alarmId; // 编号
	private Long gateId; // 闸机
	private Date alarmDatetime; // 日期
	private Integer alarmType; // 类型
	private String alarmCode;
	public String getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}

	private String alarmComment; // 注释
	private String gateIp; // 地址
	private String gateCampus; // 校区
	private String gateRoom; // 房间
	
	
	public Long getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	public Long getGateId() {
		return gateId;
	}
	public void setGateId(Long gateId) {
		this.gateId = gateId;
	}
	public Date getAlarmDatetime() {
		return alarmDatetime;
	}
	public void setAlarmDatetime(Date alarmDatetime) {
		this.alarmDatetime = alarmDatetime;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmComment() {
		return alarmComment;
	}
	public void setAlarmComment(String alarmComment) {
		this.alarmComment = alarmComment;
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
	
	
}
