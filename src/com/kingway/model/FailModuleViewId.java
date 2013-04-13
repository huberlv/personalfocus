package com.kingway.model;

/**
 * FailModuleViewId entity. @author MyEclipse Persistence Tools
 */

public class FailModuleViewId implements java.io.Serializable {

	// Fields

	private Long moduleId;
	private Integer failTimes;

	// Constructors

	/** default constructor */
	public FailModuleViewId() {
	}

	/** full constructor */
	public FailModuleViewId(Long moduleId, Integer failTimes) {
		this.moduleId = moduleId;
		this.failTimes = failTimes;
	}

	// Property accessors

	public Long getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getFailTimes() {
		return this.failTimes;
	}

	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FailModuleViewId))
			return false;
		FailModuleViewId castOther = (FailModuleViewId) other;

		return ((this.getModuleId() == castOther.getModuleId()) || (this
				.getModuleId() != null
				&& castOther.getModuleId() != null && this.getModuleId()
				.equals(castOther.getModuleId())))
				&& ((this.getFailTimes() == castOther.getFailTimes()) || (this
						.getFailTimes() != null
						&& castOther.getFailTimes() != null && this
						.getFailTimes().equals(castOther.getFailTimes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getModuleId() == null ? 0 : this.getModuleId().hashCode());
		result = 37 * result
				+ (getFailTimes() == null ? 0 : this.getFailTimes().hashCode());
		return result;
	}

}