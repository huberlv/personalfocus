package dwr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 定义模块
 * @author ZERO
 * @date 2010-10-02
 *
 */
public class AddModuleInfoByManual{
	private static int timeoutMillis=1000*60*2;
   
	public String  addAModule(String urlstr, String css[], String websiteName,
			String userModuleName, String text1, int isMobile, boolean isMail,
			String messageType, String messageStartTime,
			String messageStopTime, String messageFrequency,
			String messageFrequencyType, String messageMaxNum,
			String emailStartTime, String emailStopTime, String emailFrequency,
			String emailFrequencyType, String path, boolean saveConfig,
			boolean useDefaultConfig, String style, int monitorType,
			Long subgroupId,String theight,String twidth) {
		URL url=null;
	    try {
			url=new URL(urlstr);
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "网址不正确或不可访问！";
		}
		Document document=null;
		try {
		    document=Jsoup.parse(url, timeoutMillis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "网址不可访问！";
		}
		css=new String[2];
		if(document.select("base").size()>0&&document.select("base").attr("href")!=null){
			css[0]=document.select("base").outerHtml();
		}else{
			css[0]="<base href='"+urlstr+"'/>";
		}
		css[0]=css[0]+document.select("head").html();
		//css[0]=css[0]+document.select("head script").outerHtml();
		css[1]=document.select("body style").outerHtml();
		css[1]=document.select("body link").outerHtml();
		css[1]=css[1]+document.select("body script").outerHtml();
		try{
		   document.select(path).addClass("defineModuleClass");
		}catch(Exception e){
			e.printStackTrace();
			return "特征不合法，请重新输入！";
		}
		text1=document.outerHtml();
		style="position: static;height:"+theight+";width:"+twidth+";top:3000px;left:0px;display:none;";	 
			
		return new AddModuleInfo().checkModuleInfo( urlstr,  css,  websiteName,
				 userModuleName,  text1,  isMobile,  isMail,
				 messageType,  messageStartTime,
				 messageStopTime,  messageFrequency,
				 messageFrequencyType,  messageMaxNum,
				 emailStartTime,  emailStopTime,  emailFrequency,
				 emailFrequencyType,  path,  saveConfig,
				 useDefaultConfig,  style,  monitorType,
				 subgroupId);	
	}
}
