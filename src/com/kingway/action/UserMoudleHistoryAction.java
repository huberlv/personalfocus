package com.kingway.action;
import java.util.List;

import moduledefine.Style;
import com.kingway.dao.impl.UserMoudleHistoryImpl;
import com.opensymphony.xwork2.ActionContext;

/**
 * 吕鸿佩
 * @author Administrator
 *
 */
public class UserMoudleHistoryAction {
	private String userModuleId;
	private String bufferId;
	private String html;
	private String headCssAndScript;
	private String bodyCssAndScript;
	private String style;
	private String width = "600px";
	private String height = "600px";
	public String getBufferId() {
		return bufferId;
	}

	public void setBufferId(String bufferId) {
		this.bufferId = bufferId;
	}
	public String getUserMoudleHistory() {
		String userid=(String)ActionContext.getContext().getSession().get("userid");
		if(userid==null){
			return "fail";
		}
		UserMoudleHistoryImpl sh = new UserMoudleHistoryImpl(
				Long.parseLong(this.userModuleId),Long.parseLong(this.bufferId));

		// 空的情况未处理
		String cas[] = sh.getCss();
		this.bodyCssAndScript = cas[1];
		this.headCssAndScript = cas[0];
		this.html = sh.getUpdateContent();
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

	public void setUserModuleId(String userModuleId) {
		this.userModuleId = userModuleId;
	}

	public String getUserModuleId() {
		return userModuleId;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

}
