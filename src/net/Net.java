/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kingway.util.PropertiesUtil;

import document.MyHtmlParser;

/**
 * 
 * @author Administrator
 */
public class Net {
	static{ 
        System.out.println("PropertiesUtil.getProperty(proxySet):"+PropertiesUtil.getProperty("proxySet"));
		System.setProperty("proxySet", PropertiesUtil.getProperty("proxySet"));
        System.setProperty("proxyHost",  PropertiesUtil.getProperty("proxyHost"));
        System.setProperty("proxyPort", PropertiesUtil.getProperty("proxyPort"));
        System.setProperty("proxyUsername", PropertiesUtil.getProperty("proxyUsername"));
        System.setProperty("proxyPassword", PropertiesUtil.getProperty("proxyPassword"));
	}

	private static String replace = "&+";

	/**
	 * ����http���ӵ����ԣ���ֹ����
	 * 
	 * @param url_c
	 *            http����
	 */
	public static void setHeader(HttpURLConnection url_c) {
		try {
			url_c
					.setRequestProperty(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; InfoPath.2; CIBA; MAXTHON 2.0)");
			url_c.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// ����ֻ�����ı��������������ý��
			url_c.setRequestProperty("Accept", "*/*");
			url_c.setRequestProperty("Connection", "keep-alive");
			url_c.setRequestProperty("Pragma", "no-cache");
			url_c.setRequestProperty("Cache-Control", "no-cache");
			url_c.setUseCaches(false);
			url_c.setRequestMethod("GET");
			HttpURLConnection.setFollowRedirects(true);
			url_c.setInstanceFollowRedirects(true);
			url_c.setRequestProperty("Accept-Encoding", "gzip"); // ����ѹ������
		} catch (Exception ex) {
			Logger.getLogger(MyHtmlParser.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * ���Ͷ���,���ڶ��ŷ����ṩ�̵����ƣ�����һ������ֻ�ܷ���һ������ һ�����Ų��ܳ���180���ַ�
	 * 
	 * @param message
	 *            ��Ϣ�ַ�����������Ϣ����
	 * @throws IOException
	 * 
	 */
	public static void sendMessage(URL url, String message) throws IOException {
		int headend = message.lastIndexOf("=");
		String head = message.substring(0, headend + 1);
		int begin = headend + 1;
		URLConnection conn;
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		try {
			while (message.length() - begin > 170) {

				conn = url.openConnection();

				conn.setDoOutput(true);

				wr = new OutputStreamWriter(conn.getOutputStream());
				// System.out.println(head + message.substring(begin, begin +
				// 170));
				wr.write(head
						+ message.substring(begin, begin + 170).replaceAll(
								replace, "")); // ������Ϣ
				wr.flush();
				// Get the response
				rd = new BufferedReader(new InputStreamReader(conn
						.getInputStream()));
				wr.close();
				rd.close();
				begin = begin + 170;
			}
			conn = url.openConnection(); // ȡ������
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			// System.out.println(url.toString() + head);
			wr.write(head
					+ message.substring(begin, message.length()).replaceAll(
							replace, "")); // ������Ϣ
			// wr.write(head+"123"); //������Ϣ
			wr.flush();
			// Get the response
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (wr != null) {
				wr.close();
			}
			if (rd != null) {
				rd.close();
			}
		}
	}
}
