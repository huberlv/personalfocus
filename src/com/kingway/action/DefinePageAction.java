package com.kingway.action;

import java.util.List;

import moduledefine.HtmlHandler;
import net.URLS;
import com.kingway.dao.impl.GetModuleDaoImpl;
import com.kingway.model.MonitorByJsView;
import com.kingway.model.MonitorByJsViewId;

public class DefinePageAction {

	/* ����Ľű� */
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
	public static int olpfFlashtime = 6000; // ��ʼ���ʱ������Ҫ��̬�ı䣬δ��ɣ���λ����

	public static int getOlpfFlashtime() {
		return olpfFlashtime;
	}

	public static void setOlpfFlashtime(int olpfFlashtime) {
		DefinePageAction.olpfFlashtime = olpfFlashtime;
	}

	private static String insert2 = "; var olpfModuleId=";
	private static String insert3 = ";var  olpfFlashtime=";
	private static String insertEnd = ";" + "</script>";// ʱ��

	/* Action���� */
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
		List<MonitorByJsView> list = new GetModuleDaoImpl().getModule(); // ȡ��ÿһ�м�¼
		if (list != null) {

			this.url = (list.get(0)).getId().getWebAddress(); // �ɵ�һ�м�¼ȡ����ַ

		} else {

			StringBuilder temp = new StringBuilder(500);
			temp.append(insert1); // �����һ�����
			temp.append("'");
			// ������
			temp.append("'");
			temp.append(insert2); // ����ڶ������
			temp.append("'");
			temp.append(-1); // ����û����Ҫ���µ���ҳ����-1Ϊ��־
			temp.append("'");
			temp.append(insert3);
			temp.append(olpfFlashtime);
			temp.append(insertEnd);
			temp.append("<doctype html><html><body></body></html>");// ����Դ�ļ�
			this.html = temp.toString();
			return "definePage";
		}
		// �ȼ����������ļ��Ƿ���ҳ

		// charset = addMonitorDaoImpl.getCharset(this.url); // ��DB�����Ƿ��и�URL��Ϣ
		try {
			HtmlHandler p = null;

			p = new HtmlHandler(url,HtmlHandler.DEFINE_TOP_HEAD_STR,HtmlHandler.DEFINE_TOP_BODY_STR);
			charset = p.getCharest(); // ��ø�URL��charset

			MonitorByJsViewId mbj = null;
			StringBuffer paths = new StringBuffer(120); // ����
			StringBuffer moduleId = new StringBuffer(60); // ģ��id
			for (int i = 0; i < list.size(); i++) {
				mbj = (list.get(i)).getId();
				paths.append(mbj.getModulePath());
				paths.append("%"); // ����ָ���
				moduleId.append(mbj.getModuleId());
				moduleId.append("%"); // ����ָ���
			}
			String source = p.getSourceHtml(); // ȡ��Դ��
			StringBuilder temp = new StringBuilder(source.length() + 500);
			temp.append(insert1); // �����һ�����
			temp.append("'");
			temp.append(paths); // ��������
			temp.append("'");
			temp.append(insert2); // ����ڶ������
			temp.append("'");
			temp.append(moduleId); // ����ģ��id
			temp.append("'");
			temp.append(insert3);
			temp.append(olpfFlashtime);
			temp.append(insertEnd);
			temp.append("<base href='" + p.getBase() + "'/>"); // ����base
			temp.append(source);// ����Դ�ļ�

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
