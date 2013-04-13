package org.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 The core public access point to the jsoup functionality.

 @author Jonathan Hedley */
public class Jsoup {
    private Jsoup() {}

    /**
     Parse HTML into a Document. The parser will make a sensible, balanced document tree out of any HTML.

     @param html    HTML to parse
     @param baseUri The URL where the HTML was retrieved from. Used to resolve relative URLs to absolute URLs, that occur
     before the HTML declares a {@code <base href>} tag.
     @return sane HTML
     */
    public static Document parse(String html, String baseUri) {
        return Parser.parse(html, baseUri);
    }
/**
 * 吕鸿佩
 * @param html
 * @param baseUri
 * @return
 */
   public static Document parseUnTransform(String html, String baseUri) {
     	html=getTransformString(html);
       return Parser.parse(html, baseUri);
   }
    /**
     Parse HTML into a Document. As no base URI is specified, absolute URL detection relies on the HTML including a
     {@code <base href>} tag.

     @param html HTML to parse
     @return sane HTML

     @see #parse(String, String)
     */
    public static Document parse(String html) {
    	
        return Parser.parse(html, "");
    }

/**
 * 吕鸿佩，从未经过转换的html解析
 * @param html
 * @return
 */
   public static Document parseUnTransform(String html) {
   	html=getTransformString(html);
    return Parser.parse(html, "");
   }
    /**
     * Creates a new {@link Connection} to a URL. Use to fetch and parse a HTML page.
     * <p>
     * Use examples:
     * <ul>
     *  <li><code>Document doc = Jsoup.connect("http://example.com").userAgent("Mozilla").data("name", "jsoup").get();</code></li>
     *  <li><code>Document doc = Jsoup.connect("http://example.com").cookie("auth", "token").post();
     * </ul>
     * @param url URL to connect to. The protocol must be {@code http} or {@code https}.
     * @return the connection. You can add data, cookies, and headers; set the user-agent, referrer, method; and then execute.
     */
    public static Connection connect(String url) {
        return HttpConnection.connect(url);
    }

    /**
     Parse the contents of a file as HTML.

     @param in          file to load HTML from
     @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if
     present, or fall back to {@code UTF-8} (which is often safe to do).
     @param baseUri     The URL where the HTML was retrieved from, to generate absolute URLs relative to.
     @return sane HTML

     @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
     */
    public static Document parse(File in, String charsetName, String baseUri) throws IOException {
        return DataUtil.load(in, charsetName, baseUri);
    }

    /**
     Parse the contents of a file as HTML. The location of the file is used as the base URI to qualify relative URLs.

     @param in          file to load HTML from
     @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if
     present, or fall back to {@code UTF-8} (which is often safe to do).
     @return sane HTML

     @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
     @see #parse(File, String, String)
     */
    public static Document parse(File in, String charsetName) throws IOException {
        return DataUtil.load(in, charsetName, in.getAbsolutePath());
    }

    /**
     Parse a fragment of HTML, with the assumption that it forms the {@code body} of the HTML.

     @param bodyHtml body HTML fragment
     @param baseUri  URL to resolve relative URLs against.
     @return sane HTML document

     @see Document#body()
     */
    public static Document parseBodyFragment(String bodyHtml, String baseUri) {
    	bodyHtml=getTransformString(bodyHtml);
        return Parser.parseBodyFragment(bodyHtml, baseUri);
    }

    /**
     Parse a fragment of HTML, with the assumption that it forms the {@code body} of the HTML.

     @param bodyHtml body HTML fragment
     @return sane HTML document

     @see Document#body()
     */
    public static Document parseBodyFragment(String bodyHtml) {
    	bodyHtml=getTransformString(bodyHtml);
        
        return Parser.parseBodyFragment(bodyHtml, "");
    }

    /**
     Fetch a URL, and parse it as HTML. Provided for compatibility; in most cases use {@link #connect(String)} instead.
     <p>
     The encoding character set is determined by the content-type header or http-equiv meta tag, or falls back to {@code UTF-8}.

     @param url           URL to fetch (with a GET). The protocol must be {@code http} or {@code https}.
     @param timeoutMillis Connection and read timeout, in milliseconds. If exceeded, IOException is thrown.
     @return The parsed HTML.

     @throws IOException If the final server response != 200 OK (redirects are followed), or if there's an error reading
     the response stream.

     @see #connect(String)
     */
    public static Document parse(URL url, int timeoutMillis) throws IOException {
        Connection con = HttpConnection.connect(url);
        con.timeout(timeoutMillis);
        return con.get();
    }

    /**
     Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it through a white-list of permitted
     tags and attributes.

     @param bodyHtml  input untrusted HMTL
     @param baseUri   URL to resolve relative URLs against
     @param whitelist white-list of permitted HTML elements
     @return safe HTML

     @see Cleaner#clean(Document)
     */
    public static String clean(String bodyHtml, String baseUri, Whitelist whitelist) {
        Document dirty = parseBodyFragment(bodyHtml, baseUri);
        Cleaner cleaner = new Cleaner(whitelist);
        Document clean = cleaner.clean(dirty);
        return clean.body().html();
    }

    /**
     Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it through a white-list of permitted
     tags and attributes.

     @param bodyHtml  input untrusted HTML
     @param whitelist white-list of permitted HTML elements
     @return safe HTML

     @see Cleaner#clean(Document)
     */
    public static String clean(String bodyHtml, Whitelist whitelist) {
        return clean(bodyHtml, "", whitelist);
    }

    /**
     Test if the input HTML has only tags and attributes allowed by the Whitelist. Useful for form validation. The input HTML should
     still be run through the cleaner to set up enforced attributes, and to tidy the output.
     @param bodyHtml HTML to test
     @param whitelist whitelist to test against
     @return true if no tags or attributes were removed; false otherwise
     @see #clean(String, org.jsoup.safety.Whitelist) 
     */
    public static boolean isValid(String bodyHtml, Whitelist whitelist) {
        Document dirty = parseBodyFragment(bodyHtml, "");
        Cleaner cleaner = new Cleaner(whitelist);
        return cleaner.isValid(dirty);
    }
    
	/** 用于提取孤立文字节点的正则表达式 */
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
	 */
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
	

}

/**
 * 位置结构类，用于纪录一个字符串位置的起点和终点
 * 
 * @author Administrator
 */
class Location {

	public int end;
	public int begin;
	public String text;

	public Location(int begin, int end, String text) {
		this.begin = begin;
		this.end = end;
		this.text = text;

	}
}