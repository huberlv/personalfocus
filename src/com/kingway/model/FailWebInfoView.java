package com.kingway.model;

/**
 * FailWebInfoView entity. @author MyEclipse Persistence Tools
 */

public class FailWebInfoView implements java.io.Serializable {

	// Fields

	private FailWebInfoViewId id;

	// Constructors

	/** default constructor */
	public FailWebInfoView() {
	}

	/** full constructor */
	public FailWebInfoView(FailWebInfoViewId id) {
		this.id = id;
	}

	// Property accessors

	public FailWebInfoViewId getId() {
		return this.id;
	}

	public void setId(FailWebInfoViewId id) {
		this.id = id;
	}

}