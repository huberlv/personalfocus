package com.kingway.action;

import com.kingway.dao.impl.CheckLoginManagementDaoImpl;
import com.kingway.model.ManagerInfo;

import com.opensymphony.xwork2.ActionContext;

public class CheckLoginManagementAction {

	private String userid;
	private String psw;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	
	
	public String checkLogin(){
		
		if(this.userid==null||this.psw==null)
			return "input";
		ManagerInfo user=new CheckLoginManagementDaoImpl().check(this.userid,this.psw);
		if(user==null){
			//addFieldError("username", "’ÀªßªÚ√‹¬Î¥ÌŒÛ£°");
			return "input";
		}
		else {
			this.userid = Long.toString(user.getManagerId());
			ActionContext.getContext().getSession().put("mangerId", this.userid);			
			String username=user.getManagerName();
			ActionContext.getContext().getSession().put("managerName", username);
					
			return "success";
		}
	}
}
