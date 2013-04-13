package com.kingway.dao.impl;

import com.kingway.dao.ShowUserInfoDao;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;

public class ShowUserInfoDaoImpl implements ShowUserInfoDao {

	@Override
	public UserInfo showUserInfo(Long id) {
		Class<UserInfo> clazz=UserInfo.class;
		UserInfo u=(UserInfo) HibernateUtil.get(clazz, id);

		return u;
	}

}
