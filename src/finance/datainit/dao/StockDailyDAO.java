package finance.datainit.dao;
import org.jsoup.helper.StringUtil;
import finance.datainit.vo.StockDailyVO;
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
public class StockDailyDAO {
	
	private static Logger logger = LoggerFactory.getLogger(StockDailyDAO.class); 
    @Autowired
    private BaseDao baseDao;

    
    private void putUpdateParam(Map<String,Object> stockDailyVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(stockDailyVO!=null){  
        	if(stockDailyVO.get("date")!=null){
	            sql.append(" date=:date,"); 
	            paramMap.put("date",stockDailyVO.get("date"));
        	}
        	if(stockDailyVO.get("open")!=null){
	            sql.append(" open=:open,"); 
	            paramMap.put("open",stockDailyVO.get("open"));
        	}
        	if(stockDailyVO.get("high")!=null){
	            sql.append(" high=:high,"); 
	            paramMap.put("high",stockDailyVO.get("high"));
        	}
        	if(stockDailyVO.get("low")!=null){
	            sql.append(" low=:low,"); 
	            paramMap.put("low",stockDailyVO.get("low"));
        	}
        	if(stockDailyVO.get("close")!=null){
	            sql.append(" close=:close,"); 
	            paramMap.put("close",stockDailyVO.get("close"));
        	}
        	if(stockDailyVO.get("volume")!=null){
	            sql.append(" volume=:volume,"); 
	            paramMap.put("volume",stockDailyVO.get("volume"));
        	}
        	if(stockDailyVO.get("adjClose")!=null){
	            sql.append(" adjclose=:adjClose,"); 
	            paramMap.put("adjClose",stockDailyVO.get("adjClose"));
        	}
        	if(stockDailyVO.get("stockId")!=null){
	            sql.append(" stockid=:stockId,"); 
	            paramMap.put("stockId",stockDailyVO.get("stockId"));
        	}
        	if(stockDailyVO.get("dailyId")!=null){
	            sql.append(" daily_id=:dailyId"); 
	            paramMap.put("dailyId",stockDailyVO.get("dailyId"));
        	}
        	
        	if(sql.length()>0&&sql.charAt(sql.length()-1)==','){
        		sql.deleteCharAt(sql.length()-1);
        	}
        }
    }
    
    
    /**
     * 新增StockDaily记录
     */
    public boolean addStockDaily(StockDailyVO stockDailyVO) throws Exception{
    	
        logger.debug("In function : addStockDaily stockDailyVO={}", new Object[]{stockDailyVO});
        
        StringBuilder sql = new StringBuilder();
    	
        sql.append("INSERT INTO stock_daily(date,open,high,low,close,volume,adjclose,stockid,daily_id)");
        sql.append(" VALUES (:date,:open,:high,:low,:close,:volume,:adjClose,:stockId,:dailyId)");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        
    	paramMap.put("date", stockDailyVO.getDate());
    	paramMap.put("open", stockDailyVO.getOpen());
    	paramMap.put("high", stockDailyVO.getHigh());
    	paramMap.put("low", stockDailyVO.getLow());
    	paramMap.put("close", stockDailyVO.getClose());
    	paramMap.put("volume", stockDailyVO.getVolume());
    	paramMap.put("adjClose", stockDailyVO.getAdjClose());
    	paramMap.put("stockId", stockDailyVO.getStockId());
    	paramMap.put("dailyId", stockDailyVO.getDailyId());

        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : addStockDaily");

        return result;
    }
    /**
     * 删除StockDaily记录
     */
    public boolean deleteStockDailyById(Integer dailyId)throws Exception{
    	
    	logger.debug("In function : deleteStockDailyById: dailyId={}", new Object[]{dailyId});
        
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM stock_daily WHERE daily_id=:dailyId");
    
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("dailyId", dailyId);
        
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : deleteStockDailyById");
        return result;
    }
    /**
     * 修改StockDaily记录
     */
    public boolean updateStockDailyByParam(Map<String,Object> setParam,Map<String,Object>whereParam) throws Exception{
    	
        logger.debug("In function : updateStockDailyByParam: setParam={},whereParam={}", new Object[]{setParam,whereParam});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE stock_daily SET ");
        putUpdateParam(setParam,sql,paramMap);
    	sql.append(" WHERE 1=1 ");	
        putWhereParam(whereParam,sql,paramMap);

        logger.debug(" About to execute sql={} setParam={},whereParam={}", new Object[]{sql.toString(), paramMap,whereParam});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateStockDailyByParam");
        return result;
    }
    	
    /**
     * 更新StockDaily记录
     */
    public boolean updateStockDailyById(Integer dailyId,Map<String,Object> setParam) throws Exception{
    	
        logger.debug("In function : updateStockDailyById: dailyId={}", new Object[]{dailyId});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE stock_daily SET ");
        putUpdateParam(setParam,sql,paramMap);
        sql.append(" WHERE daily_id=:dailyId");
        paramMap.put("dailyId", dailyId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateStockDailyById");
        return result;
    }   
    
    private void putWhereParam(Map<String,Object> stockDailyVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(stockDailyVO!=null){  
        	if(stockDailyVO.get("date")!=null){
	            sql.append("   AND date=:date ");
	            paramMap.put("date",stockDailyVO.get("date"));
	        }
        	if(stockDailyVO.get("open")!=null){
	            sql.append("   AND open=:open ");
	            paramMap.put("open",stockDailyVO.get("open"));
	        }
        	if(stockDailyVO.get("high")!=null){
	            sql.append("   AND high=:high ");
	            paramMap.put("high",stockDailyVO.get("high"));
	        }
        	if(stockDailyVO.get("low")!=null){
	            sql.append("   AND low=:low ");
	            paramMap.put("low",stockDailyVO.get("low"));
	        }
        	if(stockDailyVO.get("close")!=null){
	            sql.append("   AND close=:close ");
	            paramMap.put("close",stockDailyVO.get("close"));
	        }
        	if(stockDailyVO.get("volume")!=null){
	            sql.append("   AND volume=:volume ");
	            paramMap.put("volume",stockDailyVO.get("volume"));
	        }
        	if(stockDailyVO.get("adjClose")!=null){
	            sql.append("   AND adjclose=:adjClose ");
	            paramMap.put("adjClose",stockDailyVO.get("adjClose"));
	        }
        	if(stockDailyVO.get("stockId")!=null){
	            sql.append("   AND stockid=:stockId ");
	            paramMap.put("stockId",stockDailyVO.get("stockId"));
	        }
        	if(stockDailyVO.get("dailyId")!=null){
	            sql.append("   AND daily_id=:dailyId ");
	            paramMap.put("dailyId",stockDailyVO.get("dailyId"));
	        }
        }
    }

    
    private String getSelectSQL(){
    	String sql="SELECT date,open,high,low,close,volume,adjclose,stockid,daily_id FROM stock_daily WHERE 1=1 ";
    	return sql;
    }
  
    /**
     * 查询StockDaily记录
     */
    public List<StockDailyVO> getStockDailyListByParam(Map<String,Object> stockDailyVO,PaginationCondition pc)throws Exception{
    	
        logger.debug("In function : getStockDailyListByParam: stockDailyVO={},pc={}", new Object[]{stockDailyVO,pc});
        Map<String, Object> paramMap = new HashMap<String, Object>();
          
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        
        putWhereParam(stockDailyVO,sql,paramMap);
        String sqlstr=PaginationParams.convertSqlStatement(sql.toString(), pc, paramMap);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sqlstr, paramMap});
        List<StockDailyVO> result = baseDao.namedParameterJdbcTemplate.query(sqlstr, paramMap, new BeanPropertyRowMapper<StockDailyVO>(StockDailyVO.class));  
        
        logger.debug("End function : getStockDailyListByParam");
        return result;
    }
    
    
    /**
     * 查询总数
     */
    public int getTotalStockDailyByParam(Map<String,Object> stockDailyVO) throws Exception{
    	logger.debug("In function : getTotalStockDailyByParam: stockDailyVO={}", new Object[]{stockDailyVO});
    	StringBuilder sql=new StringBuilder();
    	sql.append("SELECT COUNT(1) FROM stock_daily WHERE 1=1 ");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	putWhereParam(stockDailyVO,sql,paramMap);
    	//查询记录总数
        logger.debug(" About to execute sql={} paramMap={}",new Object[]{sql.toString(), paramMap});
        int result = baseDao.namedParameterJdbcTemplate.queryForInt(sql.toString(), paramMap);
        
        logger.debug(" End Function: getTotalStockDailyByParam");
        return result;
    }

    /**
     * 根据id查询StockDaily记录
     */
    public StockDailyVO getStockDailyById(Integer dailyId)throws Exception{
    	
        logger.debug("In function : getStockDailyById: dailyId={}", new Object[]{dailyId});
        
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        sql.append(" AND daily_id=:dailyId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dailyId", dailyId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        StockDailyVO result = baseDao.namedParameterJdbcTemplate.queryForObject(sql.toString(), paramMap, new BeanPropertyRowMapper<StockDailyVO>(StockDailyVO.class));   
        logger.debug("End function : getStockDailyById");
        return result;
    }
}