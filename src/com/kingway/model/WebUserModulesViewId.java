package com.kingway.model;

/**
 * WebUserModulesViewId entity. @author MyEclipse Persistence Tools
 */

public class WebUserModulesViewId implements java.io.Serializable {

	// Fields

	private Long webId;
	private Long contentId;

	// Constructors

	/** default constructor */
	public WebUserModulesViewId() {
	}

	/** full constructor */
	public WebUserModulesViewId(Long webId, Long contentId) {
		this.webId = webId;
		this.contentId = contentId;
	}

	// Property accessors

	public Long getWebId() {
		return this.webId;
	}

	public void setWebId(Long webId) {
		this.webId = webId;
	}

	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WebUserModulesViewId))
			return false;
		WebUserModulesViewId castOther = (WebUserModulesViewId) other;

		return ((this.getWebId() == castOther.getWebId()) || (this.getWebId() != null
				&& castOther.getWebId() != null && this.getWebId().equals(
				castOther.getWebId())))
				&& ((this.getContentId() == castOther.getContentId()) || (this
						.getContentId() != null
						&& castOther.getContentId() != null && this
						.getContentId().equals(castOther.getContentId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getWebId() == null ? 0 : this.getWebId().hashCode());
		result = 37 * result
				+ (getContentId() == null ? 0 : this.getContentId().hashCode());
		return result;
	}

}