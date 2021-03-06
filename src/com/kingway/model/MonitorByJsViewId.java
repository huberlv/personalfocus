package com.kingway.model;

// Generated 2010-10-1 0:21:31 by Hibernate Tools 3.3.0.GA

/**
 * MonitorByJsViewId generated by hbm2java
 */
public class MonitorByJsViewId implements java.io.Serializable {

	private String modulePath;
	private long moduleId;
	private String webAddress;

	public MonitorByJsViewId() {
	}

	public MonitorByJsViewId(long moduleId, String webAddress) {
		this.moduleId = moduleId;
		this.webAddress = webAddress;
	}

	public MonitorByJsViewId(String modulePath, long moduleId, String webAddress) {
		this.modulePath = modulePath;
		this.moduleId = moduleId;
		this.webAddress = webAddress;
	}

	public String getModulePath() {
		return this.modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MonitorByJsViewId))
			return false;
		MonitorByJsViewId castOther = (MonitorByJsViewId) other;

		return ((this.getModulePath() == castOther.getModulePath()) || (this
				.getModulePath() != null
				&& castOther.getModulePath() != null && this.getModulePath()
				.equals(castOther.getModulePath())))
				&& (this.getModuleId() == castOther.getModuleId())
				&& ((this.getWebAddress() == castOther.getWebAddress()) || (this
						.getWebAddress() != null
						&& castOther.getWebAddress() != null && this
						.getWebAddress().equals(castOther.getWebAddress())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getModulePath() == null ? 0 : this.getModulePath()
						.hashCode());
		result = 37 * result + (int) this.getModuleId();
		result = 37
				* result
				+ (getWebAddress() == null ? 0 : this.getWebAddress()
						.hashCode());
		return result;
	}

}
