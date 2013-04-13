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
	 * 静态方法，保证只有一个实例
	 * 
	 * @return 系统管理线程
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
	 * 启动更新服务
	 * 
	 * @return 1 代表服务已启动，不能再启动 2代表启动失败 0 代表启动成功
	 */
	public int startUpdate() // 未完成
	{
		if(this.checkUpdateThread==null){
			checkUpdateThread=new CheckUpdateThread(1,100);
			checkUpdateThread.start();
		}
		if (this.updateServer != null) {
			return 1;
		} else {
			try { 
				int updateThreadNum=3;//缺省线程数
				int sleeptime=10;
				int maxnum=10;
			//	PropertiesUtil.loadPropertiesFile(String filePath);
			//	PropertiesUtil.getProperty("messageSender");
				try{
			
				   updateThreadNum=Integer.parseInt(PropertiesUtil.getProperty("UpdateServerUpdateThreadNum"));//缺省线程数
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
		System.out.println("已启动更新服务");
		return 0;
	}

	/**
	 * 关闭更新服务
	 * 
	 * @return 1 代表服务未启动，不能关闭 2代表启动失败 0 代表启动成功
	 */
	public int stopUpdate() // 未完成
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
		System.out.println("已关闭更新服务");
		return 0;
	}



	/**
	 * 启动发送短信服务
	 * 
	 * @return 1 代表服务已启动，不能再启动 2代表启动失败 0 代表启动成功
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
		System.out.println("已启动模块短信链接服务");
		return 0;
	}

	/**
	 * 关闭短信服务
	 * 
	 * @return 1 代表服务未启动，不能关闭 2代表启动失败 0 代表启动成功
	 */
	public int stopModulesLinkSender() // 未完成
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
		System.out.println("已关闭短信链接服务");
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
		System.out.println("已启动邮件服务");
		return 0;
	}

	/**
	 * 关闭邮件服务
	 * 
	 * @return 1 代表服务未启动，不能关闭 2代表启动失败 0 代表启动成功
	 */
	public int stopEmailSender() // 未完成
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
		System.out.println("已关闭邮件服务");
		return 0;
	}
	
	

	
}
