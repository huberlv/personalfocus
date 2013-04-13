package dwr;

import java.util.Date;
import java.util.List;
import com.kingway.model.CheckUpdateUserModuleView;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;
/**
 * 吕鸿佩
 * @author Administrator
 *
 */
public class CheckUpdateUserModule {
    public long[] checkUpdate(String userId){//未封装
    	Object userid=ActionContext.getContext().getSession().get("userid");
		if (userid == null) {
			return new long[]{-1};
		}
		final String time=new Date().getTime()+"";
		ActionContext.getContext().getSession().put("checking",time);
		
		for (int j=0; (j<20)
				&& (ActionContext.getContext().getSession().get("checking")!=null)
				&&(ActionContext.getContext().getSession().get("checking").equals(time));) {
//			System.out.println((ActionContext.getContext().getSession().get("checking").equals(time)));
			// 延长session时间
			List<CheckUpdateUserModuleView> checkUpdateUserModule = getUpdateUserModule(userId);
			//System.out.println("checkUpdateUserModule.size()--2:"+checkUpdateUserModule.size());
			if (checkUpdateUserModule.size() == 0) {
//				System.out.println("暂无更新：" + Thread.currentThread());
				try {
					Thread.sleep(60000);   
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 睡眠一分钟
			} else {
				long uid[] = new long[checkUpdateUserModule.size()];
				for (int i = 0; i < checkUpdateUserModule.size(); i++) {
					uid[i] = checkUpdateUserModule.get(i).getId()
							.getUserModuleId();
				}
				//System.out.println("返回："+uid);
				return uid;
			}
		}
		//System.out.println(Thread.currentThread()+"结束");
		return new long[]{0};
    }
    /**
     * 取得不关注的特征
     * @param userModuleId
     * @return
     */
    public String getUncheckPath(String userModuleId){
    	Long id=null;
    	try{
    		id=Long.parseLong(userModuleId);
    	}catch (Exception e) {
			// TODO: handle exception
    		return "null";
		}
    	UserModuleInfo userModuleInfo =(UserModuleInfo) HibernateUtil.get(UserModuleInfo.class, id);
        if(userModuleInfo==null){
        	return "null";
        }else{
        	return userModuleInfo.getUnCheckPaths().replaceAll("/", ">").replaceAll("\\[", ":eq(").replaceAll("\\]", ")");
        }
    }
    
    private List<CheckUpdateUserModuleView> getUpdateUserModule(String userId){
    	List<CheckUpdateUserModuleView> checkUpdateUserModule = HibernateUtil
		.list("from CheckUpdateUserModuleView where userId="
				+ userId+" and ignoreByUser=0");
    	//System.out.println("checkUpdateUserModule.size()--1:"+checkUpdateUserModule.size());
        for(CheckUpdateUserModuleView list:checkUpdateUserModule){
			
				UserModuleContents userModuleContents=(UserModuleContents) HibernateUtil.get(UserModuleContents.class,list.getId().getContentId());
				userModuleContents.setIgnoreByUser(1);
				//System.out.println("userModuleContents.getContentId()"+userModuleContents.getContentId());
				HibernateUtil.update(userModuleContents);
			
		} 
    	return checkUpdateUserModule;
    }
}
