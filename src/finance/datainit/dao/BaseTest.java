package finance.datainit.dao;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * junit test 中用到数据库连接时需要用到数据库连接时，test类中可以继续本类来获取application context.
 * 
 * @author huber.lv
 * @since 2012-08-08
 */
public class BaseTest extends AbstractDependencyInjectionSpringContextTests {
	public String[] getConfigLocations() {
		String[] configLocations = { "classpath:applicationContext.xml"};
		return configLocations;
	}
}