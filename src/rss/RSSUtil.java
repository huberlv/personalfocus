package rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.kingway.util.PropertiesUtil;
/**
 * RSS工具类
 * @author Administrator
 *
 */
public class RSSUtil {
    private static final String RSS_DEMO_PATH=PropertiesUtil.getProperty("RSS_DEMO_PATH");
    public static final String DEFAULT_RSS_TITLE="/title/";
    /**
     * 以模块id创建一个RSS源码
     * @param moduleId
     * @return
     */
	public static RSS createANewRSS(String link,String rssContent){
		RSS rss=new RSS();
		
		if(rss.parseFile(RSSUtil.class.getResource(RSS_DEMO_PATH).getFile())==false){
			return null;
		}
		//未封装
		rss.setChannelContent(DEFAULT_RSS_TITLE, link, DEFAULT_RSS_TITLE, null, null, null, null, null, null, new Date(), null, new Date(), 20+"", null);
	    rss.addItems(rssContent,false);
		return rss;
	}
	
	
	/**
	 * 格式化日期为RSS 2.0格式
	 * @param date
	 * @return
	 */
	public static String formatRSSDate(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("EEE, dd MMM yyyy",Locale.ENGLISH);	
		return sf.format(date);
	}
}
