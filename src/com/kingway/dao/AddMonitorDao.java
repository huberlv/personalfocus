package com.kingway.dao;

import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;


public interface AddMonitorDao {

	public boolean isWebInfoExist(String url);

	public void updateWebInfo(String url, String[] css);

	public void copyUserModuleToSubUsers(long masterUserId, long userModuleId);
	
	public ModuleInfo saveModuleInfo(String path, String text, String source, MonitorWebInfo webInfo,int enable);
	
	public UserModuleInfo saveUserModuleInfo(UserInfo userInfo,
			ModuleInfo moduleInfo, String webSiteName, String userModuleName,
			String style, int monitorType, UserModuleSubgroupInfo subgroup);
	
	public UserModuleInfo updateUserModuleInfo(UserModuleInfo userModule, UserInfo userInfo,
			ModuleInfo moduleInfo, String webSiteName, String userModuleName,
			String style, int monitorType, UserModuleSubgroupInfo subgroup);
	
	public MonitorWebInfo saveMonitorWebInfo(String url, String head, String body);
	
	public void saveReceiveInfo(long userModuleId, int isMobile,
			boolean isMail, String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType);
	
	public void saveModule(String url, String path, String text, String source, long userId,
			String websiteName, String userModuleName, String[] css, String style,
			int monitorType, Long subgroupId);
	
	public void saveReceive(int message, boolean email, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, String emailStartTime, String emailStopTime,
			String emailFrequency, String emailFrequencyType);
	
	public void copyDefaultAsUserReceive(long userId);
}
