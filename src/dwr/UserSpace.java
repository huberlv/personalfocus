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
 * 用户空间及订阅相关操作
 * 
 * @author ZERO
 * @date 2010-10-02
 * 
 */
public class UserSpace {

	@SuppressWarnings("unchecked")
	Vector receives = null; // 存放user_default_receive_info的相关信息

	/**
	 * 检验用户账户和密码
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
	 * 订阅用户的某一模块 , 将某一模块信息写入自己的模块信息表中
	 * 
	 * @param subUserId
	 *            订阅者的用户id
	 * @param userModuleId
	 *            订阅的模块id
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
			// 写入用户模块信息表
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
			 * 写入用户接收信息表 由于receives存放的是{手机接收类型，邮件接收类型, UserDefaultReceiveInfo1,
			 * UserDefaultReceiveInfo2}， 所以从i=2开始
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
			return "订阅失败！模块已存在！";
		}
		return "订阅成功！";
	}

	/**
	 * 订阅用户现在所有模块,将所关注的空间模块信息写入自己的模块信息表中
	 * 
	 * @param subUserId
	 *            订阅者的用户id
	 * @param masterUserId
	 *            被订阅者的用户id
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
		return "订阅成功！";
	}

	/**
	 * 订阅用户的空间, 将订阅关系写入订阅表中
	 * 
	 * @param subUserId
	 *            订阅者的用户id
	 * @param masterUserId
	 *            被订阅者的用户id
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
			return "订阅失败！你已订阅该空间！";
		}
		return "订阅成功！";
	}

	/**
	 * 查询用户默认接收表信息
	 * 
	 * @param subUserId
	 *            订阅者的用户id
	 * @return Vector{手机接收类型，邮件接收类型, UserDefaultReceiveInfo1,
	 *         UserDefaultReceiveInfo2}
	 */
	@SuppressWarnings("unchecked")
	public Vector getDefaultReceiveInfo(long subUserId) {
		List<UserDefaultReceiveInfo> receives = HibernateUtil
				.list("From UserDefaultReceiveInfo where userId=" + subUserId);
		Vector info = new Vector();
		info.add(0); // 存放手机接收类型
		info.add(false); // 存放邮件接收类型
		int type;
		for (int i = 0; i < receives.size(); i++) {
			type = receives.get(i).getReceiveType();
			// 发送到手机
			if (type != 0 && type != 7) {
				info.set(0, type);
				info.add(receives.get(i));
			}
			// 发送到邮箱
			if (type == 7) {
				info.set(1, type == 0 ? false : true);
				info.add(receives.get(i));
			}
		}
		return info;
	}

	/**
	 * 获取订阅者的默认分组信息
	 * 
	 * @return
	 */
	public UserModuleSubgroupInfo getDefaultSubgroupInfo(long subUserId) {
		String hql = "From UserModuleSubgroupInfo where userId=" + subUserId
				+ " and groupType=3";
		return ((UserModuleSubgroupInfo) HibernateUtil.getRecord(hql));
	}

	/**
	 * 订阅用户的某些模块 , 将某模块信息写入自己的模块信息表中
	 * 
	 * @param subUserId
	 *            订阅者的用户id
	 * @param userModuleId
	 *            订阅的模块id
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

		return "订阅成功！";
	}
}
