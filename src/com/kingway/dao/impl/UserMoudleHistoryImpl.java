package com.kingway.dao.impl;

import com.kingway.dao.UserMoudleHistoryDao;
import com.kingway.model.UserModuleHistoryView;
import com.kingway.util.HibernateUtil;
/**
 * 
 * @author ¬¿∫Ë≈Â
 *
 */
public class UserMoudleHistoryImpl implements UserMoudleHistoryDao{

	private UserModuleHistoryView userModuleHistory;
	public UserMoudleHistoryImpl(Long userModuleId,Long bufferId){

		userModuleHistory=(UserModuleHistoryView) HibernateUtil.getRecord("from UserModuleHistoryView where userModuleId="+userModuleId+" and bufferId="+bufferId);
	}
	@Override
	public String[] getCss() {
		// TODO Auto-generated method stub
		
		String css[]=new String[2];
		css[0] = userModuleHistory.getId().getHeadCssAndScript();
		css[1] =userModuleHistory.getId().getBodyCssAndScript();
		return css;
	}

	@Override
	public String getUpdateContent() {
		// TODO Auto-generated method stub
		
		return userModuleHistory.getId().getContent();
	}

	@Override
	public String getUserModuleStyle() {
		// TODO Auto-generated method stub
		
		return userModuleHistory.getId().getUserModuleStyle();
	}

}
