package com.kingway.action;

import java.util.LinkedList;
import java.util.List;
import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.dao.impl.MobileUserManagementDaoImpl;
import com.kingway.model.ContentNeedToBeSendView;
import com.kingway.model.UserInfo;
import com.kingway.model.struct.MobileModuel;
import com.kingway.model.struct.Moduel;
import com.kingway.util.OnlineFocus;
import com.opensymphony.xwork2.ActionContext;

/**
 * 手机登录后的页面
 * @author Administrator
 *
 */
public class ShowMobileUserIndexAction {
	private String userId;
	private String password;
	private String userName;

	public List<MobileModuel> moduels = new LinkedList<MobileModuel>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String showMobileUserIndex() {
		
		UserInfo user =null;

		if(userId==null){
			user=(UserInfo)ActionContext.getContext().getSession().get("userInfo");
		}
		if(user==null && userId!=null){
			user = new CheckLoginUserDaoImpl().check(userId, password);
		}
		if (user == null) 
		{        
			return "fail";
		} else { 
			this.userId=user.getUserId()+"";
			if(this.password==null)
			{
				this.password= (String)ActionContext.getContext().getSession().get("password");
			}
			if(ActionContext.getContext().getSession().get("userInfo")==null){
			   ActionContext.getContext().getSession().put("userInfo", user);
			   ActionContext.getContext().getSession().put("password",this.password);
			}
			List<ContentNeedToBeSendView> content = null;

			long UserModuleId = -1;
			String UserModuleName = "";
			long SubgroupId = -1;
			String WebsiteName = "";
			this.userName = user.getUserName();
			MobileUserManagementDaoImpl mb=new MobileUserManagementDaoImpl();
			content = mb.getUserModules(userId);

			MobileModuel current = null;
			Moduel currentModuel = null;
			for (int i = 0; i < content.size(); i++) {
				if (content.get(i).getId().getSubgroupId()!=SubgroupId) {
					SubgroupId = content.get(i).getId().getSubgroupId();
					WebsiteName = content.get(i).getId().getGroupName();
					current = new MobileModuel(WebsiteName);
					this.moduels.add(current);
				}
			
				if (content.get(i).getId().getUserModuleId() != UserModuleId&&
						((content.get(i).getId().getMonitorType()&OnlineFocus.MONITOR_BY_USER)!=OnlineFocus.MONITOR_BY_USER)) {

					UserModuleId = content.get(i).getId().getUserModuleId();
					UserModuleName = content.get(i).getId().getUserModuleName();
					currentModuel = new Moduel(UserModuleName, content.get(i).getId().getUserModuleId()
							+ "");
					if(content.get(i).getId().getContentType()==1){
						currentModuel.setClassss(OnlineFocus.UPDATESTYLE);
					}
					current.getModules().add(currentModuel);

				}

			} // end of for
		
		}

		return "success";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
