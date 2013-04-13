package com.kingway.model;

/**
 * WebUserModulesView entity. @author MyEclipse Persistence Tools
 */

public class WebUserModulesView implements java.io.Serializable {

	// Fields

	private WebUserModulesViewId id;

	// Constructors

	/** default constructor */
	public WebUserModulesView() {
	}

	/** full constructor */
	public WebUserModulesView(WebUserModulesViewId id) {
		this.id = id;
	}

	// Property accessors

	public WebUserModulesViewId getId() {
		return this.id;
	}

	public void setId(WebUserModulesViewId id) {
		this.id = id;
	}

}