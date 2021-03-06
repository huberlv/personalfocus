package com.kingway.model;

// Generated 2010-10-24 10:03:09 by Hibernate Tools 3.3.0.GA

/**
 * UserModuleHistoryListViewId generated by hbm2java
 */
public class UserModuleHistoryListViewId implements java.io.Serializable {

	private long userModuleId;
	private long bufferId;

	public UserModuleHistoryListViewId() {
	}

	public UserModuleHistoryListViewId(long userModuleId, long bufferId) {
		this.userModuleId = userModuleId;
		this.bufferId = bufferId;
	}

	public long getUserModuleId() {
		return this.userModuleId;
	}

	public void setUserModuleId(long userModuleId) {
		this.userModuleId = userModuleId;
	}

	public long getBufferId() {
		return this.bufferId;
	}

	public void setBufferId(long bufferId) {
		this.bufferId = bufferId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserModuleHistoryListViewId))
			return false;
		UserModuleHistoryListViewId castOther = (UserModuleHistoryListViewId) other;

		return (this.getUserModuleId() == castOther.getUserModuleId())
				&& (this.getBufferId() == castOther.getBufferId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getUserModuleId();
		result = 37 * result + (int) this.getBufferId();
		return result;
	}

}
