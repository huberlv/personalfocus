package com.kingway.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kingway.dao.CheckLoginUserDao;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.MD5Util;

public class CheckLoginUserDaoImpl implements CheckLoginUserDao {
	Session s = null;
	Transaction tx = null;

	@Override
	public UserInfo check(String userid, String password) {
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			String hql = "from UserInfo as ui where ui.id=:n";
			Query q = s.createQuery(hql);
			q.setString("n", userid);
			UserInfo user = (UserInfo) q.uniqueResult();
			tx.commit();
			if (user == null)
				return null;
			else if (user.getPassword().equals(MD5Util.getMD5(password.getBytes())))
				return user;
			else
				return null;
		}
		finally {
			if (s != null)
				s.close();
		}
	}
	
}
