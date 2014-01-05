package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Card implements Serializable {
	public Card() {}

	private Long cardId; // 卡号
	private String cardNo; // 标记号
	private String cardName; // 姓名
	private String cardSex; // 性别
	private String cardType; // 身份
	private String cardClass; // 班级
	private String cardMajor; // 专业
	private String cardDepartment; // 系部
	private String cardInstitute; // 学院
	private Integer cardGrade; // 年级
	private String cardComment; // 注释
	private Integer cardUpdate; // 更新标记
	private Integer cardStatus; // 卡状态
	
	
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
	public Integer getCardGrade() {
		return cardGrade;
	}
	public void setCardGrade(Integer cardGrade) {
		this.cardGrade = cardGrade;
	}
	public String getCardComment() {
		return cardComment;
	}
	public void setCardComment(String cardComment) {
		this.cardComment = cardComment;
	}
	public Integer getCardUpdate() {
		return cardUpdate;
	}
	public void setCardUpdate(Integer cardUpdate) {
		this.cardUpdate = cardUpdate;
	}
	public Integer getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}
}
