package com.kingway.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import rss.RSSUtil;

import com.kingway.dao.AddMonitorDao;
import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserDefaultReceiveInfo;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.UserReceiveInfo;
import com.kingway.model.UserSubscribe;
import com.kingway.util.HibernateUtil;
import com.kingway.util.OnlineFocus;

import dwr.UserSpace;

/**
 * 保存模块
 * @author ZERO
 *
 */
public class AddMonitorDaoImpl implements AddMonitorDao {
	
	private ModuleInfo module; // 模块信息
	private UserModuleInfo userModule; // 用户模块信息
	private boolean isNewUserModule = true; // 用户模块是否已经存在的标志
	public boolean isNewUserModule() {
		return isNewUserModule;
	}

	public void setNewUserModule(boolean isNewUserModule) {
		this.isNewUserModule = isNewUserModule;
	}

	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ModuleInfo getModule() {
		return module;
	}

	public void setModule(ModuleInfo module) {
		this.module = module;
	}

	public UserModuleInfo getUserModule() {
		return userModule;
	}

	public void setUserModule(UserModuleInfo userModule) {
		this.userModule = userModule;
	}

	/**
	 * 判断是否存在该webInfo记录
	 */
	public boolean isWebInfoExist(String url) {
		return(HibernateUtil.isRecordExist("from MonitorWebInfo where webAddress='"+url+"'"));
	}
	
	/**
	 * 更新WebInfo
	 */
	public void updateWebInfo(String url, String[] css) {
		MonitorWebInfo webInfo;
		try {
			webInfo = (MonitorWebInfo) HibernateUtil.getRecord("from MonitorWebInfo where webAddress='" + url+ "'");
			Long userCount = webInfo.getUserCount();
			webInfo.setUserCount(userCount + 1);
			webInfo.setHeadCssAndScript(css[0]);
			webInfo.setBodyCssAndScript(css[1]);
			HibernateUtil.update(webInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存monitor_web_info
	 */
	@Override
	public MonitorWebInfo saveMonitorWebInfo(String url, String head,
			String body) {
		MonitorWebInfo webInfo = new MonitorWebInfo();
		webInfo.setWebAddress(url);
		webInfo.setHeadCssAndScript(head);
		webInfo.setBodyCssAndScript(body);
		HibernateUtil.save(webInfo);
		return webInfo;
	}
	
	/**
	 * 保存module_info
	 */
	@Override
	public ModuleInfo saveModuleInfo(String path, String text,String source,
			MonitorWebInfo webInfo,int enable) {
		
		ModuleInfo moduleInfo = new ModuleInfo();
		moduleInfo.setModulePath(path);
		moduleInfo.setUpdateContent(text);
		moduleInfo.setMonitorWebInfo(webInfo);
		moduleInfo.setEnable(enable);
		moduleInfo.setDefineSource(source);
		//吕鸿佩添加
		moduleInfo.setRssContent(RSSUtil.createANewRSS( webInfo.getWebAddress(), text).getXML());
		moduleInfo.setUserCount(1L);
		moduleInfo.setDefineSource(source);
		HibernateUtil.save(moduleInfo);
		return moduleInfo;
	}
	
	/**
	 * 保存user_module_info
	 * @return
	 */
	@Override
	public UserModuleInfo saveUserModuleInfo(UserInfo userInfo,
			ModuleInfo moduleInfo, String webSiteName, String userModuleName,
			String style, int monitorType, UserModuleSubgroupInfo subgroup) {
		UserModuleInfo userModule = new UserModuleInfo();
		userModule.setUserInfo(userInfo);
		userModule.setModuleInfo(moduleInfo);
		userModule.setWebsiteName(webSiteName);
		userModule.setUserModuleName(userModuleName);
		userModule.setUserModuleStyle(style);
		userModule.setMonitorType(monitorType);
		userModule.setUserModuleSubgroupInfo(subgroup);
		userModule.setUnCheckPaths("null");
		if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
			userModule.setAccessType(2);  //吕鸿佩增加
		}
		HibernateUtil.save(userModule);
		
		return userModule;
	}
	
	/**
	 * 更新user_module_info
	 */
	@Override
	public UserModuleInfo updateUserModuleInfo(UserModuleInfo userModule, UserInfo userInfo,
			ModuleInfo moduleInfo, String webSiteName, String userModuleName,
			String style, int monitorType, UserModuleSubgroupInfo subgroup) {
		userModule.setUserInfo(userInfo);
		userModule.setModuleInfo(moduleInfo);
		userModule.setWebsiteName(webSiteName);
		userModule.setUserModuleName(userModuleName);
		userModule.setUserModuleStyle(style);
		userModule.setMonitorType(monitorType);
		if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
			userModule.setAccessType(2);  //吕鸿佩增加	
		}else{
			userModule.setAccessType(1);  //吕鸿佩增加	
		}
		userModule.setUserModuleSubgroupInfo(subgroup);
		HibernateUtil.update(userModule);
		return userModule;
	}
	
	/**
	 * 保存UserReceiveInfo
	 */
	public void saveReceiveInfo(long userModuleId, int isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		String dateStr = "2000-01-01"; //日期字符串表示
		String timeStr; //当天的时间字符串表示
		String dateTime; //当天的日期 时间字符串表示，即Date
		UserModuleInfo userModule = new UserModuleInfo(); 
		userModule.setUserModuleId(userModuleId);
		UserReceiveInfo receive = new UserReceiveInfo();
		receive.setUserModuleInfo(userModule);
		/* 如果需要发送到手机,isMobile对应定义页面的“是否发送到手机的checkbox” */
		if(isMobile != 0){	
			try {
				receive.setStartTime(sdfTime.parse(messageStartTime));
				receive.setStopTime(sdfTime.parse(messageStopTime));
				receive.setReceiveType(Integer.parseInt(messageType));
				receive.setMaxReceiveNum(Integer.parseInt(messageMaxNum));
				if(messageFrequencyType.equals("hour"))
					timeStr = messageFrequency+":00:00";
				else
					timeStr = "00:"+messageFrequency+":00";
				dateTime = dateStr+" "+timeStr;
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
				dateTime = "2000-01-01 00:00:00";
				receive.setLastSendTime(sdfDateTime.parse(dateTime)); //第一次插入receiveInfo表，设置一个远小于当前日期的日子
				receive.setMaxReceiveNum(Integer.parseInt(messageMaxNum));
				HibernateUtil.save(receive);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		    
		} //end of if
		
		/* 如果发送到邮箱 */
		if(isMail){
			try {
				receive.setStartTime(sdfTime.parse(emailStartTime));
				receive.setStopTime(sdfTime.parse(emailStopTime));
				receive.setReceiveType(7);
				if(emailFrequencyType.equals("hour"))
					timeStr = emailFrequency+":00:00";
				else
					timeStr = "00:"+emailFrequency+":00";
				dateTime = dateStr+" "+timeStr;
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
				dateTime = "2000-01-01 00:00:00";
				receive.setLastSendTime(sdfDateTime.parse(dateTime)); //第一次插入receiveInfo表，设置一个远小于当前日期的日子
				receive.setMaxReceiveNum(0);
				HibernateUtil.save(receive);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}//end of if
	}
	
	/**
	 * 保存定义的模块
	 */
	public void saveModule(String url, String path, String text,String source, long userId,
			String websiteName, String userModuleName, String[] css, String style,
			int monitorType, Long subgroupId){
		
		/* user_info */
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		/* 分组信息 */
		UserModuleSubgroupInfo subgroup = (UserModuleSubgroupInfo) HibernateUtil
				.get(UserModuleSubgroupInfo.class, subgroupId);
		/* web_info是否已经存在 */
		boolean flag = isWebInfoExist(url);
		//如果web_info已经存在
		if(flag){		
			MonitorWebInfo webInfo = (MonitorWebInfo) HibernateUtil
					.getRecord("from MonitorWebInfo where webAddress='" + url + "'");
			webInfo.setBodyCssAndScript(css[1]);
			webInfo.setHeadCssAndScript(css[0]);  
			HibernateUtil.update(webInfo);   
			Long webId = webInfo.getWebId();
			if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){//如果监控类型是个人监rp
				
				String hql = "from ModuleInfo where webId="+webId+" and modulePath='"+path.replaceAll("'", "\"")+"' and enable=0";
				this.module = (ModuleInfo) HibernateUtil.getRecord(hql);
				if(module==null){
					 module = saveModuleInfo(path, text, source,webInfo,0);
				}else{
					long moduleInfoId = module.getModuleId();	
					hql = "from UserModuleInfo where moduleId=" + moduleInfoId + " and userId="+userId;
					Object obj=HibernateUtil.getRecord(hql);
					if(obj==null){ 
						this.isNewUserModule = true;
						 module = saveModuleInfo(path, text, source, webInfo,0);
					}else{
						this.isNewUserModule = false;
						userModule = updateUserModuleInfo((UserModuleInfo)obj, userInfo, module,
								websiteName, userModuleName, style, monitorType, subgroup);
						
					}
				}
			}else{
				
			String hql = "from ModuleInfo where webId="+webId+" and modulePath='"+path.replaceAll("'", "\"")+"'";
			/* 保存ModuleInfo */
			this.module = (ModuleInfo) HibernateUtil.getRecord(hql);
			if (module == null) {
				if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
				   module = saveModuleInfo(path, text, source, webInfo,0);
				}else{
					 module = saveModuleInfo(path, text,  source,webInfo,1);
					 
				}
			} else { // 如果存在则更新
				module.setUpdateContent(text);
				module.setMonitorWebInfo(webInfo);
				if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
					module.setEnable(0);
					}else{
						 module.setEnable(1);
					}
				HibernateUtil.update(module);
			}
			}
			/* 保存UserModuleInfo */	
			this.userModule = new UserModuleInfo();
			long moduleInfoId = module.getModuleId();	
			String hql = "from UserModuleInfo where moduleId=" + moduleInfoId + " and userId="+userId;
			
			Object obj=HibernateUtil.getRecord(hql);
			if(obj==null){ 
				System.out.println("如果用户模块不存在,插入用户模块");
				//如果用户模块不存在,插入用户模块
				this.isNewUserModule = true;
				userModule = saveUserModuleInfo(userInfo, module, websiteName,
						userModuleName, style, monitorType, subgroup);
			}else{ 
				System.out.println("如果用户模块存在,更新用户模块");
				//用户模块已存在,更新用户模块
				this.isNewUserModule = false;
				userModule = updateUserModuleInfo((UserModuleInfo)obj, userInfo, module,
						websiteName, userModuleName, style, monitorType, subgroup);
			}
			
		} else {
			/* 保存MonitorWebInfo */
			MonitorWebInfo webInfo = saveMonitorWebInfo(url, css[0], css[1]);
			/* 保存ModuleInfo */
			if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
			this.module = saveModuleInfo(path, text, source, webInfo,0);
			}else{
				this.module = saveModuleInfo(path, text, source, webInfo,1);
			}
			/* 保存UserModuleInfo */
			this.userModule = saveUserModuleInfo(userInfo, module, websiteName,
					userModuleName, style, monitorType, subgroup);
			
		}
	}
	
	/**
	 * 保存用户接收表
	 */
	public void saveReceive(int message, boolean email, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, String emailStartTime, String emailStopTime,
			String emailFrequency, String emailFrequencyType) {
		// 如果是新的用户模块
		if(this.isNewUserModule){
			// 如果需要有勾选“发送到手机”，需要读取出发送短信的类型
			if(message == 1)
				message = Integer.parseInt(messageType); 
			// 保存用户接收表
			saveReceiveInfo(userModule.getUserModuleId(), message, email,
					messageType, messageStartTime, messageStopTime,
					messageFrequency, messageFrequencyType, messageMaxNum,
					emailStartTime, emailStopTime, emailFrequency,
					emailFrequencyType);
		} else {
			boolean isMobile = false;
			if (message != 0)
				isMobile = true;
			new UpdateUserModuleInfoDaoImpl().updateUserReceiveInfo(userModule
					.getUserModuleId()+ "", isMobile, email, messageType, messageStartTime,
					messageStopTime, messageFrequency, messageFrequencyType,
					messageMaxNum, emailStartTime, emailStopTime,
					emailFrequency, emailFrequencyType);
		}	
	}
	
	/**
	 * 将用户默认接收表作为用户模块的接收表设置
	 */
	@SuppressWarnings("unchecked")
	public void copyDefaultAsUserReceive(long userId){
		List<UserDefaultReceiveInfo> defaultList = HibernateUtil
				.list("From UserDefaultReceiveInfo where userId=" + userId);
		UserReceiveInfo receive;
		for(UserDefaultReceiveInfo d : defaultList){
			receive = new UserReceiveInfo();
			int receiveType = d.getReceiveType();
			receive.setStartTime(d.getStartTime());
			receive.setStopTime(d.getStopTime());
			receive.setSendFrequency(d.getSendFrequency());
			receive.setMaxReceiveNum(d.getMaxReceiveNum());
			receive.setUserModuleInfo(userModule);
			String dateTime = "2000-01-01 00:00:00";
			try {
				receive.setLastSendTime(sdfDateTime.parse(dateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			receive.setReceiveType(receiveType);
			HibernateUtil.save(receive);
		}
	}
	
	/**
	 * copy被订阅者的新栏目给其所有的订阅者
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void copyUserModuleToSubUsers(long masterUserId, long userModuleId) {
		List<UserSubscribe> users = HibernateUtil.list("from UserSubscribe where subscribeUserId=" + masterUserId);
		long subUserId; //订阅者的用户id
		UserSpace space = new UserSpace();
		for (int i = 0; i < users.size(); i++) {
			subUserId = users.get(i).getUserInfoByUserId().getUserId();
			space.subscribeAUserModule(subUserId, userModuleId);
		}	
	}
	
	
}
