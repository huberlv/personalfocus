package com.kingway.action;

import java.util.ArrayList;
import java.util.List;

import com.kingway.dao.impl.ModuleInfoManageListDaoImpl;
import com.kingway.dao.impl.SubgroupManagementDaoImpl;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.struct.ModuleInfoManageList;
import com.opensymphony.xwork2.ActionContext;

public class ShowFocusManagementDetailAction {
	private List<ModuleInfoManageList> modifylist;
	private List<UserModuleSubgroupInfo> subgroupList = new ArrayList<UserModuleSubgroupInfo>();

	public List<UserModuleSubgroupInfo> getSubgroupList() {
		return subgroupList;
	}

	public void setSubgroupList(List<UserModuleSubgroupInfo> subgroupList) {
		this.subgroupList = subgroupList;
	}

	public List<ModuleInfoManageList> getModifylist() {
		return modifylist;
	}

	public void setModifylist(List<ModuleInfoManageList> modifylist) {
		this.modifylist = modifylist;
	}

	public String showfocusmanagementdetail(){
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id=Long.parseLong(idstr);
		this.modifylist = new ModuleInfoManageListDaoImpl().getModuleInfoManageList(id);
		this.subgroupList = new SubgroupManagementDaoImpl().getSubgroupList(id);
		System.out.println("modifylist.size():"+this.modifylist.size());
		return "success";
	}
}
