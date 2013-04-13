package com.kingway.action;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;

import net.URLS;

import org.apache.struts2.ServletActionContext;
import org.jsoup.nodes.Document;

import com.kingway.util.LhpUtil;


public class ShowPageAction {
	private String html;
	private String url;
	public static String showPageUrl = URLS.getHostAddress()
			+ "/personalfocus/mobile/showPage?url=";
	public static String pagePostfix = "(htm|html|jsp|php|asp|aspx|shtml|shtm)";
    private String charset="GBK";
    private String returnTo="返回平台";
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

	public String showPage() {
	//	HttpServletRequest req = ServletActionContext.getRequest();
	//	String s = req.getQueryString();
	//	s = s.substring(4);
	//	this.url = s;
		// 先检查所请求的文件是否网页
		this.url=LhpUtil.getSourceURL(url);	 
		try {
			String file = new URL(this.url).getFile();
			int dot = file.indexOf('.');
			if (dot > 0
					&& file.substring(dot + 1, file.length()).matches(
							this.pagePostfix) == false) {
				// 返回提醒和此网址
				System.out.println(this.url);
				return "filepage";
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		Document document=LhpUtil.getMbHtmlFromURL(url, url, showPageUrl);
		this.html =document.body().html();
        this.charset=document.outputSettings().charset().displayName();
   
		return "htmlpage";
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public void setReturnTo(String returnTo) {
		this.returnTo = returnTo;
	}

	public String getReturnTo() {
		return returnTo;
	}
}
