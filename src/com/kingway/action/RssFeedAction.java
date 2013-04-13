package com.kingway.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import rss.RSSUtil;

import com.kingway.model.ModuleInfo;
import com.kingway.util.HibernateUtil;

/**
 *吕鸿佩 RSS种子类
 * @author Administrator
 *
 */
public class RssFeedAction {
    private String moduleId;
    private String rssContent;
    private String title;
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleId() {
		return moduleId;
	}
	
	public String getRSSContent(){
		if(moduleId==null){
			return "fail";
		}
		//未封装
		Object obj=HibernateUtil.get(ModuleInfo.class, Long.parseLong(moduleId));
		if(obj==null){
			return "fail";
		}
		ModuleInfo moduleInfo=(ModuleInfo)obj;

		HttpServletRequest req = ServletActionContext.getRequest();
		String title = req.getQueryString();
		int index=title.indexOf("title=");
		if(index>0){
			title = title.substring(index+ 6,
					title.length());
			try {
				title = URLDecoder.decode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				title="RSS";
			}
		}else{
			title="RSS";
		}
		rssContent=moduleInfo.getRssContent().replaceFirst(RSSUtil.DEFAULT_RSS_TITLE, title);
		return "OK";
	}

	public void setRssContent(String rssContent) {
		this.rssContent = rssContent;
	}

	public String getRssContent() {
		return rssContent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
