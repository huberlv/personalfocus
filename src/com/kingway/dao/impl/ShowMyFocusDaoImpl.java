package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.ShowMyFocusDao;
import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.util.HibernateUtil;

public class ShowMyFocusDaoImpl implements ShowMyFocusDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModuleInfoShowView> showmyfocus(Long id,boolean onlyshowUpdate) {
		List<ModuleInfoShowView> viewList=null;
		if(onlyshowUpdate==false){
		   viewList = HibernateUtil.list("from ModuleInfoShowView where userId= "+id);
		}else{
			viewList = HibernateUtil.list("from ModuleInfoShowView where userId= "+id+" and contentType=1");
		}
		for(ModuleInfoShowView list:viewList){
			
			String con=list.getId().getUpdateContent();
			con = con.replaceAll("&amp;", "&");
			list.getId().setUpdateContent(con);
			if((list.getId().getContentType()==1)&&(list.getId().getIgnoreByUser()==0)){
				UserModuleContents userModuleContents=(UserModuleContents) HibernateUtil.get(UserModuleContents.class,list.getId().getContentId());
				userModuleContents.setIgnoreByUser(1);
				HibernateUtil.update(userModuleContents);
			}
		} 
		
		return viewList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModuleSubgroupInfo> showUsrGroup(Long id) {
		return HibernateUtil.list("from UserModuleSubgroupInfo where userId= "+id); 
	}
	
	

}
