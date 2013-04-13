/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kingway.dao.impl.DatabaseDaoImpl;
import com.kingway.util.LhpUtil;
import com.kingway.util.PropertiesUtil;

import document.*;

/**
 *负责监测网站的线程，在线程池中，运行完一次后等待 在再次唤醒时要先用setModuleNeedToBeUpdate方法设置新监控的内容
 * 并且要先读取其url，通知数据库将其is_monitoring设为0
 * 
 * @author Administrator
 */
public class JsoupUpdateThread extends UpdateThread {

	private static final long TIME_OUT = Long.parseLong(PropertiesUtil
			.getProperty("TIME_OUT"));
	private static final String base = PropertiesUtil.getProperty("baseurl");
	/** 保持运行，可用此变量控制线程是否一直运行，或终止线程 */
	private volatile boolean keepRun = true;
	/** 此线程所属的UpdateServer */
	private UpdateServer updateServer;
	static int updateThreadnum = 0;

	/** 已运行时间，单位为秒 */
	public int failTimes = 0;
	private DatabaseDaoImpl db = new DatabaseDaoImpl();

	private MyHtmlParser page = new MyHtmlParser();

	private StringBuffer text;
	private ArrayList<UpdateContent> updatecontent;
	
	static{
		System.setProperty("proxySet", PropertiesUtil.getProperty("proxySet"));
        System.setProperty("proxyHost",  PropertiesUtil.getProperty("proxyHost"));
        System.setProperty("proxyPort", PropertiesUtil.getProperty("proxyPort"));
        System.setProperty("proxyUsername", PropertiesUtil.getProperty("proxyUsername"));
        System.setProperty("proxyPassword", PropertiesUtil.getProperty("proxyPassword"));
	}

	/**
	 * 构造函数
	 * 
	 * @param keepRun
	 *            是否保持运行
	 * @param updateServer
	 *            此线程所属的更新服务器
	 */
	public JsoupUpdateThread(boolean keepRun, UpdateServer updateServer) {
		this.keepRun = keepRun;
		this.updateServer = updateServer;

	}

	public void stopRun() {
		this.keepRun = false;
		synchronized (lock) {
			lock.notifyAll();
		}

	}

	@Override
	public void run() {
		for (; keepRun == true;) {
			this.busy = true;
			try {
				page.parserURL(url);
			} catch (IOException e) {
				e.printStackTrace();
				// 如果连接失败则返回
				System.out.println("连接" + url + "失败，网络连接不可用或已跳转");
				db.increaseWebFailTimes(url);
				done();
				waitForNextMission();
				continue;
			}
			boolean error = false;
			for (int i = 0; i < modules.size(); i++) {
				error = false;
				text = new StringBuffer(120);
				ArrayList<String> paths = LhpUtil.getPaths(modules.get(i).path);
				// 取得所有特征的文字
				String temp = null;
				for (int h = 0; h < paths.size(); h++) {
					// 取得每一特征文字
					try {
						temp = page.getHtmlOfPath(paths.get(h),
								MyHtmlParser.TYPE_TD);
						if (temp != null) {
							text.append(temp);
							temp = null;
						}
					} catch (Exception ex) {
						Logger.getLogger(JsoupUpdateThread.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				//检测特征是否有效
				String newtext = new String(text);
				if (newtext.length() < 1) {
					System.out
							.print("失败信息：" + url + "  " + modules.get(i).path);
					db.increaseModuleFailTimes(modules.get(i).moduleId);
					// 特征无效
					error = true;
					continue;
				}
				
				//特征有效，对比更新
				error = false;
				updatecontent = page.getUpdate(newtext, modules.get(i).text,
						MyHtmlParser.REMOVE_OLD);
				if (updatecontent != null) {
					// 先更新栏目最新内容表
					db.updateModuleUpdateConetnt(modules.get(i).moduleId,
							newtext, updatecontent
									.get(updatecontent.size() - 1).content);
					// 插入新的模块更新信息
					for (int h = 0; h < updatecontent.size(); h++) {
						db.insertNewContent(modules.get(i).moduleId,
								updatecontent.get(h).content, updatecontent
										.get(h).monitorType);
					}
				}
				updatecontent = null;
			}
			done();
			waitForNextMission();
		}
		System.out.println("更新线程已关闭");
	}

	private void waitForNextMission() {
		busy = false;
		System.out.println(url + "完成监控");
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void done() {
		db.setLastMonitorTime(url);
		db.resetWebMonitoring(url);
	}
	
	public void checkTimeOut() {
	}
}
