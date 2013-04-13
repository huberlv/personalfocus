package com.kingway.dao.impl;

import com.kingway.dao.CustomModuleDao;
import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.util.HibernateUtil;

public class CustomModuleDaoImpl implements CustomModuleDao {

	@Override
	public MonitorWebInfo getWebInfo() {
		MonitorWebInfo web = (MonitorWebInfo) HibernateUtil
				.getRecord("from MonitorWebInfo where webAddress='../index/showindex'");
		//如果设定的web_info记录不存在则插入
		if(web == null){
			web = new MonitorWebInfo();
			web.setWebAddress("../index/showindex");
			web.setIsMonitoring(false);
			web.setEnable(2);
			web.setFailTimes(0);
			web.setHeadCssAndScript("");
			web.setBodyCssAndScript("");
			HibernateUtil.save(web);
		}
		return web;
	}

	@Override
	public void save(Object o) {
		HibernateUtil.save(o);
	}

	@Override
	public ModuleInfo getModule(Long moduleId) {
		return (ModuleInfo)HibernateUtil.get(ModuleInfo.class, moduleId);
	}
	
	@Override
	public UserModuleInfo getUserModule(Long userModuleId) {
		return (UserModuleInfo)HibernateUtil.get(UserModuleInfo.class, userModuleId);
	}
	
	@Override
	public void update(Object o) {
		HibernateUtil.update(o);
	}
	
	@Override
	public UserModuleSubgroupInfo getSubgroup(Long subgroupId) {
		return (UserModuleSubgroupInfo) HibernateUtil.get(UserModuleSubgroupInfo.class, subgroupId);
	}

	@Override
	public UserModuleContents getUserModuleContents(Long userModuleId) {
		// TODO Auto-generated method stub
		return (UserModuleContents) HibernateUtil.getRecord("from UserModuleContents where userModuleId="+userModuleId);
	}
	
}
