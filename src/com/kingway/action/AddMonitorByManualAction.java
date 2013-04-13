package com.kingway.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.kingway.dao.SubgroupManagementDao;
import com.kingway.dao.impl.SubgroupManagementDaoImpl;
import com.kingway.model.UserModuleSubgroupInfo;
import com.opensymphony.xwork2.ActionContext;

public class AddMonitorByManualAction {
	private List<UserModuleSubgroupInfo> subgroupList; // 模块分组信息
	private SubgroupManagementDao subgroupManagementDao;
	public AddMonitorByManualAction(){
		subgroupManagementDao=new SubgroupManagementDaoImpl();
	}
	public String show()
	{		
		if(ServletActionContext.getRequest().getSession(false)!=null){
			Long userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
			setSubgroupList(subgroupManagementDao.getSubgroupList(userId));		
			return "success";
		}else{
			return "fail";
		}
		
	}
	public void setSubgroupList(List<UserModuleSubgroupInfo> subgroupList) {
		this.subgroupList = subgroupList;
	}
	public List<UserModuleSubgroupInfo> getSubgroupList() {
		return subgroupList;
	}
}
