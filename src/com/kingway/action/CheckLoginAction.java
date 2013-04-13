package com.kingway.action;


import moduledefine.Style;
import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.dao.impl.GetThemeDaoImpl;
import com.kingway.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CheckLoginAction extends ActionSupport{
	private String userid;
	private String password;

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

		if(this.userid==null||this.password==null)
			return "input";
		UserInfo user=new CheckLoginUserDaoImpl().check(this.userid,this.password);
		if(user==null){
			addFieldError("username", "账户或密码错误！");
			return "input";
		}
		else if(user.getEnable()==0){
			addFieldError("username", "用户被禁用");
			return "input";
		}
		else if(user.getUserType()==0){
			addFieldError("username", "用户未激活");
			return "input";
		}
		
		else {
			this.userid = Long.toString(user.getUserId());
			ActionContext.getContext().getSession().put("userid", this.userid);
			
			String username=user.getUserName();
			ActionContext.getContext().getSession().put("username", username);
			
			
			String userSpaceStyle=new GetThemeDaoImpl().getUserSpaceStyle(this.userid);//获取用户样式信息
			Style userStyle = new Style(userSpaceStyle);//处理样式信息，读入到hashMap
			
			String cssFile=userStyle.getAStyle("cssFile");
			String navColor=userStyle.getAStyle("navColor");
			String moduleColor=userStyle.getAStyle("moduleColor");
			String bgColor=userStyle.getAStyle("bgColor");
			String linkColor=userStyle.getAStyle("linkColor");
			
		    ActionContext.getContext().getSession().put("cssFile",cssFile);
			ActionContext.getContext().getSession().put("navColor",navColor);
			ActionContext.getContext().getSession().put("moduleColor",moduleColor);
			ActionContext.getContext().getSession().put("bgColor",bgColor);
			ActionContext.getContext().getSession().put("linkColor",linkColor);
			return "success";
		}
		
	}
	
}
