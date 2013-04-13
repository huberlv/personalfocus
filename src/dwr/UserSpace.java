package dwr;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.model.UserDefaultReceiveInfo;
import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.UserReceiveInfo;
import com.kingway.model.UserSubscribe;
import com.kingway.util.HibernateUtil;

/**
 * �û��ռ估������ز���
 * 
 * @author ZERO
 * @date 2010-10-02
 * 
 */
public class UserSpace {

	@SuppressWarnings("unchecked")
	Vector receives = null; // ���user_default_receive_info�������Ϣ

	/**
	 * �����û��˻�������
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public String checkLogin(String userId, String password) {
		UserInfo userInfo = new CheckLoginUserDaoImpl().check(userId, password);
		if (userInfo != null) {
			return userInfo.getUserName();
		}
		return "fail";
	}

	/**
	 * �����û���ĳһģ�� , ��ĳһģ����Ϣд���Լ���ģ����Ϣ����
	 * 
	 * @param subUserId
	 *            �����ߵ��û�id
	 * @param userModuleId
	 *            ���ĵ�ģ��id
	 * @return
	 */
	public String subscribeAUserModule(long subUserId, long userModuleId) {
		try {
			if (receives == null)
				receives = getDefaultReceiveInfo(subUserId);
			UserModuleInfo owner = (UserModuleInfo) HibernateUtil.get(
					UserModuleInfo.class, userModuleId);
			UserModuleInfo wanter = new UserModuleInfo();
			wanter.setUserModuleStyle(owner.getUserModuleStyle().replaceAll(
					"absolute", "static"));
			UserInfo u = new UserInfo();
			u.setUserId(subUserId);  
			// д���û�ģ����Ϣ��
			wanter.setUserInfo(u);
			wanter.setUnCheckPaths(owner.getUnCheckPaths());
			wanter.setModuleInfo(owner.getModuleInfo());
			wanter.setWebsiteName(owner.getWebsiteName());
			wanter.setUserModuleSubgroupInfo(getDefaultSubgroupInfo(subUserId));
			wanter.setMonitorType(owner.getMonitorType());
			wanter.setEnable(owner.getEnable());
			wanter.setUserModuleName(owner.getUserModuleName());
			wanter.setUserModuleStyle(owner.getUserModuleStyle());
			HibernateUtil.save(wanter);
			/*
			 * д���û�������Ϣ�� ����receives��ŵ���{�ֻ��������ͣ��ʼ���������, UserDefaultReceiveInfo1,
			 * UserDefaultReceiveInfo2}�� ���Դ�i=2��ʼ
			 */
			for (int i = 2; i < receives.size(); i++) {
				UserDefaultReceiveInfo d = (UserDefaultReceiveInfo) receives
						.get(i);
				UserReceiveInfo r = new UserReceiveInfo();
				r.setStartTime(d.getStartTime());
				r.setStopTime(d.getStopTime());
				r.setReceiveType(d.getReceiveType());
				r.setLastSendTime(new Date());
				r.setMaxReceiveNum(d.getMaxReceiveNum());
				r.setSendFrequency(d.getSendFrequency());
				r.setUserModuleInfo(wanter);
				HibernateUtil.save(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ�ܣ�ģ���Ѵ��ڣ�";
		}
		return "���ĳɹ���";
	}

	/**
	 * �����û���������ģ��,������ע�Ŀռ�ģ����Ϣд���Լ���ģ����Ϣ����
	 * 
	 * @param subUserId
	 *            �����ߵ��û�id
	 * @param masterUserId
	 *            �������ߵ��û�id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String subscribeAllUserModule(long subUserId, long masterUserId) {
		List<UserModuleInfo> modules = HibernateUtil
				.list("From UserModuleInfo where userId=" + masterUserId +"and accessType=1");
		long userModuleId;
		for (int i = 0; i < modules.size(); i++) {
			userModuleId = modules.get(i).getUserModuleId();
			subscribeAUserModule(subUserId, userModuleId);
		}
		return "���ĳɹ���";
	}

	/**
	 * �����û��Ŀռ�, �����Ĺ�ϵд�붩�ı���
	 * 
	 * @param subUserId
	 *            �����ߵ��û�id
	 * @param masterUserId
	 *            �������ߵ��û�id
	 * @return
	 */
	public String subscribeUser(long subUserId, long masterUserId) {
		try {
			subscribeAllUserModule(subUserId, masterUserId);
			UserSubscribe subscribe = new UserSubscribe();
			UserInfo u1 = (UserInfo) HibernateUtil.get(UserInfo.class,
					subUserId);
			UserInfo u2 = (UserInfo) HibernateUtil.get(UserInfo.class,
					masterUserId);
			subscribe.setUserInfoBySubscribeUserId(u2);
			subscribe.setUserInfoByUserId(u1);
			HibernateUtil.save(subscribe);
		} catch (Exception e) {
			return "����ʧ�ܣ����Ѷ��ĸÿռ䣡";
		}
		return "���ĳɹ���";
	}

	/**
	 * ��ѯ�û�Ĭ�Ͻ��ձ���Ϣ
	 * 
	 * @param subUserId
	 *            �����ߵ��û�id
	 * @return Vector{�ֻ��������ͣ��ʼ���������, UserDefaultReceiveInfo1,
	 *         UserDefaultReceiveInfo2}
	 */
	@SuppressWarnings("unchecked")
	public Vector getDefaultReceiveInfo(long subUserId) {
		List<UserDefaultReceiveInfo> receives = HibernateUtil
				.list("From UserDefaultReceiveInfo where userId=" + subUserId);
		Vector info = new Vector();
		info.add(0); // ����ֻ���������
		info.add(false); // ����ʼ���������
		int type;
		for (int i = 0; i < receives.size(); i++) {
			type = receives.get(i).getReceiveType();
			// ���͵��ֻ�
			if (type != 0 && type != 7) {
				info.set(0, type);
				info.add(receives.get(i));
			}
			// ���͵�����
			if (type == 7) {
				info.set(1, type == 0 ? false : true);
				info.add(receives.get(i));
			}
		}
		return info;
	}

	/**
	 * ��ȡ�����ߵ�Ĭ�Ϸ�����Ϣ
	 * 
	 * @return
	 */
	public UserModuleSubgroupInfo getDefaultSubgroupInfo(long subUserId) {
		String hql = "From UserModuleSubgroupInfo where userId=" + subUserId
				+ " and groupType=3";
		return ((UserModuleSubgroupInfo) HibernateUtil.getRecord(hql));
	}

	/**
	 * �����û���ĳЩģ�� , ��ĳģ����Ϣд���Լ���ģ����Ϣ����
	 * 
	 * @param subUserId
	 *            �����ߵ��û�id
	 * @param userModuleId
	 *            ���ĵ�ģ��id
	 * @return
	 */
	public String subscribeUserModules(long subUserId, long userModuleId[]) {

		if (receives == null) {
			receives = getDefaultReceiveInfo(subUserId);
		}
		for (int j = 0; j < userModuleId.length; j++) {
			try {
				subscribeAUserModule(subUserId,userModuleId[j]);
			} catch (Exception e) {
                 e.printStackTrace();
			}
		}

		return "���ĳɹ���";
	}
}
