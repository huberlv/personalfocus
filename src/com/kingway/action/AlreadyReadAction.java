package com.kingway.action;

import com.kingway.dao.impl.AlreadyReadActionDaoImpl;
/**
 * ���ֻ��ͻ��˽��ո�����Ϣ�� "֪����"������ʴ�action����content_receiverɾ�����û��Ŀͻ��˽��ռ�¼
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
