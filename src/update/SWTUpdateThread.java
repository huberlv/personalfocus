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
 *负责监测网站的线程，在线程池中，运行完一次后等待 在再次唤醒时要先用setModuleNeedToBeUpdate方法设置新监控的内容
 * 并且要先读取其url，通知数据库将其is_monitoring设为0
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
	/** 保持运行，可用此变量控制线程是否一直运行，或终止线程 */
	private volatile boolean keepRun = true;
	/** 此线程所属的UpdateServer */
	private UpdateServer updateServer;
	static int updateThreadnum = 0;

	/** 已运行时间，单位为秒 */
	public int failTimes = 0;
	private DatabaseDaoImpl db = new DatabaseDaoImpl();
	private String html;
	private Display display;
    private Shell shell;

    private MyHtmlParser page = new MyHtmlParser();

    private StringBuffer text;
    private ArrayList<UpdateContent> updatecontent;
	/**
	 * 构造函数
	 * 
	 * @param keepRun
	 *            是否保持运行
	 * @param updateServer
	 *            此线程所属的更新服务器
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
						System.out.println("浏览器已关闭11");
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
		// 初始化浏览器
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
					System.out.println("打开一个新窗口");
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
					// System.out.println("当前"+event.current);
					// System.out.println("所有"+event.total);

				}

				public void completed(ProgressEvent event) {
					System.out.println("监控完毕！" + url);

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
						// 如果连接失败则返回
						System.out.println("连接"
								+ LhpUtil.handelHref(browser.getUrl())
								+ "失败，网络连接不可用或已跳转");
						System.out.println("脚本中网页地址："
								+ LhpUtil.handelHref(browser.evaluate(
										"return window.location.toString();")
										.toString()));
						System.out.println("browswer地址："
								+ LhpUtil.handelHref(browser.getUrl()));
						System.out.println("URL地址：" + LhpUtil.handelHref(url));
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
					// 网页中的特征是否有效的标志，如果都没效，则认为是网页封锁了ip
					boolean error = false;

					try {
						page.parser(html, url);
						
						for (int i = 0; i < modules.size(); i++) {
							
							error = false;
							// System.out.print("更新已启动");
							text = new StringBuffer(120);
							// tempTag =
							// page.getTagNodeByPathByAllPage(this.modules.get(i).path,
							// page);
							// 没有找到相应节点，则认为特征不合法，通知数据库将其设为无效
							ArrayList<String> paths = LhpUtil.getPaths(modules
									.get(i).path);
							// 取得所有特征的文字
							String temp = null;
							boolean thereIsNull=false;//是否模块物征失效
							for (int h = 0; h < paths.size(); h++) {
								// 取得每一特征文字
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
							if(thereIsNull){//有模块物征失效
								if(!pingUrl(url)){
									// 如果连接失败则返回
									System.out.println("连接"
											+ LhpUtil.handelHref(browser.getUrl())
											+ "失败，网络连接不可用或已跳转");
									System.out.println("脚本中网页地址："
											+ LhpUtil.handelHref(browser.evaluate(
													"return window.location.toString();")
													.toString()));
									System.out.println("browswer地址："
											+ LhpUtil.handelHref(browser.getUrl()));
									System.out.println("URL地址：" + LhpUtil.handelHref(url));
									db.increaseWebFailTimes(url);
									done();
									return;
								}
							}
							// System.out.println("//输出得到的文字"+text);
							// //输出得到的文字
							String newtext = new String(text);
							if (newtext.length() < 1) {
								System.out.print("失败信息：" + url + "  "
										+ modules.get(i).path);
								db
										.increaseModuleFailTimes(modules.get(i).moduleId);
								// 特征无效
								error = true;
								continue;
							}
							// 只要有一个栏目特征有效，则认为ip没有被封
							error = false;

							updatecontent = page.getUpdate(newtext, modules
									.get(i).text, MyHtmlParser.REMOVE_OLD);
							if (updatecontent != null) {
								// 先更新栏目最新内容表
								db.updateModuleUpdateConetnt(
										modules.get(i).moduleId, newtext,updatecontent.get(updatecontent.size()-1).content);
								// 插入新的模块更新信息
								for (int h = 0; h < updatecontent.size(); h++) {
									db.insertNewContent(
											modules.get(i).moduleId,
											updatecontent.get(h).content,
											updatecontent.get(h).monitorType);
								}
							}
							updatecontent = null;

						}
						// 特征都无效，认为ip被封
						if (error == true) {
							db.setLastMonitorTime(url);
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("访问" + url + "失败");
						db.increaseWebFailTimes(url);

					} finally {
						done();
					}
					// ///////////

				}
			});

			beginTime = new Date().getTime();
			System.out.println("正在监控" + url);
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
		System.out.println("浏览器已关闭10");
	}

	public void checkTimeOut() {

		if (busy == true) {
			if (new Date().getTime() - beginTime > TIME_OUT) {
				System.out.println("网址" + url + "连接超时！");
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
	 * 测试网址是否可以连接
	 * @param urlstr
	 * @return
	 */
	private boolean pingUrl(String urlstr){
		InputStream in = null;
		try {
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			Net.setHeader(conn); // 设置请求头
			// 用于保存字节数组，将每次读取的小数组合并成大数组
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

