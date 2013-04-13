package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.kingway.model.UserModuleUpdatepathsView;
import com.kingway.model.UserModuleUpdatepathsViewId;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.opensymphony.xwork2.ActionContext;

public class CheckModuleUpdatePathsAction {
	    private String url;
	    private String path;
	    public String checkModuleUpdatePaths(){
			
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

			Object userId = ActionContext.getContext().getSession().get("userid");
			if (userId == null) {
				 pw.print("false");
				 pw.flush();
					pw.close();
					
			    	return "success";
			}
			//未封装
			Object u=null;
			try{
			 u=HibernateUtil.getRecord("from UserModuleUpdatepathsView where userId="+userId+" and webAddress='"+this.url+"' and modulePath='"+
					this.path+"'");
			}catch(Exception e){
				e.printStackTrace();
			}
			if(u==null){
			  pw.print("false");//不存在模块
			  System.out.println("不存在模块");
			}else{
				 System.out.println("服务器存在模块");
				UserModuleUpdatepathsViewId userModuleUpdatepaths=((UserModuleUpdatepathsView)u).getId();
			    pw.print("userModuleId:"+userModuleUpdatepaths.getUserModuleId()+";");
			   
			    if(userModuleUpdatepaths.getContentType()==1){//有更新
			    	 pw.print("contentId:"+userModuleUpdatepaths.getContentId()+";");//输出id
			    	 pw.print("updatePaths:"+LhpUtil.getUpdatePaths(userModuleUpdatepaths.getUserModuleContent()+";"));
			    }else{
			    	 pw.print("contentId:"+"null;");//输出id
			    	 pw.print("updatePaths:"+"null;");		   
			    }		  
			}
			pw.flush();
			pw.close();
			
	    	return "success";
	    }

		public void setUrl(String url) {
			this.url = url;
		}
		public String getUrl() {
			return url;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getPath() {
			return path;
		}
}
