package com.kingway.model;

// Generated 2011-1-23 9:52:10 by Hibernate Tools 3.3.0.GA

/**
 * UserFriendsViewId generated by hbm2java
 */
public class UserFriendsViewId implements java.io.Serializable {

	private long userId;
	private String userName;
	private long friendId;
	private String friendInfo;
	private String groupName;

	public UserFriendsViewId() {
	}

	public UserFriendsViewId(long userId, String userName, long friendId,
			String groupName) {
		this.userId = userId;
		this.userName = userName;
		this.friendId = friendId;
		this.groupName = groupName;
	}

	public UserFriendsViewId(long userId, String userName, long friendId,
			String friendInfo, String groupName) {
		this.userId = userId;
		this.userName = userName;
		this.friendId = friendId;
		this.friendInfo = friendInfo;
		this.groupName = groupName;
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

	public long getFriendId() {
		return this.friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public String getFriendInfo() {
		return this.friendInfo;
	}

	public void setFriendInfo(String friendInfo) {
		this.friendInfo = friendInfo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserFriendsViewId))
			return false;
		UserFriendsViewId castOther = (UserFriendsViewId) other;

		return (this.getUserId() == castOther.getUserId())
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())))
				&& (this.getFriendId() == castOther.getFriendId())
				&& ((this.getFriendInfo() == castOther.getFriendInfo()) || (this
						.getFriendInfo() != null
						&& castOther.getFriendInfo() != null && this
						.getFriendInfo().equals(castOther.getFriendInfo())))
				&& ((this.getGroupName() == castOther.getGroupName()) || (this
						.getGroupName() != null
						&& castOther.getGroupName() != null && this
						.getGroupName().equals(castOther.getGroupName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getUserId();
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37 * result + (int) this.getFriendId();
		result = 37
				* result
				+ (getFriendInfo() == null ? 0 : this.getFriendInfo()
						.hashCode());
		result = 37 * result
				+ (getGroupName() == null ? 0 : this.getGroupName().hashCode());
		return result;
	}

}
