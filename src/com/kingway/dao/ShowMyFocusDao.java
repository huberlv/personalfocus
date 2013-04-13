package com.kingway.dao;

import java.util.List;

import com.kingway.model.UserModuleSubgroupInfo;

public interface ShowMyFocusDao {

	public List showmyfocus(Long id,boolean onlyshowUpdate);
	public List<UserModuleSubgroupInfo> showUsrGroup(Long id);
}
