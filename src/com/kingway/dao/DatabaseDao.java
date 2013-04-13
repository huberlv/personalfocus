package com.kingway.dao;

import java.util.ArrayList;
import java.util.Date;

import update.ModuleNeedToBeUpdate;

import message.ModuleLinkStruct;
import message.SendPara;

public interface DatabaseDao {

	public void setModuleEnable(String moduleId,int enable);
	public void deleteAlreadySend(String sendId);

	public ArrayList<SendPara> getTerMInfo(int maxnum, long userId);

	public void setLastSendTime(String receiveId);

	public ArrayList<ModuleLinkStruct> getModuleInfo(int maxnum);

	public void setAlreadySend(String sendId);

	public ArrayList<SendPara> getMail(int maxnum);

	public ArrayList<ModuleNeedToBeUpdate> getMonitorModule(int maxNum);

	public boolean setWebMonitoring(String webAddress);

	public void insertNewContent(String moduleId, String content,int monitorType);

	public void resetMonitorWebInfo();

	public void setLastMonitorTime(String url);

	public boolean updateModuleUpdateConetnt(String moduleId, String content,String newContent);

	public boolean insertNewUserModule(String userId, String monitorModuleName,
			long moduleId, String monitorFrequency, String webName);

	public long queryMonitorId(long webId, String path);

	public boolean insertNewModule(long webId, String modulePath,
			StringBuffer updateContent);

	public boolean resetWebMonitoring(String webAddress);

	public void resetWebFailTimes(String webAddress);
	
	public void resetModuleFailTimes(long moduleId);
	
	public long insertNewWeb(String urlstr, String monitorFrequency);
	
	public void increaseWebFailTimes(String webAddress);
	
	public void increaseModuleFailTimes(String moduleId);
	
	public long queryWebId(String urlstr);
	
	public void setWebMonitorFrequency(long webId, String monitorFrequency);
	
	public String getModuleUpdateContent(long moduleId);
	public void resetWebIsMonitor();
	
	public void insertNewContent(String moduleId, String content,int monitorType,Date date);

	
}
