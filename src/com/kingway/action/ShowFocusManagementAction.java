package com.kingway.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingway.dao.impl.ShowFocusManagementDaoImpl;
import com.kingway.model.UserModuleInfo;
import com.opensymphony.xwork2.ActionContext;

public class ShowFocusManagementAction {

	private List<UserModuleInfo>enablelist;
	private List<UserModuleInfo>disablelist;
	private Map<Long,String>enablemodulename = new HashMap<Long, String>();
	private Map<Long,String> disablemodulename= new HashMap<Long, String>();

	
	public Map<Long, String> getEnablemodulename() {
		return enablemodulename;
	}


	public void setEnablemodulename(Map<Long, String> enablemodulename) {
		this.enablemodulename = enablemodulename;
	}


	public Map<Long, String> getDisablemodulename() {
		return disablemodulename;
	}


	public void setDisablemodulename(Map<Long, String> disablemodulename) {
		this.disablemodulename = disablemodulename;
	}


	public String showfocusmanagement(){
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id =Long.parseLong(idstr);
		enablelist=new ShowFocusManagementDaoImpl().showenable(id);
		disablelist=new ShowFocusManagementDaoImpl().showdisable(id);
		
		for(UserModuleInfo u :enablelist){
			enablemodulename.put(u.getUserModuleId(),u.getUserModuleName());
		}
		for(UserModuleInfo u: disablelist)
		{
			disablemodulename.put(u.getUserModuleId(),u.getUserModuleName());
		}
		return "success";
	}
}
