package com.kingway.action;

import java.util.List;

import moduledefine.HtmlHandler;
import net.URLS;
import com.kingway.dao.impl.GetModuleDaoImpl;
import com.kingway.model.MonitorByJsView;
import com.kingway.model.MonitorByJsViewId;

public class DefinePageAction {

	/* 插入的脚本 */
	private static String insert1 = "<SCRIPT type=text/javascript" + "	src='"
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js'></SCRIPT>"
			+ "	<script type='text/javascript'" + "		src='"
			+ URLS.getHostAddress() + "/personalfocus/dwr/engine.js'></script>"
			+ "	<script type='text/javascript'" + "		src='"
			+ URLS.getHostAddress() + "/personalfocus/dwr/util.js'></script>"
			+ "		<script type='text/javascript'" + "		src='"
			+ URLS.getHostAddress()
			+ "/personalfocus/dwr/interface/monitorByJs.js'></script>"
			+ "<SCRIPT type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/toolkits.js\"></SCRIPT>"
			+ "		<script type='text/javascript'" + "		src='"
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/monitorByMyJs.js'></script>"
			+ "	<script type='text/javascript'>" + "var olpfPaths=";
	public static int olpfFlashtime = 6000; // 开始监控时间间隔，要动态改变，未完成，单位毫秒

	public static int getOlpfFlashtime() {
		return olpfFlashtime;
	}

	public static void setOlpfFlashtime(int olpfFlashtime) {
		DefinePageAction.olpfFlashtime = olpfFlashtime;
	}

	private static String insert2 = "; var olpfModuleId=";
	private static String insert3 = ";var  olpfFlashtime=";
	private static String insertEnd = ";" + "</script>";// 时间

	/* Action部分 */
	private String html = "<body></body>";
	private String url;
	private String charset = "gb2312";

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String showWebPage() {
		// HttpServletRequest req = ServletActionContext.getRequest();
		// AddMonitorDaoImpl addMonitorDaoImpl=new AddMonitorDaoImpl();
		// System.out.println(url);
		try{
		List<MonitorByJsView> list = new GetModuleDaoImpl().getModule(); // 取得每一行纪录
		if (list != null) {

			this.url = (list.get(0)).getId().getWebAddress(); // 由第一行纪录取得网址

		} else {

			StringBuilder temp = new StringBuilder(500);
			temp.append(insert1); // 加入第一条语句
			temp.append("'");
			// 无特征
			temp.append("'");
			temp.append(insert2); // 加入第二条语句
			temp.append("'");
			temp.append(-1); // 加入没有需要更新的网页，以-1为标志
			temp.append("'");
			temp.append(insert3);
			temp.append(olpfFlashtime);
			temp.append(insertEnd);
			temp.append("<doctype html><html><body></body></html>");// 加入源文件
			this.html = temp.toString();
			return "definePage";
		}
		// 先检查所请求的文件是否网页

		// charset = addMonitorDaoImpl.getCharset(this.url); // 在DB查找是否有该URL信息
		try {
			HtmlHandler p = null;

			p = new HtmlHandler(url,HtmlHandler.DEFINE_TOP_HEAD_STR,HtmlHandler.DEFINE_TOP_BODY_STR);
			charset = p.getCharest(); // 获得该URL的charset

			MonitorByJsViewId mbj = null;
			StringBuffer paths = new StringBuffer(120); // 特征
			StringBuffer moduleId = new StringBuffer(60); // 模块id
			for (int i = 0; i < list.size(); i++) {
				mbj = (list.get(i)).getId();
				paths.append(mbj.getModulePath());
				paths.append("%"); // 加入分隔符
				moduleId.append(mbj.getModuleId());
				moduleId.append("%"); // 加入分隔符
			}
			String source = p.getSourceHtml(); // 取得源码
			StringBuilder temp = new StringBuilder(source.length() + 500);
			temp.append(insert1); // 加入第一条语句
			temp.append("'");
			temp.append(paths); // 加入特征
			temp.append("'");
			temp.append(insert2); // 加入第二条语句
			temp.append("'");
			temp.append(moduleId); // 加入模块id
			temp.append("'");
			temp.append(insert3);
			temp.append(olpfFlashtime);
			temp.append(insertEnd);
			temp.append("<base href='" + p.getBase() + "'/>"); // 加入base
			temp.append(source);// 加入源文件

			this.html = temp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}catch (Exception e) {
			e.printStackTrace();
			return "definePage";
			
		}
		return "definePage";
	}

}
