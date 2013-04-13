package com.kingway.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.kingway.dao.ManageDefaultReceiveInfoDao;
import com.kingway.model.UserDefaultReceiveInfo;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;

/**
 * 管理默认接收表
 * @author ZERO
 * @date 2010-10-02
 */
public class ManageDefaultReceiveInfoDaoImpl implements
		ManageDefaultReceiveInfoDao {

	/**
	 * 获取默认接收表
	 */
	@SuppressWarnings("unchecked")
	public List<UserDefaultReceiveInfo> getDefault(long userId) {
		List<UserDefaultReceiveInfo> defaultList = HibernateUtil.list("From UserDefaultReceiveInfo where userId="+userId);
		return defaultList;
	}

	/**
	 * 更新默认接收表
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
		// 发送到手机
		if(isMobile){
			//如果手机接收信息已存在
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
			// 由于不知道该信息是新建还是修改已有的，所以使用saveOrUpdate方法
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// 如果手机接收信息已存在，由于用户更新为不发送到手机，删除该接收信息
			if (mobile != null)
				HibernateUtil.delete(mobile);
		}
		// 发送到邮箱
		if(isMail){
			// 如果邮箱接收信息已存在
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
			// 由于不知道该信息是新建还是修改已有的，所以使用saveOrUpdate方法
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// 如果邮件接收信息已存在，由于用户更新为不发送到邮箱，删除该接收信息
			if (mail != null)
				HibernateUtil.delete(mail);
		}
	}

	
}
