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
 *��������վ���̣߳����̳߳��У�������һ�κ�ȴ� ���ٴλ���ʱҪ����setModuleNeedToBeUpdate���������¼�ص�����
 * ����Ҫ�ȶ�ȡ��url��֪ͨ���ݿ⽫��is_monitoring��Ϊ0
 * 
 * @author Administrator
 */
public class JsoupUpdateThread extends UpdateThread {

	private static final long TIME_OUT = Long.parseLong(PropertiesUtil
			.getProperty("TIME_OUT"));
	private static final String base = PropertiesUtil.getProperty("baseurl");
	/** �������У����ô˱��������߳��Ƿ�һֱ���У�����ֹ�߳� */
	private volatile boolean keepRun = true;
	/** ���߳�������UpdateServer */
	private UpdateServer updateServer;
	static int updateThreadnum = 0;

	/** ������ʱ�䣬��λΪ�� */
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
	 * ���캯��
	 * 
	 * @param keepRun
	 *            �Ƿ񱣳�����
	 * @param updateServer
	 *            ���߳������ĸ��·�����
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
				// �������ʧ���򷵻�
				System.out.println("����" + url + "ʧ�ܣ��������Ӳ����û�����ת");
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
				// ȡ����������������
				String temp = null;
				for (int h = 0; h < paths.size(); h++) {
					// ȡ��ÿһ��������
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
				//��������Ƿ���Ч
				String newtext = new String(text);
				if (newtext.length() < 1) {
					System.out
							.print("ʧ����Ϣ��" + url + "  " + modules.get(i).path);
					db.increaseModuleFailTimes(modules.get(i).moduleId);
					// ������Ч
					error = true;
					continue;
				}
				
				//������Ч���Աȸ���
				error = false;
				updatecontent = page.getUpdate(newtext, modules.get(i).text,
						MyHtmlParser.REMOVE_OLD);
				if (updatecontent != null) {
					// �ȸ�����Ŀ�������ݱ�
					db.updateModuleUpdateConetnt(modules.get(i).moduleId,
							newtext, updatecontent
									.get(updatecontent.size() - 1).content);
					// �����µ�ģ�������Ϣ
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
		System.out.println("�����߳��ѹر�");
	}

	private void waitForNextMission() {
		busy = false;
		System.out.println(url + "��ɼ��");
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
