package com.kingway.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kingway.dao.CheckUsernameDao;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;

public class CheckUsernameDaoImpl implements CheckUsernameDao {

	Session s=null;
	Transaction tx=null;
	
	@Override
	public String check(String name) {
		try{
			s=HibernateUtil.getSession();
			tx=s.beginTransaction();
			String hql="from UserInfo as ui where ui.username=:n";
			Query q= s.createQuery(hql);
			q.setString("n", name);
			UserInfo u=(UserInfo) q.uniqueResult();
			if(u!=null)
				return u.getUserName();
			else return null;
		}
		finally{
			if(s!=null)
				s.close();
		}
	}

}
