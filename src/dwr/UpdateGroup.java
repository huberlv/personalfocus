package dwr;

import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public class UpdateGroup {
	public String updateGroupName(Long subgroupId ,String newName){
		Class clazz = UserModuleSubgroupInfo.class;
		UserModuleSubgroupInfo u = (UserModuleSubgroupInfo) HibernateUtil.get(clazz, subgroupId);
		u.setGroupName(newName);
		HibernateUtil.update(u);
		return null;
	}
	
	public Long addGroup(String newName){
		String useridstr=(String) ActionContext.getContext().getSession().get("userid");
		Long userid = Long.parseLong(useridstr);
		UserModuleSubgroupInfo u = new UserModuleSubgroupInfo();
		u.setGroupName(newName);
		u.setGroupType(1);
		UserInfo userinfo = new UserInfo();
		userinfo.setUserId(userid);
		
		u.setUserInfo(userinfo);
		HibernateUtil.save(u);
		
		return u.getSubgroupId();
	}
}
