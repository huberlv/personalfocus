package com.kingway.dao;

import java.util.Date;

public interface UpdateUserInfoDao {

	public void updateuserinfo(Long id,String username,String phone,String mailbox,Date birthday);
	
}
