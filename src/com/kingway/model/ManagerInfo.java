package com.kingway.model;

// Generated 2010-10-1 0:21:31 by Hibernate Tools 3.3.0.GA

/**
 * ManagerInfo generated by hbm2java
 */
public class ManagerInfo implements java.io.Serializable {

	private Long managerId;
	private String managerName;
	private String managerPsw;

	public ManagerInfo() {
	}

	public ManagerInfo(String managerName, String managerPsw) {
		this.managerName = managerName;
		this.managerPsw = managerPsw;
	}

	public Long getManagerId() {
		return this.managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPsw() {
		return this.managerPsw;
	}

	public void setManagerPsw(String managerPsw) {
		this.managerPsw = managerPsw;
	}

}