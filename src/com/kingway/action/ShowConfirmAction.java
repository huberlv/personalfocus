package com.kingway.action;

import com.opensymphony.xwork2.ActionContext;

public class ShowConfirmAction {

	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String showconfirm(){
		this.msg = (String) ActionContext.getContext().getSession().get("text");
		System.out.println("msg1:"+this.msg);
		return "success";
	}
}
