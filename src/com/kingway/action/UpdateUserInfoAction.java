package com.kingway.action;

import java.util.Date;

import com.kingway.dao.impl.UpdateUserInfoDaoImpl;
import com.opensymphony.xwork2.ActionContext;

public class UpdateUserInfoAction {
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


	public String updateuserinfo(){
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id=Long.parseLong(idstr);
		new UpdateUserInfoDaoImpl().updateuserinfo(id, username, phone, mailbox, birthday);
		ActionContext.getContext().getSession().put("username", username);
		return "success";
	}
}
