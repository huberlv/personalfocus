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
		 * ���̸�Ŀ¼����
		 * �磺D:\eclipseworks\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\personalfocus\
		 */
		String rootPath = this.getServletContext().getRealPath("/"); 
		/**
		 * log4jĿ¼
		 * �����Զ���logĿ¼
		 */
		String logPath = this.getServletConfig().getInitParameter("logPath"); 
		//���log4jĿ¼���������ڣ���ʹ�ù��̸�Ŀ¼����
		logPath = (logPath == null || "".equals(logPath)) ? rootPath : logPath;	
		Properties props = new Properties();
		try {
			props.load(LogServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		props.setProperty("log4j.appender.R.File", props.getProperty("log4j.appender.R.File").replace("logPath", logPath));
		PropertyConfigurator.configure(props);//װ��log4j������Ϣ 
		System.out.println("log4j.appender.R.File:"+props.getProperty("log4j.appender.R.File"));
		super.init();
	}

}
