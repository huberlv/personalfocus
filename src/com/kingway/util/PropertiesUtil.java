package com.kingway.util;

import java.io.IOException;
import java.util.Properties;


public final class PropertiesUtil {
	
	private PropertiesUtil(){}
	
	private static Properties props = new Properties();
	
	static{

		try {
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("net/baseurl.properties"));
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("systemmanager/systemmanager.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadPropertiesFile(String filePath){
		try {
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
	
}
