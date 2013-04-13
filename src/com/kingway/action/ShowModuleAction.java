package com.kingway.action;

import com.kingway.dao.impl.ShowModuleDaoImpl;
import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;

public class ShowModuleAction {
	private Long userId;
	private String password;
	private Long moduleId;
	private int source;
	private String updatecontent;
    private String title;
	public String getUpdatecontent() {
		return updatecontent;
	}


	public void setUpdatecontent(String updatecontent) {
		this.updatecontent = updatecontent;
	}


	public int getSource() {
		return source;
	}


	public void setSource(int source) {
		this.source = source;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	public Long getModuleId() {
		return moduleId;
	}


	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}


	public String showModule(){
		ShowModuleDaoImpl showModuleDaoImpl=new ShowModuleDaoImpl();
		this.updatecontent =showModuleDaoImpl.showModule(this.userId,
				this.password, this.moduleId, this.source);
		if(updatecontent!=null){
			ActionContext.getContext().getSession().put("userid", this.userId);
			UserInfo user=showModuleDaoImpl.getUser();
			
			ActionContext.getContext().getSession().put("userInfo",user);
			ActionContext.getContext().getSession().put("password",this.password);
		}
		ModuleInfoShowView temp=showModuleDaoImpl.getModuleInfoShowView(moduleId);
		if(temp==null){
			this.title="互联网关注订阅平台";
		}else{
		    this.title=showModuleDaoImpl.getModuleInfoShowView(moduleId).getId().getWebsiteName();
		}
		return "success";
		
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle() {
		return title;
	}
}
