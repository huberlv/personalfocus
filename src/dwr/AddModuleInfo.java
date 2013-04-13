package dwr;

import net.URLS;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.kingway.dao.impl.AddMonitorDaoImpl;
import com.kingway.dao.impl.ManageDefaultReceiveInfoDaoImpl;
import com.kingway.util.LhpUtil;
import com.kingway.util.OnlineFocus;
import com.kingway.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * ����ģ��
 * @author ZERO
 * @date 2010-10-02
 *
 */
public class AddModuleInfo{
	public String text;
	public String path;
	public long userId;
	public long userModuleId;
	public long moduleId;
	public String url;
	public static final String DEFINE_MODULE_CLASS="<link href='"+URLS.getHostAddress()+"/personalfocus/css/userindexcss/defineModuleClass.css' rel='stylesheet' type='text/css'/>";
	private static String base=PropertiesUtil.getProperty("baseurl");
	public String checkModuleInfo(String urlstr, String css[], String websiteName,
			String userModuleName, String text1, int isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType, String path, boolean saveConfig,
			boolean useDefaultConfig, String style, int monitorType,
			Long subgroupId) {
		
		Document document=Jsoup.parseUnTransform(text1);
		LhpUtil.removeDivs(document);
	    Elements ups=document.getElementsByTag("head");
	    if(ups.size()>0){
	    	ups.get(0).append(DEFINE_MODULE_CLASS);
	    }
		text1=document.outerHtml();
		Elements elements=document.select(".defineModuleClass");
		this.url = LhpUtil.getSourceURL(urlstr);
		StringBuilder temp=new StringBuilder();
		for(int i=0;i<elements.size();i++){
			LhpUtil.cureLink(elements.get(i), url);
			temp.append(elements.get(i).outerHtml());
			temp.append("<br>");
		}
		
		this.text = temp.toString().replaceAll(AddModuleInfo.base+"/personalfocus/user/showWebPage\\?olpfsIsIframe=1&amp;url=","")
		.replaceAll(AddModuleInfo.base+"/personalfocus/user/showWebPage\\?olpfsIsIframe=0&amp;url=","");
		
		this.path = path; 
		AddMonitorDaoImpl addMonitorDaoImpl = new AddMonitorDaoImpl();
		try {
			userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
		} catch (Exception e) {
			return "��¼�������ʱ�������µ�¼��";
		}
		/* �Ƿ񱣴�ΪĬ�Ͻ������� */
		if (saveConfig) {
			boolean mobile = false;
			if (isMobile == 1)
				mobile = true;
			new ManageDefaultReceiveInfoDaoImpl().updateDefault(userId, mobile,
					messageType, messageStartTime, messageStopTime,
					messageFrequency, messageFrequencyType, messageMaxNum,
					isMail, emailStartTime, emailStopTime, emailFrequency,
					emailFrequencyType);
		}
		/* �����û�ģ����Ϣ */
		addMonitorDaoImpl.saveModule(url, path, this.text,text1, userId, websiteName,
				userModuleName, css, style, monitorType, subgroupId);
		/* �����û�������Ϣ */
		if (useDefaultConfig) {
			addMonitorDaoImpl.copyDefaultAsUserReceive(userId);
		} else {
			addMonitorDaoImpl.saveReceive(isMobile, isMail, messageType,
					messageStartTime, messageStopTime, messageFrequency,
					messageFrequencyType, messageMaxNum, emailStartTime,
					emailStopTime, emailFrequency, emailFrequencyType);
		}
		if(((monitorType&OnlineFocus.MONITOR_BY_USER)==OnlineFocus.MONITOR_BY_USER)){
			return "���ĳɹ�";
		}
		if(addMonitorDaoImpl.isNewUserModule()==false){
			return "�Ѵ��ڶ��Ĺ�ע�����³ɹ���";
		}
		moduleId = addMonitorDaoImpl.getModule().getModuleId();
		userModuleId = addMonitorDaoImpl.getUserModule().getUserModuleId();
		
		
		// ���¶�����Ϣ
		addMonitorDaoImpl.copyUserModuleToSubUsers(this.userId, this.userModuleId);
		
		return "���ĳɹ�";
	}
}
