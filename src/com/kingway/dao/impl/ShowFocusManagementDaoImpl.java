package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.ShowFocusManagementDao;
import com.kingway.model.UserModuleInfo;
import com.kingway.util.HibernateUtil;

public class ShowFocusManagementDaoImpl implements ShowFocusManagementDao{

	List<UserModuleInfo> enable;
	List<UserModuleInfo> disable;
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModuleInfo> showenable(Long id) {
		enable=HibernateUtil.list("from UserModuleInfo where userInfo.userId = "+id + " and( accessType=0 or accessType=1)");
		return enable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModuleInfo> showdisable(Long id) {
		disable=HibernateUtil.list("from UserModuleInfo where userInfo.userId = "+id + " and  accessType=2");
		return disable;
	}
	
}
