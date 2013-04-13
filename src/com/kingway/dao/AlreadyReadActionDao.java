package com.kingway.dao;
/**
 * 供手机客户端接收更新信息后按 "知道了"，后访问此action，从content_receiver删除此用户的客户端接收纪录
 * 
 * @author Administrator
 *
 */
public interface AlreadyReadActionDao {
	
   public void alreadyRead(long userId,String passWord);
}
