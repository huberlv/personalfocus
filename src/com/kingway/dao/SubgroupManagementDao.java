package com.kingway.dao;

import java.util.List;

import com.kingway.model.UserModuleSubgroupInfo;

public interface SubgroupManagementDao {
	
	public List<UserModuleSubgroupInfo> getSubgroupList(Long userId);
	
	public UserModuleSubgroupInfo getCurrentSubgroup(Long userModuleId);
	
	public List<UserModuleSubgroupInfo> getUserSubgroupList(Long userId);
}
