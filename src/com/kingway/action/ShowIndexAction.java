package com.kingway.action;

import com.opensymphony.xwork2.ActionContext;

public class ShowIndexAction {

	public String showindex(){
		SystemAction.countAccessTime(ActionContext.getContext());
		return "success";
	}

}
