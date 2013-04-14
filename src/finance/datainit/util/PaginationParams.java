package finance.datainit.util;

import java.util.Map;

public class PaginationParams {

	/**
	 * 描述：组成加了分页查询的sql</br> 
	 * 参数：String sql, PaginationCondition pgCondition, Map(String, Object) paramMap</br>
	 * 返回：String sql</br> 
	 * 版本�?012-08-13 svjie created</br>
	 * */
	public static String convertSqlStatement(String sql, PaginationCondition pgCondition, Map<String, Object> paramMap) {
		StringBuilder result = new StringBuilder(sql);
		if (pgCondition != null) {
            result.append(" limit :start, :n  ");
			paramMap.put("n", pgCondition.getPageSize());
	        paramMap.put("start", (pgCondition.getPageNum() - 1) * pgCondition.getPageSize());
		}
		return result.toString();
	}
}
