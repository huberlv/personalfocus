package com.kingway.action;

import com.kingway.model.ModuleInfo;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public class ShowSourceAction {
	private String moduleId;
	private String source;
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleId() {
		return moduleId;
	}
	
	public String showSource(){
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return "input";
		}
		ModuleInfo moduleInfo = (ModuleInfo)HibernateUtil.get(ModuleInfo.class, Long.parseLong(moduleId));
		setSource(moduleInfo.getDefineSource());
		return "OK";
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
}
