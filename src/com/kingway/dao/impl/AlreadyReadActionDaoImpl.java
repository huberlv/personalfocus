package com.kingway.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.kingway.dao.AlreadyReadActionDao;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleDisingoreView;
import com.kingway.util.HibernateUtil;
import com.kingway.util.MD5Util;

/**
 * ���ֻ��ͻ��˽��ո�����Ϣ�� "֪����"������ʴ�action����content_receiverɾ�����û��Ŀͻ��˽��ռ�¼
 * 
 * @author Administrator
 * 
 */
public class AlreadyReadActionDaoImpl implements AlreadyReadActionDao {

	@Override
	/**
	 * ��content_receiver��ɾ�����͵��ֻ��ն˵ļ�¼
	 */
	public void alreadyRead(long userId, String password) {
		UserInfo userInfo = (UserInfo) HibernateUtil
				.get(UserInfo.class, userId);
		if (userInfo == null)
			return;
		if (userInfo.getPassword().equals(MD5Util.getMD5(password.getBytes()))) {
			List<UserModuleDisingoreView> disingore=HibernateUtil
			.list("from UserModuleDisingoreView where userId=" + userId );
			List<Long> contentIds=new ArrayList<Long>();
			for(int i=0;i<disingore.size();i++)
			{
				contentIds.add(disingore.get(i).getId().getContentId());
			}
			List<UserModuleContents> view = HibernateUtil
					.batchGet(UserModuleContents.class, contentIds);
			for (UserModuleContents temp : view)
				{
				   temp.setIgnoreByUser(1);
				}
			HibernateUtil.batchUpdate(view);
		}
	}

}
