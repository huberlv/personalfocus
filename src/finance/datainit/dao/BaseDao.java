package finance.datainit.dao;


import java.util.List;

import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public  class BaseDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired  
	@Qualifier("namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * ��������
	 * @param insertSql update��SQL���
	 * @param beans ֵ����
	 * @throws SoaException
	 * @author Molo.Wu �ⴺ��
	 * @since 2012-9-22
	 */
	public void batchUpdateBeanList(String insertSql, List<? extends Object> beans) throws Exception{ 
			SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(beans.toArray());
			int[] results = this.namedParameterJdbcTemplate.batchUpdate(insertSql, params);
	}
	
}
