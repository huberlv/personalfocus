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
	 * 配置http连接的属性，防止被封
	 * 
	 * @param url_c
	 *            http连接
	 */
	public static void setHeader(HttpURLConnection url_c) {
		try {
			url_c
					.setRequestProperty(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; InfoPath.2; CIBA; MAXTHON 2.0)");
			url_c.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置只接收文本，不接收其余多媒体
			url_c.setRequestProperty("Accept", "*/*");
			url_c.setRequestProperty("Connection", "keep-alive");
			url_c.setRequestProperty("Pragma", "no-cache");
			url_c.setRequestProperty("Cache-Control", "no-cache");
			url_c.setUseCaches(false);
			url_c.setRequestMethod("GET");
			HttpURLConnection.setFollowRedirects(true);
			url_c.setInstanceFollowRedirects(true);
			url_c.setRequestProperty("Accept-Encoding", "gzip"); // 接收压缩内容
		} catch (Exception ex) {
			Logger.getLogger(MyHtmlParser.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * 发送短信,由于短信服务提供商的限制，建立一次连接只能发送一条短信 一条短信不能超过180个字符
	 * 
	 * @param message
	 *            信息字符串，包含信息参数
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
								replace, "")); // 发送信息
				wr.flush();
				// Get the response
				rd = new BufferedReader(new InputStreamReader(conn
						.getInputStream()));
				wr.close();
				rd.close();
				begin = begin + 170;
			}
			conn = url.openConnection(); // 取得连接
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			// System.out.println(url.toString() + head);
			wr.write(head
					+ message.substring(begin, message.length()).replaceAll(
							replace, "")); // 发送信息
			// wr.write(head+"123"); //发送信息
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
