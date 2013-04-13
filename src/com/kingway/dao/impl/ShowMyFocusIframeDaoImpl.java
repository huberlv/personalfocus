package com.kingway.dao.impl;


import com.kingway.dao.ShowMyFocusIframeDao;
import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.ModuleInfoShowViewId;
import com.kingway.util.HibernateUtil;

public class ShowMyFocusIframeDaoImpl implements ShowMyFocusIframeDao {
	ModuleInfoShowViewId moduleInfoShowViewId=null;
	/**
	 * ¬¿∫Ë≈Â
	 * @param userModuleId
	 */
	public ShowMyFocusIframeDaoImpl(long userModuleId){
		moduleInfoShowViewId = ( (ModuleInfoShowView)HibernateUtil.getRecord("from ModuleInfoShowView where userModuleId="+userModuleId)).getId();	
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
	public long getContentId() {
		// TODO Auto-generated method stub
		return moduleInfoShowViewId.getContentId();
	}
	@Override
	public int getContentType() {
		// TODO Auto-generated method stub
		return moduleInfoShowViewId.getContentType();
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
