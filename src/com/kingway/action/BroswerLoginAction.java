package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * ������
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class BroswerLoginAction extends ActionSupport{
	private String userid;
	private String password;
	private final static String split="��";
	private String userName=null;
	private String message;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String checkLogin(){
		ActionContext.getContext().getSession();
		if(this.userid==null||this.password==null){
			message="�û���������Ϊ��";
			return "fail";
		}
		UserInfo user=new CheckLoginUserDaoImpl().check(this.userid,this.password);
		
		if(user==null){
			message="�˻����������";
			
			return "fail";
		}else if(user.getEnable()==0){
		//	addFieldError("username", "�û�������");
			message="�û������ã�";
			
			return "fail";
		}
		else if(user.getUserType()==0){
		//	addFieldError("username", "�û�δ����");
			message="�û�δ����";
			
			return "fail";
		}else {
			this.userid = Long.toString(user.getUserId());
			ActionContext.getContext().getSession().put("userid", this.userid);
			userName=user.getUserName();
			return "success";
		}
		
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
}
