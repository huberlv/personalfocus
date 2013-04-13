package com.kingway.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import net.URLS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import document.MyHtmlParser;


public class LhpUtil {
    
	/**
	 * 从指定文本中去掉不检查的文本
	 * 
	 * @param content
	 * @param paths
	 * @return
	 */
	public static String getTrimUncheckContent(String content, String paths) {
		if (paths == null)
			return content;

		Document document = Jsoup.parse(content);
		trimUnCheckPaths(document,paths);
		if (document.select("."+OnlineFocus.UPDATESTYLE).size() < 1) {
			return null;

		} else {
			return LhpUtil.getBodyString(document);
		}
	}

	/**
	 * 由以,号分隔的成组的特征取得特征数组
	 * 
	 * @param path
	 * @return
	 */
	public static ArrayList<String> getPaths(String path) {
		ArrayList<String> paths = new ArrayList<String>();
		int begin = 0, end = 0;
		end = path.indexOf(',', begin);
		while (end >= 0) {
			paths.add(path.substring(begin, end));
			begin = end + 1;
			end = path.indexOf(',', begin);
		}
		paths.add(path.substring(begin, path.length()));
		return paths;
	}

	/** 用于提取孤立文字节点的正则表达式 
	private static final String change = ">([^<^\\s]+)<[^/]{1}";

	/**
	 * 将xml文档中的孤立文字加上text标签，让其成为叶子节点
	 * 
	 * @param page
	 *            网页对象的根节点
	 * @param cleaner
	 *            HtmlCleaner，用于获取其属性
	 * @return 经处理后的page对象的根节点
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 *
	public static String getTransformString(String source) {

		Pattern changePattern = Pattern.compile(change);
		StringBuilder str = new StringBuilder(source);
		Matcher matcher = changePattern.matcher(str);
		ArrayList<Location> vector = new ArrayList<Location>(20);
		while (matcher.find()) {
			if (matcher.group(1).trim().length() < 2) {
				continue;
			} else {
				vector
						.add(new Location(matcher.start(1), matcher.end(1),
								null));
			}

		}
		int sum = 0;
		for (int i = 0; i < vector.size(); i++) {
			str.insert(vector.get(i).begin + sum, "<font>");
			sum = sum + 6;
			str.insert(vector.get(i).end + sum, "</font>");
			sum = sum + 7;
		}

		return new String(str);
	}
*/
	public static final String srcstr = "src";
	public static String href = "href";

	/**
	 * 将此结点下的链接改为绝对链接
	 * 
	 * @param tag
	 * @throws java.net.MalformedURLException
	 */
	public static void cureLink(Element root, String base) {

		Elements children = root.getElementsByTag("a");
		String attr = null;
		Element tag = null;
		for (int i = 0; i < children.size(); i++) {
			
			tag = children.get(i);
			if (tag.hasAttr(href) == true) {
				// 不是空连接或锚点连接
				attr = tag.attr(href);
				if (attr.length() > 0) {
					// 删掉javascript:void(0)死连接
					if (attr.indexOf("javascript:") < 0) {
						try {
							tag.attr(href, new URL(new URL(base), attr)
									.toString());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						// 删除连接
						tag.removeAttr(href);
					}
				}
			}

			if (tag.hasAttr(srcstr) == true) {
				try {
					tag.attr(srcstr, new URL(new URL(base), tag.attr(srcstr))
							.toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static String UnUseMbTag = "(script|img|form|input|iframe|frame|embed|textarea|applet|area|bgSound|button|dataTransfer|embed|event|external|fieldSet|implementation|isIndex|big|small|link|style)";

	/**
	 * 过滤内容，取得适合手机显示的网页
	 * 
	 * @param tag
	 * @param myUrl
	 */
	public static void trimUnuseTag(Element tag, String base, String myUrl) {
		if (tag.tagName().matches(MyHtmlParser.UnUseMbTag)) {
			tag.remove();
		}
		if (base != null) {
			LhpUtil.cureLink(tag, base);
		}
		String link = tag.attr(MyHtmlParser.href);
		String src = tag.attr(MyHtmlParser.srcstr);
		String classs = tag.attr("class");
		Attributes m = tag.attributes();
		Iterator it = m.iterator();
		Attribute current;
		while (it.hasNext()) {
			current = (Attribute) it.next();
			if (current.getKey().equals("class")) {
				if (current.getValue().indexOf(OnlineFocus.UPDATESTYLE) >= 0) {
					continue;
				}
			}
			tag.removeAttr(current.getKey());
		}
		if (link != null) {
			tag.attr("href", myUrl + link);
		}
		if (src != null) {
			tag.attr("src", src);
		}
		if (classs != null) {
			if(classs.indexOf(OnlineFocus.UPDATESTYLE)>0)
			{
			  tag.attr("class", classs);
			}
		}
		
	}

	public static String getMobileDOM(String source) {
		return source.replaceAll("&amp;", "&").replaceAll("<table",
				"<ul class='tableUl'").replaceAll("table>", "ul>").replaceAll(
				"<tr", "<ul  class='trUl'").replaceAll("tr>", "ul>")
				.replaceAll("<td", "<li class='tdli'").replaceAll("td>", "li>");

		/*
		 * return source.replaceAll("&amp;",
		 * "&").replaceAll("<table","<div").replaceAll("table>","div>")
		 * .replaceAll
		 * ("<tr","<div").replaceAll("tr>","div>").replaceAll("<td","<div"
		 * ).replaceAll("td>","div>");
		 */
	}

	/**
	 * 由给定的文档合并更新
	 * 
	 * @param source
	 * @param update
	 */
	public static void mergeUpdate(Document source, Document update) {
		Elements updates = update.select("."+OnlineFocus.UPDATESTYLE);

		// System.out.println(update.outerHtml());
		// System.out.println(updates.size());
		HashMap<String, String> currentUpdate = new HashMap<String, String>();
		for (int i = 0; i < updates.size(); i++) {
			currentUpdate.put(updates.get(i).getPath(), updates.get(i).text());
		}
		Object iterator[] = currentUpdate.keySet().toArray();

		Elements currentE = null;
		String path = null;
		Elements siblingElements;

		for (int j = 0; j < iterator.length; j++) { // 对比特征中的文本
			path = iterator[j].toString();
			//System.out.println("取得特征："+path);
			currentE = source.select(path);
			if (currentE.size()>0) {
				if (currentE.get(0).text().equals(currentUpdate.get(path)) == true) {// 相等则将其设为updateStyle
					currentE.get(0).addClass(OnlineFocus.UPDATESTYLE);
					currentUpdate.remove(path);
					// System.out.println("对应特征");
				} else {
					// System.out.println("同层兄弟");
					siblingElements = source.siblingElements();// 先对比其兄弟
					boolean match = false;
					if (siblingElements != null) {
						for (int i = 0; i < siblingElements.size(); i++) {
							if (siblingElements.get(i).text().equals(
									currentUpdate.get(path)) == true) {// 相等则将其设为updateStyle
								siblingElements.get(i).addClass(OnlineFocus.UPDATESTYLE);
								currentUpdate.remove(path);
								match = true;
								break;
							}
						}
					}
					if (match == false) {
						String tempPath = path.replaceAll(":eq\\([0-9]+\\)", "");// 再对比同层的
						siblingElements = source.select(tempPath);// 先对同层兄弟
						for (int i = 0; i < siblingElements.size(); i++) {
							if (siblingElements.get(i).text().equals(
									currentUpdate.get(path)) == true) {// 相等则将其设为updateStyle
								siblingElements.get(i).addClass(OnlineFocus.UPDATESTYLE);

								currentUpdate.remove(path);
								break;
							}
						}
					}

				}
			}
		}
		// 如果还有特征

		if (currentUpdate.isEmpty() == false) { // 对比特征中的文本
			// System.out.println("全文搜索");
			FiltUpdate(source, currentUpdate, true);
		}
	}

	/**
	 * 过滤旧节点，得到更新节点，并将update标志设为true
	 * 
	 * @param newT
	 * @param oldT
	 * @param update
	 */
	public static void FiltUpdate(Element newT,  HashMap<String,String> currentUpdate,
			boolean mode) {
		// 如果是需要合并文字的节点并且没有a 儿子
		/*
		 * System.out.println(newT.tagName() + "///" +
		 * newT.getElementsByTag("a").size());
		 */
		if(newT.tagName().equals("a")){
			return;
		}
		if (newT.tagName().matches(MyHtmlParser.CONTEXT_TAG)) {
			if (newT.getElementsByTag("a").size() == 0) {
				checkUpdateOfTag(newT, currentUpdate, mode);
				return;
			}
		}
		Elements children = newT.children();
		if (children.size() < 1) {
			checkUpdateOfTag(newT, currentUpdate, mode);
			return;
		}
		// 是叶子节点
		for (int i = 0; i < children.size(); i++) {
			FiltUpdate(children.get(i), currentUpdate, mode);
		}

	}

	/**
	 * 检查一个节点是否更新了，没有更新则将其删除
	 * 
	 * @param newT
	 * @param oldT
	 * @param update
	 */
	private static void checkUpdateOfTag(Element newT,
			 HashMap<String,String> currentUpdate, boolean mode) {
		String newText = null;
		newText = newT.text();
		if (newText.length() < 2) {
			return;
		}
		if (mode == true) {
			if(currentUpdate.containsKey(newText)){
				currentUpdate.remove(newText);			
				newT.addClass(OnlineFocus.UPDATESTYLE);
			}
		} else {
			if(currentUpdate.containsKey(newText)==false){		
				newT.addClass(OnlineFocus.UPDATESTYLE);
			}
		}

	}

	/**
	 * 由给定字符串得到特征
	 * 
	 * @param tempPaths
	 * @return
	 */
	public static List<String> getUnCheckPaths(String tempPaths) {
		StringTokenizer stringTokenizer = new StringTokenizer(tempPaths, ",");
		List<String> paths = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			paths.add( stringTokenizer.nextToken());
		}
		return paths;
	}

	/**
	 * 由给定字符串得到特征
	 * 
	 * @param tempPaths
	 * @return
	 */
	public static void trimUnCheckPaths(Document document, String tempPaths) {
		if (tempPaths.equals("null")) {
			return;
		}

		List<String> uncheckPaths = getUnCheckPaths(tempPaths);

		Elements current = null;
		for (int i = 0; i < uncheckPaths.size(); i++) {
			current = document.select(uncheckPaths.get(i));
			if (current.size()>0) {
				current.removeClass(OnlineFocus.UPDATESTYLE);
				Elements elements=current.select("."+OnlineFocus.UPDATESTYLE);				
				elements.removeClass(OnlineFocus.UPDATESTYLE);			
			}
		}

	}

	/**
	 * 返回body字符串
	 * 
	 * @param document
	 * @return
	 */
	public static String getBodyString(Document document) {
		if (document.body().attributes().size() > 0) {
			return document.body().outerHtml();
		} else {
			return document.body().html();
		}

	}

	/**
	 * 是否有更新
	 * 
	 * @param document
	 * @return
	 */
	public static boolean isUpdate(Document document) {
		return document.select("."+OnlineFocus.UPDATESTYLE).size() > 0 ? true : false;
	}

	/**
	 * 从哈希表中取得list
	 * 
	 * @param hashmap
	 * @return
	 */
	public static List<String> getListFormHashMap(
			HashMap<String, String> hashmap) {
		Object[] temp = hashmap.values().toArray();
		List<String> list = new ArrayList<String>();
		for (Object tempstr : temp) {
			list.add((String) tempstr);
		}
		return list;
	}

	/**
	 * 过滤内容，取得适合手机显示的网页
	 */
	public static String getMbHtml(String text, String base, String myUrl) {

		try {
			MyHtmlParser myHtmlParser = new MyHtmlParser();
			Document document = myHtmlParser.parser(text);
			Elements all = document.getAllElements();
			for (int i = 0; i < all.size(); i++) {
				trimUnuseTag(all.get(i), base, myUrl);
			}
			String temp = LhpUtil.getBodyString(document);
			temp = getMobileDOM(temp);
			return temp;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 过滤内容，取得适合手机显示的网页
	 */
	public static Document getMbHtmlFromURL(String url, String base,
			String myUrl) {

		try {
			MyHtmlParser myHtmlParser = new MyHtmlParser();
			Document document = myHtmlParser.parserURL(url);
			Elements all = document.getAllElements();
			for (int i = 0; i < all.size(); i++) {
				trimUnuseTag(all.get(i), base, myUrl);
			}

			return document;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得有更新的文本的特征
	 * @param html
	 * @return
	 */
	public static String getUpdatePaths(String html){
		Document document=Jsoup.parse(html);
		Elements updates=document.select("."+OnlineFocus.UPDATESTYLE);
		StringBuilder paths=new StringBuilder(256);
		for(int i=0;i<updates.size();i++){
			paths.append(updates.get(i).getPath());
			paths.append(",");
		}
		if(paths.length()>0){
		   return paths.deleteCharAt(paths.length()-1).toString();
		}else{
			return null;
		}
	}
	
	public static void removeDivs(Element document){
		Element deleteE=null;
	    deleteE=document.getElementById("extraDiv");
	    if(deleteE!=null){
	    	  deleteE.remove();
	    }
	   
	    document.select("div[name='tempdiv']").remove();
	    document.select(".ke-dialog").remove();
	    Elements ups=document.select("."+OnlineFocus.UPDATESTYLE);
	    for(int i=0;i<ups.size();i++){
	    	ups.get(i).removeClass(OnlineFocus.UPDATESTYLE);
	    }
	    ups=document.select("script");
	    for(int i=0;i<ups.size();i++){
	    	if(ups.get(i).attr("src")!=null){
	    		if(ups.get(i).attr("src").indexOf(URLS.getHostAddress())>=0){
	    			ups.get(i).remove();
	    		}
	    	}    	
	    }
	    ups=document.select("link");
	    for(int i=0;i<ups.size();i++){
	    	if(ups.get(i).attr("href")!=null){
	    		if(ups.get(i).attr("href").indexOf(URLS.getHostAddress())>=0){
	    			ups.get(i).remove();
	    		}
	    	}    	
	    }
	}
	/**
	 * 由基特征和当前更新特征纠正为正确更新特征
	 * @param tpath
	 * @param basepath
	 * @return
	 */
	public static String getUpPath(String tpath, String basepath) {
		if (basepath.endsWith("body[0]")) {
			return tpath;
		}
		int begin=basepath.lastIndexOf(">");
		String tag=basepath.substring(begin,basepath.indexOf(":eq(",begin)+4);
		
		String paths[] = tpath.split(",");
	 // System.out.println("基特征："+basepath);
		for (int i = 0; i < paths.length; i++) {
		//	System.out.println("当前特征："+paths[i]);
			begin=paths[i].indexOf(tag);
			begin=paths[i].indexOf(")",begin)+1;
			if(begin<paths[i].length()){
			   paths[i]=basepath+paths[i].substring(begin);
			}else{
				paths[i]=basepath;
			}
			
		//	System.out.println("合特征："+paths[i]);
		}
		StringBuilder temp=new StringBuilder();
		for(int i=0;i<paths.length;i++){
			temp.append(paths[i]);
			temp.append(",");
		}
		if(temp.toString().endsWith(",")){
			temp.delete(temp.length()-1, temp.length());
		}
		//System.out.println(temp.toString());
		return temp.toString();
	}
	
	public static String getSourceURL(String url){
		return handelHref(url.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceFirst("%3F&", "?"));	 
	}
	
	public static String handelHref(String href){
		if(href.endsWith("#")&&href.length()>1){
			href=href.substring(0,href.length()-1);
		}
		if(href.endsWith("/")&&href.length()>1){
			href=href.substring(0,href.length()-1);
		}
		href=href.replaceAll("\\\\", "/");
		return href.trim();
	}
}
