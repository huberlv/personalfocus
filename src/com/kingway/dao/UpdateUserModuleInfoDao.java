package com.kingway.dao;

public interface UpdateUserModuleInfoDao {
	
	public void updateUserModuleInfo(String userModuleId, String modulename);
	
	public void updateUserReceiveInfo(String userModuleId, boolean isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType);
	
	public void updateUserModuleStyle(String style,String uid); 
	
	public void hideUserModuel(Long userModuleId);
	
    /**
     * ÂÀºèÅå£¬±£´æunCheckPaths×Ö¶Î
     * @param userModuleId
     * @param paths
     * @return
     */
    public boolean updateUnCheckPaths(Long userModuleId,String paths);
}
