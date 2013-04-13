package com.kingway.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.kingway.dao.ManageDefaultReceiveInfoDao;
import com.kingway.model.UserDefaultReceiveInfo;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;

/**
 * ����Ĭ�Ͻ��ձ�
 * @author ZERO
 * @date 2010-10-02
 */
public class ManageDefaultReceiveInfoDaoImpl implements
		ManageDefaultReceiveInfoDao {

	/**
	 * ��ȡĬ�Ͻ��ձ�
	 */
	@SuppressWarnings("unchecked")
	public List<UserDefaultReceiveInfo> getDefault(long userId) {
		List<UserDefaultReceiveInfo> defaultList = HibernateUtil.list("From UserDefaultReceiveInfo where userId="+userId);
		return defaultList;
	}

	/**
	 * ����Ĭ�Ͻ��ձ�
	 */
	public void updateDefault(Long userId,boolean isMobile, String messageType,
			String messageStartTime, String messageStopTime,
			String messageFrequency, String messageFrequencyType,
			String messageMaxNum, boolean isMail, String emailStartTime,
			String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		UserInfo user = (UserInfo) HibernateUtil.get(UserInfo.class, userId);
		List<UserDefaultReceiveInfo> modifylist = new ManageDefaultReceiveInfoDaoImpl().getDefault(userId);
		UserDefaultReceiveInfo mobile = null;
		UserDefaultReceiveInfo mail = null;
		UserDefaultReceiveInfo receive = null;
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		String dateTime = "";
		String timeStr = "";	
		String dateStr = "2000-01-01";
		if (modifylist.size() == 2){
			mobile = modifylist.get(0);
			if (mobile.getReceiveType() == 7) {
				mobile = modifylist.get(1);
				mail = modifylist.get(0);
			} else {
				mail = modifylist.get(1);
			}
		}
		if(modifylist.size() == 1){
			mobile = modifylist.get(0);
			if (mobile.getReceiveType() == 7) {
				mail = mobile;
				mobile = null;
			}
		}
		// ���͵��ֻ�
		if(isMobile){
			//����ֻ�������Ϣ�Ѵ���
			if(mobile != null)
				receive = mobile;
			else
				receive = new UserDefaultReceiveInfo();
			receive.setReceiveType(Integer.parseInt(messageType));
			if(messageFrequencyType.equals("hour"))
				timeStr = messageFrequency+":00:00";
			else
				timeStr = "00:"+messageFrequency+":00";
			dateTime = dateStr+" "+timeStr;
			try {
				receive.setStartTime(sdfTime.parse(messageStartTime));
				receive.setStopTime(sdfTime.parse(messageStopTime));
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			receive.setMaxReceiveNum(Integer.parseInt(messageMaxNum));
			receive.setUserInfo(user);
			// ���ڲ�֪������Ϣ���½������޸����еģ�����ʹ��saveOrUpdate����
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// ����ֻ�������Ϣ�Ѵ��ڣ������û�����Ϊ�����͵��ֻ���ɾ���ý�����Ϣ
			if (mobile != null)
				HibernateUtil.delete(mobile);
		}
		// ���͵�����
		if(isMail){
			// ������������Ϣ�Ѵ���
			if(mail != null)
				receive = mail;
			else
				receive = new UserDefaultReceiveInfo();
			if(emailFrequencyType.equals("hour"))
				timeStr = emailFrequency+":00:00";
			else
				timeStr = "00:"+emailFrequency+":00";
			dateTime = dateStr+" "+timeStr;
			try {
				receive.setStartTime(sdfTime.parse(emailStartTime));
				receive.setStopTime(sdfTime.parse(emailStopTime));
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			receive.setReceiveType(7);
			receive.setUserInfo(user);
			// ���ڲ�֪������Ϣ���½������޸����еģ�����ʹ��saveOrUpdate����
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// ����ʼ�������Ϣ�Ѵ��ڣ������û�����Ϊ�����͵����䣬ɾ���ý�����Ϣ
			if (mail != null)
				HibernateUtil.delete(mail);
		}
	}

	
}
