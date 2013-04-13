package com.kingway.model;

/**
 * FailWebInfoViewId entity. @author MyEclipse Persistence Tools
 */

public class FailWebInfoViewId implements java.io.Serializable {

	// Fields

	private Long webId;
	private String webAddress;
	private Integer failTimes;
	private Integer totalMonitorTimes;

	// Constructors

	/** default constructor */
	public FailWebInfoViewId() {
	}

	/** full constructor */
	public FailWebInfoViewId(Long webId, String webAddress, Integer failTimes,
			Integer totalMonitorTimes) {
		this.webId = webId;
		this.webAddress = webAddress;
		this.failTimes = failTimes;
		this.totalMonitorTimes = totalMonitorTimes;
	}

	// Property accessors

	public Long getWebId() {
		return this.webId;
	}

	public void setWebId(Long webId) {
		this.webId = webId;
	}

	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Integer getFailTimes() {
		return this.failTimes;
	}

	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}

	public Integer getTotalMonitorTimes() {
		return this.totalMonitorTimes;
	}

	public void setTotalMonitorTimes(Integer totalMonitorTimes) {
		this.totalMonitorTimes = totalMonitorTimes;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FailWebInfoViewId))
			return false;
		FailWebInfoViewId castOther = (FailWebInfoViewId) other;

		return ((this.getWebId() == castOther.getWebId()) || (this.getWebId() != null
				&& castOther.getWebId() != null && this.getWebId().equals(
				castOther.getWebId())))
				&& ((this.getWebAddress() == castOther.getWebAddress()) || (this
						.getWebAddress() != null
						&& castOther.getWebAddress() != null && this
						.getWebAddress().equals(castOther.getWebAddress())))
				&& ((this.getFailTimes() == castOther.getFailTimes()) || (this
						.getFailTimes() != null
						&& castOther.getFailTimes() != null && this
						.getFailTimes().equals(castOther.getFailTimes())))
				&& ((this.getTotalMonitorTimes() == castOther
						.getTotalMonitorTimes()) || (this
						.getTotalMonitorTimes() != null
						&& castOther.getTotalMonitorTimes() != null && this
						.getTotalMonitorTimes().equals(
								castOther.getTotalMonitorTimes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getWebId() == null ? 0 : this.getWebId().hashCode());
		result = 37
				* result
				+ (getWebAddress() == null ? 0 : this.getWebAddress()
						.hashCode());
		result = 37 * result
				+ (getFailTimes() == null ? 0 : this.getFailTimes().hashCode());
		result = 37
				* result
				+ (getTotalMonitorTimes() == null ? 0 : this
						.getTotalMonitorTimes().hashCode());
		return result;
	}

}