package com.kingway.model;

// Generated 2010-10-1 0:21:31 by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;

/**
 * UserModuleSubgroupInfo generated by hbm2java
 */
public class UserModuleSubgroupInfo implements java.io.Serializable {

	private Long subgroupId;
	private UserInfo userInfo;
	private String groupName;
	private int groupType;
	private Set<UserModuleInfo> userModuleInfos = new HashSet<UserModuleInfo>(0);

	public UserModuleSubgroupInfo() {
	}

	public UserModuleSubgroupInfo(UserInfo userInfo, String groupName,
			int groupType) {
		this.userInfo = userInfo;
		this.groupName = groupName;
		this.groupType = groupType;
	}

	public UserModuleSubgroupInfo(UserInfo userInfo, String groupName,
			int groupType, Set<UserModuleInfo> userModuleInfos) {
		this.userInfo = userInfo;
		this.groupName = groupName;
		this.groupType = groupType;
		this.userModuleInfos = userModuleInfos;
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

	public Set<UserModuleInfo> getUserModuleInfos() {
		return this.userModuleInfos;
	}

	public void setUserModuleInfos(Set<UserModuleInfo> userModuleInfos) {
		this.userModuleInfos = userModuleInfos;
	}

}
