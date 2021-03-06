package com.kingway.model;

// Generated 2011-1-23 9:52:10 by Hibernate Tools 3.3.0.GA

/**
 * UserFriendSubgroupInfo generated by hbm2java
 */
public class UserFriendSubgroupInfo implements java.io.Serializable {

	private Long subgroupId;
	private UserInfo userInfo;
	private String groupName;
	private int groupType;

	public UserFriendSubgroupInfo() {
	}

	public UserFriendSubgroupInfo(UserInfo userInfo, String groupName,
			int groupType) {
		this.userInfo = userInfo;
		this.groupName = groupName;
		this.groupType = groupType;
	}

	public Long getSubgroupId() {
		return this.subgroupId;
	}

	public void setSubgroupId(Long subgroupId) {
		this.subgroupId = subgroupId;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupType() {
		return this.groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

}
