package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.kingway.model.CheckUpdateUserModuleView;
import com.kingway.model.UserFocusWebModuleView;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 浏浏器端检查模块更新
 * 吕鸿佩
 * @author Administrator
 *
 */
public class BrowserCheckUpdateUserModuleAction {
    public String checkUpdateUserModule(){
    	PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse(); // 取response对象
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//未封装
		List<UserFocusWebModuleView> u=null;
		Object userId = ActionContext.getContext().getSession().get("userid");
		if (userId == null) {
			pw.print("fail");
			pw.flush();
			pw.close();			    	
		}else{
			List<CheckUpdateUserModuleView> checkUpdateUserModules=HibernateUtil.list("from CheckUpdateUserModuleView where userId="+userId+" and ignoreByUser=0");
	    	if(checkUpdateUserModules.size()==0){	    	
				pw.print("null");
				pw.flush();
				pw.close();		
			}else{
				pw.print("update");
				pw.flush();
				pw.close();	
			}
		}
    	return "success";
    }
}
