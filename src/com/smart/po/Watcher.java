package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Watcher implements Serializable {
	public Watcher() {}

	private Integer watcherId; // 编号
	private String watcherIp; // 地址
	private String watcherMac; // 物理地址
	
	public String getWatcherMac() {
		return watcherMac;
	}
	public void setWatcherMac(String watcherMac) {
		this.watcherMac = watcherMac;
	}

	private String watcherCampus; // 校区
	private String watcherRoom; // 房间
	
	public Integer getWatcherId() {
		return watcherId;
	}
	public void setWatcherId(Integer watcherId) {
		this.watcherId = watcherId;
	}
	public String getWatcherIp() {
		return watcherIp;
	}
	public void setWatcherIp(String watcherIp) {
		this.watcherIp = watcherIp;
	}
	public String getWatcherCampus() {
		return watcherCampus;
	}
	public void setWatcherCampus(String watcherCampus) {
		this.watcherCampus = watcherCampus;
	}
	public String getWatcherRoom() {
		return watcherRoom;
	}
	public void setWatcherRoom(String watcherRoom) {
		this.watcherRoom = watcherRoom;
	}
}
