package dwr;

import com.opensymphony.xwork2.ActionContext;
import com.kingway.model.UserInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.MD5Util;

public class UpdatePsw {
    public String updatePsw(String currentPsw,String newPsw,String pswConfirm){
    	String userIdStr=(String) ActionContext.getContext().getSession().get("userid");
    	Long userId=Long.parseLong(userIdStr);
    	Class clazz = UserInfo.class;
    	UserInfo userInfo =(UserInfo) HibernateUtil.get(clazz,userId);
    	String MD5Psw=userInfo.getPassword();
    	currentPsw=MD5Util.getMD5(currentPsw.getBytes()); //��ȡ�õ����룬���ܺ������ݿ��еıȽ�
    	if(!currentPsw.equals(MD5Psw)){
    		return "���벻��ȷ";
    	}
    	else if(!newPsw.equals(pswConfirm)){
    		return "������������벻һ��";
    	}
    	else{
    		userInfo.setPassword(MD5Util.getMD5(newPsw.getBytes()));
    		HibernateUtil.update(userInfo);
    		return "�޸ĳɹ�";
    	}
    }
}
