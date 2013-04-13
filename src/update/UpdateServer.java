/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kingway.dao.impl.DatabaseDaoImpl;
import com.kingway.util.PropertiesUtil;

/**
 *更新线程的管理线程，还负责向数据库中插入更新的信息
 * 
 * @author Administrator
 */
public class UpdateServer extends Thread {

	/** 保持运行，可用此变量控制线程是否一直运行，或终止线程 */
	public volatile boolean keepRun = true;
	public ArrayList<UpdateThread> updateThread;
	/** 加入和移除更新线程的同步锁 */
	private final Object updateThreadLock = new Object();
	/** 线程睡眠时间，时间为秒 */
	private int sleepTime;
	private DatabaseDaoImpl dbase = new DatabaseDaoImpl();
	private int updateThreadNum;
	private ArrayList<ModuleNeedToBeUpdate> modules;
	private int failTimes = 0;
	private int maxNum; // 每次从数据库中取出的需要更新的模块纪录数

	private UpdateThread getUpdateThreadInstance(boolean keepRun, UpdateServer us){
		if("SWTUpdateThread".equals(PropertiesUtil.getProperty("UpdateThread"))){
		   return new SWTUpdateThread(keepRun,us);
		}else{
			return   new JsoupUpdateThread(keepRun,us);
		}
	}
	/**
	 * 构造函数
	 * 
	 * @param db
	 * @param updateThreadNum
	 * @param sleeptime
	 * @throws java.lang.Exception
	 */
	public UpdateServer(int updateThreadNum, int sleeptime, int maxModulenum)
			throws Exception {
		this.maxNum = maxModulenum;
		updateThread = new ArrayList<UpdateThread>(updateThreadNum);
		this.sleepTime = sleeptime * 1000;
		this.updateThreadNum = updateThreadNum;
		/** 建立线程池 */
		for (int i = 0; i < this.updateThreadNum; i++) {
			try {
				this.updateThread.add(getUpdateThreadInstance(true, this));
			} catch (Exception ex8) {
				Logger.getLogger(UpdateServer.class.getName()).log(
						Level.SEVERE, null, ex8);
				break;
			}
		}
	}

	/**
	 * 在同步块中关闭所有更新线程
	 */
	public synchronized void closeUpdateServer() {
		this.keepRun = false;
		for (int i = 0; i < this.updateThread.size(); i++) {
			this.updateThread.get(i).stopRun();

		}
	}

	/**
	 * 在同步块加入新的更新线程
	 * 
	 * @param newThread新建的更新线程
	 * @return 是否加入成功
	 */
	private boolean addUpdateThread(UpdateThread newThread) {
		synchronized (this.updateThreadLock) {
			return this.updateThread.add(newThread);
		}
	}

	/**
	 * 在同步块移除更新线程
	 * 
	 * @param newThread新建的更新线程
	 * @return 是否加入成功
	 */
	private boolean removeUpdateThread(UpdateThread oldThread) {
		oldThread.stopRun();
		oldThread.destroy();
		synchronized (this.updateThreadLock) {
			return this.updateThread.remove(oldThread);
		}
	}

	/**
	 * 负责从数据库中取出要检测的模块
	 */
	@Override
	public void run() {
		System.out.print("已启动更新服务");
		this.dbase.resetWebIsMonitor();
		for (; this.keepRun;) { // 数据库连接已关闭？
			try {
				// 数据库连接有效
				this.failTimes = 0;

				/** 在外面改变线程数后，要将多出来的线程终结 */
				while (this.updateThread.size() > this.updateThreadNum) {
					for (int i = 0; i < this.updateThread.size()
							&& (this.updateThread.size() > this.updateThreadNum); i++) {
						if (this.updateThread.get(i).busy == false) {
							this.updateThread.get(i).interrupt(); // 中断线程
							this.updateThread.remove(i);
						}
					}
					// 如果还是大于定义的线程数，则睡眠一段时间，等待一部分线程更新完毕
					if (this.updateThread.size() > this.updateThreadNum) {
						try {
							Thread.sleep(sleepTime); // 睡眠一段时间再检楂，未完成
						} catch (InterruptedException ex5) {
							Logger.getLogger(UpdateServer.class.getName()).log(
									Level.SEVERE, null, ex5);
						}
					}
				}
				/** 在外面增加线程数后，创建线程 */
				while (this.updateThread.size() < this.updateThreadNum) {
					try {
						this.updateThread.add(getUpdateThreadInstance(true, this));
					} catch (Exception ex6) {

						Logger.getLogger(UpdateServer.class.getName()).log(
								Level.SEVERE, null, ex6);
						break;
					}
				}

				this.modules = dbase.getMonitorModule(maxNum);

				// 如果没有要更新的信息，就睡眠，过一段时间再检测
				if (this.modules.size() <= 0) {
					try {
						Thread.sleep(sleepTime); // 睡眠一段时间再检楂
					} catch (InterruptedException ex5) {
						Logger.getLogger(UpdateServer.class.getName()).log(
								Level.SEVERE, null, ex5);
					}
					continue;
				}
				/** 挑选出线程执行更新 */
				int num = 0;
				for (; this.keepRun;) {
					num = 0;
					int size = this.modules.size();
					for (int i = 0; i < this.updateThread.size() && num < size; i++) {
						if (this.updateThread.get(i).busy == false) {
							synchronized (this.updateThread.get(i).lock) {
								// 设置此线程要监测的网站和模块路径等信息
								this.updateThread
										.get(i)
										.setModuleNeedToBeUpdate(modules.get(0));
								// 设置网站正在监测中
								// this.dbase.setWebMonitoring(modules.get(num).address);
								this.modules.remove(0);
								num++;
								if (this.updateThread.get(i).getState() == Thread.State.NEW) {
									this.updateThread.get(i).start();
								} else {
									this.updateThread.get(i).lock.notifyAll();
								}
							}
						} else {
							this.updateThread.get(i).checkTimeOut();
						}

					}
					// 如果还有等待监测的模块，就睡眠等待空闲的更新线程
					if (this.modules.size() > 0) {
						try {
							UpdateServer.sleep(this.sleepTime);
						} catch (InterruptedException ex9) {
							Logger.getLogger(UpdateServer.class.getName()).log(
									Level.SEVERE, null, ex9);
						}
					} else {
						break;
					}
				}
				try {
					UpdateServer.sleep(this.sleepTime);
				} catch (InterruptedException ex9) {
					Logger.getLogger(UpdateServer.class.getName()).log(
							Level.SEVERE, null, ex9);
				}
			} catch (Exception ex10) {
				Logger.getLogger(UpdateServer.class.getName()).log(
						Level.SEVERE, null, ex10);
			}
		}
	}

	/**
	 * 停止发送
	 */
	public void stopRun() {
		this.keepRun = false;
	}

	/**
	 * 改变睡眠时间，以调节性能
	 * 
	 * @param sleepTime
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime * 1000;
	}

	/**
	 * 检查线程是否在运行
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return this.keepRun;
	}
}
/** 连接数据库，取出需要开始监控的模块，并将同一网址的模块合并在一个更新线程中开始运行 */

