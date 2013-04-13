package com.kingway.dao;

import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;

public interface CustomModuleDao {	
	public MonitorWebInfo getWebInfo();
	public void save(Object o);
	public ModuleInfo getModule(Long moduleId);
	public UserModuleInfo getUserModule(Long userModuleId);
	public void update(Object o);
	public UserModuleSubgroupInfo getSubgroup(Long subgroupId);
	public UserModuleContents getUserModuleContents(Long userModuleId);
}
