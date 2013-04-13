package com.kingway.action;

import java.util.List;

import com.kingway.dao.impl.SubgroupManagementDaoImpl;
import com.kingway.model.UserModuleSubgroupInfo;
import com.opensymphony.xwork2.ActionContext;

public class ShowGroupManagementAction {
	private List<UserModuleSubgroupInfo> subgroupList;

	
	public List<UserModuleSubgroupInfo> getSubgroupList() {
		return subgroupList;
	}


	public void setSubgroupList(List<UserModuleSubgroupInfo> subgroupList) {
		this.subgroupList = subgroupList;
	}


	public String showGroup(){
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id=Long.parseLong(idstr);
		this.subgroupList = new SubgroupManagementDaoImpl().getUserSubgroupList(id);
		return "success";
	}

}
