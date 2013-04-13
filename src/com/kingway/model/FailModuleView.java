package com.kingway.model;

/**
 * FailModuleView entity. @author MyEclipse Persistence Tools
 */

public class FailModuleView implements java.io.Serializable {

	// Fields

	private FailModuleViewId id;

	// Constructors

	/** default constructor */
	public FailModuleView() {
	}

	/** full constructor */
	public FailModuleView(FailModuleViewId id) {
		this.id = id;
	}

	// Property accessors

	public FailModuleViewId getId() {
		return this.id;
	}

	public void setId(FailModuleViewId id) {
		this.id = id;
	}

}