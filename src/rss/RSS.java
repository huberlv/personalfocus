package rss;
import java.io.File;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.kingway.util.OnlineFocus;
import com.kingway.util.PropertiesUtil;
/**
 * rss类
 * @author Administrator
 *
 */
public class RSS {
	private Element channel;
	private Document rss;
	private static final int MAX_RSS_ITEM_NUM=Integer.parseInt(PropertiesUtil
			.getProperty("MAX_RSS_ITEM_NUM"));
	/**
	 * 解析一个RSS文件
	 * @param path
	 * @return
	 */
	public boolean parseFile(String path) {
		SAXReader reader = new SAXReader();	
		try {
			rss = reader.read(new File(path));
			channel=rss.getRootElement().element("channel");
			return true;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 解析一段RSS字符串
	 * @param path
	 * @return
	 */
	public boolean parseStr(String rssContent) {
		try {
			rss=DocumentHelper.parseText(rssContent);
			channel=rss.getRootElement().element("channel");
			return true;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 设置频道属性
	 * @param title
	 * @param link
	 * @param description
	 * @param category
	 * @param cloud
	 * @param copyright
	 * @param docs
	 * @param generator
	 * @param language
	 * @param lastBuildDate
	 * @param managingEditor
	 * @param pubDate
	 * @param ttl
	 * @param webMaster
	 */
	public void setChannelContent(String title,String link, String description,
			String category, String cloud, String copyright, String docs,
			String generator, String language, Date lastBuildDate,
			String managingEditor, Date pubDate, String ttl,
			String webMaster) {
		Element temp=null;
		temp=channel.element("title");
		
		if(temp!=null&&title!=null){
			temp.setText(title);
		}
		temp=channel.element("link");
		if(temp!=null&&link!=null){
			temp.setText(link);
		}
		temp=channel.element("description");
		if(temp!=null&&description!=null){
			temp.setText(description);
		}
		temp=channel.element("category");
		if(temp!=null&&category!=null){
			temp.setText(category);
		}
		temp=channel.element("cloud");
		if(temp!=null&&cloud!=null){
			temp.setText(cloud);
		}
		temp=channel.element("copyright");
		if(temp!=null&&copyright!=null){
			temp.setText(copyright);
		}
		temp=channel.element("docs");
		if(temp!=null&&docs!=null){
			temp.setText(docs);
		}
		temp=channel.element("generator");
		if(temp!=null&&generator!=null){
			temp.setText(generator);
		}
		temp=channel.element("language");
		if(temp!=null&&language!=null){
			temp.setText(language);
		}
		temp=channel.element("lastBuildDate");
		if(temp!=null&&lastBuildDate!=null){
			temp.setText(RSSUtil.formatRSSDate(lastBuildDate));
		}
		temp=channel.element("managingEditor");
		if(temp!=null&&managingEditor!=null){
			temp.setText(managingEditor);
		}
		temp=channel.element("pubDate");
		if(temp!=null&&pubDate!=null){
			temp.setText(RSSUtil.formatRSSDate(pubDate));
		}
		temp=channel.element("ttl");
		if(temp!=null&&ttl!=null){
			temp.setText(ttl);
		}
		temp=channel.element("webMaster");
		if(temp!=null&&webMaster!=null){
			temp.setText(webMaster);
		}
	}
	
	/**
	 * 增加一个item
	 * @param title
	 * @param link
	 * @param description
	 * @param guid
	 * @param pubDate
	 */
	public void addItem(String title,String link, String description,String guid,Date pubDate){
		Element item=channel.addElement("item");
		if(title!=null){
		   Element title_E =item.addElement("title");
		   title_E.setText(title);
		}
		if(link!=null){
			   Element temp =item.addElement("link");
			   temp.setText(link);
		}
		if(description!=null){
			   Element temp =item.addElement("description");
			   temp.setText(description);
		}
		if(guid!=null){
			   Element temp =item.addElement("guid");
			   temp.setText(guid);
		}
		if(pubDate!=null){
			   Element temp =item.addElement("pubDate");
			   temp.setText(RSSUtil.formatRSSDate(pubDate));
		}		
	}
	
	/**
	 * 返回RSS源码
	 * @return
	 */
	public String getXML(){
		if(rss!=null){
		  return rss.asXML();
		}else{
			return null;
		}
	}
	
	/**
	 * 检查RSS文件是否超过最大限制值
	 */
	private void checkRSSSize(){
		if(channel!=null){
			List items=channel.elements("item");
			while(items.size()>MAX_RSS_ITEM_NUM){
				items.remove(0);
			}
		}
	}
	
	/**
	 * 从更新本中增加RSS items
	 * @param contentBuffer
	 */
	public  boolean addItems(String content,boolean onlyAddUpdate){
		Elements links=Jsoup.parse(content).select("a");
		if(onlyAddUpdate==true){
			links=links.select("."+OnlineFocus.UPDATESTYLE);
		}
		for(int i=0;i<links.size();i++){
			if(links.get(i).hasAttr("href")){
				addItem(links.get(i).text(), links.get(i).attr("href"), links.get(i).text(), links.get(i).text().hashCode()+""+links.get(i).attr("href").hashCode(), new Date());
			}
		}
		checkRSSSize();
		return true;
	}
}
