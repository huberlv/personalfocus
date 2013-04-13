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
 *�����̵߳Ĺ����̣߳������������ݿ��в�����µ���Ϣ
 * 
 * @author Administrator
 */
public class UpdateServer extends Thread {

	/** �������У����ô˱��������߳��Ƿ�һֱ���У�����ֹ�߳� */
	public volatile boolean keepRun = true;
	public ArrayList<UpdateThread> updateThread;
	/** ������Ƴ������̵߳�ͬ���� */
	private final Object updateThreadLock = new Object();
	/** �߳�˯��ʱ�䣬ʱ��Ϊ�� */
	private int sleepTime;
	private DatabaseDaoImpl dbase = new DatabaseDaoImpl();
	private int updateThreadNum;
	private ArrayList<ModuleNeedToBeUpdate> modules;
	private int failTimes = 0;
	private int maxNum; // ÿ�δ����ݿ���ȡ������Ҫ���µ�ģ���¼��

	private UpdateThread getUpdateThreadInstance(boolean keepRun, UpdateServer us){
		if("SWTUpdateThread".equals(PropertiesUtil.getProperty("UpdateThread"))){
		   return new SWTUpdateThread(keepRun,us);
		}else{
			return   new JsoupUpdateThread(keepRun,us);
		}
	}
	/**
	 * ���캯��
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
		/** �����̳߳� */
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
	 * ��ͬ�����йر����и����߳�
	 */
	public synchronized void closeUpdateServer() {
		this.keepRun = false;
		for (int i = 0; i < this.updateThread.size(); i++) {
			this.updateThread.get(i).stopRun();

		}
	}

	/**
	 * ��ͬ��������µĸ����߳�
	 * 
	 * @param newThread�½��ĸ����߳�
	 * @return �Ƿ����ɹ�
	 */
	private boolean addUpdateThread(UpdateThread newThread) {
		synchronized (this.updateThreadLock) {
			return this.updateThread.add(newThread);
		}
	}

	/**
	 * ��ͬ�����Ƴ������߳�
	 * 
	 * @param newThread�½��ĸ����߳�
	 * @return �Ƿ����ɹ�
	 */
	private boolean removeUpdateThread(UpdateThread oldThread) {
		oldThread.stopRun();
		oldThread.destroy();
		synchronized (this.updateThreadLock) {
			return this.updateThread.remove(oldThread);
		}
	}

	/**
	 * ��������ݿ���ȡ��Ҫ����ģ��
	 */
	@Override
	public void run() {
		System.out.print("���������·���");
		this.dbase.resetWebIsMonitor();
		for (; this.keepRun;) { // ���ݿ������ѹرգ�
			try {
				// ���ݿ�������Ч
				this.failTimes = 0;

				/** ������ı��߳�����Ҫ����������߳��ս� */
				while (this.updateThread.size() > this.updateThreadNum) {
					for (int i = 0; i < this.updateThread.size()
							&& (this.updateThread.size() > this.updateThreadNum); i++) {
						if (this.updateThread.get(i).busy == false) {
							this.updateThread.get(i).interrupt(); // �ж��߳�
							this.updateThread.remove(i);
						}
					}
					// ������Ǵ��ڶ�����߳�������˯��һ��ʱ�䣬�ȴ�һ�����̸߳������
					if (this.updateThread.size() > this.updateThreadNum) {
						try {
							Thread.sleep(sleepTime); // ˯��һ��ʱ���ټ�髣�δ���
						} catch (InterruptedException ex5) {
							Logger.getLogger(UpdateServer.class.getName()).log(
									Level.SEVERE, null, ex5);
						}
					}
				}
				/** �����������߳����󣬴����߳� */
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

				// ���û��Ҫ���µ���Ϣ����˯�ߣ���һ��ʱ���ټ��
				if (this.modules.size() <= 0) {
					try {
						Thread.sleep(sleepTime); // ˯��һ��ʱ���ټ��
					} catch (InterruptedException ex5) {
						Logger.getLogger(UpdateServer.class.getName()).log(
								Level.SEVERE, null, ex5);
					}
					continue;
				}
				/** ��ѡ���߳�ִ�и��� */
				int num = 0;
				for (; this.keepRun;) {
					num = 0;
					int size = this.modules.size();
					for (int i = 0; i < this.updateThread.size() && num < size; i++) {
						if (this.updateThread.get(i).busy == false) {
							synchronized (this.updateThread.get(i).lock) {
								// ���ô��߳�Ҫ������վ��ģ��·������Ϣ
								this.updateThread
										.get(i)
										.setModuleNeedToBeUpdate(modules.get(0));
								// ������վ���ڼ����
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
					// ������еȴ�����ģ�飬��˯�ߵȴ����еĸ����߳�
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
	 * ֹͣ����
	 */
	public void stopRun() {
		this.keepRun = false;
	}

	/**
	 * �ı�˯��ʱ�䣬�Ե�������
	 * 
	 * @param sleepTime
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime * 1000;
	}

	/**
	 * ����߳��Ƿ�������
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return this.keepRun;
	}
}
/** �������ݿ⣬ȡ����Ҫ��ʼ��ص�ģ�飬����ͬһ��ַ��ģ��ϲ���һ�������߳��п�ʼ���� */

