package finance.datainit.dao;
import org.jsoup.helper.StringUtil;
import finance.datainit.vo.StockBaseInfoVO;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import finance.datainit.util.PaginationCondition;
import finance.datainit.util.PaginationParams;

@Repository
public class StockBaseInfoDAO {
	
	private static Logger logger = LoggerFactory.getLogger(StockBaseInfoDAO.class); 
    @Autowired
    private BaseDao baseDao;

    
    private void putUpdateParam(Map<String,Object> stockBaseInfoVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(stockBaseInfoVO!=null){  
        	if(stockBaseInfoVO.get("stockId")!=null){
	            sql.append(" stockid=:stockId,"); 
	            paramMap.put("stockId",stockBaseInfoVO.get("stockId"));
        	}
        	if(stockBaseInfoVO.get("stockCode")!=null){
	            sql.append(" stockcode=:stockCode,"); 
	            paramMap.put("stockCode",stockBaseInfoVO.get("stockCode"));
        	}
        	if(stockBaseInfoVO.get("stockName")!=null){
	            sql.append(" stockname=:stockName,"); 
	            paramMap.put("stockName",stockBaseInfoVO.get("stockName"));
        	}
        	if(stockBaseInfoVO.get("createTime")!=null){
	            sql.append(" createtime=:createTime,"); 
	            paramMap.put("createTime",stockBaseInfoVO.get("createTime"));
        	}
        	if(stockBaseInfoVO.get("eastmoneyBarUrl")!=null){
	            sql.append(" eastmoneybarurl=:eastmoneyBarUrl,"); 
	            paramMap.put("eastmoneyBarUrl",stockBaseInfoVO.get("eastmoneyBarUrl"));
        	}
        	if(stockBaseInfoVO.get("eastmoneyComment")!=null){
	            sql.append(" eastmoneycomment=:eastmoneyComment,"); 
	            paramMap.put("eastmoneyComment",stockBaseInfoVO.get("eastmoneyComment"));
        	}
        	if(stockBaseInfoVO.get("earningsRatio")!=null){
	            sql.append(" earningsratio=:earningsRatio,"); 
	            paramMap.put("earningsRatio",stockBaseInfoVO.get("earningsRatio"));
        	}
        	if(stockBaseInfoVO.get("updateDate")!=null){
	            sql.append(" updatedate=:updateDate"); 
	            paramMap.put("updateDate",stockBaseInfoVO.get("updateDate"));
        	}
        	
        	if(sql.length()>0&&sql.charAt(sql.length()-1)==','){
        		sql.deleteCharAt(sql.length()-1);
        	}
        }
    }
    
    
    /**
     * 新增StockBaseInfo记录
     */
    public boolean addStockBaseInfo(StockBaseInfoVO stockBaseInfoVO) throws Exception{
    	
        logger.debug("In function : addStockBaseInfo stockBaseInfoVO={}", new Object[]{stockBaseInfoVO});
        
        StringBuilder sql = new StringBuilder();
    	
        sql.append("INSERT INTO stock_base_info(stockid,stockcode,stockname,createtime,eastmoneybarurl,eastmoneycomment,earningsratio,updatedate)");
        sql.append(" VALUES (:stockId,:stockCode,:stockName,:createTime,:eastmoneyBarUrl,:eastmoneyComment,:earningsRatio,:updateDate)");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        
    	paramMap.put("stockId", stockBaseInfoVO.getStockId());
    	paramMap.put("stockCode", stockBaseInfoVO.getStockCode());
    	paramMap.put("stockName", stockBaseInfoVO.getStockName());
    	paramMap.put("createTime", stockBaseInfoVO.getCreateTime());
    	paramMap.put("eastmoneyBarUrl", stockBaseInfoVO.getEastmoneyBarUrl());
    	paramMap.put("eastmoneyComment", stockBaseInfoVO.getEastmoneyComment());
    	paramMap.put("earningsRatio", stockBaseInfoVO.getEarningsRatio());
    	paramMap.put("updateDate", stockBaseInfoVO.getUpdateDate());

        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : addStockBaseInfo");

        return result;
    }
    /**
     * 删除StockBaseInfo记录
     */
    public boolean deleteStockBaseInfoById(Integer stockId)throws Exception{
    	
    	logger.debug("In function : deleteStockBaseInfoById: stockId={}", new Object[]{stockId});
        
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM stock_base_info WHERE stockid=:stockId");
    
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("stockId", stockId);
        
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : deleteStockBaseInfoById");
        return result;
    }
    /**
     * 修改StockBaseInfo记录
     */
    public boolean updateStockBaseInfoByParam(Map<String,Object> setParam,Map<String,Object>whereParam) throws Exception{
    	
        logger.debug("In function : updateStockBaseInfoByParam: setParam={},whereParam={}", new Object[]{setParam,whereParam});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE stock_base_info SET ");
        putUpdateParam(setParam,sql,paramMap);
    	sql.append(" WHERE 1=1 ");	
        putWhereParam(whereParam,sql,paramMap);

        logger.debug(" About to execute sql={} setParam={},whereParam={}", new Object[]{sql.toString(), paramMap,whereParam});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateStockBaseInfoByParam");
        return result;
    }
    	
    /**
     * 更新StockBaseInfo记录
     */
    public boolean updateStockBaseInfoById(Integer stockId,Map<String,Object> setParam) throws Exception{
    	
        logger.debug("In function : updateStockBaseInfoById: stockId={}", new Object[]{stockId});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE stock_base_info SET ");
        putUpdateParam(setParam,sql,paramMap);
        sql.append(" WHERE stockid=:stockId");
        paramMap.put("stockId", stockId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateStockBaseInfoById");
        return result;
    }   
    
    private void putWhereParam(Map<String,Object> stockBaseInfoVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(stockBaseInfoVO!=null){  
        	if(stockBaseInfoVO.get("stockId")!=null){
	            sql.append("   AND stockid=:stockId ");
	            paramMap.put("stockId",stockBaseInfoVO.get("stockId"));
	        }
        	if(stockBaseInfoVO.get("stockCode")!=null){
	            sql.append("   AND stockcode=:stockCode ");
	            paramMap.put("stockCode",stockBaseInfoVO.get("stockCode"));
	        }
        	if(stockBaseInfoVO.get("stockName")!=null){
	            sql.append("   AND stockname=:stockName ");
	            paramMap.put("stockName",stockBaseInfoVO.get("stockName"));
	        }
        	if(stockBaseInfoVO.get("createTime")!=null){
	            sql.append("   AND createtime=:createTime ");
	            paramMap.put("createTime",stockBaseInfoVO.get("createTime"));
	        }
        	if(stockBaseInfoVO.get("eastmoneyBarUrl")!=null){
	            sql.append("   AND eastmoneybarurl=:eastmoneyBarUrl ");
	            paramMap.put("eastmoneyBarUrl",stockBaseInfoVO.get("eastmoneyBarUrl"));
	        }
        	if(stockBaseInfoVO.get("eastmoneyComment")!=null){
	            sql.append("   AND eastmoneycomment=:eastmoneyComment ");
	            paramMap.put("eastmoneyComment",stockBaseInfoVO.get("eastmoneyComment"));
	        }
        	if(stockBaseInfoVO.get("earningsRatio")!=null){
	            sql.append("   AND earningsratio=:earningsRatio ");
	            paramMap.put("earningsRatio",stockBaseInfoVO.get("earningsRatio"));
	        }
        	if(stockBaseInfoVO.get("updateDate")!=null){
	            sql.append("   AND updatedate=:updateDate ");
	            paramMap.put("updateDate",stockBaseInfoVO.get("updateDate"));
	        }
        }
    }

    
    private String getSelectSQL(){
    	String sql="SELECT stockid,stockcode,stockname,createtime,eastmoneybarurl,eastmoneycomment,earningsratio,updatedate FROM stock_base_info WHERE 1=1 ";
    	return sql;
    }
  
    /**
     * 查询StockBaseInfo记录
     */
    public List<StockBaseInfoVO> getStockBaseInfoListByParam(Map<String,Object> stockBaseInfoVO,PaginationCondition pc)throws Exception{
    	
        logger.debug("In function : getStockBaseInfoListByParam: stockBaseInfoVO={},pc={}", new Object[]{stockBaseInfoVO,pc});
        Map<String, Object> paramMap = new HashMap<String, Object>();
          
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        
        putWhereParam(stockBaseInfoVO,sql,paramMap);
        String sqlstr=PaginationParams.convertSqlStatement(sql.toString(), pc, paramMap);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sqlstr, paramMap});
        List<StockBaseInfoVO> result = baseDao.namedParameterJdbcTemplate.query(sqlstr, paramMap, new BeanPropertyRowMapper<StockBaseInfoVO>(StockBaseInfoVO.class));  
        
        logger.debug("End function : getStockBaseInfoListByParam");
        return result;
    }
    
    
    /**
     * 查询总数
     */
    public int getTotalStockBaseInfoByParam(Map<String,Object> stockBaseInfoVO) throws Exception{
    	logger.debug("In function : getTotalStockBaseInfoByParam: stockBaseInfoVO={}", new Object[]{stockBaseInfoVO});
    	StringBuilder sql=new StringBuilder();
    	sql.append("SELECT COUNT(1) FROM stock_base_info WHERE 1=1 ");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	putWhereParam(stockBaseInfoVO,sql,paramMap);
    	//查询记录总数
        logger.debug(" About to execute sql={} paramMap={}",new Object[]{sql.toString(), paramMap});
        int result = baseDao.namedParameterJdbcTemplate.queryForInt(sql.toString(), paramMap);
        
        logger.debug(" End Function: getTotalStockBaseInfoByParam");
        return result;
    }

    /**
     * 根据id查询StockBaseInfo记录
     */
    public StockBaseInfoVO getStockBaseInfoById(Integer stockId)throws Exception{
    	
        logger.debug("In function : getStockBaseInfoById: stockId={}", new Object[]{stockId});
        
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        sql.append(" AND stockid=:stockId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("stockId", stockId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        StockBaseInfoVO result = baseDao.namedParameterJdbcTemplate.queryForObject(sql.toString(), paramMap, new BeanPropertyRowMapper<StockBaseInfoVO>(StockBaseInfoVO.class));   
        logger.debug("End function : getStockBaseInfoById");
        return result;
    }
    
    
    public boolean isStockExists(String stockCode){
    	StringBuilder sql=new StringBuilder();
    	sql.append("SELECT COUNT(1) FROM stock_base_info WHERE stockCode=:stockCode");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("stockCode", stockCode);
    	//查询记录总数
        int result = baseDao.namedParameterJdbcTemplate.queryForInt(sql.toString(), paramMap);
        
        return result>0?true:false;
    }
}