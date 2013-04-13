package com.kingway.dao.impl;

import com.kingway.dao.ActiveAccountDao;
import com.kingway.model.UserActivecode;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public class ActiveAccountDaoImpl implements ActiveAccountDao{

	@Override
	public boolean active(Long id, String code) {
		Class clazz = UserActivecode.class;
		UserActivecode uac = (UserActivecode) HibernateUtil.get(clazz, id);
		if(uac.getActiveCode().equals(code)){
			Class c= UserInfo.class;
			Long userId=uac.getUserInfo().getUserId();
			String userName=uac.getUserInfo().getUserName();
			UserInfo userinfo = (UserInfo) HibernateUtil.get(c, userId);
			userinfo.setUserType(1);
			HibernateUtil.update(userinfo);
			
			ActionContext.getContext().getSession().put("userid", userId.toString());
			ActionContext.getContext().getSession().put("username",userName);
			return true;
		}
			
		else return false;
	}

}
