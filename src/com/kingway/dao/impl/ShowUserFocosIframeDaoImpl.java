package com.kingway.dao.impl;

import com.kingway.dao.ShowUserFocosIframeDao;
import com.kingway.model.UserSpaceModuleInfoShowView;
import com.kingway.model.UserSpaceModuleInfoShowViewId;
import com.kingway.util.HibernateUtil;

public class ShowUserFocosIframeDaoImpl implements ShowUserFocosIframeDao {
	UserSpaceModuleInfoShowViewId moduleInfoShowViewId=null;
	/**
	 * ¬¿∫Ë≈Â
	 * @param userModuleId
	 */
	public ShowUserFocosIframeDaoImpl(long userModuleId){
		moduleInfoShowViewId = ( (UserSpaceModuleInfoShowView)HibernateUtil.getRecord("from UserSpaceModuleInfoShowView where userModuleId="+userModuleId)).getId();	
	}
	@Override
	public String[] getCss() {
		String[] css = new String[2];
		css[0] = moduleInfoShowViewId.getHeadCssAndScript();
		css[1] =moduleInfoShowViewId.getBodyCssAndScript();
		return css;
	}
	

	
	@Override
	public String getUserModuleStyle() {
		
		return moduleInfoShowViewId.getUserModuleStyle();
	}


	@Override
	public String getUpdateContent() {
		// TODO Auto-generated method stub
		return moduleInfoShowViewId.getUpdateContent();
	}
	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return this.moduleInfoShowViewId==null;
	}

}
