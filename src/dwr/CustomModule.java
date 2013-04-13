
package dwr;

import java.text.SimpleDateFormat;
import java.util.Date;

import rss.RSSUtil;

import com.kingway.dao.CustomModuleDao;
import com.kingway.dao.DatabaseDao;
import com.kingway.dao.impl.AddMonitorDaoImpl;
import com.kingway.dao.impl.CustomModuleDaoImpl;
import com.kingway.dao.impl.DatabaseDaoImpl;

import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleInfo;
import com.opensymphony.xwork2.ActionContext;

public class CustomModule {
	
	/**
	 * 保存用户订阅源
	 * @param moduleContent
	 * @param userModuleStyle
	 */
	public String saveCustomModule(String userModuleName, String moduleContent, String userModuleStyle, Long subgroupId){
		if(moduleContent == null || moduleContent.equals(""))
			return "请输入订阅源内容！";
		moduleContent="<div class='updateStyle'>"+moduleContent+"</div>";
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomModuleDaoImpl impl = new CustomModuleDaoImpl();
		Long userId = Long.parseLong((String)ActionContext.getContext().getSession().get("userid"));
		MonitorWebInfo web = (MonitorWebInfo)ActionContext.getContext().getApplication().get("customWeb");
		if(web==null){
			web = impl.getWebInfo();
			ActionContext.getContext().getApplication().put("customWeb", web);
		}
		/* 保存module_info */
		ModuleInfo module = new ModuleInfo();
		module.setEnable(2);
		module.setMonitorWebInfo(web);
		module.setUpdateContent(moduleContent);
		module.setModulePath(sdfDateTime.format(new Date())+"-"+userId);
		module.setRssContent(RSSUtil.createANewRSS( web.getWebAddress(), moduleContent).getXML());
		module.setDefineSource(moduleContent);
		module.setUserCount(1L);
		impl.save(module);
		/* 保存user_module_info */
		UserModuleInfo userModule = new UserModuleInfo();
		userModule.setUserModuleStyle(userModuleStyle);
		UserInfo user = new UserInfo();
		user.setUserId(userId);
		userModule.setUserInfo(user);
		userModule.setUserModuleName(userModuleName);
		userModule.setEnable(1);
		userModule.setWebsiteName("互联网关注订阅平台");
		userModule.setModuleInfo(module);
		userModule.setUserModuleSubgroupInfo(impl.getSubgroup(subgroupId));
		userModule.setMonitorType(9);
		impl.save(userModule);
		// 更新订阅信息
		AddMonitorDaoImpl addMonitorDaoImpl = new AddMonitorDaoImpl();
		addMonitorDaoImpl.copyUserModuleToSubUsers(userId,userModule.getUserModuleId());
		userModule.setEnable(2);
		impl.update(userModule);
		
		DatabaseDao databaseDao=new DatabaseDaoImpl();
		Date date=new Date();
		date.setYear(3000);
		databaseDao.insertNewContent(module.getModuleId()+"",  moduleContent, 4,date);
		databaseDao.insertNewContent(module.getModuleId()+"", moduleContent, 8,date);
		return "保存成功！";
	}
	
	/**
	 * 更新用户订阅源
	 * @param userModuleName
	 * @param moduleContent
	 * @param userModuleStyle
	 * @param moduleId
	 * @param userModuleId
	 */
	public String updateCustomModule(String userModuleName, String moduleContent,
			String userModuleStyle, Long moduleId, Long userModuleId, Long subgroupId) {
		if(moduleContent == null || moduleContent.equals(""))
			return "请输入订阅源内容！";
		moduleContent="<div class='updateStyle'>"+moduleContent+"</div>";
		//插入历史纪录  
		DatabaseDao databaseDao=new DatabaseDaoImpl();
		Date date=new Date();
		date.setYear(3000);
		databaseDao.insertNewContent(moduleId+"",moduleContent, 4,date);
		databaseDao.insertNewContent(moduleId+"", moduleContent, 8,date);
		
		
		CustomModuleDao impl = new CustomModuleDaoImpl();	
		ModuleInfo module = impl.getModule(moduleId);
		
		
		/* 更新ModuleInfo */
		module.setUpdateContent(moduleContent);
		impl.update(module);
		UserModuleInfo userModule = impl.getUserModule(userModuleId);
		/* 更新UserModuleInfo */
		userModule.setUserModuleName(userModuleName);
		userModule.setUserModuleStyle(userModuleStyle);
		userModule.setUserModuleSubgroupInfo(impl.getSubgroup(subgroupId));
		impl.update(userModule);

		return "更新成功！";
	}
	
}
