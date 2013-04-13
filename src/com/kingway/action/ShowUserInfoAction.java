package com.kingway.action;

import java.util.Date;

import com.kingway.dao.impl.ShowUserInfoDaoImpl;
import com.opensymphony.xwork2.ActionContext;

public class ShowUserInfoAction {

	private String username;
	private String phone;
	private String mailbox;
	private Date birthday;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMailbox() {
		return mailbox;
	}


	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String userinfo(){
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id =Long.parseLong(idstr);
		this.username=new ShowUserInfoDaoImpl().showUserInfo(id).getUserName();
		this.phone=new ShowUserInfoDaoImpl().showUserInfo(id).getPhone();
		this.birthday=new ShowUserInfoDaoImpl().showUserInfo(id).getBirthday();
		this.mailbox=new ShowUserInfoDaoImpl().showUserInfo(id).getMailbox();
		return "success";
	}
}
