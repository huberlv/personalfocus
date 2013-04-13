package com.kingway.action;

import java.util.Date;

import com.kingway.dao.impl.AddUserDaoImpl;
import com.kingway.util.MD5Util;
import com.opensymphony.xwork2.ActionContext;

public class RegistAction {
	private Long id;
	private String inp_uname;
	private String[] password;
	private String mailbox;
	private String mobile;
	private String gender;
	private Date birthday;
	private Date registdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInp_uname() {
		return inp_uname;
	}

	public void setInp_uname(String inpUname) {
		inp_uname = inpUname;
	}

	public String[] getPassword() {
		return password;
	}

	public void setPassword(String[] password) {
		this.password = password;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getRegistdate() {
		return registdate;
	}

	public void setRegistdate(Date registdate) {
		this.registdate = registdate;
	}

	public String doRegist() {
		System.out.println(inp_uname);
		System.out.println(password[0]);

		if (gender.equals("1"))
			gender = "ÄÐ";
		else
			gender = "Å®";
		System.out.println("gender:"+gender);
		System.out.println("mailbox:"+mailbox);
		System.out.println("mobile:"+mobile);
		System.out.println("birthday:"+birthday);
		this.id=new AddUserDaoImpl().adduser(inp_uname, password[0], gender, mailbox,
				mobile, birthday, 1);
		
		ActionContext.getContext().getSession().put("userid", this.id.toString());
		ActionContext.getContext().getSession().put("username", this.inp_uname);
		return "success";
	}

}
