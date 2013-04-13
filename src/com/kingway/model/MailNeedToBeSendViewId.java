package com.kingway.model;

// Generated 2010-10-25 22:38:40 by Hibernate Tools 3.3.0.GA

import java.util.Date;

/**
 * MailNeedToBeSendViewId generated by hbm2java
 */
public class MailNeedToBeSendViewId implements java.io.Serializable {

	private long userId;
	private String userName;
	private String mail;
	private String userModuleName;
	private String websiteName;
	private long contentId;
	private Date updateTime;

	public MailNeedToBeSendViewId() {
	}

	public MailNeedToBeSendViewId(long userId, String userName,
			String userModuleName, String websiteName, long contentId,
			Date updateTime) {
		this.userId = userId;
		this.userName = userName;
		this.userModuleName = userModuleName;
		this.websiteName = websiteName;
		this.contentId = contentId;
		this.updateTime = updateTime;
	}

	public MailNeedToBeSendViewId(long userId, String userName, String mail,
			String userModuleName, String websiteName, long contentId,
			Date updateTime) {
		this.userId = userId;
		this.userName = userName;
		this.mail = mail;
		this.userModuleName = userModuleName;
		this.websiteName = websiteName;
		this.contentId = contentId;
		this.updateTime = updateTime;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUserModuleName() {
		return this.userModuleName;
	}

	public void setUserModuleName(String userModuleName) {
		this.userModuleName = userModuleName;
	}

	public String getWebsiteName() {
		return this.websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public long getContentId() {
		return this.contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MailNeedToBeSendViewId))
			return false;
		MailNeedToBeSendViewId castOther = (MailNeedToBeSendViewId) other;

		return (this.getUserId() == castOther.getUserId())
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())))
				&& ((this.getMail() == castOther.getMail()) || (this.getMail() != null
						&& castOther.getMail() != null && this.getMail()
						.equals(castOther.getMail())))
				&& ((this.getUserModuleName() == castOther.getUserModuleName()) || (this
						.getUserModuleName() != null
						&& castOther.getUserModuleName() != null && this
						.getUserModuleName().equals(
								castOther.getUserModuleName())))
				&& ((this.getWebsiteName() == castOther.getWebsiteName()) || (this
						.getWebsiteName() != null
						&& castOther.getWebsiteName() != null && this
						.getWebsiteName().equals(castOther.getWebsiteName())))
				&& (this.getContentId() == castOther.getContentId())
				&& ((this.getUpdateTime() == castOther.getUpdateTime()) || (this
						.getUpdateTime() != null
						&& castOther.getUpdateTime() != null && this
						.getUpdateTime().equals(castOther.getUpdateTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getUserId();
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37 * result
				+ (getMail() == null ? 0 : this.getMail().hashCode());
		result = 37
				* result
				+ (getUserModuleName() == null ? 0 : this.getUserModuleName()
						.hashCode());
		result = 37
				* result
				+ (getWebsiteName() == null ? 0 : this.getWebsiteName()
						.hashCode());
		result = 37 * result + (int) this.getContentId();
		result = 37
				* result
				+ (getUpdateTime() == null ? 0 : this.getUpdateTime()
						.hashCode());
		return result;
	}

}