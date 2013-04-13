package com.kingway.action;

import moduledefine.Style;
import com.kingway.dao.impl.ShowMyFocusIframeDaoImpl;


public class ShowMyFocosIframeAction {
	private String html;
	private String headCssAndScript;
	private String bodyCssAndScript;
	private long userModuleId;
	private long moduleId;
	private String style;
	private boolean isupdate = false;


	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	private String width = "600px";
	private String height = "600px";
	private String sendIDs;

	public String showMyFocosIframe() {
		ShowMyFocusIframeDaoImpl sh = new ShowMyFocusIframeDaoImpl(this.userModuleId);

		//空的情况未处理
		String cas[] = sh.getCss();
		this.bodyCssAndScript = cas[1];
		this.headCssAndScript = cas[0];
	

		this.html = sh.getUpdateContent();
		this.isupdate=sh.getContentType()==0?false:true;
		this.sendIDs=sh.getContentId()+"";
	
		this.style = sh.getUserModuleStyle();
		Style s = new Style(this.style);
		this.width = s.getAStyle("width");
		this.height = s.getAStyle("height");
		return "success";
	}

	

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return html;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return height;
	}

	public void setBodyCssAndScript(String bodyCssAndScript) {
		this.bodyCssAndScript = bodyCssAndScript;
	}

	public String getBodyCssAndScript() {
		return bodyCssAndScript;
	}

	public void setHeadCssAndScript(String headCssAndScript) {
		this.headCssAndScript = headCssAndScript;
	}

	public String getHeadCssAndScript() {
		return headCssAndScript;
	}

	public void setUserModuleId(long userModuleId) {
		this.userModuleId = userModuleId;
	}

	public long getUserModuleId() {
		return userModuleId;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

	public void setSendIDs(String sendIDs) {
		this.sendIDs = sendIDs;
	}

	public String getSendIDs() {
		return sendIDs;
	}

	public void setIsupdate(boolean isupdate) {
		this.isupdate = isupdate;
	}

	public boolean getIsupdate() {
		return isupdate;
	}

}
