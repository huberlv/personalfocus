package dwr;

import com.kingway.dao.impl.ManageDefaultReceiveInfoDaoImpl;
import com.opensymphony.xwork2.ActionContext;

/**
 * Ĭ�Ͻ��ձ����
 * @author ZERO
 *
 */
public class UpdateDefault {

	public String updateDefaultReceive(boolean isMobile, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, boolean isMail, String emailStartTime,
			String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		
		ManageDefaultReceiveInfoDaoImpl d = new ManageDefaultReceiveInfoDaoImpl();
		Long userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
		/* ����Ĭ�Ͻ��ձ� */
		d.updateDefault(userId, isMobile, messageType, messageStartTime,
				messageStopTime, messageFrequency, messageFrequencyType,
				messageMaxNum, isMail, emailStartTime, emailStopTime,
				emailFrequency, emailFrequencyType);
		
		return "���³ɹ���";
	}
}
