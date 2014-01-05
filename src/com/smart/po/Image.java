package com.smart.po;

import java.io.Serializable;

import java.util.Date;

@SuppressWarnings("serial")
public class Image implements Serializable {
	public Image() {}

	private String imageId; // 图片ID
	private Long alarmId; // 警告ID
	private String imageUrl; // 图片地址
	private Double imageSize; // 图片大小
	private Date imageMdate; // 最后编辑日期
	
	
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	public Long getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Double getImageSize() {
		return imageSize;
	}
	public void setImageSize(Double imageSize) {
		this.imageSize = imageSize;
	}
	public Date getImageMdate() {
		return imageMdate;
	}
	public void setImageMdate(Date imageMdate) {
		this.imageMdate = imageMdate;
	}
}
