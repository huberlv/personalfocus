package com.kingway.action;

import java.util.List;

import com.kingway.model.FailModuleView;
import com.kingway.model.FailWebInfoView;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public class ShowSysManagementAction {

	private List<FailModuleView> failModule;
	private List<FailWebInfoView> failWebInfoView;
	public List<FailWebInfoView> getFailWebInfoView() {
		return failWebInfoView;
	}

	public void setFailWebInfoView(List<FailWebInfoView> failWebInfoView) {
		this.failWebInfoView = failWebInfoView;
	}

	public String showsysmanagement(){
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return "fail";
		}
		
		//Î´·â×°
		setFailModule(HibernateUtil.list("from FailModuleView"));
		List list = HibernateUtil.list("from FailWebInfoView");
		failWebInfoView=(List<FailWebInfoView>)list;
		//Î´·â×°
		return "success";
	}

	public void setFailModule(List<FailModuleView> failModule) {
		this.failModule = failModule;
	}

	public List<FailModuleView> getFailModule() {
		return failModule;
	}
	
	
}
