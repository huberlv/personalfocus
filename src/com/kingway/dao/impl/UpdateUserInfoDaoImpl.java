package com.kingway.dao.impl;

import java.util.Date;

import com.kingway.dao.UpdateUserInfoDao;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;

public class UpdateUserInfoDaoImpl implements UpdateUserInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public void updateuserinfo(Long id, String username, String phone,
			String mailbox, Date birthday) {
		Class clazz=UserInfo.class;
		UserInfo ui=(UserInfo) HibernateUtil.get(clazz, id);
		ui.setBirthday(birthday);
		ui.setMailbox(mailbox);
		ui.setPhone(phone);
		ui.setUserName(username);
		HibernateUtil.update(ui);

	}

}
