package com.kingway.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.kingway.dao.UpdateUserModuleInfoDao;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.UserReceiveInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;

/**
 * �û����Ĺ���DAO
 * @author ZERO
 * @date 2010-10-02
 */
public class UpdateUserModuleInfoDaoImpl implements UpdateUserModuleInfoDao {

	/**
	 * �����û�ģ���style
	 */
	public void updateUserModuleStyle(String style,String uid){
		HibernateUtil.updateRecord("update UserModuleInfo set userModuleStyle='"
				+style+ "' where userModuleId="
				+uid);
	}
	/**
	 * �����û�ģ����Ϣ��
	 */
	public void updateUserModuleInfo(String userModuleId, String moduleName) {
		HibernateUtil.updateRecord("update UserModuleInfo set userModuleName='"+ moduleName
				+ "' where userModuleId=" + userModuleId);
	}

	/**
	 * �����û����ձ�
	 */
	@SuppressWarnings("unchecked")
	public void updateUserReceiveInfo(String userModuleId, boolean isMobile,
			boolean isMail, String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		//��ȡ��userModuleId��UserReceiveInfo
		List<UserReceiveInfo> receives = HibernateUtil.list("from UserReceiveInfo where userModuleId="+userModuleId);
		UserReceiveInfo mail = null; //���͵�����Ľ��ձ�
		UserReceiveInfo mobile = null; //���͵��ֻ��Ľ��ձ�
		if(receives.size() == 2){ //����Ϊ2˵�����ݿ��д����ֻ�������Ľ��ձ�
			UserReceiveInfo r0 = receives.get(0);
			UserReceiveInfo r1 = receives.get(1);
			if(r0.getReceiveType() == 7){ //���r0�Ľ���������7����Ϊ���͵�����
				mail = r0; 
				mobile = r1;
			}		
			else{
				mobile = r0;
				mail = r1; 	
			}
		}
		if(receives.size() == 1){
			UserReceiveInfo r0 = receives.get(0);
			if(r0.getReceiveType() == 7){
				mail = r0;
			}
			else{
				mobile = r0;
			}		
		}
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		String dateTime = "";
		String timeStr = "";	
		String dateStr = "2000-01-01";
		UserReceiveInfo receive = null; 
		UserModuleInfo userModule = (UserModuleInfo) HibernateUtil.get(UserModuleInfo.class, Long.valueOf(userModuleId));
		// ���͵��ֻ�
		if(isMobile){
			//����ֻ�������Ϣ�Ѵ���
			if(mobile != null) 
				receive = mobile;
			else
			    receive = new UserReceiveInfo();
			receive.setUserModuleInfo(userModule);
			try {
				receive.setStopTime(sdfTime.parse(messageStopTime));
				receive.setStartTime(sdfTime.parse(messageStartTime));
			} catch (ParseException e) {		
				e.printStackTrace();
			}
			receive.setReceiveType(Integer.parseInt(messageType));
			receive.setMaxReceiveNum(Integer.parseInt(messageMaxNum));
			if(messageFrequencyType.equals("hour"))
				timeStr = messageFrequency+":00:00";
			else
				timeStr = "00:"+messageFrequency+":00";
			dateTime = dateStr+" "+timeStr;
			try {
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			receive.setLastSendTime(new Date());
			// ���ڲ�֪������Ϣ���½������޸����еģ�����ʹ��saveOrUpdate����
			HibernateUtil.saveOrUpdate(receive);
		}else {
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
			    receive = new UserReceiveInfo();
			receive.setUserModuleInfo(userModule);
			try {
				receive.setStartTime(sdfTime.parse(emailStartTime));
				receive.setStopTime(sdfTime.parse(emailStopTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			receive.setReceiveType(7);
			if(emailFrequencyType.equals("hour"))
				timeStr = emailFrequency+":00:00";
			else
				timeStr = "00:"+emailFrequency+":00";
			dateTime = dateStr+" "+timeStr;
			try {
				receive.setSendFrequency(sdfDateTime.parse(dateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			receive.setMaxReceiveNum(0);
			receive.setLastSendTime(new Date());
			// ���ڲ�֪������Ϣ���½������޸����еģ�����ʹ��saveOrUpdate����
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// ����ʼ�������Ϣ�Ѵ��ڣ������û�����Ϊ�����͵����䣬ɾ���ý�����Ϣ
			if (mail != null)
				HibernateUtil.delete(mail);
		}
	}
	
	public void delUserModule(String userModuleId){
		HibernateUtil.delete(HibernateUtil.get(UserModuleInfo.class, Long.valueOf(userModuleId)));
	}
	
	public void changeGroup(String userModuleId,Long subgroupId){
		UserModuleInfo u =(UserModuleInfo) HibernateUtil.getRecord("from UserModuleInfo where userModuleId="+userModuleId);//��ѯ����Ƿ������⣿
		u.getUserModuleSubgroupInfo().setSubgroupId(subgroupId);
		u.setUserModuleStyle(u.getUserModuleStyle().replaceAll("absolute", "static"));
		HibernateUtil.update(u);
	}
	
	public void delGroup(String subgroupId){
		UserModuleSubgroupInfo usermodulesubgroupinfo = (UserModuleSubgroupInfo) HibernateUtil.getRecord("from UserModuleSubgroupInfo where subgroupId="+subgroupId);
		HibernateUtil.delete(usermodulesubgroupinfo);
	}
	@Override
	public void hideUserModuel(Long userModuleId) {
		UserModuleInfo userModuleInfo=(UserModuleInfo)HibernateUtil.get(UserModuleInfo.class, userModuleId);
		userModuleInfo.setAccessType(2);
		HibernateUtil.update(userModuleInfo);
	}
	@Override
    /**
     * �����壬����unCheckPaths�ֶ�
     * @param userModuleId
     * @param paths
     * @return
     */
	public boolean updateUnCheckPaths(Long userModuleId, String paths) {
		// TODO Auto-generated method stub
		
		UserModuleInfo userModuleInfo=(UserModuleInfo)HibernateUtil.get(UserModuleInfo.class, userModuleId);
		userModuleInfo.setUnCheckPaths(paths);
		HibernateUtil.update(userModuleInfo);
		
		
		UserModuleContents userModuleContents;
		Object temp = null;
			
			temp = HibernateUtil.getRecord("from UserModuleContents where userModuleId="+userModuleId);
			if (temp != null) {
				userModuleContents = (UserModuleContents) temp;
				if (userModuleContents.getContentType() == 0) {
					return true;
				} else {
					Document document=null;
					document=Jsoup.parse(userModuleContents.getUserModuleContent());
					LhpUtil.trimUnCheckPaths(document, paths);
					if(LhpUtil.isUpdate(document)==false){
						userModuleContents.setContentType(0);
					}
					userModuleContents.setUserModuleContent(LhpUtil.getBodyString(document));
					HibernateUtil.update(userModuleContents);
				}
			}

		
		return true;
	}

}
