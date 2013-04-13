package com.kingway.model.struct;

import java.util.Date;

import com.kingway.model.UserModuleSubgroupInfo;

public class ModuleInfoManageList {
	private String websiteName; // 网站名
	private String userModuleName; // 用户栏目名
	private long userModuleId; // 用户模块ID
	private Date mailStartTime;
	private Date mailStopTime;
	private String mailSf;
	private String mailFrequencyType;
	private Date messageStartTime;
	private Date messageStopTime;
	private int messageMax;
	private String messageSf;
	private int messageReceiveType;
	private String messageFrequencyType;
	private boolean email = false; // 是否发送邮件
	private int message = 0; // 是否发送短信
	private UserModuleSubgroupInfo subgroup;

	public ModuleInfoManageList(String websiteName, String userModuleName,
			long userModuleId, Date mailStartTime, Date mailStopTime,
			String mailSf, String mailFrequencyType, Date messageStartTime,
			Date messageStopTime, int messageMax, String messageSf,
			int messageReceiveType, String messageFrequencyType, boolean email,
			int message, UserModuleSubgroupInfo subgroup) {
		super();
		this.websiteName = websiteName;
		this.userModuleName = userModuleName;
		this.userModuleId = userModuleId;
		this.mailStartTime = mailStartTime;
		this.mailStopTime = mailStopTime;
		this.mailSf = mailSf;
		this.mailFrequencyType = mailFrequencyType;
		this.messageStartTime = messageStartTime;
		this.messageStopTime = messageStopTime;
		this.messageMax = messageMax;
		this.messageSf = messageSf;
		this.messageReceiveType = messageReceiveType;
		this.messageFrequencyType = messageFrequencyType;
		this.email = email;
		this.message = message;
		this.subgroup = subgroup;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getUserModuleName() {
		return userModuleName;
	}

	public void setUserModuleName(String userModuleName) {
		this.userModuleName = userModuleName;
	}

	public long getUserModuleId() {
		return userModuleId;
	}

	public void setUserModuleId(long userModuleId) {
		this.userModuleId = userModuleId;
	}

	public Date getMailStartTime() {
		return mailStartTime;
	}

	public void setMailStartTime(Date mailStartTime) {
		this.mailStartTime = mailStartTime;
	}

	public Date getMailStopTime() {
		return mailStopTime;
	}

	public void setMailStopTime(Date mailStopTime) {
		this.mailStopTime = mailStopTime;
	}

	public String getMailSf() {
		return mailSf;
	}

	public void setMailSf(String mailSf) {
		this.mailSf = mailSf;
	}

	public String getMailFrequencyType() {
		return mailFrequencyType;
	}

	public void setMailFrequencyType(String mailFrequencyType) {
		this.mailFrequencyType = mailFrequencyType;
	}

	public Date getMessageStartTime() {
		return messageStartTime;
	}

	public void setMessageStartTime(Date messageStartTime) {
		this.messageStartTime = messageStartTime;
	}

	public Date getMessageStopTime() {
		return messageStopTime;
	}

	public void setMessageStopTime(Date messageStopTime) {
		this.messageStopTime = messageStopTime;
	}

	public int getMessageMax() {
		return messageMax;
	}

	public void setMessageMax(int messageMax) {
		this.messageMax = messageMax;
	}

	public String getMessageSf() {
		return messageSf;
	}

	public void setMessageSf(String messageSf) {
		this.messageSf = messageSf;
	}

	public int getMessageReceiveType() {
		return messageReceiveType;
	}

	public void setMessageReceiveType(int messageReceiveType) {
		this.messageReceiveType = messageReceiveType;
	}

	public String getMessageFrequencyType() {
		return messageFrequencyType;
	}

	public void setMessageFrequencyType(String messageFrequencyType) {
		this.messageFrequencyType = messageFrequencyType;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public UserModuleSubgroupInfo getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(UserModuleSubgroupInfo subgroup) {
		this.subgroup = subgroup;
	}

}
