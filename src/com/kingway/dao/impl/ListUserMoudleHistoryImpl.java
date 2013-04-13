package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.ListUserMoudleHistoryDao;
import com.kingway.model.ModuleHistoyNumView;
import com.kingway.model.UserModuleHistoryListView;
import com.kingway.model.UserModuleInfo;
import com.kingway.util.HibernateUtil;
/**
 * ¬¿∫Ë≈Â
 * @author Administrator
 *
 */
public class ListUserMoudleHistoryImpl implements ListUserMoudleHistoryDao{

	@Override
	public List<UserModuleHistoryListView> getUserModuleHistoryList(
			Long userModuleId,int begin,int max) {
		//System.out.println(HibernateUtil.list("from UserModuleHistoryListView where userModuleId="+userModuleId,begin,max).size());
		return HibernateUtil.list("from UserModuleHistoryListView where userModuleId="+userModuleId,begin,max);
	}

	@Override
	public String getUserModuleStyle(Long userModuleId) {
		// TODO Auto-generated method stub
		UserModuleInfo u=(UserModuleInfo) HibernateUtil.get(UserModuleInfo.class, userModuleId);
		if(u!=null){
			return u.getUserModuleStyle();
		}
		return null;
	}

	@Override
	public int getSize(Long userModuleId) {
		// TODO Auto-generated method stub
		Object temp=HibernateUtil.getRecord("from ModuleHistoyNumView where userModuleId="+userModuleId);
		if(temp==null){
			return 0;
		}
		ModuleHistoyNumView moduleHistoyNum=(ModuleHistoyNumView)temp;
		return moduleHistoyNum.getId().getNum().intValue();
		
		
	}

}
