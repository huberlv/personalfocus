package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.SubgroupManagementDao;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.util.HibernateUtil;

public class SubgroupManagementDaoImpl implements SubgroupManagementDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModuleSubgroupInfo> getSubgroupList(Long userId) {
		List<UserModuleSubgroupInfo> subgroups = HibernateUtil
				.list("from UserModuleSubgroupInfo where userId=" + userId);
		return subgroups;
	}
	
	@Override
	public UserModuleSubgroupInfo getCurrentSubgroup(Long userModuleId) {
		UserModuleInfo userModule = (UserModuleInfo) HibernateUtil
				.getRecord("from UserModuleInfo where userModuleId=" + userModuleId);
		return userModule.getUserModuleSubgroupInfo();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModuleSubgroupInfo> getUserSubgroupList(Long userId) {
		List<UserModuleSubgroupInfo> subgroups = HibernateUtil
				.list("from UserModuleSubgroupInfo where groupType<>0 and userId=" + userId);
		return subgroups;
	}
	
}
