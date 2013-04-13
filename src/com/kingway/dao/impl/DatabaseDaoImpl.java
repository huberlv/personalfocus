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
 * ���ݿ����������
 * 
 * @author ZERO
 * 
 */
public class DatabaseDaoImpl implements DatabaseDao {
	private static final Object lock = new Object();
	private static final int MAX_FAIL_TIMES=10;

	/**
	 * ɾ���ѷ��͵���Ϣ�����߼�¼
	 * 
	 * @param sendID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteAlreadySend(String sendId) {
		ContentReceiver cr = (ContentReceiver) HibernateUtil.get(
				ContentReceiver.class, Long.parseLong(sendId));
		// ������
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
	 * ��ѯҪ���͵���Ӧ�û�id����Ϣ
	 * 
	 * @return ���ܷ���null
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
	 * ��ѯ modules_link_view ��ͼ���ҳ�Ҫ���͵Ķ�������
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
	 * ��ѯ mail_need_to_be_send_view ��ͼ���ҳ�Ҫ���͵��ż� ͬһ�������ߵ��ż�����ͬһ���ż���
	 * 25���������޸�
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
	 * ����Ҫ���µ���ַ����ģ��·������������
	 * 
	 * @return ArrayList<ModuleNeedToBeUpdate> �������򷵻�null
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ModuleNeedToBeUpdate> getMonitorModule(int maxNum) {
		// ͬһʱ��ֻ����һ���߳�ѡ���ģ��
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
				for (int i = 1; i < moduleList.size(); i++) { // ��0���Ѿ���ȡ���ӵ�1����ʼ��ȡ
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
	 * �� content_buffer�����һ���¼�¼
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
	 * ��monitor_web_info ���е� is_Monitoring��ȫ����Ϊ0
	 */
	@Override
	public void resetMonitorWebInfo() {
		HibernateUtil.updateRecord("update MonitorWebInfo set isMonitoring=0");
	}

	/**
	 * ���� monitor_web_info���и� ����ַ��last_monitor_time Ϊ��ǰʱ��
	 * 
	 * @param url
	 * @param type
	 *            Ϊ0ʱ��Ϊ��ǰʱ�䣬Ϊ1ʱ��Ϊ��ǰʱ�����next_time_if_error�ֶ�ָ����ʱ��
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
	 * ����Module_update_conetnt ��,��������޴˼�¼�������һ����¼
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
		//�������޸�
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
	 * �� user_monitor_module_info_table �����һ���¼�¼ ������м�¼����������ݣ�����enable��Ϊ1
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
	 * ��ѯ monitor_module_info�� ���������enable��־��Ϊ1�� ����monitor_id ,���򷵻�-1
	 * 
	 * @param webId
	 * @param path
	 * @return moduleId
	 * @exception ���׳��쳣
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
	 * �� module_info �����һ���¼�¼
	 * 
	 * @param webId
	 * @param modulePath
	 * @return �Ƿ����ɹ�
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
	 *���� monitor_web_info���е� is_MonitoringΪ1
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
	 *���� monitor_web_info���еĶ�Ӧweb_address �� is_MonitoringΪ0
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
	 * ������ַʧ�ܼ�����Ϊ0
	 * 
	 * @param urlstr
	 *            ��ַ
	 */
	@Override
	public void resetWebFailTimes(String webAddress) {
		String hql = "update MonitorWebInfo wi set wi.failTimes=0 where webAddress='"
				+ webAddress + "'";
		HibernateUtil.updateRecord(hql);
	}

	/**
	 *����ģ��ʧ�ܼ�¼Ϊ0
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
	 * ��monitor_web_info �����µ���ַ��Ϣ����������webId
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
	 * ��ַʧ�ܼ�������һ
	 * 
	 * @param urlstr
	 *            ��ַ
	 */
	@Override
	public void increaseWebFailTimes(String webAddress) {
		String hql = "update MonitorWebInfo set failTimes=failTimes+1 where webAddress='"
				+ webAddress + "'";
		HibernateUtil.updateRecord(hql);
	}

	/**
	 * ģ��ʧ�ܼ�������һ
	 * ������1212�޸�
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
				userModuleContents.get(j).setUserModuleContent("<center>ģ����ʧЧ����<a href='javascript:void(0);return false;' onclick=\"window.parent.deleteUserModuleAndSubNew("+userModuleContents.get(j).getUserModuleId()+",'"+moduleInfo.getMonitorWebInfo().getWebAddress()+"')\">���¶���</a></center>");
			}
			
			HibernateUtil.batchUpdate(userModuleContents);			
			moduleInfo.setFailTimes(moduleInfo.getFailTimes()+1);
			
			HibernateUtil.update(moduleInfo);
		}else if(moduleInfo.getFailTimes()<MAX_FAIL_TIMES){*/
			moduleInfo.setFailTimes(moduleInfo.getFailTimes()+1);	
			HibernateUtil.update(moduleInfo);
	}

	/**
	 * ��ѯmonitor_web_info �� ���������enable��־��Ϊ1�� ���ض�Ӧ��ַ��web_id
	 * 
	 * @param urlstr
	 * @return ���ض�Ӧ��ַ��web_id,���򷵻�-1
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
	 * monitor_frequency�� monitor_web_info�е� monitor_frequency�Ա�,С���������
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
	 * �����ϴη���ʱ��
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
	 * ��ʶ��֪ͨ�û��鿴�ķ��ͼ�¼
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
	 * ����ָ��module_info��ĸ�������
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
	 * ��������վ��Ϊδ���
	 */
	@Override
	public void resetWebIsMonitor() {
		// TODO Auto-generated method stub
		HibernateUtil.updateRecord("update MonitorWebInfo set isMonitoring=0");
	}

	/**
	 * ������ ����ӦmoduleId��enable��Ϊ��Ӧֵ
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
