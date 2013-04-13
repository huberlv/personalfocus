package com.kingway.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kingway.dao.CheckLoginManagementDao;
import com.kingway.model.ManagerInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.MD5Util;

public class CheckLoginManagementDaoImpl implements CheckLoginManagementDao {
	Session s = null;
	Transaction tx = null;

	@Override
	public ManagerInfo check(String userid, String password) {
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			String hql = "from ManagerInfo as mi where mi.managerId=:n";
			Query q = s.createQuery(hql);
			q.setString("n", userid);
			ManagerInfo user = (ManagerInfo) q.uniqueResult();
			
			if(user==null){
				return null;
			}else if(user.getManagerPsw().equals(MD5Util.getMD5(password.getBytes()))){
				return user;
			}
			else return null;

		}finally {

			if(s!=null)
				s.close();
		}

	}

}
