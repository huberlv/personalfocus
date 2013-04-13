package com.kingway.model.struct;

public class Moduel {
	
	private String moduelName;
	private String moduelUrl;
	private String classss="";
	
	public Moduel(String moduelName, String moduelUrl) {
		super();
		this.moduelName = moduelName;
		this.moduelUrl = moduelUrl;
	}
	
	public String getModuelName() {
		return moduelName;
	}
	public void setModuelName(String moduelName) {
		this.moduelName = moduelName;
	}
	public String getModuelUrl() {
		return moduelUrl;
	}
	public void setModuelUrl(String moduelUrl) {
		this.moduelUrl = moduelUrl;
	}
	public String getClassss() {
		return classss;
	}
	public void setClassss(String classss) {
		this.classss = classss;
	}
	
}