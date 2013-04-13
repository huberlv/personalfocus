package com.kingway.dao.impl;

import java.util.Date;
import java.util.List;

import com.kingway.dao.GetModuleDao;
import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorByJsView;
import com.kingway.model.MonitorWebInfo;
import com.kingway.util.HibernateUtil;

public class GetModuleDaoImpl implements GetModuleDao {

	//对应测试用例，test包下的GetModuleTest,通过测试请删除该注释
	/**
	 * List如果为空，返回null;
	 * 否则返回如下list:存放两个元素：0.webAddress,1.MonitorByJsView记录集
	 */
	@SuppressWarnings("unchecked")
	public List<MonitorByJsView> getModule() {
		List<MonitorByJsView> mbjvList = HibernateUtil.list("from MonitorByJsView");
		if(mbjvList.size()!=0){
			//先设置上次监控时间为当前时间
			ModuleInfo module = (ModuleInfo) HibernateUtil.get(ModuleInfo.class, mbjvList.get(0).getId().getModuleId());
			MonitorWebInfo webInfo = (MonitorWebInfo) HibernateUtil.get(MonitorWebInfo.class, module.getMonitorWebInfo().getWebId());
			webInfo.setLastMonitorTime(new Date());
			HibernateUtil.update(webInfo);
		}else{
			return null;
		}
		return mbjvList;	
	}

}
