package com.kingway.dao.impl;


import com.kingway.action.ShowPageAction;
import com.kingway.dao.ShowModuleDao;

import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.kingway.util.MD5Util;


public class ShowModuleDaoImpl implements ShowModuleDao {
    private UserInfo user=null;
/**
 * 显示一个模块
 */
	@Override
	public String showModule(Long id, String psw, Long moduleId, int source) {
		Class clazz = UserInfo.class;
		UserInfo u = (UserInfo) HibernateUtil.get(clazz, id);
		System.out.println(u);
		if (u.getPassword().equals(MD5Util.getMD5(psw.getBytes()))) {
            this.setUser((UserInfo)u);
			UserModuleContents m = (UserModuleContents) HibernateUtil
					.getRecord("from UserModuleContents where userModuleId="+moduleId);
			if (source == 1) {
				
				return LhpUtil.getMbHtml(m.getUserModuleContent(), null, "");
			} else {
				return LhpUtil.getMbHtml(m.getUserModuleContent(), null, ShowPageAction.showPageUrl);
			}

		} else {
			
			return null;
		}

	}

	@Override
	public ModuleInfoShowView getModuleInfoShowView(long moduleId) {
		try{
		   ModuleInfoShowView temp=(ModuleInfoShowView)HibernateUtil.getRecord("from ModuleInfoShowView where moduleId= "+moduleId);
		   return temp;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserInfo getUser() {
		return user;
	}

}
