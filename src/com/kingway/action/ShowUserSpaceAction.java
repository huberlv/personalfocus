package com.kingway.action;

import java.util.ArrayList;
import java.util.List;

import moduledefine.Style;

import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.dao.impl.GetThemeDaoImpl;
import com.kingway.dao.impl.ShowMyFocusDaoImpl;
import com.kingway.dao.impl.ShowUserSpaceDaoImpl;
import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.UserSpaceInfo;
import com.kingway.model.struct.UserModuleGroup;

public class ShowUserSpaceAction {
	
	private String userId;
	private String userName;
	private String masterUserId;
	private String userSpaceName;
	private String password;
	private String question;
	private List<UserModuleGroup> focusList;
	private List<UserModuleSubgroupInfo> userModuleSubgroupInfo;
	private String cssFile;
	private String navColor;
	private String moduleColor;
	private String bgColor;
	private String linkColor;

	private String answer;
	private String wrongMessage;
		
	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}

	public String getCssFile() {
		return cssFile;
	}

	public void setCssFile(String cssFile) {
		this.cssFile = cssFile;
	}

	public String getNavColor() {
		return navColor;
	}

	public void setNavColor(String navColor) {
		this.navColor = navColor;
	}

	public String getModuleColor() {
		return moduleColor;
	}

	public void setModuleColor(String moduleColor) {
		this.moduleColor = moduleColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUserSpaceName(String userSpaceName) {
		this.userSpaceName = userSpaceName;
	}

	public String getUserSpaceName() {
		return userSpaceName;
	}

	public void setMasterUserId(String masterUserId) {
		this.masterUserId = masterUserId;
	}

	public String getMasterUserId() {
		return masterUserId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}
	

	@SuppressWarnings("unchecked")
	public List getFocusList() {
		return focusList;
	}

	@SuppressWarnings("unchecked")
	public void setFocusList(List focusList) {
		this.focusList = focusList;
	}

	public String myfocus() {
		// 取得分组
		ShowMyFocusDaoImpl showMyFocusDaoImpl = new ShowMyFocusDaoImpl();
		List<ModuleInfoShowView> vlist = showMyFocusDaoImpl.showmyfocus(Long.parseLong(masterUserId),false);
		userModuleSubgroupInfo = showMyFocusDaoImpl.showUsrGroup(Long.parseLong(this.masterUserId));
		focusList = new ArrayList<UserModuleGroup>();
		for (int h = 0; h < userModuleSubgroupInfo.size(); h++) {
			focusList.add(new UserModuleGroup(userModuleSubgroupInfo.get(h).getGroupName(), 
					userModuleSubgroupInfo.get(h).getSubgroupId()));
		}
		for (int i = 0; i < focusList.size(); i++) {
			for (int j = 0; j < vlist.size(); j++) {
				if (vlist.get(j).getId().getSubgroupId() == focusList.get(i)
						.getGroupId()) {
					focusList.get(i).getViewList().add(vlist.get(j));
				}
			}
		}
		return "success";
	}
	
	/**
	 * 
	 * @return
	 */
	public String showUserSpace() {
	
		ShowUserSpaceDaoImpl userSpaceDaoImpl = new ShowUserSpaceDaoImpl();
		UserSpaceInfo userSpaceInfo = userSpaceDaoImpl.getUserSpaceInfo(Long.parseLong(this.masterUserId));
		if (userSpaceInfo == null) {
			return "wrong";
		}
		if (this.userId != null && this.password!=null) {
			UserInfo userInfo = new CheckLoginUserDaoImpl().check(userId, password);
			if( userInfo !=null){
			   this.userName = userInfo.getUserName();		
			}
		}
		if (this.userName == null) {
			this.userName = "游客";
		}
		int access = userSpaceInfo.getAccessType();
		if (access == 0) {//仅主人可访问
			return "onlyOwnerAccess";
		}  else if (access == 1) {
			
			return "onlyFriendAccess";
		} else if ((access == 3)||(access == 2)){//公开访问
			this.question=userSpaceInfo.getQuestion();
		    if (access == 2) {//问题访问
					if(answer==null){
						return "userQuestion";
					}else if(answer.trim().equals(userSpaceInfo.getAnswer())==false){
						setWrongMessage("答案错误，请重新输入或联系主人");
						return "userQuestion";
					}
			}
			myfocus();
			this.userSpaceName = userSpaceInfo.getUserSpaceName();
			String userSpaceStyle=new GetThemeDaoImpl().getUserSpaceStyle(this.masterUserId);//获取用户样式信息
			Style userStyle = new Style(userSpaceStyle);//处理样式信息，读入到hashMap
			
			this.cssFile=userStyle.getAStyle("cssFile");
			this.navColor=userStyle.getAStyle("navColor");
			this.moduleColor=userStyle.getAStyle("moduleColor");
			this.bgColor=userStyle.getAStyle("bgColor");
			this.linkColor=userStyle.getAStyle("linkColor");
			
			return "access";
		} else {
			return "wrong";
		}	
		
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setWrongMessage(String wrongMessage) {
		this.wrongMessage = wrongMessage;
	}

	public String getWrongMessage() {
		return wrongMessage;
	}

}
