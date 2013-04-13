package com.kingway.action;

import com.opensymphony.xwork2.ActionContext;

public class showMobileIndexAction {


		public String showMobileIndex(){
			SystemAction.countMobileAccessTime(ActionContext.getContext());
			return "success";
		}
}
