package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.MobileUserManagementDao;
import com.kingway.model.ContentNeedToBeSendView;
import com.kingway.util.HibernateUtil;

public class MobileUserManagementDaoImpl implements MobileUserManagementDao {


	@Override
	public List<ContentNeedToBeSendView> getUserModules(String userId) {
		// TODO Auto-generated method stub
		return HibernateUtil.list("from ContentNeedToBeSendView where userId="+userId+" ORDER BY subgroupId");

	}

	
}
