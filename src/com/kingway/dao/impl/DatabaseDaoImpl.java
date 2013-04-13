package com.kingway.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rss.RSS;

import update.Module;
import update.ModuleNeedToBeUpdate;

import message.ModuleLinkStruct;
import message.SendPara;

import com.kingway.dao.DatabaseDao;
import com.kingway.model.ContentBuffer;
import com.kingway.model.ContentReceiver;
import com.kingway.model.MailNeedToBeSendView;
import com.kingway.model.MailNeedToBeSendViewId;
import com.kingway.model.ModuleInfo;
import com.kingway.model.ModuleNeedToBeUpdateView;
import com.kingway.model.ModuleNeedToBeUpdateViewId;
import com.kingway.model.ModulesLinkView;
import com.kingway.model.ModulesLinkViewId;
import com.kingway.model.MonitorWebInfo;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserReceiveInfo;
import com.kingway.util.HibernateUtil;
import email.EmailStruct;

/**
 * 数据库操作工具类
 * 
 * @author ZERO
 * 
 */
public class DatabaseDaoImpl implements DatabaseDao {
	private static final Object lock = new Object();
	private static final int MAX_FAIL_TIMES=10;

	/**
	 * 删除已发送的信息接收者纪录
	 * 
	 * @param sendID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteAlreadySend(String sendId) {
		ContentReceiver cr = (ContentReceiver) HibernateUtil.get(
				ContentReceiver.class, Long.parseLong(sendId));
		// 触发器
		if (cr != null) {
			if (cr.getSendType() != 7) {
				List<ContentReceiver> crs = (List<ContentReceiver>) HibernateUtil
						.list("from ContentReceiver where bufferId="
								+ cr.getContentBuffer().getBufferId()
								+ " and sendType<>7 and userModuleId="
								+ cr.getUserModuleInfo().getUserModuleId());
				for (ContentReceiver temp : crs) {
					HibernateUtil.delete(temp);
					
				}

			} else {
				HibernateUtil.delete(cr);
			}
		}

	}

	/**
	 * 查询要发送到相应用户id的信息
	 * 
	 * @return 不能返回null
	 */
	@Override
	public ArrayList<SendPara> getTerMInfo(int maxnum, long userId) {
		ArrayList<SendPara> send = new ArrayList<SendPara>(maxnum);
		// List<ContentNeedToBeSendView> sendList =
		// HibernateUtil.list("from ContentNeedToBeSendView where userId='"+
		// userId + "';");
		// for (ContentNeedToBeSendView content : sendList) {
		// send.add(new SendPara(...));
		// }
		return send;
	}

	

	/**
	 * 查询 modules_link_view 视图，找出要发送的短信链接
	 * 
	 * @param maxnum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ModuleLinkStruct> getModuleInfo(int maxnum) {
		
		ArrayList<ModuleLinkStruct> send = new ArrayList<ModuleLinkStruct>(
				maxnum);
		List<ModulesLinkView> sendList = HibernateUtil
				.list("from ModulesLinkView");
		ArrayList<String> receiveId = new ArrayList<String>(5);
		receiveId.add("");
		for (int i = 0; i < sendList.size() && (maxnum-- > 0); i++) {
			ModulesLinkViewId link = sendList.get(i).getId();
			send.add(new ModuleLinkStruct( link
					.getUserName(), link.getPhone()));
			if (!(link.getReceiveId() + "").equals(receiveId.get(receiveId
					.size() - 1))) {
				receiveId.add(link.getReceiveId() + "");
			}
			this.setAlreadySend(link.getUserModuleId()+"");
		}
	
		for (int i = 1; i < receiveId.size(); i++) {
			setLastSendTime(receiveId.get(i));
		}
		return send;
	}

	/**
	 * 查询 mail_need_to_be_send_view 视图，找出要发送的信件 同一个接收者的信件放在同一封信件中
	 * 25日吕鸿佩修改
	 * @param maxnum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SendPara> getMail(int maxnum) {
		ArrayList<SendPara> send = new ArrayList<SendPara>(maxnum);
		List<MailNeedToBeSendView> mailList = HibernateUtil
				.list("from MailNeedToBeSendView");
		ArrayList<Long> contentIds = new ArrayList<Long>(5);
		MailNeedToBeSendViewId arecord;
		long current =-1;
		EmailStruct currentEmailStruct=null;
		for (int i = 0; i < mailList.size() && (maxnum-- > 0); i++) {
			 arecord = mailList.get(i).getId();
			contentIds.add(arecord.getContentId());
			if(current!=arecord.getUserId()){
				current=arecord.getUserId();
				if(currentEmailStruct!=null){
				  send.add(new SendPara(arecord.getMail(), currentEmailStruct.getContent()));
				}
				currentEmailStruct=new EmailStruct(arecord.getUserName());
				
			}
			currentEmailStruct.getMailNeedToBeSend().add(arecord);
			
		}
		if(mailList.size()>0){
		send.add(new SendPara(mailList.get(mailList.size()-1).getId().getMail(), currentEmailStruct.getContent()));
		}
		List <UserModuleContents> userModuleContents=HibernateUtil.batchGet(UserModuleContents.class, contentIds);
		if(userModuleContents!=null){
		for (int i = 0; i < userModuleContents.size(); i++) {
			userModuleContents.get(i).setAlreadySendMail(1);
		}
		HibernateUtil.batchUpdate(userModuleContents);
		}
		return send;
	}

	/**
	 * 返回要更新的网址和其模块路径，最新内容
	 * 
	 * @return ArrayList<ModuleNeedToBeUpdate> ，如无则返回null
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ModuleNeedToBeUpdate> getMonitorModule(int maxNum) {
		// 同一时间只能有一个线程选监控模块
		synchronized (DatabaseDaoImpl.lock) {
			ArrayList<ModuleNeedToBeUpdate> module = new ArrayList<ModuleNeedToBeUpdate>(
					maxNum);
			ArrayList<Module> user = new ArrayList<Module>(5);
			ModuleNeedToBeUpdate currentm = null;
			String address = " ";
			String path;
			String text;
			String id;
			String nextadd = "";
			ModuleNeedToBeUpdateViewId moduleView = null;
			try {
				List<ModuleNeedToBeUpdateView> moduleList = HibernateUtil
						.list("from ModuleNeedToBeUpdateView");
				if (moduleList.size() > 0) {
					moduleView = moduleList.get(0).getId();
					address = moduleView.getWebAddress();
					path = moduleView.getPath();
					text = moduleView.getUpdateContent();
					id = moduleView.getModuleId() + "";
					user.add(new Module(path, text, id));
					currentm = new ModuleNeedToBeUpdate(user, address);
				} else {
					return module;
				}
				for (int i = 1; i < moduleList.size(); i++) { // 第0个已经读取，从第1个开始读取
					moduleView = moduleList.get(i).getId();
					nextadd = moduleView.getWebAddress();
					if (nextadd.equals(address) == false) {
						module.add(currentm);
						this.setWebMonitoring(currentm.address);
						user = new ArrayList<Module>(5);
						address = nextadd;
						currentm = new ModuleNeedToBeUpdate(user, address);
					}
					path = moduleView.getPath();
					text = moduleView.getUpdateContent();
					id = moduleView.getModuleId() + "";
					user.add(new Module(path, text, id));
				}
				module.add(currentm);
				for (int i = 0; i < module.size(); i++) {
					System.out.println(module.get(i).address);
					for (int j = 0; j < module.get(i).modules.size(); j++) {
						System.out.println(module.get(i).modules.get(j).path);
					}
				}
				return module;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 往 content_buffer表插入一条新纪录
	 * 
	 * @param moduleId
	 * @param content
	 */
	@Override
	public void insertNewContent(String moduleId, String content,
			int monitorType) {
		ContentBuffer cb = new ContentBuffer();
		ModuleInfo mi = new ModuleInfo();
		mi.setModuleId(Long.valueOf(moduleId));
		cb.setModuleInfo(mi);
		cb.setContent(content);
		cb.setUpdateType(monitorType);
		cb.setUpdateTime(new Date());
		HibernateUtil.save(cb);
	}

	/**
	 * 将monitor_web_info 表中的 is_Monitoring列全部设为0
	 */
	@Override
	public void resetMonitorWebInfo() {
		HibernateUtil.updateRecord("update MonitorWebInfo set isMonitoring=0");
	}

	/**
	 * 设置 monitor_web_info表中给 定网址的last_monitor_time 为当前时间
	 * 
	 * @param url
	 * @param type
	 *            为0时设为当前时间，为1时设为当前时间加上next_time_if_error字段指定的时间
	 */
	@Override
	public void setLastMonitorTime(String url) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String lastTime = sdf.format(time);
			HibernateUtil
					.updateRecord("update MonitorWebInfo set lastMonitorTime='"
							+ lastTime + "' where webAddress='" + url + "'");
		
	}

	/**
	 * 更新Module_update_conetnt 表,如果表中无此纪录，则插入一条纪录
	 * 
	 * @param moduleId
	 * @param content
	 * @return
	 */
	@Override
	public boolean updateModuleUpdateConetnt(String moduleId, String content,String newContent) {
		ModuleInfo module = (ModuleInfo) HibernateUtil.get(ModuleInfo.class,
				Long.parseLong(moduleId));
		module.setUpdateContent(content);
		//吕鸿佩修改
		RSS rss=new RSS();
		boolean result =rss.parseStr(module.getRssContent());
		if(result==true){
			rss.addItems(newContent, true);
		}
		module.setRssContent(rss.getXML());
		try {
			HibernateUtil.update(module);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向 user_monitor_module_info_table 表插入一条新纪录 如果已有纪录则更新其内容，并将enable设为1
	 * 
	 * @return
	 */
	@Override
	public boolean insertNewUserModule(String userId, String monitorModuleName,
			long moduleId, String monitorFrequency, String webName) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		UserModuleInfo umi = new UserModuleInfo();
		UserInfo ui = new UserInfo();
		ModuleInfo mi = new ModuleInfo();
		ui.setUserId(Long.valueOf(userId));
		umi.setUserInfo(ui);
		umi.setUserModuleName(monitorModuleName);
		mi.setModuleId(moduleId);
		umi.setModuleInfo(mi);
		try {
			umi.setMonitorFrequency(sdf.parse(monitorFrequency));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		umi.setWebsiteName(webName);
		try {
			HibernateUtil.saveOrUpdate(umi);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询 monitor_module_info表， 如果有则将其enable标志置为1， 返回monitor_id ,无则返回-1
	 * 
	 * @param webId
	 * @param path
	 * @return moduleId
	 * @exception 不抛出异常
	 */
	@Override
	public long queryMonitorId(long webId, String path) {
		long id = -1;
		ModuleInfo module = (ModuleInfo) HibernateUtil
				.getRecord("from ModuleInfo where webId=" + webId
						+ " and modulePath='" + path + "'");
		if (module != null) {
			id = module.getModuleId();
		}
		return id;
	}

	/**
	 * 向 module_info 表插入一条新纪录
	 * 
	 * @param webId
	 * @param modulePath
	 * @return 是否插入成功
	 */
	@Override
	public boolean insertNewModule(long webId, String modulePath,
			StringBuffer updateContent) {
		ModuleInfo module = new ModuleInfo();
		MonitorWebInfo webInfo = new MonitorWebInfo();
		webInfo.setWebId(webId);
		module.setMonitorWebInfo(webInfo);
		module.setModulePath(modulePath);
		module.setUpdateContent(updateContent.toString());
		try {
			HibernateUtil.save(module);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 *设置 monitor_web_info表中的 is_Monitoring为1
	 * 
	 * @param webAddress
	 * @return
	 */
	@Override
	public boolean setWebMonitoring(String webAddress) {
		String hql = "update MonitorWebInfo wi set wi.isMonitoring=1 where webAddress='"
				+ webAddress + "'";
		try {
			HibernateUtil.updateRecord(hql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 *设置 monitor_web_info表中的对应web_address 的 is_Monitoring为0
	 * 
	 * @param webAddress
	 * @return
	 */
	@Override
	public boolean resetWebMonitoring(String webAddress) {
		String hql = "update MonitorWebInfo wi set wi.isMonitoring=0,totalMonitorTimes=totalMonitorTimes+1 where webAddress='"
				+ webAddress + "'";
		try {
			HibernateUtil.updateRecord(hql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 重置网址失败记数器为0
	 * 
	 * @param urlstr
	 *            网址
	 */
	@Override
	public void resetWebFailTimes(String webAddress) {
		String hql = "update MonitorWebInfo wi set wi.failTimes=0 where webAddress='"
				+ webAddress + "'";
		HibernateUtil.updateRecord(hql);
	}

	/**
	 *重置模块失败纪录为0
	 * 
	 * @param moduleId
	 */
	@Override
	public void resetModuleFailTimes(long moduleId) {
		String hql = "update ModuleInfo set failTimes=0 where moduleId="
				+ moduleId;
		HibernateUtil.updateRecord(hql);
	}

	/**
	 * 向monitor_web_info 插入新的网址信息，并返回其webId
	 * 
	 * @param urlstr
	 * @param monitorFrequency
	 * @return
	 */
	@Override
	public long insertNewWeb(String urlstr, String monitorFrequency) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		MonitorWebInfo web = new MonitorWebInfo();
		web.setWebAddress(urlstr);
		try {
			web.setMonitorFrequency(sdf.parse(monitorFrequency));
			HibernateUtil.save(web);
			return web.getWebId();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 网址失败记数器加一
	 * 
	 * @param urlstr
	 *            网址
	 */
	@Override
	public void increaseWebFailTimes(String webAddress) {
		String hql = "update MonitorWebInfo set failTimes=failTimes+1 where webAddress='"
				+ webAddress + "'";
		HibernateUtil.updateRecord(hql);
	}

	/**
	 * 模块失败记数器加一
	 * 吕鸿佩1212修改
	 * @param moduleId
	 */
	@Override
	public void increaseModuleFailTimes(String moduleId) {
	/*	String hql = "update ModuleInfo set failTimes=failTimes+1 where moduleId="
				+ moduleId;
		HibernateUtil.updateRecord(hql);*/
		ModuleInfo moduleInfo=(ModuleInfo) HibernateUtil.get(ModuleInfo.class, Long.parseLong(moduleId));
	/*	if(moduleInfo.getFailTimes()==MAX_FAIL_TIMES){
            moduleInfo.setEnable(-1);         
			List<UserModuleInfo> userModuleInfos=HibernateUtil.list("from UserModuleInfo where moduleId="+moduleId);			
			StringBuilder temp=new StringBuilder(userModuleInfos.get(0).getUserModuleId()+"");
			for (int i = 1; i < userModuleInfos.size(); i++) {
				temp.append(" or userModuleId=");
				temp.append(userModuleInfos.get(i).getUserModuleId());
			}  		
			List<UserModuleContents> userModuleContents=HibernateUtil.list("from UserModuleContents where userModuleId="+temp);	
			for (int j = 0; j < userModuleContents.size(); j++) {
				
				userModuleContents.get(j).setAlreadySendMail(0);
				userModuleContents.get(j).setAlreadySendMessage(0);
				userModuleContents.get(j).setContentType(1);
				userModuleContents.get(j).setIgnoreByUser(0);  
				userModuleContents.get(j).setUserModuleContent("<center>模块已失效，请<a href='javascript:void(0);return false;' onclick=\"window.parent.deleteUserModuleAndSubNew("+userModuleContents.get(j).getUserModuleId()+",'"+moduleInfo.getMonitorWebInfo().getWebAddress()+"')\">重新订阅</a></center>");
			}
			
			HibernateUtil.batchUpdate(userModuleContents);			
			moduleInfo.setFailTimes(moduleInfo.getFailTimes()+1);
			
			HibernateUtil.update(moduleInfo);
		}else if(moduleInfo.getFailTimes()<MAX_FAIL_TIMES){*/
			moduleInfo.setFailTimes(moduleInfo.getFailTimes()+1);	
			HibernateUtil.update(moduleInfo);
	}

	/**
	 * 查询monitor_web_info ， 如果有则将其enable标志置为1， 返回对应网址的web_id
	 * 
	 * @param urlstr
	 * @return 返回对应网址的web_id,无则返回-1
	 */
	@Override
	public long queryWebId(String urlstr) {
		long id = -1;
		MonitorWebInfo web = (MonitorWebInfo) HibernateUtil
				.getRecord("from MonitorWebInfo where webAddress='" + urlstr
						+ "'");
		if (web != null) {
			id = web.getWebId();
			HibernateUtil
					.updateRecord("update MonitorWebInfo set enable=1,userCount=userCount+1 where webAddress='"
							+ urlstr + "'");
		}
		return id;
	}

	/**
	 * monitor_frequency和 monitor_web_info中的 monitor_frequency对比,小于则将其更新
	 * 
	 * @param webId
	 * @param monitorFrequency
	 */
	@Override
	public void setWebMonitorFrequency(long webId, String monitorFrequency) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			HibernateUtil
					.updateRecord("update MonitorWebInfo set monitorFrequency="
							+ sdf.parse(monitorFrequency) + " where webId="
							+ webId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置上次发送时间
	 * 
	 * @param receiveId
	 */
	@Override
	public void setLastSendTime(String receiveId) {
		UserReceiveInfo receive = (UserReceiveInfo) HibernateUtil.get(
				UserReceiveInfo.class, Long.parseLong(receiveId));
		receive.setLastSendTime(new Date());
		HibernateUtil.update(receive);
	}

	/**
	 * 标识已通知用户查看的发送纪录
	 * 
	 * @param sendId
	 */
	@Override
	public void setAlreadySend(String userModuleId) {
		String hql = "update UserModuleContents  set alreadySendMessage=1 where userModuleId="+
		userModuleId;
		HibernateUtil.updateRecord(hql);
	}

	/**
	 * 返回指定module_info表的更新内容
	 */
	@Override
	public String getModuleUpdateContent(long moduleId) {
		String updateContent = null;
		ModuleInfo module = (ModuleInfo) HibernateUtil.get(ModuleInfo.class,
				moduleId);
		updateContent = module.getUpdateContent();
		return updateContent;
	}

	/**
	 * 将所有网站设为未监控
	 */
	@Override
	public void resetWebIsMonitor() {
		// TODO Auto-generated method stub
		HibernateUtil.updateRecord("update MonitorWebInfo set isMonitoring=0");
	}

	/**
	 * 吕鸿佩 将对应moduleId的enable设为相应值
	 */
	@Override
	public void setModuleEnable(String moduleId, int enable) {
		// TODO Auto-generated method stub
		HibernateUtil.updateRecord("update ModuleInfo set enable="+enable+" where moduleId="+moduleId);
	}

	@Override
	public void insertNewContent(String moduleId, String content,
			int monitorType, Date date) {
		// TODO Auto-generated method stub
		ContentBuffer cb = new ContentBuffer();
		ModuleInfo mi = new ModuleInfo();
		mi.setModuleId(Long.valueOf(moduleId));
		cb.setModuleInfo(mi);
		cb.setContent(content);
		cb.setUpdateType(monitorType);
		cb.setUpdateTime(date);
		HibernateUtil.save(cb);
	}

}
