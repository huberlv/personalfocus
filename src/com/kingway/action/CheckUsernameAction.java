package com.kingway.action;

import com.kingway.dao.impl.CheckUsernameDaoImpl;

public class CheckUsernameAction {
	private String inp_uname;
	private String message;
	
	public String getInp_uname() {
		return inp_uname;
	}

	public void setInp_uname(String inpUname) {
		inp_uname = inpUname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String checkusername(){
		System.out.println(this.inp_uname);
		CheckUsernameDaoImpl c=new CheckUsernameDaoImpl();
		if(c.check(this.getInp_uname())==null)
			this.message="改用户名可以注册！";
		else this.message="改用户名已被占用！";
		return "success";
	}
}
