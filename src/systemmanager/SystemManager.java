/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package systemmanager;


import email.EmailSenderThread;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import message.Config;
import message.ModulesLinkSender;

import org.dom4j.DocumentException;

import com.kingway.util.PropertiesUtil;

import update.CheckUpdateThread;
import update.UpdateServer;

/**
 * 
 * @author Administrator
 */
public class SystemManager extends Thread {

	private static final String systemConfigXmlFile = "systemConfig.xml";

	private String MSconfigXmlFile = null;
	private String EmailSenderXmlFile = null;
	private static SystemManager systemManager;
	private UpdateServer updateServer = null;
	private EmailSenderThread mailSenderThread = null;
	private ModulesLinkSender modulesLinkSender;
	private String rootPath = this.getClass().getResource("/").getPath();

	private CheckUpdateThread checkUpdateThread=null;
	/**
     * 
     */
	private SystemManager() throws DocumentException, IOException {
		Config config = new Config(rootPath + systemConfigXmlFile);
		System.out.println(rootPath + systemConfigXmlFile);

		this.EmailSenderXmlFile = config.getParameter("EmailSenderXmlFile");
		this.MSconfigXmlFile = config.getParameter("MSconfigXmlFile");
	}

	/**
	 * ��̬��������ֻ֤��һ��ʵ��
	 * 
	 * @return ϵͳ�����߳�
	 */
	public static SystemManager getInstance() throws DocumentException,
			IOException {
		if (SystemManager.systemManager != null) {
			return SystemManager.systemManager;
		} else {
			systemManager = new SystemManager();
			return SystemManager.systemManager;
		}
	}

	/**
	 * �������·���
	 * 
	 * @return 1 ������������������������� 2��������ʧ�� 0 ���������ɹ�
	 */
	public int startUpdate() // δ���
	{
		if(this.checkUpdateThread==null){
			checkUpdateThread=new CheckUpdateThread(1,100);
			checkUpdateThread.start();
		}
		if (this.updateServer != null) {
			return 1;
		} else {
			try { 
				int updateThreadNum=3;//ȱʡ�߳���
				int sleeptime=10;
				int maxnum=10;
			//	PropertiesUtil.loadPropertiesFile(String filePath);
			//	PropertiesUtil.getProperty("messageSender");
				try{
			
				   updateThreadNum=Integer.parseInt(PropertiesUtil.getProperty("UpdateServerUpdateThreadNum"));//ȱʡ�߳���
				    sleeptime=Integer.parseInt(PropertiesUtil.getProperty("UpdateServerSleeptime"));
				    maxnum=Integer.parseInt(PropertiesUtil.getProperty("UpdateServerMaxModulenum"));
				}catch(Exception e){
					e.printStackTrace();
				}
				
				this.updateServer = new UpdateServer(updateThreadNum, sleeptime,  maxnum);
				this.updateServer.start();
			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("���������·���");
		return 0;
	}

	/**
	 * �رո��·���
	 * 
	 * @return 1 �������δ���������ܹر� 2��������ʧ�� 0 ���������ɹ�
	 */
	public int stopUpdate() // δ���
	{
		if(this.checkUpdateThread!=null){
			checkUpdateThread.stopRun();
			checkUpdateThread=null;
		}
		if (this.updateServer == null) {
			return 1;
		} else {
			try {
				this.updateServer.closeUpdateServer();
				this.updateServer.keepRun = false;
				this.updateServer = null;
			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("�ѹرո��·���");
		return 0;
	}



	/**
	 * �������Ͷ��ŷ���
	 * 
	 * @return 1 ������������������������� 2��������ʧ�� 0 ���������ɹ�
	 */
	public int startModulesLinkSender() {
		if (this.modulesLinkSender != null) {
			return 1;
		} else {
			try {
				this.modulesLinkSender = new ModulesLinkSender(
						null, new Config(rootPath+this.MSconfigXmlFile).getCommonPara(),
						20, 100);
				this.modulesLinkSender.start();
			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("������ģ��������ӷ���");
		return 0;
	}

	/**
	 * �رն��ŷ���
	 * 
	 * @return 1 �������δ���������ܹر� 2��������ʧ�� 0 ���������ɹ�
	 */
	public int stopModulesLinkSender() // δ���
	{
		if (this.modulesLinkSender == null) {
			return 1;
		} else {
			try {
				this.modulesLinkSender.stopRun();
				this.modulesLinkSender = null;

			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("�ѹرն������ӷ���");
		return 0;
	}



	public int startEmailSender() {
		if (this.mailSenderThread != null) {
			return 1;
		} else {
			try {
				this.mailSenderThread = new EmailSenderThread( 20,
						rootPath+this.EmailSenderXmlFile, 100, 30);
				this.mailSenderThread.start();
			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("�������ʼ�����");
		return 0;
	}

	/**
	 * �ر��ʼ�����
	 * 
	 * @return 1 �������δ���������ܹر� 2��������ʧ�� 0 ���������ɹ�
	 */
	public int stopEmailSender() // δ���
	{
		if (this.mailSenderThread == null) {
			return 1;
		} else {
			try {
				this.mailSenderThread.stopRun();
				this.mailSenderThread = null;
			} catch (Exception ex) {
				Logger.getLogger(SystemManager.class.getName()).log(
						Level.SEVERE, null, ex);
				return 2;
			}
		}
		System.out.println("�ѹر��ʼ�����");
		return 0;
	}
	
	

	
}
