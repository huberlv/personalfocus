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
	 * ��ָ���ı���ȥ���������ı�
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
	 * ����,�ŷָ��ĳ��������ȡ����������
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

	/** ������ȡ�������ֽڵ��������ʽ 
	private static final String change = ">([^<^\\s]+)<[^/]{1}";

	/**
	 * ��xml�ĵ��еĹ������ּ���text��ǩ�������ΪҶ�ӽڵ�
	 * 
	 * @param page
	 *            ��ҳ����ĸ��ڵ�
	 * @param cleaner
	 *            HtmlCleaner�����ڻ�ȡ������
	 * @return ��������page����ĸ��ڵ�
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
	 * ���˽���µ����Ӹ�Ϊ��������
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
				// ���ǿ����ӻ�ê������
				attr = tag.attr(href);
				if (attr.length() > 0) {
					// ɾ��javascript:void(0)������
					if (attr.indexOf("javascript:") < 0) {
						try {
							tag.attr(href, new URL(new URL(base), attr)
									.toString());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						// ɾ������
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
	 * �������ݣ�ȡ���ʺ��ֻ���ʾ����ҳ
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
	 * �ɸ������ĵ��ϲ�����
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

		for (int j = 0; j < iterator.length; j++) { // �Ա������е��ı�
			path = iterator[j].toString();
			//System.out.println("ȡ��������"+path);
			currentE = source.select(path);
			if (currentE.size()>0) {
				if (currentE.get(0).text().equals(currentUpdate.get(path)) == true) {// ���������ΪupdateStyle
					currentE.get(0).addClass(OnlineFocus.UPDATESTYLE);
					currentUpdate.remove(path);
					// System.out.println("��Ӧ����");
				} else {
					// System.out.println("ͬ���ֵ�");
					siblingElements = source.siblingElements();// �ȶԱ����ֵ�
					boolean match = false;
					if (siblingElements != null) {
						for (int i = 0; i < siblingElements.size(); i++) {
							if (siblingElements.get(i).text().equals(
									currentUpdate.get(path)) == true) {// ���������ΪupdateStyle
								siblingElements.get(i).addClass(OnlineFocus.UPDATESTYLE);
								currentUpdate.remove(path);
								match = true;
								break;
							}
						}
					}
					if (match == false) {
						String tempPath = path.replaceAll(":eq\\([0-9]+\\)", "");// �ٶԱ�ͬ���
						siblingElements = source.select(tempPath);// �ȶ�ͬ���ֵ�
						for (int i = 0; i < siblingElements.size(); i++) {
							if (siblingElements.get(i).text().equals(
									currentUpdate.get(path)) == true) {// ���������ΪupdateStyle
								siblingElements.get(i).addClass(OnlineFocus.UPDATESTYLE);

								currentUpdate.remove(path);
								break;
							}
						}
					}

				}
			}
		}
		// �����������

		if (currentUpdate.isEmpty() == false) { // �Ա������е��ı�
			// System.out.println("ȫ������");
			FiltUpdate(source, currentUpdate, true);
		}
	}

	/**
	 * ���˾ɽڵ㣬�õ����½ڵ㣬����update��־��Ϊtrue
	 * 
	 * @param newT
	 * @param oldT
	 * @param update
	 */
	public static void FiltUpdate(Element newT,  HashMap<String,String> currentUpdate,
			boolean mode) {
		// �������Ҫ�ϲ����ֵĽڵ㲢��û��a ����
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
		// ��Ҷ�ӽڵ�
		for (int i = 0; i < children.size(); i++) {
			FiltUpdate(children.get(i), currentUpdate, mode);
		}

	}

	/**
	 * ���һ���ڵ��Ƿ�����ˣ�û�и�������ɾ��
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
	 * �ɸ����ַ����õ�����
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
	 * �ɸ����ַ����õ�����
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
	 * ����body�ַ���
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
	 * �Ƿ��и���
	 * 
	 * @param document
	 * @return
	 */
	public static boolean isUpdate(Document document) {
		return document.select("."+OnlineFocus.UPDATESTYLE).size() > 0 ? true : false;
	}

	/**
	 * �ӹ�ϣ����ȡ��list
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
	 * �������ݣ�ȡ���ʺ��ֻ���ʾ����ҳ
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
	 * �������ݣ�ȡ���ʺ��ֻ���ʾ����ҳ
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
	 * ȡ���и��µ��ı�������
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
	 * �ɻ������͵�ǰ������������Ϊ��ȷ��������
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
	 // System.out.println("��������"+basepath);
		for (int i = 0; i < paths.length; i++) {
		//	System.out.println("��ǰ������"+paths[i]);
			begin=paths[i].indexOf(tag);
			begin=paths[i].indexOf(")",begin)+1;
			if(begin<paths[i].length()){
			   paths[i]=basepath+paths[i].substring(begin);
			}else{
				paths[i]=basepath;
			}
			
		//	System.out.println("��������"+paths[i]);
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
