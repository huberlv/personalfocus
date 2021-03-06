package com.kingway.model;

// Generated 2010-10-1 0:21:31 by Hibernate Tools 3.3.0.GA

import java.util.Date;

/**
 * UserDefaultReceiveInfo generated by hbm2java
 */
public class UserDefaultReceiveInfo implements java.io.Serializable {

	private Long defaultReceiveId;
	private UserInfo userInfo;
	private Date startTime;
	private Date stopTime;
	private int receiveType;
	private Date sendFrequency;
	private int maxReceiveNum;

	public UserDefaultReceiveInfo() {
	}

	public UserDefaultReceiveInfo(UserInfo userInfo, Date startTime,
			Date stopTime, int receiveType, Date sendFrequency,
			int maxReceiveNum) {
		this.userInfo = userInfo;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.receiveType = receiveType;
		this.sendFrequency = sendFrequency;
		this.maxReceiveNum = maxReceiveNum;
	}

	public Long getDefaultReceiveId() {
		return this.defaultReceiveId;
	}

	public void setDefaultReceiveId(Long defaultReceiveId) {
		this.defaultReceiveId = defaultReceiveId;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public int getReceiveType() {
		return this.receiveType;
	}

	public void setReceiveType(int receiveType) {
		this.receiveType = receiveType;
	}

	public Date getSendFrequency() {
		return this.sendFrequency;
	}

	public void setSendFrequency(Date sendFrequency) {
		this.sendFrequency = sendFrequency;
	}

	public int getMaxReceiveNum() {
		return this.maxReceiveNum;
	}

	public void setMaxReceiveNum(int maxReceiveNum) {
		this.maxReceiveNum = maxReceiveNum;
	}

}
