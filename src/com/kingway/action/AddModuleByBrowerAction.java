package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.kingway.util.MD5Util;
import com.opensymphony.xwork2.ActionContext;

import dwr.AddModuleInfo;

/**
 * ��
 * 
 * @author Administrator
 * 
 */
public class AddModuleByBrowerAction {
	private String userId;
	private String password;
	private String values;

	public String addModule() {

		PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse(); // ȡresponse����
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.userId != null && this.password != null) {
			try {
				UserInfo userInfo = (UserInfo) HibernateUtil.get(
						UserInfo.class, Long.parseLong(userId));// δ��װ
				if (userInfo.getPassword().equals(
						MD5Util.getMD5(password.getBytes()))) {
					ActionContext.getContext().getSession().put("userid", this.userId);
					pw.print("success");
				} else {
					System.out.println(password);
					System.out.println("密码认证失败");
					pw.print("fail");
				}
			} catch (Exception e) {
				e.printStackTrace();
				pw.print("fail");
			}
		} else {
			System.out.println("用户名或密码为空");
			pw.print("fail");
		}
		this.addAModule(values);
		pw.flush();
		pw.close();
		return "success";
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getValues() {
		return values;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	private boolean addAModule(String values){
		Document document=Jsoup.parseUnTransform(values);
		LhpUtil.removeDivs(document);	
		String olpfsUrl=document.getElementsByTag("olpfsUrl").first().text().replaceAll("⟨", "&lang");
	
		olpfsUrl=LhpUtil.getSourceURL(olpfsUrl);	 


		String olpfHeadCSSAndCsript=document.getElementsByTag("olpfHeadCSSAndCsript").first().html();
		
		String olpfBodyCSSAndCsript=document.getElementsByTag("olpfBodyCSSAndCsript").first().html();
		String olpfTitle=document.getElementsByTag("olpfTitle").first().text();		
		String modulename=document.getElementsByTag("modulename").first().text();
		String olpftexts=document.getElementsByTag("olpftexts").first().html();
		
		String isMobile=document.getElementsByTag("isMobile").first().text();
		String isMail=document.getElementsByTag("isMail").first().text();
		String messageType=document.getElementsByTag("messageType").first().text();
		String messageStartTime=document.getElementsByTag("messageStartTime").first().text();
		String messageStopTime=document.getElementsByTag("messageStopTime").first().text();
		String messageFrequency=document.getElementsByTag("messageFrequency").first().text();
		String messageFrequencyType=document.getElementsByTag("messageFrequencyType").first().text();
		String messageMaxNum=document.getElementsByTag("messageMaxNum").first().text();		
		String emailStartTime=document.getElementsByTag("emailStartTime").first().text();
		String emailStopTime=document.getElementsByTag("emailStopTime").first().text();
		String emailFrequency=document.getElementsByTag("emailFrequency").first().text();
		String emailFrequencyType=document.getElementsByTag("emailFrequencyType").first().text();
		String olpfpaths=document.getElementsByTag("olpfpaths").first().text();
		String saveConfig=document.getElementsByTag("saveConfig").first().text();
		String useDefaultConfig=document.getElementsByTag("useDefaultConfig").first().text();
		String olpfsMyStyle=document.getElementsByTag("olpfsMyStyle").first().text();
		String monitorType=document.getElementsByTag("monitorType").first().text();
		String subgroupId=document.getElementsByTag("subgroupId").first().text();
        String []olpfCSSAndCsript=new String[2];
        olpfCSSAndCsript[0]=olpfHeadCSSAndCsript;
        olpfCSSAndCsript[1]=olpfBodyCSSAndCsript;
        AddModuleInfo addModuleInfo=new AddModuleInfo();
     
        addModuleInfo.checkModuleInfo(olpfsUrl, olpfCSSAndCsript, olpfTitle, modulename, olpftexts, 
        		Integer.parseInt(isMobile), Boolean.parseBoolean(isMail), messageType, 
        		messageStartTime, messageStopTime, messageFrequency, 
        		messageFrequencyType, messageMaxNum, emailStartTime, emailStopTime, emailFrequency, 
        		emailFrequencyType, olpfpaths, Boolean.parseBoolean(saveConfig), Boolean.parseBoolean(useDefaultConfig), olpfsMyStyle, 
        		Integer.parseInt(monitorType), Long.parseLong(subgroupId));
		return true;
	}
}
