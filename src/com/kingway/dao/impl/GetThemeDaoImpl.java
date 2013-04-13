package com.kingway.dao.impl;

import com.kingway.dao.GetThemeDao;
import com.kingway.model.UserSpaceInfo;
import com.kingway.util.HibernateUtil;

public class GetThemeDaoImpl implements GetThemeDao {

	@Override
	public String getUserSpaceStyle(String id) {
		Long userid = Long.parseLong(id);
		UserSpaceInfo userspaceinfo=new UserSpaceInfo();
		userspaceinfo=(UserSpaceInfo) HibernateUtil.getRecord("from UserSpaceInfo where userId = "+userid);		
		return userspaceinfo.getUserSpaceStyle();
	}

}
