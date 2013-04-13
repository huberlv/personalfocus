package com.kingway.model.struct;

import java.util.LinkedList;
import java.util.List;

import com.kingway.model.struct.Moduel;


public class MobileModuel {
	private String webName;
	private List<Moduel> modules;
	public MobileModuel(String webName){
		this.webName=webName;
		modules=new LinkedList<Moduel>();
	}
	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}


	public List<Moduel> getModules() {
		return modules;
	}

	public void setModules(List<Moduel> modules) {
		this.modules = modules;
	}

}

