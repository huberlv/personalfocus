package com.kingway.model.struct;

public class RegistList {
	public String mail;
	public String phone;

	public RegistList(String mail, String phone) {
		this.mail = mail;
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
