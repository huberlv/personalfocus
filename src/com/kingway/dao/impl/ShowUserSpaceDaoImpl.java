package com.kingway.dao.impl;

import com.kingway.dao.ShowUserSpaceDao;
import com.kingway.model.UserSpaceInfo;
import com.kingway.util.HibernateUtil;

public class ShowUserSpaceDaoImpl implements ShowUserSpaceDao{

	@Override
	public UserSpaceInfo getUserSpaceInfo(long userId) {
		// TODO Auto-generated method stub
		return (UserSpaceInfo)HibernateUtil.getRecord("from UserSpaceInfo where userId="+userId);
		
	}
}
