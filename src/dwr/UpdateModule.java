package dwr;

import com.kingway.dao.impl.UpdateUserModuleInfoDaoImpl;

/**
 * �û����Ĺ���
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
		/* �����û�ģ����Ϣ�� */
		updateInfo.updateUserModuleInfo(userModuleId, moduleName);
		/* �����û����ձ� */
		updateInfo.updateUserReceiveInfo(userModuleId, isMobile, isMail,
				messageType, messageStartTime, messageStopTime,
				messageFrequency, messageFrequencyType, messageMaxNum,
				emailStartTime, emailStopTime, emailFrequency,
				emailFrequencyType);
		
		return "���³ɹ���";
	}
	


}
