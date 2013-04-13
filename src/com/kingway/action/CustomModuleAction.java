package com.kingway.action;

import java.util.Collections;
import java.util.List;

import com.kingway.dao.impl.CustomModuleDaoImpl;
import com.kingway.dao.impl.SubgroupManagementDaoImpl;
import com.kingway.model.ModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.opensymphony.xwork2.ActionContext;

public class CustomModuleAction {
	private String moduleContent;
	private Long moduleId;
	private Long userModuleId;
	private List<UserModuleSubgroupInfo> subgroupList; // 模块分组信息
	
	public List<UserModuleSubgroupInfo> getSubgroupList() {
		return subgroupList;
	}

	public void setSubgroupList(List<UserModuleSubgroupInfo> subgroupList) {
		this.subgroupList = subgroupList;
	}

	public String getModuleContent() {
		return moduleContent;
	}

	public void setModuleContent(String moduleContent) {
		this.moduleContent = moduleContent;
	}
	
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getUserModuleId() {
		return userModuleId;
	}

	public void setUserModuleId(Long userModuleId) {
		this.userModuleId = userModuleId;
	}

	/**
	 * 发布订阅源
	 * @return
	 */
	public String uploadCustomModule(){
		Long userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
		subgroupList = new SubgroupManagementDaoImpl().getSubgroupList(userId);
		return "upload";
	}	

	/**
	 * 显示订阅源,用于更新订阅源
	 * @return
	 */
	public String showCustomModule(){
		Long userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
		SubgroupManagementDaoImpl subgroupImpl = new SubgroupManagementDaoImpl();
		subgroupList = subgroupImpl.getSubgroupList(userId);
		Long currentSubgroupId = subgroupImpl.getCurrentSubgroup(userModuleId).getSubgroupId();	
		/* 调整下拉菜单的第一个选项为用户发布订阅源时选中的选项 */
		for(int i=0; i<subgroupList.size(); i++){
			if(subgroupList.get(i).getSubgroupId() == currentSubgroupId){
				Collections.swap(subgroupList, 0, i);
				break;
			}
		}
		CustomModuleDaoImpl impl = new CustomModuleDaoImpl();
		ModuleInfo module = impl.getModule(moduleId);
		moduleContent = module.getUpdateContent();
		return "show";
	}

}
