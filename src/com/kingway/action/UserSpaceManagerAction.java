package com.kingway.action;

import com.kingway.model.UserSpaceInfo;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;
/**
 * ø’º‰–≈œ¢
 * @author Administrator
 *
 */
public class UserSpaceManagerAction {
	private String userSpaceName=null;
	private String accessType=null;
	private String question=null;
	private String answer=null;	
	public String getUserSpaceName() {
		return userSpaceName;
	}
	public void setUserSpaceName(String userSpaceName) {
		this.userSpaceName = userSpaceName;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String userSpaceInfo(){
		
		String idstr=(String) ActionContext.getContext().getSession().get("userid");
		Long id =Long.parseLong(idstr);
		
		UserSpaceInfo userSpaceInfo=(UserSpaceInfo) HibernateUtil.getRecord("from UserSpaceInfo where userId="+ id);
		
		userSpaceName =userSpaceInfo.getUserSpaceName();
		accessType=userSpaceInfo.getAccessType()+"";
		question=userSpaceInfo.getQuestion();
		answer=userSpaceInfo.getAnswer();
		return "success";
	}

}
