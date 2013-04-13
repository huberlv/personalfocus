package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.kingway.model.UserFocusWebModuleView;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 吕鸿佩
 * @author Administrator
 *
 */
public class CheckModuleExistsAction {

    private String url;

    public String checkModuleExists(){
    	ActionContext.getContext().getSession();
    	
		url=LhpUtil.getSourceURL(url);	 
    	
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
			
			 pw.print("null");
				pw.flush();
				pw.close();		
		    	return "success";
		}else{
		     u=HibernateUtil.list("from UserFocusWebModuleView where userId="+userId.toString()+" and webAddress='"+this.url+"'");
		}
		if(u==null||u.size()==0){
		  pw.print("null");
		  pw.flush();
			pw.close();		
	    	return "success";
		}else{
			StringBuilder temp=new StringBuilder();
			for(int i=0;i<u.size();i++){
				temp.append(u.get(i).getId().getModulePath());
				
				temp.append(",");
			}
			if(temp.toString().endsWith(",")){
			  temp.delete(temp.length()-1, temp.length());
			}
			if(temp.toString().length()==0){
				
				 pw.print("null");
			}
			pw.print(temp.toString());
			pw.flush();
			pw.close();		
	    	return "success";
		}

    }

	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}

}
