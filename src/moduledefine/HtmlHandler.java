package moduledefine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import com.kingway.util.PropertiesUtil;

import document.MyHtmlParser;

import net.Net;
import net.URLS;

/**
 * html预处理类，对html进行预处理
 * 
 * @author Administrator
 */
public class HtmlHandler {
	public static int BUFFER_SIZE = 2048;
	public static int webLength = 102400; // 网面缓冲区大小,100K
	/** 是否发现编码的标志 */
	private boolean charestFound = false;
	/** 网页的编码 */
	public String charset = null;
	private String urlstr;
	private static String charsetR = "(?i)charset\\s*=\\s*['|\"]{0,1}\\s*([^'\"\\s>]+)";
	private String base = null;
	private static String baseR = "(?i)<base[^h]+href\\s*=\\s*['|\"]{0,1}\\s*([^'\"\\s>]+)";
	public static String head = "(?i)<[^h]*head[^>]*>";
	public static String body = "(?i)<body[^>]*>";
	private boolean isbaseFound;
	private String sourceHtml = null;
	private Vector<byte[]> byteVector = new Vector<byte[]>(25, 25);
	private Vector<String> vs = new Vector<String>(2, 2);
	private static final String DEFINED_TOP_HEAD_A = "<SCRIPT type=\"text/javascript\"  src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/defineIframe.js\">"
			+ "</SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/olpftooltips.js\"></SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/common/feature.js\"></SCRIPT>"
			
			+ "<script type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/dwr/interface/addModule.js\"></script>";

	private static  String HEAD_BASE = "<link href=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/css/userindexcss/olpfmaster.css\" rel=\"stylesheet\" type=\"text/css\"/>"
			+ "<SCRIPT type=\"text/javascript\"  src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js\">"
			+ "</SCRIPT>"

			+ "<SCRIPT type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/toolkits.js\"></SCRIPT>"

			+ "<script type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/dwr/engine.js\"></script>"

			+ "<script type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/dwr/util.js\"></script>"

			+ "<script type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/dwr/interface/monitorByJs.js\"></script>";

	public static  String DEFINE_TOP_HEAD_STR = HEAD_BASE
			+ DEFINED_TOP_HEAD_A;
	private static final String MODIFY_MODULE = "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/common/dialog.js\"></SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/common/feature.js\"></SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/modifyModulePath.js\"></SCRIPT>"
			
			+ "<script type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/dwr/interface/modifyModulePathByJs.js\"></script>";

	public static  String MODIFY_MODULE_TOP_HEAD_STR = HEAD_BASE
			+ MODIFY_MODULE;

	public static  String DEFINE_IFRAME_HEAD_STR = "<link href=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/css/userindexcss/olpfmaster.css\" rel=\"stylesheet\" type=\"text/css\"/>"
			+ "<SCRIPT type=\"text/javascript\"  src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js\"></SCRIPT>"
			+ "<SCRIPT type=\"text/javascript\" src=\"" + URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/toolkits.js\"></SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\" src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/common/feature.js\"></SCRIPT>"
			
			+ "<SCRIPT type=\"text/javascript\"  src=\""
			+ URLS.getHostAddress()
			+ "/personalfocus/js/userindexjs/olpftooltips.js\"></SCRIPT>";
    
	// 未覆盖警告框等方法
	public static String DEFINE_TOP_BODY_STR = "<div name='noaction' id=\"waitDiv\" style=\"text-align:center;\">&nbsp;</div><div id=\"topTips\""
			+ "><p></p><p align=\"center\">互联网关注订阅平台！</p>"
			+ "<p align=\"center\">正在载入，请稍候！</p>"
			+ "<p align=\"center\">操作提示：移动鼠标到关注的信息位置，当出现红色框时单击关注此位置内容!如果页面长期无响应，请点击<a href=\"/personalfocus/index/showHelp\" target='_blank'>帮助</a></p></div>";
	public static String DEFINE_IFRAME_BODY_STR = "<div name='noaction' id=\"waitDiv\" style=\"height:100%;width:100%;-moz-opacity:0.2;opacity:0.2;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=20);-ms-filter:progid:DXImageTransform.Microsoft.Alpha(opacity=20);\"></div>";
	private final static String DEFAULTCHARSET = "GBK";
	public static final int FIREFOX = 1;
	public static final int OPERA = 2;
	public static final int IE = 0;
	private String insertIntoHead;
	private String insertIntoBody;
    private static String intercept="<SCRIPT type=\"text/javascript\" src=\"" + URLS.getHostAddress()
	+ "/personalfocus/js/userindexjs/intercept.js\"></SCRIPT>";
	static{
		
		if("1".equals(PropertiesUtil.getProperty("intercept"))){

			DEFINE_TOP_HEAD_STR=DEFINE_TOP_HEAD_STR+intercept;
			MODIFY_MODULE_TOP_HEAD_STR=MODIFY_MODULE_TOP_HEAD_STR+intercept;
			DEFINE_IFRAME_HEAD_STR=DEFINE_IFRAME_HEAD_STR+intercept;			
		} 
        System.out.println("PropertiesUtil.getProperty(proxySet):"+PropertiesUtil.getProperty("proxySet"));
		System.setProperty("proxySet", PropertiesUtil.getProperty("proxySet"));
        System.setProperty("proxyHost",  PropertiesUtil.getProperty("proxyHost"));
        System.setProperty("proxyPort", PropertiesUtil.getProperty("proxyPort"));
        System.setProperty("proxyUsername", PropertiesUtil.getProperty("proxyUsername"));
        System.setProperty("proxyPassword", PropertiesUtil.getProperty("proxyPassword"));

	
	}
	/**
	 * 
	 * @param urlstr
	 */
	public HtmlHandler(String urlstr, String insertIntoHead,
			String insertIntoBody) {
		this.urlstr = urlstr;
		this.insertIntoHead = insertIntoHead;
		this.insertIntoBody = insertIntoBody;
	}

	/**
	 * 返回网址的字符集
	 * 
	 * @return
	 */
	public String getCharest() {
		if (this.charset != null) {
			return this.charset;
		} else {
			int size = this.getWebStr().size();
			if (size > 0) {
				for (int i = 0; i < size && this.charestFound == false; i++) {
					this.charset = this.getCharset(this.byteVector.get(i));
				}
			}
		}
		if (this.charset != null) {
			try {
				new String(new byte[2], this.charset);
			} catch (Exception e) {
				e.printStackTrace();
				this.charset = HtmlHandler.DEFAULTCHARSET;
			}
		}
		if (this.charset == null) {
			this.charset = HtmlHandler.DEFAULTCHARSET;
		}
		return this.charset;
	}

	/**
	 * 从字节数组返回网页的基址
	 * 
	 * @param bstr
	 */
	public void getBase(byte[] bstr) {
		try {
			// System.out.println(this.getCharest());
			String str = new String(bstr, this.getCharest());
			Matcher matcher = Pattern.compile(baseR).matcher(str);
			this.base = this.urlstr;
			if (matcher.find()) {
				try {
					isbaseFound = true;
					this.base = new URL(new URL(base), matcher.group(1))
							.toString();
				} catch (MalformedURLException ex) {
					Logger.getLogger(HtmlHandler.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			} else {
				this.base = this.urlstr;
				isbaseFound = false;
			}
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(HtmlHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * 返回网页的缺省基址
	 * 
	 * @return
	 */
	public String getBase() {
		if (this.base != null) {
			return this.base;
		}
		{
			int size = this.getWebStr().size();
			if (size > 0) {
				for (int i = 0; i < size && this.isbaseFound == false; i++) {
					this.getBase(this.byteVector.get(i));
				}
			}
		}
		return this.base;
	}

	/**
	 * @param myUrl
	 * @param mainFrame
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Vector<String> getHandleString() {
		if (this.vs.size() > 0) {
			return this.vs;
		}
		StringBuffer temp = null;
		int num = 0;
		Vector<byte[]> tempb = this.getWebStr();
		Pattern p;
		Matcher matcher = null;
		int begin = 0, end = 0;
		boolean isheadf = false;
		boolean isbodyf = false;
		boolean isdocf = false;
		int i = 0;
		for (int j = 0; j < tempb.size()
				&& (isbodyf == false || isheadf == false); j++) {
			try {
				temp = new StringBuffer(new String(tempb.get(j), this
						.getCharest()));
				// System.out.println(temp);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				temp = new StringBuffer(new String(tempb.get(j)));
			}
			// 加入要插在头部的信息
			if (isheadf == false) {
				p = Pattern.compile(HtmlHandler.head);
				matcher = p.matcher(temp);
				if (matcher.find()) {
					isheadf = true;
					end = matcher.end();
					begin = matcher.start();

					temp.insert(end, "<base href=\"" + this.getBase() + "\"/>"
							+ this.insertIntoHead);
				}
			}
			if (isbodyf == false) {
				p = Pattern.compile(HtmlHandler.body);
				matcher = null;
				begin = 0;
				i = 0;
				// 加入要插在body部的信息

				matcher = p.matcher(temp);
				if (matcher.find()) {
					isbodyf = true;
					begin = matcher.end();
					temp.insert(begin, this.insertIntoBody).toString();
				}
			}
			if (isheadf == true || isbodyf == true || isdocf == true) {
				String s = temp.toString();
				try {
					tempb.setElementAt(s.getBytes(this.getCharest()), j);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (isbodyf == false) {
			// this.vs.insertElementAt(this.insertInBody, 0);
		}
		if (isheadf == false) {
			this.vs.insertElementAt("<base href=\"" + this.getBase() + "\"/>"
					+ this.insertIntoHead, 0);
		}
		num = 0;
		for (i = 0; i < tempb.size(); i++) {
			num = num + tempb.get(i).length; // 统计总的长度
		}
		byte[] content;
		content = new byte[num];
		int conCurrent = 0; // 大数组当前的保存位置
		// 将字节数组合并到一个大数组中
		for (i = 0; i < tempb.size(); i++) {
			System.arraycopy(tempb.get(i), 0, content, conCurrent,
					tempb.get(i).length); // 将新的小数组复制到大数组里
			conCurrent = conCurrent + tempb.get(i).length;
		}
		try {
			this.vs.add(new String(content, this.getCharest()));
			// System.out.println(temp);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.vs;
	}

	/**
	 * 将网页的字节流保存在容器中
	 * 
	 * @return
	 */
	public Vector<byte[]> getWebStr() {
		if (this.byteVector.size() > 0) {
			return this.byteVector;
		}
		InputStream in = null;
		try {
			URL url = new URL(this.urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			Net.setHeader(conn); // 设置请求头

			this.getCharestFromHead(conn);

			String encoding = conn.getContentEncoding();// 检查页面是否返回gzip编码

			if (encoding != null && encoding.indexOf("gzip") >= 0) { // 如果页面返回gzip编码，则用GZIPInputStream读取并解码
				// System.out.println("页面编码" + conn.getContentEncoding());
				in = new BufferedInputStream(new GZIPInputStream(conn
						.getInputStream()), BUFFER_SIZE);
			} else {
				in = new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
			}

			// 用于保存字节数组，将每次读取的小数组合并成大数组
			int n = 1;
			byte[] contentTemp = null;
			byte temp[] = new byte[BUFFER_SIZE]; // 较小的数组，保存每次读取的字节流
			int conCurrent = 0; // 大数组当前的保存位置

			// 先读入网页中开始一部分源码，用于获取编码
			n = in.read(temp);
			if (n > 0) {
				if (n == BUFFER_SIZE) {
					this.byteVector.add(temp);
					temp = new byte[BUFFER_SIZE];
				} else {
					byte tb[] = new byte[n];
					System.arraycopy(temp, 0, tb, 0, n);
					this.byteVector.add(tb);
					temp = new byte[BUFFER_SIZE];
				}
			}// 先读入网页中开始一部分源码，用于获取编码
			byte[] content;
			content = new byte[webLength];
			conCurrent = 0; // 大数组当前的保存位置
			while (n != -1) { // 将剩下的源码读到大数组里面，提高效率和编码转换准确性
				// 读入网页中的内容
				n = in.read(temp);
				if (n > 0) {
					// 不能直接加入content，因为不是每次都可以读满
					if (n < webLength - conCurrent) // 如果还有足够空间
					{
						System.arraycopy(temp, 0, content, conCurrent, n); // 将新的小数组复制到大数组里
						conCurrent = conCurrent + n;
					} else { // 否则将缓冲区加入到容器中,先得到实际上存储的数组
						contentTemp = new byte[conCurrent];
						System
								.arraycopy(content, 0, contentTemp, 0,
										conCurrent);
						this.byteVector.add(contentTemp);
						conCurrent = 0;
						// 保存小数组
						System.arraycopy(temp, 0, content, conCurrent, n);
						conCurrent = conCurrent + n;
					}
				}
			}
			if (conCurrent > 0) {
				contentTemp = new byte[conCurrent];
				System.arraycopy(content, 0, contentTemp, 0, conCurrent);
				this.byteVector.add(contentTemp);
			}// 将剩下的源码读到大数组里面，提高效率和编码转换准确性
			in.close();
			conn.disconnect();
			return this.byteVector;
		} catch (IOException ex) {
			Logger.getLogger(HtmlHandler.class.getName()).log(Level.SEVERE,
					null, ex);
			return this.byteVector;

		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/**
	 * 从头部获取编码
	 * 
	 * @param conn
	 */
	private void getCharestFromHead(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		String s = conn.getHeaderField("Content-Type");
		if (s == null) {
			return;
		}
		Matcher matcher = Pattern.compile(charsetR).matcher(s);
		if (matcher.find()) {
			// 发现编码
			this.charestFound = true;
			this.charset = matcher.group(1);
		}
	}

	/**
	 * 由一段源码搜索字符集
	 * 
	 * @param con
	 * @return
	 */
	public String getCharset(byte[] con) // 注意线程安全
	{
		try {
			String s = new String(con);
			// System.out.println(s);
			Matcher matcher = Pattern.compile(charsetR).matcher(s);
			if (matcher.find()) {
				// 发现编码
				this.charestFound = true;

				return matcher.group(1);
			} else {
				return null;
			}
		} catch (Exception ex) {
			Logger.getLogger(MyHtmlParser.class.getName()).log(Level.SEVERE,
					"取字符集出错", ex);
			return null;
		}
	}

	/**
	 * 返回正确编码后的网页源代码
	 * 
	 * @return
	 */
	public String getSourceHtml() {
		if (this.sourceHtml != null) {
			return this.sourceHtml;
		}
		try {
			String temp = null;
			int num = 0;
			Vector<byte[]> tempb = this.getWebStr();
			for (int i = 0; i < tempb.size(); i++) {
				num = num + tempb.get(i).length; // 统计总的长度
			}
			byte[] content;
			content = new byte[num];
			int conCurrent = 0; // 大数组当前的保存位置
			// 将字节数组合并到一个大数组中
			for (int i = 0; i < tempb.size(); i++) {
				System.arraycopy(tempb.get(i), 0, content, conCurrent, tempb
						.get(i).length); // 将新的小数组复制到大数组里
				conCurrent = conCurrent + tempb.get(i).length;
			}
			try {
				temp = new String(content, this.getCharest());
				// System.out.println(temp);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sourceHtml = temp;
			// System.out.println(temp.length());
			return this.sourceHtml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}