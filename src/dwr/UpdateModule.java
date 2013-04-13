package dwr;

import com.kingway.dao.impl.UpdateUserModuleInfoDaoImpl;

/**
 * 用户订阅管理
 * @author ZERO
 * @date 2010-10-02
 */
public class UpdateModule {
	
	public String updateModule(String userModuleId, String moduleName,
		 boolean isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		UpdateUserModuleInfoDaoImpl updateInfo = new UpdateUserModuleInfoDaoImpl();
		/* 更新用户模块信息表 */
		updateInfo.updateUserModuleInfo(userModuleId, moduleName);
		/* 更新用户接收表 */
		updateInfo.updateUserReceiveInfo(userModuleId, isMobile, isMail,
				messageType, messageStartTime, messageStopTime,
				messageFrequency, messageFrequencyType, messageMaxNum,
				emailStartTime, emailStopTime, emailFrequency,
				emailFrequencyType);
		
		return "更新成功！";
	}
	


}
