/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.util.ArrayList;

/**
 *��������վ���̣߳����̳߳��У�������һ�κ�ȴ� ���ٴλ���ʱҪ����setModuleNeedToBeUpdate���������¼�ص�����
 * ����Ҫ�ȶ�ȡ��url��֪ͨ���ݿ⽫��is_monitoring��Ϊ0
 * 
 * @author Administrator
 */
public abstract class UpdateThread extends Thread {
	public final Object lock = new Object();
	public volatile boolean busy = false;
	/** ���ڴ�ż��ģ�����Ϣ */
	public ArrayList<Module> modules;
	/** ��ַ */
	public String url;
	/** �������У����ô˱��������߳��Ƿ�һֱ���У�����ֹ�߳� */
	static int updateThreadnum = 0;
	private ModuleNeedToBeUpdate module;
	/**
	 * ����Ҫ������ַ��ģ����Ϣ
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

