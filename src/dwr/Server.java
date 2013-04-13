package dwr;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;

import com.kingway.model.ModuleUserModulesView;
import com.kingway.model.UserModuleContents;
import com.kingway.model.WebUserModulesView;
import com.kingway.util.HibernateUtil;
import com.kingway.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;

import systemmanager.SystemManager;

public class Server {
	SystemManager s = null;

	public int startUpdate() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
			ActionContext.getContext().getApplication().put("server", s);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("s.startupdate:"+s.startUpdate());
		return s.startUpdate();
	}

	public int startEmailSender() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s.startEmailSender();

	}

	public int startMessageSender() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  s.startModulesLinkSender();

	}

	public int stopUpdate() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s.stopUpdate();

	}

	public int stopEmailSender() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s.stopEmailSender();
	}

	public int stopMessageSender() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return 2;
		}
		try {
			s = SystemManager.getInstance();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  s.stopModulesLinkSender();
	}
	/**
	 * ɾ�������ģ�����ַ
	 * @return
	 */
	public String  deleteUnuse(){
		boolean flag=HibernateUtil.callProcedure(PropertiesUtil.getProperty("procedure"));
		if(flag==true){
			return "ִ�гɹ�";
		}else{
			return "ִ��ʧ��";
		}
	}
	
	
/**
 * ɾ����ʷ��¼
 * @param days
 * @return
 */
	public String  deleteUnuseContentbuffer(int days){
		boolean flag=HibernateUtil.deleteUnuseContentbuffer(days);
		if(flag==true){
			return "ִ�гɹ�";
		}else{
			return "ִ��ʧ��";
		}
	}
	
	/**
	 * ��ַʧ�ܺͼ�ش�����0
	 * @param webIds
	 * @return
	 */
	public String resetTimes(long webIds[]){
		//δ��װ��δ������
		for(int i=0;i<webIds.length;i++){
			HibernateUtil.updateRecord("update MonitorWebInfo set failTimes=0,totalMonitorTimes=0 where webId="+webIds[i]);
		}
		return "�����ɹ�";
	}
	
	public String disAbleWeb(long webIds[]){
		//δ��װ��δ������
		for(int i=0;i<webIds.length;i++){
			HibernateUtil.updateRecord("update MonitorWebInfo set enable=0 where webId="+webIds[i]);
			markDisableModule(webIds[i]);
		}
		return "�����ɹ�";
	}
	
	private void markDisableModule(long webId){
		//δ��װ��δ������
		List<WebUserModulesView> webUserModules=HibernateUtil.list("from WebUserModulesView where webId="+webId);
	    UserModuleContents userModuleContents=null;
		for(int i=0;i<webUserModules.size();i++){
			userModuleContents=(UserModuleContents) HibernateUtil.get(UserModuleContents.class, webUserModules.get(i).getId().getContentId());
			userModuleContents.setUserModuleContent("<div>�����ַ��ʧЧ����ɾ����ģ�飡<div>");
			userModuleContents.setAlreadySendMail(0);
			userModuleContents.setAlreadySendMessage(1);
			userModuleContents.setContentType(1);
			userModuleContents.setIgnoreByUser(0);
			HibernateUtil.update(userModuleContents);
		}
	}
	
	public String disAbleAModule(long moduleId){
		HibernateUtil.updateRecord("update ModuleInfo set enable=0 where moduleId="+moduleId);
		
		List<ModuleUserModulesView> moduleUserModulesView=HibernateUtil.list("from ModuleUserModulesView  where moduleId="+moduleId);
		
		UserModuleContents userModuleContents=null;
			for(int i=0;i<moduleUserModulesView.size();i++){
				userModuleContents=(UserModuleContents) HibernateUtil.get(UserModuleContents.class, moduleUserModulesView.get(i).getId().getContentId());
				userModuleContents.setUserModuleContent("<div>���ģ����ʧЧ����ɾ����ģ�飡<div>");
				userModuleContents.setAlreadySendMail(0);
				userModuleContents.setAlreadySendMessage(1);
				userModuleContents.setContentType(1);
				userModuleContents.setIgnoreByUser(0);
				HibernateUtil.update(userModuleContents);
			}
		return "�����ɹ�";
	}
	/**
	 * ������ʱ��
	 * @param monitorFrequency
	 * @return
	 */
	public String saveMonitorFra(String monitorFrequency){	
		HibernateUtil.updateRecord("update MonitorWebInfo set monitorFrequency='"+monitorFrequency+"'");
		return "�����ɹ�";
	}
	
	/**
	 * ����ҳ�������SQLȡ����ʾ���
	 * @param sql
	 * @return
	 */
	public String getQueryResultBySQL(String sql){
		System.out.println("ҳ���ύ��SQL��"+sql);
		return HibernateUtil.getQueryResultBySQL(sql);
	}
}
