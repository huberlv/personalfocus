package dwr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.kingway.model.ModuleInfo;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.opensymphony.xwork2.ActionContext;

public class ModifyModulePath {
   public String modifyModulePath(long moduleId,String path,String source){
	   System.out.println("path:"+path);
	    Object userid=ActionContext.getContext().getSession().get("mangerId");
		if(userid==null){
			return "������ʱ��";
		}
		Object obj=HibernateUtil.get( ModuleInfo.class,moduleId);
		if(obj==null){
			return "ģ�鲻���ڣ�";
		}
		ModuleInfo moduleInfo=(ModuleInfo)obj;
		moduleInfo.setModulePath(path);
		moduleInfo.setFailTimes(0);
		//////
		Document document=Jsoup.parseUnTransform(source);
		LhpUtil.removeDivs(document);
	    Elements ups=document.getElementsByTag("head");
	    if(ups.size()>0){
	    	ups.get(0).append(AddModuleInfo.DEFINE_MODULE_CLASS);
	    }
	    source=document.outerHtml();
		//////////
		moduleInfo.setDefineSource(source);
		HibernateUtil.update(moduleInfo);
	   return "�޸ĳɹ�";
   }
}
