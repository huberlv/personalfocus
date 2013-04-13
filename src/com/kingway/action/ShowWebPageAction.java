package com.kingway.action;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import moduledefine.HtmlHandler;
import org.apache.struts2.ServletActionContext;

import com.kingway.model.UserFocusWebModuleView;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.opensymphony.xwork2.ActionContext;

public class ShowWebPageAction {
	private String exeitsPaths;
	private Vector<String> html;
	private String url;
	private String charset;
	// public static String showPageUrl =
	// URLS.getHostAddress()+"/personalfocus/user/showWebPage?url=";
	public static String pagePostfix = "(?i)(htm|html|jsp|php|asp|aspx|shtml|shtm)";
	public static String downloadFile = "(?i)xml";
	private boolean downfile = false;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Vector<String> getHtml() {
		return html;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHtml(Vector<String> html) {
		this.html = html;
	}

	@SuppressWarnings("unchecked")
	public String showWebPage() {
		if(ActionContext.getContext().getSession().get("userid")==null){
		   return "fail";
		}
		exeitsPaths=getExeitsPaths();
		HttpServletRequest req = ServletActionContext.getRequest();
		String s = req.getQueryString();
		if (s != null) {
			int begin = s.indexOf("url=");
			if (begin >= 0) {
				s = s.substring(begin + "url=".length()); // ȡ����ַ
			}
		} else {
			String temp1 = req.getParameter("url");
			if (temp1 == null) {
				return "��ַ����";
			}
			StringBuilder tempurl = new StringBuilder(100);
			tempurl.append(temp1);
			Enumeration names = req.getParameterNames();
			String tempname = null;
			while (names.hasMoreElements()) {
				tempname = names.nextElement().toString();
				if (tempname.equals("url") == false) {
					tempurl.append(tempname);
					tempurl.append("=");
					tempurl.append(req.getParameter(tempname));
					tempurl.append("&");
				}
			}
			tempurl.delete(tempurl.length() - 1, tempurl.length());
			s = tempurl.toString();
		}
		s=LhpUtil.getSourceURL(s);	  // �ָ��ύ����ַ��ת�岿���ַ����Ծ�ת�������ת����ַ��
		System.out.println("��ַ" + s);
		// AddMonitorDaoImpl addMonitorDaoImpl = new AddMonitorDaoImpl();
		// δ��ɼ�����ַ�Ƿ�Ϊjavascript
		// �ȼ����������ļ��Ƿ���ҳ
		
		try {
			String file = new URL(this.url).getFile();
			int dot = file.indexOf('.');
			if (dot > 0) {
				String temp = file.substring(dot + 1, file.length());// ȡ�õ������ַ���
				if (temp.matches("[a-zA-Z0-9]+") == true)
					if (temp.matches(pagePostfix) == false) {
						// �������Ѻʹ���ַ
						// System.out.println(this.url);
						if (temp.matches(downloadFile) == false) {
							return "filepage";
						}
						downfile = true;
					}
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return "��ַ����";
		}
		this.url=s;
		HtmlHandler p = null;
		if(req.getParameter("olpfsIsIframe")==null){
			p=new HtmlHandler(url,HtmlHandler.DEFINE_TOP_HEAD_STR+this.exeitsPaths,HtmlHandler.DEFINE_TOP_BODY_STR);
		}else{
			p=new HtmlHandler(url,HtmlHandler.DEFINE_IFRAME_HEAD_STR+this.exeitsPaths,HtmlHandler.DEFINE_IFRAME_BODY_STR);
		}
		charset = p.getCharest(); // ��ø�URL��charset

		// charset = addMonitorDaoImpl.getCharset(this.url);
		if (this.downfile == false)
			this.html = p.getHandleString();
		
		else {
			this.html = new Vector<String>(1);
			this.html.add(p.getSourceHtml());
		}
		return "htmlpage";
	}
	
	/**
	 * ��ȡ�Ѵ��ڵĹ�ע������
	 * @return
	 */
	private String getExeitsPaths() {
		//δ��װ
		List<UserFocusWebModuleView> u=null;
		Object userId = ActionContext.getContext().getSession().get("userid");

		u=HibernateUtil.list("from UserFocusWebModuleView where userId="+userId.toString()+" and webAddress='"+this.url+"'");
		
		if(u==null||u.size()==0){	
			String temp1="<script> var exeitsPaths=\"\";</script>";
	       return temp1;
		}else{
			StringBuilder temp=new StringBuilder();
			for(int i=0;i<u.size();i++){
				temp.append(u.get(i).getId().getModulePath());			
				temp.append(",");
			}
			if(temp.toString().endsWith(",")){
			  temp.delete(temp.length()-1, temp.length());
			}
			String temp1="<script> var exeitsPaths=\""+temp.toString().replaceAll("\"", "\\\\\"")+"\";</script>";
			System.out.println(temp.toString().replaceAll("\"", "\\\\\""));
			return temp1;
		}
	}
}
