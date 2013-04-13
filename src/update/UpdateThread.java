/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.util.ArrayList;

/**
 *负责监测网站的线程，在线程池中，运行完一次后等待 在再次唤醒时要先用setModuleNeedToBeUpdate方法设置新监控的内容
 * 并且要先读取其url，通知数据库将其is_monitoring设为0
 * 
 * @author Administrator
 */
public abstract class UpdateThread extends Thread {
	public final Object lock = new Object();
	public volatile boolean busy = false;
	/** 用于存放监控模块的信息 */
	public ArrayList<Module> modules;
	/** 网址 */
	public String url;
	/** 保持运行，可用此变量控制线程是否一直运行，或终止线程 */
	static int updateThreadnum = 0;
	private ModuleNeedToBeUpdate module;
	/**
	 * 设置要检测的网址和模块信息
	 * 
	 * @param module
	 */
	public void setModuleNeedToBeUpdate(ModuleNeedToBeUpdate module) {
		this.module = module;
		this.url = module.address;

		this.modules = module.modules;
		if (module.modules == null) {
			this.modules = new ArrayList<Module>(5);
		} else {
			this.modules = module.modules;
		}
	}
	public abstract void stopRun();
	public abstract void checkTimeOut();

}

