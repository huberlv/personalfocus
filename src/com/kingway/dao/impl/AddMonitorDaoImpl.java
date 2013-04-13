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
 * ����ģ��
 * @author ZERO
 *
 */
public class AddMonitorDaoImpl implements AddMonitorDao {
	
	private ModuleInfo module; // ģ����Ϣ
	private UserModuleInfo userModule; // �û�ģ����Ϣ
	private boolean isNewUserModule = true; // �û�ģ���Ƿ��Ѿ����ڵı�־
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
	 * �ж��Ƿ���ڸ�webInfo��¼
	 */
	public boolean isWebInfoExist(String url) {
		return(HibernateUtil.isRecordExist("from MonitorWebInfo where webAddress='"+url+"'"));
	}
	
	/**
	 * ����WebInfo
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
	 * ����monitor_web_info
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
	 * ����module_info
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
		//���������
		moduleInfo.setRssContent(RSSUtil.createANewRSS( webInfo.getWebAddress(), text).getXML());
		moduleInfo.setUserCount(1L);
		moduleInfo.setDefineSource(source);
		HibernateUtil.save(moduleInfo);
		return moduleInfo;
	}
	
	/**
	 * ����user_module_info
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
			userModule.setAccessType(2);  //����������
		}
		HibernateUtil.save(userModule);
		
		return userModule;
	}
	
	/**
	 * ����user_module_info
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
			userModule.setAccessType(2);  //����������	
		}else{
			userModule.setAccessType(1);  //����������	
		}
		userModule.setUserModuleSubgroupInfo(subgroup);
		HibernateUtil.update(userModule);
		return userModule;
	}
	
	/**
	 * ����UserReceiveInfo
	 */
	public void saveReceiveInfo(long userModuleId, int isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		String dateStr = "2000-01-01"; //�����ַ�����ʾ
		String timeStr; //�����ʱ���ַ�����ʾ
		String dateTime; //��������� ʱ���ַ�����ʾ����Date
		UserModuleInfo userModule = new UserModuleInfo(); 
		userModule.setUserModuleId(userModuleId);
		UserReceiveInfo receive = new UserReceiveInfo();
		receive.setUserModuleInfo(userModule);
		/* �����Ҫ���͵��ֻ�,isMobile��Ӧ����ҳ��ġ��Ƿ��͵��ֻ���checkbox�� */
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
				receive.setLastSendTime(sdfDateTime.parse(dateTime)); //��һ�β���receiveInfo������һ��ԶС�ڵ�ǰ���ڵ�����
				receive.setMaxReceiveNum(Integer.parseInt(messageMaxNum));
				HibernateUtil.save(receive);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		    
		} //end of if
		
		/* ������͵����� */
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
				receive.setLastSendTime(sdfDateTime.parse(dateTime)); //��һ�β���receiveInfo������һ��ԶС�ڵ�ǰ���ڵ�����
				receive.setMaxReceiveNum(0);
				HibernateUtil.save(receive);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}//end of if
	}
	
	/**
	 * ���涨���ģ��
	 */
	public void saveModule(String url, String path, String text,String source, long userId,
			String websiteName, String userModuleName, String[] css, String style,
			int monitorType, Long subgroupId){
		
		/* user_info */
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		/* ������Ϣ */
		UserModuleSubgroupInfo subgroup = (UserModuleSubgroupInfo) HibernateUtil
				.get(UserModuleSubgroupInfo.class, subgroupId);
		/* web_info�Ƿ��Ѿ����� */
		boolean flag = isWebInfoExist(url);
		//���web_info�Ѿ�����
		if(flag){		
			MonitorWebInfo webInfo = (MonitorWebInfo) HibernateUtil
					.getRecord("from MonitorWebInfo where webAddress='" + url + "'");
			webInfo.setBodyCssAndScript(css[1]);
			webInfo.setHeadCssAndScript(css[0]);  
			HibernateUtil.update(webInfo);   
			Long webId = webInfo.getWebId();
			if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){//�����������Ǹ��˼�rp
				
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
			/* ����ModuleInfo */
			this.module = (ModuleInfo) HibernateUtil.getRecord(hql);
			if (module == null) {
				if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
				   module = saveModuleInfo(path, text, source, webInfo,0);
				}else{
					 module = saveModuleInfo(path, text,  source,webInfo,1);
					 
				}
			} else { // ������������
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
			/* ����UserModuleInfo */	
			this.userModule = new UserModuleInfo();
			long moduleInfoId = module.getModuleId();	
			String hql = "from UserModuleInfo where moduleId=" + moduleInfoId + " and userId="+userId;
			
			Object obj=HibernateUtil.getRecord(hql);
			if(obj==null){ 
				System.out.println("����û�ģ�鲻����,�����û�ģ��");
				//����û�ģ�鲻����,�����û�ģ��
				this.isNewUserModule = true;
				userModule = saveUserModuleInfo(userInfo, module, websiteName,
						userModuleName, style, monitorType, subgroup);
			}else{ 
				System.out.println("����û�ģ�����,�����û�ģ��");
				//�û�ģ���Ѵ���,�����û�ģ��
				this.isNewUserModule = false;
				userModule = updateUserModuleInfo((UserModuleInfo)obj, userInfo, module,
						websiteName, userModuleName, style, monitorType, subgroup);
			}
			
		} else {
			/* ����MonitorWebInfo */
			MonitorWebInfo webInfo = saveMonitorWebInfo(url, css[0], css[1]);
			/* ����ModuleInfo */
			if((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER){
			this.module = saveModuleInfo(path, text, source, webInfo,0);
			}else{
				this.module = saveModuleInfo(path, text, source, webInfo,1);
			}
			/* ����UserModuleInfo */
			this.userModule = saveUserModuleInfo(userInfo, module, websiteName,
					userModuleName, style, monitorType, subgroup);
			
		}
	}
	
	/**
	 * �����û����ձ�
	 */
	public void saveReceive(int message, boolean email, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, String emailStartTime, String emailStopTime,
			String emailFrequency, String emailFrequencyType) {
		// ������µ��û�ģ��
		if(this.isNewUserModule){
			// �����Ҫ�й�ѡ�����͵��ֻ�������Ҫ��ȡ�����Ͷ��ŵ�����
			if(message == 1)
				message = Integer.parseInt(messageType); 
			// �����û����ձ�
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
	 * ���û�Ĭ�Ͻ��ձ���Ϊ�û�ģ��Ľ��ձ�����
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
	 * copy�������ߵ�����Ŀ�������еĶ�����
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void copyUserModuleToSubUsers(long masterUserId, long userModuleId) {
		List<UserSubscribe> users = HibernateUtil.list("from UserSubscribe where subscribeUserId=" + masterUserId);
		long subUserId; //�����ߵ��û�id
		UserSpace space = new UserSpace();
		for (int i = 0; i < users.size(); i++) {
			subUserId = users.get(i).getUserInfoByUserId().getUserId();
			space.subscribeAUserModule(subUserId, userModuleId);
		}	
	}
	
	
}
