package com.kingway.dao.impl;

import java.util.Date;

import com.kingway.dao.AddUserDao;
import com.kingway.model.UserInfo;
import com.kingway.model.UserSpaceInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.MD5Util;

public class AddUserDaoImpl implements AddUserDao {

	@Override
	public Long adduser(String username, String password, String gender,
			String mailbox, String phone, Date birthday , int usertype) {
		
		password = MD5Util.getMD5(password.getBytes()); //����
		
		UserInfo user = new UserInfo();
		user.setUserName(username);
		user.setPassword(password);
		user.setGender(gender);
		user.setMailbox(mailbox);
		user.setPhone(phone);
		user.setBirthday(birthday);
		user.setUserType(usertype);
		HibernateUtil.save(user);
	
		//��Ӽ�¼���ռ��-------��û���ԣ��д�
		UserSpaceInfo userspaceinfo = new UserSpaceInfo();
		userspaceinfo.setUserInfo(user);
		userspaceinfo.setAccessType(3);
		userspaceinfo.setUserSpaceName(username+"�Ŀռ�");
		HibernateUtil.save(userspaceinfo);
	
		return user.getUserId();
	}

}
