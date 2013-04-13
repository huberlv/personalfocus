package log;


import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class LogServlet extends HttpServlet{
	
	@Override
	public void init() throws ServletException {
		/**
		 * 工程根目录特征
		 * 如：D:\eclipseworks\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\personalfocus\
		 */
		String rootPath = this.getServletContext().getRealPath("/"); 
		/**
		 * log4j目录
		 * 用于自定义log目录
		 */
		String logPath = this.getServletConfig().getInitParameter("logPath"); 
		//如果log4j目录特征不存在，则使用工程根目录特征
		logPath = (logPath == null || "".equals(logPath)) ? rootPath : logPath;	
		Properties props = new Properties();
		try {
			props.load(LogServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		props.setProperty("log4j.appender.R.File", props.getProperty("log4j.appender.R.File").replace("logPath", logPath));
		PropertyConfigurator.configure(props);//装入log4j配置信息 
		System.out.println("log4j.appender.R.File:"+props.getProperty("log4j.appender.R.File"));
		super.init();
	}

}
