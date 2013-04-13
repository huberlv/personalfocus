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
 * htmlԤ�����࣬��html����Ԥ����
 * 
 * @author Administrator
 */
public class HtmlHandler {
	public static int BUFFER_SIZE = 2048;
	public static int webLength = 102400; // ���滺������С,100K
	/** �Ƿ��ֱ���ı�־ */
	private boolean charestFound = false;
	/** ��ҳ�ı��� */
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
    
	// δ���Ǿ����ȷ���
	public static String DEFINE_TOP_BODY_STR = "<div name='noaction' id=\"waitDiv\" style=\"text-align:center;\">&nbsp;</div><div id=\"topTips\""
			+ "><p></p><p align=\"center\">��������ע����ƽ̨��</p>"
			+ "<p align=\"center\">�������룬���Ժ�</p>"
			+ "<p align=\"center\">������ʾ���ƶ���굽��ע����Ϣλ�ã������ֺ�ɫ��ʱ������ע��λ������!���ҳ�泤������Ӧ������<a href=\"/personalfocus/index/showHelp\" target='_blank'>����</a></p></div>";
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
	 * ������ַ���ַ���
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
	 * ���ֽ����鷵����ҳ�Ļ�ַ
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
	 * ������ҳ��ȱʡ��ַ
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
			// ����Ҫ����ͷ������Ϣ
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
				// ����Ҫ����body������Ϣ

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
			num = num + tempb.get(i).length; // ͳ���ܵĳ���
		}
		byte[] content;
		content = new byte[num];
		int conCurrent = 0; // �����鵱ǰ�ı���λ��
		// ���ֽ�����ϲ���һ����������
		for (i = 0; i < tempb.size(); i++) {
			System.arraycopy(tempb.get(i), 0, content, conCurrent,
					tempb.get(i).length); // ���µ�С���鸴�Ƶ���������
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
	 * ����ҳ���ֽ���������������
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

			Net.setHeader(conn); // ��������ͷ

			this.getCharestFromHead(conn);

			String encoding = conn.getContentEncoding();// ���ҳ���Ƿ񷵻�gzip����

			if (encoding != null && encoding.indexOf("gzip") >= 0) { // ���ҳ�淵��gzip���룬����GZIPInputStream��ȡ������
				// System.out.println("ҳ�����" + conn.getContentEncoding());
				in = new BufferedInputStream(new GZIPInputStream(conn
						.getInputStream()), BUFFER_SIZE);
			} else {
				in = new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
			}

			// ���ڱ����ֽ����飬��ÿ�ζ�ȡ��С����ϲ��ɴ�����
			int n = 1;
			byte[] contentTemp = null;
			byte temp[] = new byte[BUFFER_SIZE]; // ��С�����飬����ÿ�ζ�ȡ���ֽ���
			int conCurrent = 0; // �����鵱ǰ�ı���λ��

			// �ȶ�����ҳ�п�ʼһ����Դ�룬���ڻ�ȡ����
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
			}// �ȶ�����ҳ�п�ʼһ����Դ�룬���ڻ�ȡ����
			byte[] content;
			content = new byte[webLength];
			conCurrent = 0; // �����鵱ǰ�ı���λ��
			while (n != -1) { // ��ʣ�µ�Դ��������������棬���Ч�ʺͱ���ת��׼ȷ��
				// ������ҳ�е�����
				n = in.read(temp);
				if (n > 0) {
					// ����ֱ�Ӽ���content����Ϊ����ÿ�ζ����Զ���
					if (n < webLength - conCurrent) // ��������㹻�ռ�
					{
						System.arraycopy(temp, 0, content, conCurrent, n); // ���µ�С���鸴�Ƶ���������
						conCurrent = conCurrent + n;
					} else { // ���򽫻��������뵽������,�ȵõ�ʵ���ϴ洢������
						contentTemp = new byte[conCurrent];
						System
								.arraycopy(content, 0, contentTemp, 0,
										conCurrent);
						this.byteVector.add(contentTemp);
						conCurrent = 0;
						// ����С����
						System.arraycopy(temp, 0, content, conCurrent, n);
						conCurrent = conCurrent + n;
					}
				}
			}
			if (conCurrent > 0) {
				contentTemp = new byte[conCurrent];
				System.arraycopy(content, 0, contentTemp, 0, conCurrent);
				this.byteVector.add(contentTemp);
			}// ��ʣ�µ�Դ��������������棬���Ч�ʺͱ���ת��׼ȷ��
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
	 * ��ͷ����ȡ����
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
			// ���ֱ���
			this.charestFound = true;
			this.charset = matcher.group(1);
		}
	}

	/**
	 * ��һ��Դ�������ַ���
	 * 
	 * @param con
	 * @return
	 */
	public String getCharset(byte[] con) // ע���̰߳�ȫ
	{
		try {
			String s = new String(con);
			// System.out.println(s);
			Matcher matcher = Pattern.compile(charsetR).matcher(s);
			if (matcher.find()) {
				// ���ֱ���
				this.charestFound = true;

				return matcher.group(1);
			} else {
				return null;
			}
		} catch (Exception ex) {
			Logger.getLogger(MyHtmlParser.class.getName()).log(Level.SEVERE,
					"ȡ�ַ�������", ex);
			return null;
		}
	}

	/**
	 * ������ȷ��������ҳԴ����
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
				num = num + tempb.get(i).length; // ͳ���ܵĳ���
			}
			byte[] content;
			content = new byte[num];
			int conCurrent = 0; // �����鵱ǰ�ı���λ��
			// ���ֽ�����ϲ���һ����������
			for (int i = 0; i < tempb.size(); i++) {
				System.arraycopy(tempb.get(i), 0, content, conCurrent, tempb
						.get(i).length); // ���µ�С���鸴�Ƶ���������
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