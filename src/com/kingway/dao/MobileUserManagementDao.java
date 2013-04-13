package com.kingway.dao;

import java.util.List;

import com.kingway.model.ContentNeedToBeSendView;


public interface MobileUserManagementDao {

	public List<ContentNeedToBeSendView> getUserModules(String userId);
}
