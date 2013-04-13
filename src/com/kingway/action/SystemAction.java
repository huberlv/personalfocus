package com.kingway.action;

import java.util.Map;

import com.kingway.util.HibernateUtil;
import com.kingway.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author lhp
 *
 */
public class SystemAction {
	/**
	 * 统计访问次数
	 */
	public  synchronized  static void countAccessTime(ActionContext actionContext){
		Map<String,Object> application=ActionContext.getContext().getApplication();
		System.out.println();
		if(ActionContext.getContext().getSession().get("userid")!=null){
			return;
		}
		if(application.get("accessTime")==null){
			application.put("accessTime",0);
		}else{
			application.put("accessTime",(Integer)application.get("accessTime")+1);
		}
		int accessTime=(Integer)application.get("accessTime");
		if(accessTime>Integer.parseInt(PropertiesUtil.getProperty("accessCatch"))){
			addAccessTime(accessTime,"accessTime");		
			application.put("accessTime",0);
		}
	}
	
	
	/**
	 * 统计手机访问次数
	 */
	public  synchronized  static void countMobileAccessTime(ActionContext actionContext){
		Map<String,Object> application=ActionContext.getContext().getApplication();
		if(ActionContext.getContext().getSession().get("userid")!=null){
			return;
		}
		if(application.get("mobileAccessTime")==null){
			application.put("mobileAccessTime",0);
		}else{
			application.put("mobileAccessTime",(Integer)application.get("mobileAccessTime")+1);
		}
		int accessTime=(Integer)application.get("mobileAccessTime");
		if(accessTime>Integer.parseInt(PropertiesUtil.getProperty("accessCatch"))){
			addAccessTime(accessTime,"mobileAccessTime");		
			application.put("mobileAccessTime",0);
		}
	}

	private static void  addAccessTime(int accessTime,String configName) {
		// TODO Auto-generated method stub
		HibernateUtil.getQueryResultBySQL("update system_config set configValue=cast(cast(configValue as UNSIGNED)+"+accessTime+" as CHAR) where configName='"+configName+"' ");
	}
}
