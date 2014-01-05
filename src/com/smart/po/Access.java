package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Access implements Serializable {
	public Access() {}

	private Long accessId; // 编号
	private Long cardId; // 卡号
	private String cardNo; // 标记号
	private String cardName; // 姓名
	private String cardSex; // 性别
	private String cardType; // 身份
	private String cardClass; // 班级
	private String cardMajor; // 专业
	private String cardDepartment; // 系部
	private String cardInstitute; // 学院
	private Long gateId; // 闸机号
	private String gateIp; // 地址
	private String gateCampus; // 校区
	private String gateRoom; // 房间
	private Date accessDatetime; // 通过时间
	private Integer accessDirection; // 通过方向
	private Integer cardGrade; // 年级
	public Long getAccessId() {
		return accessId;
	}
	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardSex() {
		return cardSex;
	}
	public void setCardSex(String cardSex) {
		this.cardSex = cardSex;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardClass() {
		return cardClass;
	}
	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}
	public String getCardMajor() {
		return cardMajor;
	}
	public void setCardMajor(String cardMajor) {
		this.cardMajor = cardMajor;
	}
	public String getCardDepartment() {
		return cardDepartment;
	}
	public void setCardDepartment(String cardDepartment) {
		this.cardDepartment = cardDepartment;
	}
	public String getCardInstitute() {
		return cardInstitute;
	}
	public void setCardInstitute(String cardInstitute) {
		this.cardInstitute = cardInstitute;
	}
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
	public Date getAccessDatetime() {
		return accessDatetime;
	}
	public void setAccessDatetime(Date accessDatetime) {
		this.accessDatetime = accessDatetime;
	}
	public Integer getAccessDirection() {
		return accessDirection;
	}
	public void setAccessDirection(Integer accessDirection) {
		this.accessDirection = accessDirection;
	}
	public Integer getCardGrade() {
		return cardGrade;
	}
	public void setCardGrade(Integer cardGrade) {
		this.cardGrade = cardGrade;
	}
	
	
}
