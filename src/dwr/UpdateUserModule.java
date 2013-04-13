package dwr;

import org.apache.struts2.ServletActionContext;

import com.kingway.dao.impl.UpdateUserModuleInfoDaoImpl;
import com.opensymphony.xwork2.ActionContext;

public class UpdateUserModule {
	public String updateStyle(String style[], String userModuleId[]) {
		UpdateUserModuleInfoDaoImpl userModule = new UpdateUserModuleInfoDaoImpl();
		for (int i = 0; i < style.length; i++) {
			userModule.updateUserModuleStyle(style[i], userModuleId[i]);
		}
		return "保存成功！";
	}
	public String deleteUserModule(String userModuleId) {
		UpdateUserModuleInfoDaoImpl userModule = new UpdateUserModuleInfoDaoImpl();
		userModule.delUserModule(userModuleId);
		return "模块已删除！";
	}
	
    public String hideUserModuel(long userModuleId){
    	new UpdateUserModuleInfoDaoImpl().hideUserModuel(userModuleId);	
		return "隐藏成功,可在订阅管理重新显示该模块！";
	}
    /**
     * 吕鸿佩，保存unCheckPaths字段
     * @param userModuleId
     * @param paths
     * @return
     */
    public String updateUnCheckPaths(long userModuleId,String paths){

     	new UpdateUserModuleInfoDaoImpl().updateUnCheckPaths(userModuleId, paths);
    	return "保存成功！";  	
    }
    
    public String exit(){
    	Object obj=ActionContext.getContext().getSession().get("userid");
    	if(obj==null){
    		return "成功退出！";
    	}else{
    		ServletActionContext.getRequest().getSession().invalidate();
        	return "成功退出！";
    	}
    	
    	
    }
    
    public String exitCheck(){
    	Object obj=ActionContext.getContext().getSession().get("userid");
    	if(obj==null){
    		return "";
    	}else{
    		
    		ActionContext.getContext().getSession().remove("checking");
        	return "成功退出！";
    	}
    	
    	
    }
}
