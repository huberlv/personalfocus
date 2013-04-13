package net;

import com.kingway.util.PropertiesUtil;

public class URLS {
	public static String INDEX_URL = null;

	public static String getHostAddress() {
		if (INDEX_URL != null) {
			return INDEX_URL;
		}
		INDEX_URL = PropertiesUtil.getProperty("baseurl");
		return INDEX_URL;
	}
}
