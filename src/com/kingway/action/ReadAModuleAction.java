package com.kingway.action;

import com.kingway.model.UserModuleContents;
import com.kingway.util.HibernateUtil;
import com.kingway.util.OnlineFocus;
import com.opensymphony.xwork2.ActionContext;

public class ReadAModuleAction {

    public String userModuleId;
    public String readAModule(){
   /* 	String idstr= (String) ActionContext.getContext().getSession().get("userid");//Î´·â×°
		if(idstr==null){
			return "fail";		
		}*/
        Object obj=ActionContext.getContext().getSession().get("userid");
        if(obj==null){
        	return "fail";
        }
    	UserModuleContents userModuleContents;
    	String userModuleIds[]=userModuleId.split(",");
    	Object temp = null;
    	for(int i=0;i<userModuleIds.length;i++){
		
    	temp = HibernateUtil.getRecord("from UserModuleContents where userModuleId="+userModuleIds[i]);
		if (temp != null) {
			userModuleContents = (UserModuleContents) temp;
			if (userModuleContents.getContentType() != 0) {
				userModuleContents.setContentType(0);
				userModuleContents.setIgnoreByUser(1);
				userModuleContents.setAlreadySendMail(1);
				userModuleContents.setAlreadySendMessage(1);
				
				userModuleContents.setUserModuleContent(userModuleContents
						.getUserModuleContent().replaceAll(OnlineFocus.UPDATESTYLE,
								""));
				HibernateUtil.update(userModuleContents);
			}
		}
    	}
		return "success";
    }
}
