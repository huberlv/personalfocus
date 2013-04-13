package com.kingway.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import moduledefine.Style;

import com.kingway.dao.ListUserMoudleHistoryDao;
import com.kingway.dao.impl.ListUserMoudleHistoryImpl;
import com.kingway.model.UserModuleHistoryListView;
import com.kingway.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;

public class ListUserMoudleHistoryAction {
	private Long userModuleId;
    private List<UserModuleHistoryListView> userModuleHistoryList;
    private String userModuleStyle;
    private String moduleNum;
    private String page;
    private List<String> hrefs=new ArrayList<String>();
    private static int maxHistoryNum=PropertiesUtil.getProperty("maxHistoryNum")==null?40:Integer.parseInt(PropertiesUtil.getProperty("maxHistoryNum"));
	public List<String> getHrefs() {
		return hrefs;
	}
	public String listUserMoudleHistory() {
		
		String userid=(String)ActionContext.getContext().getSession().get("userid");
		if(userid==null){
			return "fail";
		}
		else{
			int numint=Integer.parseInt(moduleNum);
			int pageint=Integer.parseInt(page);
			ListUserMoudleHistoryDao list=new ListUserMoudleHistoryImpl();
			userModuleHistoryList=list.getUserModuleHistoryList(userModuleId,(pageint-1)*numint,numint);

			userModuleStyle=list.getUserModuleStyle(userModuleId);
			Style s = new Style(this.userModuleStyle);
			String width = s.getAStyle("width");
			String height = s.getAStyle("height");
			userModuleStyle="width:"+width+";height:"+height+";";
			int pagesize1=list.getSize(userModuleId);
			int pageSize2=pagesize1/numint;
			if(pagesize1%numint>0){
				pageSize2++;
			}
			getMyHrefs(pageSize2);
			return "success";
		}
	}
	public String getUserModuleStyle() {
		return userModuleStyle;
	}

	public void setUserModuleStyle(String userModuleStyle) {
		this.userModuleStyle = userModuleStyle;
	}

	public void setUserModuleId(Long userModuleId) {
		this.userModuleId = userModuleId;
	}

	public Long getUserModuleId() {
		return userModuleId;
	}

	public void setUserModuleHistoryList(List<UserModuleHistoryListView> userModuleHistoryList) {
		this.userModuleHistoryList = userModuleHistoryList;
	}

	public List<UserModuleHistoryListView> getUserModuleHistoryList() {
		return userModuleHistoryList;
	}

	public void setModuleNum(String moduleNum) {
		this.moduleNum = moduleNum;
	}

	public String getModuleNum() {
		return moduleNum;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setHrefs(List<String> hrefs) {  
		this.hrefs = hrefs;
	}
	public void getMyHrefs(int pageSize2) {
		HttpServletRequest req = ServletActionContext.getRequest();
		String url=req.getContextPath()+req.getServletPath()+"?"+req.getQueryString();
		for(int i=0;i<pageSize2&&i<maxHistoryNum;i++){
			
			hrefs.add(url.substring(0,url.indexOf("page=")+"page=".length())+(i+1));
		}
	}
}
