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
		return "����ɹ���";
	}
	public String deleteUserModule(String userModuleId) {
		UpdateUserModuleInfoDaoImpl userModule = new UpdateUserModuleInfoDaoImpl();
		userModule.delUserModule(userModuleId);
		return "ģ����ɾ����";
	}
	
    public String hideUserModuel(long userModuleId){
    	new UpdateUserModuleInfoDaoImpl().hideUserModuel(userModuleId);	
		return "���سɹ�,���ڶ��Ĺ���������ʾ��ģ�飡";
	}
    /**
     * �����壬����unCheckPaths�ֶ�
     * @param userModuleId
     * @param paths
     * @return
     */
    public String updateUnCheckPaths(long userModuleId,String paths){

     	new UpdateUserModuleInfoDaoImpl().updateUnCheckPaths(userModuleId, paths);
    	return "����ɹ���";  	
    }
    
    public String exit(){
    	Object obj=ActionContext.getContext().getSession().get("userid");
    	if(obj==null){
    		return "�ɹ��˳���";
    	}else{
    		ServletActionContext.getRequest().getSession().invalidate();
        	return "�ɹ��˳���";
    	}
    	
    	
    }
    
    public String exitCheck(){
    	Object obj=ActionContext.getContext().getSession().get("userid");
    	if(obj==null){
    		return "";
    	}else{
    		
    		ActionContext.getContext().getSession().remove("checking");
        	return "�ɹ��˳���";
    	}
    	
    	
    }
}
