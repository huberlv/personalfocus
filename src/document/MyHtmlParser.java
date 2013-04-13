/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 20
 */
package document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.kingway.util.LhpUtil;
import com.kingway.util.OnlineFocus;

import update.UpdateContent;

/**
 *和网页的xml文档相关的类
 * 
 * @author Administrator
 */
public class MyHtmlParser {
	/* 要合并文字的标签 */
	public static final String CONTEXT_TAG = "span|strong|em|blink|strike|s|u|wbr|abbr|acronym|address|b|bdo|big|cite|code|del|dfn|font|i|ins|kbd|"
			+ "small|sub|tt";
	private HashMap<String, String> oldT = null; // 旧源码的文字内容容器
	private HashMap<String, String> oldHref = null; // 旧源码的文字内容容器
	public static int TYPE_TD = 0;
	/** 网页的网址 */
	private String urlstr;
	/** 匹配器 */
	private Matcher matcher;
	/** 网页的编码 */
	public String charset = null;

	public Document document = null;
	// public Element root = null;
	/** 用于特征字符串中的特征的正则表达式 */
	private static final String group = "/([^\\[]+)\\[([0-9]+)\\]";
	/** 用于特征字符串中的特征的解析器 */
	private final Pattern groupPattern = Pattern.compile(group);
	/** 用于提取孤立文字节点的正则表达式 */
	private static final String change = ">([^<]+)<[^/]{1}";
	/** 用于提取孤立文字节点的解析器 */
	private final Pattern changePattern = Pattern.compile(change);
	/** 网页的基址 */
	private String base = null;

	/** 网页的内嵌网页的标签 */
	// private static final String innerHtmlStr = "iframe|frame";

	public static final String srcstr = "src";
	/** 缺省的HtmlCleaner */
	/** 空白的正则表达式 */
	private static final String sstr = "\\s*";
	public static final String empty = "\\s+";
	// 要过滤的字符串
	public static String regx = "(&nbsp;|&gt;|&quot;|&apos;|&sect|&copy|&reg|&times|&divide;|nbsp;|&raquo;)+";

	/** 表示是整个模块 */
	public static final int TYPE_MODULE = 1;

	public static int REMOVE_OLD = 0;
	public static int MARK_NEW = 1;
	// public static String UnUseMbTag =
	// "(style|script|link|img|form|input|iframe|frame|embed|textarea|applet|area|bgSound|button|dataTransfer|embed|event|external|fieldSet|implementation|isIndex|big|small)";
	// public static String UnUseMbTag =
	// "(script|img|input|iframe|frame|embed|textarea|applet|area|bgSound|button|dataTransfer|embed|event|external|fieldSet|implementation|isIndex|big|small|link|style)";
	public static String UnUseMbTag = "(script|embed|applet|bgSound|link|style)";

	public static int TEXT = 6;
	public static int TEXTNODE = 7;

	public MyHtmlParser(String html, String urlstr) {
		this.document = Jsoup.parseUnTransform(html, urlstr);

	}

	public Document parser(String html, String urlstr) {
		this.document = Jsoup.parseUnTransform(html, urlstr);
		this.base = null;
		this.charset = null;
		return document;
	}

	public MyHtmlParser() {

	}

	public Document parser(String html) {
		this.document = Jsoup.parseUnTransform(html);
		this.base = null;
		this.charset = null;
		return this.document;
	}

	public Document parserURL(String urlstr) throws IOException {

		int end = urlstr.indexOf("&t");
		if (end > 0) {
			urlstr = urlstr.substring(0, end);
		}

		URL url;

		url = new URL(urlstr);
		this.document = Jsoup.parse(url, 120000);
		this.urlstr = urlstr;
		this.base = null;
		this.charset = null;
		return this.document;
	}

	/**
	 * 构造函数
	 * 
	 * @param url
	 *            网址
	 * @param cleaner
	 *            HtmlCleaner,为null时采用缺省的HtmlCleaner
	 * @throws IOException
	 */
	public MyHtmlParser(String urlstr) throws IOException {

		int end = urlstr.indexOf("&t");
		if (end > 0) {
			urlstr = urlstr.substring(0, end);
		}
		URL url;

		url = new URL(urlstr);
		this.document = Jsoup.parse(url, 120000);
		this.urlstr = urlstr;
	}

	/**
	 * 取得网页的基址
	 * 
	 * @return 网页的基址
	 */
	public String getBase() {
		if (this.base != null) {
			return this.base;
		} else {
			this.base = this.document.baseUri();// 未确定是否正确
			return this.base;
		}
	}

	private static String table = "table";
	private static String div = "div";

	public String getCharset() {
		if (this.charset != null) {
			return this.charset;
		}
		this.charset = this.document.outputSettings().charset().displayName();
		return this.charset;
	}

	public static String href = "href";
	public static String replace = ";|\"|'";

	/**
	 * 得到给定特征下的节点的树结构,转成xml文件存放在text中
	 * 
	 * @param path
	 * @param text
	 * @throws java.io.IOException
	 */
	public String getHtmlOfPath(String path, int type) throws IOException {

		// 将该节点写成xml存入数据库
		Elements tag = this.getTagNodeByPath(path);

		if (tag == null) {
			return null;
		}
		if (tag.size()==0) {
			return null;
		}
		for (int i = 0; i < tag.size(); i++) {
			LhpUtil.cureLink(tag.get(i), this.getBase());
		}
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < tag.size(); i++) {
			temp.append(tag.get(i).outerHtml());
		}
		return temp.toString();
	}

	/**
	 * 将新旧的文本和其链接进行对比，返回更新的文本和链接的xml字符串
	 * 算法：如果某一节点在旧树中有相同的内容，则将其删除，否则将新树的更新标志设为true
	 * 
	 * @param text
	 * @param old
	 * @return 更新的文本和链接
	 */
	public ArrayList<UpdateContent> getUpdate(String stext, String old, int TYPE) {
		// 未完成
		ArrayList<UpdateContent> update = new ArrayList<UpdateContent>();
		Document oldbody = null;
		Document newbody = null;
		if (this.urlstr != null) {
			newbody = Jsoup.parse(stext, this.urlstr);
			oldbody = Jsoup.parse(old, this.urlstr);
		} else {
			newbody = Jsoup.parse(stext);
			oldbody = Jsoup.parse(old);
		}
		LhpUtil.removeDivs(newbody);
		// ///
		this.oldT = new HashMap<String, String>();
		this.oldHref = new HashMap<String, String>();
		Elements oldElementLinks = oldbody.select("a");
		for (int i = 0; i < oldElementLinks.size(); i++) {
			this.oldT.put(oldElementLinks.get(i).text(), null);
			this.oldHref.put(LhpUtil.handelHref(oldElementLinks.get(i).attr(
					"href")), null);
		}
		Elements newElementLinks = newbody.select("a");
		for (int i = 0; i < newElementLinks.size(); i++) {
			if (this.oldT.containsKey(newElementLinks.get(i).text()) == false) {
				newElementLinks.get(i).addClass(OnlineFocus.UPDATESTYLE);
			} else {
				if (this.oldHref.containsKey(LhpUtil.handelHref(newElementLinks
						.get(i).attr("href"))) == false) {
					newElementLinks.get(i).addClass(OnlineFocus.UPDATESTYLE);
				}
			}
		}
		if (LhpUtil.isUpdate(newbody) == true) {
			update.add(new UpdateContent(LhpUtil.getBodyString(newbody),
					OnlineFocus.MONITOR_LINKS));
		}
		// //
		this.addContent(oldbody);
		LhpUtil.FiltUpdate(newbody, this.oldT, false);
		if (LhpUtil.isUpdate(newbody) == true) {
			update.add(new UpdateContent(LhpUtil.getBodyString(newbody),
					OnlineFocus.MONITOR_ALL));
		}
		if (update.size() > 0) {
			return update;
		}
		return null;
	}

	/**
	 *设置网址
	 * 
	 * @param s
	 *            网址的字符串
	 */
	public void setURL(String s) // 设置URL
	{
		if (this.urlstr != null && this.urlstr.equals(s) == true) // 不同网址时重新尝试解析
		{
			return;
		} else {
			this.base = null;
			this.urlstr = s;
		}
	}

	/**
	 * 由给定的特征返回其节点
	 * 
	 * @param path
	 *            特征
	 * @param tag
	 *            给定的节点
	 * @return 给定的特征的节点
	 */
	public Elements getTagNodeByPath(String path) {
	/*	try {
			path = path.trim();
			if (path.startsWith(".") && path.indexOf(":eq(") > 0) {
				String classes = path.substring(0, path.indexOf(":eq("));
				String numstr = path.substring(path.indexOf(":eq(") + 4, path
						.indexOf(")"));
				int num = Integer.parseInt(numstr);
				Elements els = this.document.select(classes);
				if (els.size() > num) {
					return new Elements(els.get(num));
				} else {
					return null;
				}
			}
			if (path.indexOf("name=") > 0 && path.indexOf(":eq(") > 0) {
				String name = path.substring(0, path.indexOf(":eq("))
						.replaceAll("'", "").replaceAll("\"", "");  
				String numstr = path.substring(path.indexOf(":eq(") + 4, path
						.indexOf(")"));
				int num = Integer.parseInt(numstr);
				Elements els = this.document.select(name);
				if (els.size() > num) {
					return new Elements(els.get(num));
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
*/   
		return this.document.select(path);
	}

	/**
	 * 递归函数，往文字节点容器中加入文字
	 * 
	 * @param tag
	 */
	public void addContent(Element tag) {
		String temp;
		if (tag.tagName().equals("a")) {
			return;
		}
		if (tag.tagName().matches(CONTEXT_TAG)) {
			if (tag.getElementsByTag("a").size() == 0) {
				temp = tag.text();
				if (temp.length() > 1) {
					this.oldT.put(temp, null);
				}
				return;
			}
		}
		Elements children = tag.children();
		if (children.size() < 1) {
			temp = tag.text();
			if (temp.length() > 1) {
				this.oldT.put(temp, null);
			}
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			this.addContent(children.get(i));
		}
	}

	public static void main(String args[]) throws IOException {
		// MyHtmlParser my = new MyHtmlParser("http://www.baidu.com");
		// System.out.println(my.getHtmlOfPath("html[0]/body[0]/div[0]/p[1]",
		// 1));
		String text = "<html><head><base href='http://www.baidu.com'/></head><body id=op>9990<a>8909</a></body></html>";
		String old = "<html><head><base href='http://www.baidu.com'/></head><body id=op>9990<a>8909</a></body></html>";

		MyHtmlParser my = new MyHtmlParser(
				"<html><head><base href='http://www.baidu.com'/></head><body>999<a>&cent;  </a></body></html>",
				"http://www.baidu.com");
		// my.parserURL("http://119.75.213.50/");
		// System.out.println(my.getUpdate(text, old, 0).get(0).content);
		System.out.println(my.getTagNodeByPath("html[0]/body[0]/a[0]")
				.outerHtml());
	}
}
