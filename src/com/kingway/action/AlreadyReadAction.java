package com.kingway.action;

import com.kingway.dao.impl.AlreadyReadActionDaoImpl;
/**
 * 供手机客户端接收更新信息后按 "知道了"，后访问此action，从content_receiver删除此用户的客户端接收纪录
 * 
 * @author Administrator
 *
 */
public class AlreadyReadAction {
    private long userId;
    private String passWord;
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getUserId() {
		return userId;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPassWord() {
		return passWord;
	}
	
	public String alreadyRead(){
		new AlreadyReadActionDaoImpl().alreadyRead(userId, passWord);
		return "success";
	}
}
