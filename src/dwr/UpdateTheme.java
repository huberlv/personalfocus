package dwr;

import moduledefine.Style;

import com.kingway.dao.impl.GetThemeDaoImpl;
import com.kingway.util.HibernateUtil;
import com.opensymphony.xwork2.ActionContext;

public class UpdateTheme {
	public String updateTheme(String cssFile ,String navColor , String moduleColor , String bgColor,String linkColor ){
		
		String id= (String) ActionContext.getContext().getSession().get("userid");
		Long userId = Long.parseLong(id);

		String userSpaceStyle=new GetThemeDaoImpl().getUserSpaceStyle(id);//获取用户样式信息
		Style userStyle = new Style(userSpaceStyle);//处理样式信息，读入到hashMap
		
		userStyle.setAStyle("cssFile", cssFile);
		userStyle.setAStyle("navColor", navColor);
		userStyle.setAStyle("moduleColor", moduleColor);
		userStyle.setAStyle("bgColor", bgColor);
		userStyle.setAStyle("linkColor", linkColor);
		
		String newStyle = userStyle.getStyleStr();
		HibernateUtil.updateRecord("update UserSpaceInfo set userSpaceStyle ='"+newStyle+"' where userId ="+userId);
		
		ActionContext.getContext().getSession().put("cssFile",cssFile);
		ActionContext.getContext().getSession().put("navColor",navColor);
		ActionContext.getContext().getSession().put("moduleColor",moduleColor);
		ActionContext.getContext().getSession().put("bgColor",bgColor);
		ActionContext.getContext().getSession().put("linkColor",linkColor);
		
		return "保存成功";
		
	}
}
