/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package update;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import moduledefine.HtmlHandler;
import net.Net;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jsoup.Jsoup;

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
public class SWTUpdateThread extends UpdateThread {
	private static final boolean DEBUG_MODU = Boolean.parseBoolean(PropertiesUtil
			.getProperty("DEBUG_MODU"));
	private static final long TIME_OUT = Long.parseLong(PropertiesUtil
			.getProperty("TIME_OUT"));
	private long beginTime;
	private static final String olpfComplate = "olpfComplate";
	private static final String base = PropertiesUtil.getProperty("baseurl");
	private Browser browser;
	/** �������У����ô˱��������߳��Ƿ�һֱ���У�����ֹ�߳� */
	private volatile boolean keepRun = true;
	/** ���߳�������UpdateServer */
	private UpdateServer updateServer;
	static int updateThreadnum = 0;

	/** ������ʱ�䣬��λΪ�� */
	public int failTimes = 0;
	private DatabaseDaoImpl db = new DatabaseDaoImpl();
	private String html;
	private Display display;
    private Shell shell;

    private MyHtmlParser page = new MyHtmlParser();

    private StringBuffer text;
    private ArrayList<UpdateContent> updatecontent;
	/**
	 * ���캯��
	 * 
	 * @param keepRun
	 *            �Ƿ񱣳�����
	 * @param updateServer
	 *            ���߳������ĸ��·�����
	 */
	public SWTUpdateThread(boolean keepRun, UpdateServer updateServer){
		this.keepRun = keepRun;
		this.updateServer = updateServer;

	}



	public void stopRun() {
		this.keepRun = false;
		if (display != null && display.isDisposed() == false) {
			display.asyncExec(new Runnable() {
				public void run() {
					if (display.isDisposed() == false) {
						display.dispose();
						System.out.println("������ѹر�11");
					}
					synchronized (lock) {
						lock.notifyAll();
					}
				}
			});
		}
		
	}

	@Override
	public void run() {
		display = new Display();
		// ��ʼ�������
		for (; keepRun == true;) {
			this.busy = true;		
			// display.syncExec(parent);
		   shell = new Shell(display);
			browser = new Browser(shell, SWT.BORDER);
			if (DEBUG_MODU == true) {
				shell.setLayout(new FormLayout());
				FormData data1 = new FormData();
				// Composite controls = new Composite(shell, SWT.NONE);
				data1.left = new FormAttachment(shell, 0, SWT.LEFT);
				data1.top = new FormAttachment(shell);
				data1.bottom = new FormAttachment(100, 0);
				data1.right = new FormAttachment(100, 0);
				browser.setLayoutData(data1);
				
			}
			browser.addOpenWindowListener(new OpenWindowListener() {
				public void open(WindowEvent event) {
					Shell shell = new Shell(new Display());
					Browser tempbrowser = new Browser(shell, SWT.BORDER);
					System.out.println("��һ���´���");
					event.browser = tempbrowser;	
				}
			});
			browser.addStatusTextListener(new StatusTextListener() {
				public void changed(StatusTextEvent event) {
					if (event.text.startsWith(olpfComplate)) {
						browser.setData("html", event.text
								.substring(olpfComplate.length()));
					}

				}
			});
			browser.addProgressListener(new ProgressListener() {
				public void changed(ProgressEvent event) {
					// System.out.println("��ǰ"+event.current);
					// System.out.println("����"+event.total);

				}

				public void completed(ProgressEvent event) {
					System.out.println("�����ϣ�" + url);

					if (keepRun == false) {
						done();
						return;
					}
					if (LhpUtil.handelHref(
							browser.evaluate(
									"return window.location.toString();")
									.toString()).equals(
							LhpUtil.handelHref(browser.getUrl())) == false
							|| (LhpUtil.handelHref(browser.getUrl()).equals(
									LhpUtil.handelHref(url)) == false)) {
						// �������ʧ���򷵻�
						System.out.println("����"
								+ LhpUtil.handelHref(browser.getUrl())
								+ "ʧ�ܣ��������Ӳ����û�����ת");
						System.out.println("�ű�����ҳ��ַ��"
								+ LhpUtil.handelHref(browser.evaluate(
										"return window.location.toString();")
										.toString()));
						System.out.println("browswer��ַ��"
								+ LhpUtil.handelHref(browser.getUrl()));
						System.out.println("URL��ַ��" + LhpUtil.handelHref(url));
						db.increaseWebFailTimes(url);
						done();
						return;
					}
					browser
							.execute("scrip=document.createElement('script');scrip.src='"
									+ base
									+ "/js/userindexjs/overwrite.js';document.getElementsByTagName('head')[0].appendChild(scrip);");

					browser.execute("window.status='" + olpfComplate
							+ "'+document.documentElement.innerHTML;");

					html = (String) browser.getData("html");
					// //////////
					// ��ҳ�е������Ƿ���Ч�ı�־�������ûЧ������Ϊ����ҳ������ip
					boolean error = false;

					try {
						page.parser(html, url);
						
						for (int i = 0; i < modules.size(); i++) {
							
							error = false;
							// System.out.print("����������");
							text = new StringBuffer(120);
							// tempTag =
							// page.getTagNodeByPathByAllPage(this.modules.get(i).path,
							// page);
							// û���ҵ���Ӧ�ڵ㣬����Ϊ�������Ϸ���֪ͨ���ݿ⽫����Ϊ��Ч
							ArrayList<String> paths = LhpUtil.getPaths(modules
									.get(i).path);
							// ȡ����������������
							String temp = null;
							boolean thereIsNull=false;//�Ƿ�ģ������ʧЧ
							for (int h = 0; h < paths.size(); h++) {
								// ȡ��ÿһ��������
								try {
									
									temp = page.getHtmlOfPath(paths.get(h),
											MyHtmlParser.TYPE_TD);
									if (temp != null) {
										text.append(temp);
										temp = null;										
									}else{
										thereIsNull=true;
									}
								} catch (Exception ex) {
									Logger.getLogger(
											SWTUpdateThread.class.getName()).log(
											Level.SEVERE, null, ex);
								}
							}
							if(thereIsNull){//��ģ������ʧЧ
								if(!pingUrl(url)){
									// �������ʧ���򷵻�
									System.out.println("����"
											+ LhpUtil.handelHref(browser.getUrl())
											+ "ʧ�ܣ��������Ӳ����û�����ת");
									System.out.println("�ű�����ҳ��ַ��"
											+ LhpUtil.handelHref(browser.evaluate(
													"return window.location.toString();")
													.toString()));
									System.out.println("browswer��ַ��"
											+ LhpUtil.handelHref(browser.getUrl()));
									System.out.println("URL��ַ��" + LhpUtil.handelHref(url));
									db.increaseWebFailTimes(url);
									done();
									return;
								}
							}
							// System.out.println("//����õ�������"+text);
							// //����õ�������
							String newtext = new String(text);
							if (newtext.length() < 1) {
								System.out.print("ʧ����Ϣ��" + url + "  "
										+ modules.get(i).path);
								db
										.increaseModuleFailTimes(modules.get(i).moduleId);
								// ������Ч
								error = true;
								continue;
							}
							// ֻҪ��һ����Ŀ������Ч������Ϊipû�б���
							error = false;

							updatecontent = page.getUpdate(newtext, modules
									.get(i).text, MyHtmlParser.REMOVE_OLD);
							if (updatecontent != null) {
								// �ȸ�����Ŀ�������ݱ�
								db.updateModuleUpdateConetnt(
										modules.get(i).moduleId, newtext,updatecontent.get(updatecontent.size()-1).content);
								// �����µ�ģ�������Ϣ
								for (int h = 0; h < updatecontent.size(); h++) {
									db.insertNewContent(
											modules.get(i).moduleId,
											updatecontent.get(h).content,
											updatecontent.get(h).monitorType);
								}
							}
							updatecontent = null;

						}
						// ��������Ч����Ϊip����
						if (error == true) {
							db.setLastMonitorTime(url);
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("����" + url + "ʧ��");
						db.increaseWebFailTimes(url);

					} finally {
						done();
					}
					// ///////////

				}
			});

			beginTime = new Date().getTime();
			System.out.println("���ڼ��" + url);
			browser.setUrl(url);
			db.setWebMonitoring(url);
			if (DEBUG_MODU == true) {
				shell.open();
			}
			
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			shell.dispose();
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
		System.out.println("������ѹر�10");
	}

	public void checkTimeOut() {

		if (busy == true) {
			if (new Date().getTime() - beginTime > TIME_OUT) {
				System.out.println("��ַ" + url + "���ӳ�ʱ��");
				if (display != null) {
					//if(display.isDisposed() == false){
					display.asyncExec(new Runnable() {
						public void run() {
							if (shell!=null&&shell.isDisposed() == false) {
								done();
							}
						}
					});
					//}
				}
			}
		}

	}

	private void done() {
		db.setLastMonitorTime(url);
		db.resetWebMonitoring(url);
		shell.dispose();
	}
	
	/**
	 * ������ַ�Ƿ��������
	 * @param urlstr
	 * @return
	 */
	private boolean pingUrl(String urlstr){
		InputStream in = null;
		try {
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			Net.setHeader(conn); // ��������ͷ
			// ���ڱ����ֽ����飬��ÿ�ζ�ȡ��С����ϲ��ɴ�����
			in=conn.getInputStream();
			in.read(new byte[1]);

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}

