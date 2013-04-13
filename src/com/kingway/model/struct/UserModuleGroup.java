package com.kingway.model.struct;

import java.util.ArrayList;
import java.util.List;

import com.kingway.model.ModuleInfoShowView;


public class UserModuleGroup {
	private String groupName;
	private long groupId;
	private List<ModuleInfoShowView> viewList=new ArrayList<ModuleInfoShowView>();
    public List<ModuleInfoShowView> getViewList() {
		return viewList;
	}
	public void setViewList(List<ModuleInfoShowView> viewList) {
		this.viewList = viewList;
	}
	public UserModuleGroup(String groupName,long groupId){
    	this.groupName=groupName;
    	this.groupId=groupId;
    }
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}
