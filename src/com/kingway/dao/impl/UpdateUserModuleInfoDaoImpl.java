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
 * 用户订阅管理DAO
 * @author ZERO
 * @date 2010-10-02
 */
public class UpdateUserModuleInfoDaoImpl implements UpdateUserModuleInfoDao {

	/**
	 * 更新用户模块的style
	 */
	public void updateUserModuleStyle(String style,String uid){
		HibernateUtil.updateRecord("update UserModuleInfo set userModuleStyle='"
				+style+ "' where userModuleId="
				+uid);
	}
	/**
	 * 更新用户模块信息表
	 */
	public void updateUserModuleInfo(String userModuleId, String moduleName) {
		HibernateUtil.updateRecord("update UserModuleInfo set userModuleName='"+ moduleName
				+ "' where userModuleId=" + userModuleId);
	}

	/**
	 * 更新用户接收表
	 */
	@SuppressWarnings("unchecked")
	public void updateUserReceiveInfo(String userModuleId, boolean isMobile,
			boolean isMail, String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType) {
		//获取该userModuleId的UserReceiveInfo
		List<UserReceiveInfo> receives = HibernateUtil.list("from UserReceiveInfo where userModuleId="+userModuleId);
		UserReceiveInfo mail = null; //发送到邮箱的接收表
		UserReceiveInfo mobile = null; //发送到手机的接收表
		if(receives.size() == 2){ //容量为2说明数据库中存在手机和邮箱的接收表
			UserReceiveInfo r0 = receives.get(0);
			UserReceiveInfo r1 = receives.get(1);
			if(r0.getReceiveType() == 7){ //如果r0的接收类型是7，即为发送到邮箱
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
		// 发送到手机
		if(isMobile){
			//如果手机接收信息已存在
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
			// 由于不知道该信息是新建还是修改已有的，所以使用saveOrUpdate方法
			HibernateUtil.saveOrUpdate(receive);
		}else {
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
			// 由于不知道该信息是新建还是修改已有的，所以使用saveOrUpdate方法
			HibernateUtil.saveOrUpdate(receive);
		} else {
			// 如果邮件接收信息已存在，由于用户更新为不发送到邮箱，删除该接收信息
			if (mail != null)
				HibernateUtil.delete(mail);
		}
	}
	
	public void delUserModule(String userModuleId){
		HibernateUtil.delete(HibernateUtil.get(UserModuleInfo.class, Long.valueOf(userModuleId)));
	}
	
	public void changeGroup(String userModuleId,Long subgroupId){
		UserModuleInfo u =(UserModuleInfo) HibernateUtil.getRecord("from UserModuleInfo where userModuleId="+userModuleId);//查询语句是否有问题？
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
     * 吕鸿佩，保存unCheckPaths字段
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
