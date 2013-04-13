package com.kingway.dao.impl;

import java.util.Date;
import java.util.List;

import com.kingway.dao.GetModuleDao;
import com.kingway.model.ModuleInfo;
import com.kingway.model.MonitorByJsView;
import com.kingway.model.MonitorWebInfo;
import com.kingway.util.HibernateUtil;

public class GetModuleDaoImpl implements GetModuleDao {

	//��Ӧ����������test���µ�GetModuleTest,ͨ��������ɾ����ע��
	/**
	 * List���Ϊ�գ�����null;
	 * ���򷵻�����list:�������Ԫ�أ�0.webAddress,1.MonitorByJsView��¼��
	 */
	@SuppressWarnings("unchecked")
	public List<MonitorByJsView> getModule() {
		List<MonitorByJsView> mbjvList = HibernateUtil.list("from MonitorByJsView");
		if(mbjvList.size()!=0){
			//�������ϴμ��ʱ��Ϊ��ǰʱ��
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
