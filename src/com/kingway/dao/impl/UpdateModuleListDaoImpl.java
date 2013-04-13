package com.kingway.dao.impl;

import java.util.List;

import com.kingway.dao.UpdateModuleListDao;
import com.kingway.model.UserModuleInfo;
import com.kingway.util.HibernateUtil;

public class UpdateModuleListDaoImpl implements UpdateModuleListDao {

	@SuppressWarnings("unchecked")
	@Override
	public void updatemodulelist(List<Long> enable, List<Long> disable) {
		 Class clazz =UserModuleInfo.class;
		 for (Long id1 : enable) {
			 UserModuleInfo u= (UserModuleInfo) HibernateUtil.get(clazz, id1);
			 u.setAccessType(1);
			 HibernateUtil.update(u);
		  }

		 for (Long id2 : disable) {
			 UserModuleInfo u= (UserModuleInfo) HibernateUtil.get(clazz, id2);
			 u.setAccessType(2);
			 HibernateUtil.update(u);
		  }
	}

}
