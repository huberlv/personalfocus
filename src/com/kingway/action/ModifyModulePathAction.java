package com.kingway.action;

import java.util.Vector;

import net.URLS;
import moduledefine.HtmlHandler;
import com.kingway.model.ModuleInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.OnlineFocus;
import com.opensymphony.xwork2.ActionContext;

public class ModifyModulePathAction {
	private Vector<String> html;
	private String charset;
	// public static String showPageUrl =
	// URLS.getHostAddress()+"/personalfocus/user/showWebPage?url=";
	public static String pagePostfix = "(?i)(htm|html|jsp|php|asp|aspx|shtml|shtm)";
	public static String downloadFile = "(?i)xml";
    private String moduleId;
    private static final String DIVCSS= "<link rel=\"stylesheet\" type=\"text/css\" href=\""
    + URLS.getHostAddress()
   + "/personalfocus/css/userindexcss/kindeditorLayer.css\">";
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

	public String showWebPage() {
		Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return "input";
		}
		ModuleInfo moduleInfo = (ModuleInfo)HibernateUtil.get(ModuleInfo.class, Long.parseLong(moduleId));
		String url=moduleInfo.getMonitorWebInfo().getWebAddress();	
		String moduleIdStr="<script type=\"text/javascript\">moduleId="+this.moduleId+"" +
				";var oldpath=\""+moduleInfo.getModulePath().replaceAll("\"", "\\\"")+"\";</script>"+DIVCSS;
		HtmlHandler p = new HtmlHandler(url, HtmlHandler.MODIFY_MODULE_TOP_HEAD_STR+moduleIdStr,HtmlHandler.DEFINE_TOP_BODY_STR);
		charset = p.getCharest(); // 获得该URL的charset
	    this.html = p.getHandleString();
		return "htmlpage";
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleId() {
		return moduleId;
	}
}
