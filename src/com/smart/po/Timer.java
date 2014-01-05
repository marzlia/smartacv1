package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Timer implements Serializable {
	public Timer() {}

	private Long timerId; // 编号
	private Date timerBegin; // 开始时间
	private Date timerEnd; // 结束时间
	private Integer timerEnable; // 激活
	private Integer timerCreatenew; // 是否创建新进程
	private String timerTask; // 定时任务
	private Integer cycleId; // 循环方式
	private String cycleName; // 循环名称
	
	public Long getTimerId() {
		return timerId;
	}
	public void setTimerId(Long timerId) {
		this.timerId = timerId;
	}
	
	public Date getTimerBegin() {
		return timerBegin;
	}
	public void setTimerBegin(Date timerBegin) {
		this.timerBegin = timerBegin;
	}
	public Date getTimerEnd() {
		return timerEnd;
	}
	public void setTimerEnd(Date timerEnd) {
		this.timerEnd = timerEnd;
	}
	public Integer getTimerEnable() {
		return timerEnable;
	}
	public void setTimerEnable(Integer timerEnable) {
		this.timerEnable = timerEnable;
	}
	public Integer getTimerCreatenew() {
		return timerCreatenew;
	}
	public void setTimerCreatenew(Integer timerCreatenew) {
		this.timerCreatenew = timerCreatenew;
	}
	public String getTimerTask() {
		return timerTask;
	}
	public void setTimerTask(String timerTask) {
		this.timerTask = timerTask;
	}
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
