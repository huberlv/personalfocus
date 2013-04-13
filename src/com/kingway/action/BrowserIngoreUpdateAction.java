package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleDisingoreView;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;
/**
 * 用户在浏览器忽略了更新
 * @author Administrator
 *
 */
public class BrowserIngoreUpdateAction {
    public String ingore(){
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
		Object userId = ActionContext.getContext().getSession().get("userid");
		if (userId == null) {
			pw.print("fail");
			pw.flush();
			pw.close();			    	
		}
    	List<UserModuleDisingoreView> disingore=HibernateUtil
		.list("from UserModuleDisingoreView where userId=" + userId );
		List<Long> contentIds=new ArrayList<Long>();
		for(int i=0;i<disingore.size();i++)
		{
			contentIds.add(disingore.get(i).getId().getContentId());
		}
		List<UserModuleContents> view = HibernateUtil
				.batchGet(UserModuleContents.class, contentIds);
		for (UserModuleContents temp : view)
			{
			   temp.setIgnoreByUser(1);
			}
		HibernateUtil.batchUpdate(view);
		pw.print("success");
		pw.flush();
		pw.close();		
		return "success";
    }
}
