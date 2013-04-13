package com.kingway.action;

import org.apache.struts2.ServletActionContext;

public class ShowAction {

	public String show()
	{		
		if(ServletActionContext.getRequest().getSession(false)!=null){
			return "success";
		}else{
			return "fail";
		}
		
	}
}
