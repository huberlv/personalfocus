package com.kingway.dao;

import java.util.List;

import com.kingway.model.UserDefaultReceiveInfo;

public interface ManageDefaultReceiveInfoDao {
	
	public List<UserDefaultReceiveInfo> getDefault(long userId);
	
	public void updateDefault(Long userId, boolean isMobile, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, boolean isMail, String emailStartTime,
			String emailStopTime, String emailFrequency,
			String emailFrequencyType);

	
}
