package rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.kingway.util.PropertiesUtil;
/**
 * RSS������
 * @author Administrator
 *
 */
public class RSSUtil {
    private static final String RSS_DEMO_PATH=PropertiesUtil.getProperty("RSS_DEMO_PATH");
    public static final String DEFAULT_RSS_TITLE="/title/";
    /**
     * ��ģ��id����һ��RSSԴ��
     * @param moduleId
     * @return
     */
	public static RSS createANewRSS(String link,String rssContent){
		RSS rss=new RSS();
		
		if(rss.parseFile(RSSUtil.class.getResource(RSS_DEMO_PATH).getFile())==false){
			return null;
		}
		//δ��װ
		rss.setChannelContent(DEFAULT_RSS_TITLE, link, DEFAULT_RSS_TITLE, null, null, null, null, null, null, new Date(), null, new Date(), 20+"", null);
	    rss.addItems(rssContent,false);
		return rss;
	}
	
	
	/**
	 * ��ʽ������ΪRSS 2.0��ʽ
	 * @param date
	 * @return
	 */
	public static String formatRSSDate(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("EEE, dd MMM yyyy",Locale.ENGLISH);	
		return sf.format(date);
	}
}
