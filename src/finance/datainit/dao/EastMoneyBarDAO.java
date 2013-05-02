package finance.datainit.dao;
import org.jsoup.helper.StringUtil;
import finance.datainit.vo.EastMoneyBarVO;
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
public class EastMoneyBarDAO {
	
	private static Logger logger = LoggerFactory.getLogger(EastMoneyBarDAO.class); 
    @Autowired
    private BaseDao baseDao;

    
    private void putUpdateParam(Map<String,Object> eastMoneyBarVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(eastMoneyBarVO!=null){  
        	if(eastMoneyBarVO.get("stockId")!=null){
	            sql.append(" stockid=:stockId,"); 
	            paramMap.put("stockId",eastMoneyBarVO.get("stockId"));
        	}
        	if(eastMoneyBarVO.get("title")!=null){
	            sql.append(" title=:title,"); 
	            paramMap.put("title",eastMoneyBarVO.get("title"));
        	}
        	if(eastMoneyBarVO.get("time")!=null){
	            sql.append(" time=:time,"); 
	            paramMap.put("time",eastMoneyBarVO.get("time"));
        	}
        	if(eastMoneyBarVO.get("autor")!=null){
	            sql.append(" autor=:autor,"); 
	            paramMap.put("autor",eastMoneyBarVO.get("autor"));
        	}
        	if(eastMoneyBarVO.get("ip")!=null){
	            sql.append(" ip=:ip,"); 
	            paramMap.put("ip",eastMoneyBarVO.get("ip"));
        	}
        	if(eastMoneyBarVO.get("createTime")!=null){
	            sql.append(" createtime=:createTime,"); 
	            paramMap.put("createTime",eastMoneyBarVO.get("createTime"));
        	}
        	if(eastMoneyBarVO.get("topicId")!=null){
	            sql.append(" topicid=:topicId,"); 
	            paramMap.put("topicId",eastMoneyBarVO.get("topicId"));
        	}
        	if(eastMoneyBarVO.get("url")!=null){
	            sql.append(" url=:url"); 
	            paramMap.put("url",eastMoneyBarVO.get("url"));
        	}
        	
        	if(sql.length()>0&&sql.charAt(sql.length()-1)==','){
        		sql.deleteCharAt(sql.length()-1);
        	}
        }
    }
    
    
    /**
     * 新增EastMoneyBar记录
     */
    public boolean addEastMoneyBar(EastMoneyBarVO eastMoneyBarVO) throws Exception{
    	
        logger.debug("In function : addEastMoneyBar eastMoneyBarVO={}", new Object[]{eastMoneyBarVO});
        
        StringBuilder sql = new StringBuilder();
    	
        sql.append("INSERT INTO east_money_bar(stockid,title,time,autor,ip,createtime,topicid,url)");
        sql.append(" VALUES (:stockId,:title,:time,:autor,:ip,:createTime,:topicId,:url)");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        
    	paramMap.put("stockId", eastMoneyBarVO.getStockId());
    	paramMap.put("title", eastMoneyBarVO.getTitle());
    	paramMap.put("time", eastMoneyBarVO.getTime());
    	paramMap.put("autor", eastMoneyBarVO.getAutor());
    	paramMap.put("ip", eastMoneyBarVO.getIp());
    	paramMap.put("createTime", eastMoneyBarVO.getCreateTime());
    	paramMap.put("topicId", eastMoneyBarVO.getTopicId());
    	paramMap.put("url", eastMoneyBarVO.getUrl());

        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : addEastMoneyBar");

        return result;
    }
    /**
     * 删除EastMoneyBar记录
     */
    public boolean deleteEastMoneyBarById(Integer topicId)throws Exception{
    	
    	logger.debug("In function : deleteEastMoneyBarById: topicId={}", new Object[]{topicId});
        
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM east_money_bar WHERE topicid=:topicId");
    
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("topicId", topicId);
        
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : deleteEastMoneyBarById");
        return result;
    }
    /**
     * 修改EastMoneyBar记录
     */
    public boolean updateEastMoneyBarByParam(Map<String,Object> setParam,Map<String,Object>whereParam) throws Exception{
    	
        logger.debug("In function : updateEastMoneyBarByParam: setParam={},whereParam={}", new Object[]{setParam,whereParam});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE east_money_bar SET ");
        putUpdateParam(setParam,sql,paramMap);
    	sql.append(" WHERE 1=1 ");	
        putWhereParam(whereParam,sql,paramMap);

        logger.debug(" About to execute sql={} setParam={},whereParam={}", new Object[]{sql.toString(), paramMap,whereParam});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateEastMoneyBarByParam");
        return result;
    }
    	
    /**
     * 更新EastMoneyBar记录
     */
    public boolean updateEastMoneyBarById(Integer topicId,Map<String,Object> setParam) throws Exception{
    	
        logger.debug("In function : updateEastMoneyBarById: topicId={}", new Object[]{topicId});
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        sql.append("UPDATE east_money_bar SET ");
        putUpdateParam(setParam,sql,paramMap);
        sql.append(" WHERE topicid=:topicId");
        paramMap.put("topicId", topicId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        
        boolean result=baseDao.namedParameterJdbcTemplate.update(sql.toString(), paramMap)>0?true:false;

        logger.debug("End function : updateEastMoneyBarById");
        return result;
    }   
    
    private void putWhereParam(Map<String,Object> eastMoneyBarVO,StringBuilder sql, Map<String, Object> paramMap){
    	if(eastMoneyBarVO!=null){  
        	if(eastMoneyBarVO.get("stockId")!=null){
	            sql.append("   AND stockid=:stockId ");
	            paramMap.put("stockId",eastMoneyBarVO.get("stockId"));
	        }
        	if(eastMoneyBarVO.get("title")!=null){
	            sql.append("   AND title=:title ");
	            paramMap.put("title",eastMoneyBarVO.get("title"));
	        }
        	if(eastMoneyBarVO.get("time")!=null){
	            sql.append("   AND time=:time ");
	            paramMap.put("time",eastMoneyBarVO.get("time"));
	        }
        	if(eastMoneyBarVO.get("autor")!=null){
	            sql.append("   AND autor=:autor ");
	            paramMap.put("autor",eastMoneyBarVO.get("autor"));
	        }
        	if(eastMoneyBarVO.get("ip")!=null){
	            sql.append("   AND ip=:ip ");
	            paramMap.put("ip",eastMoneyBarVO.get("ip"));
	        }
        	if(eastMoneyBarVO.get("createTime")!=null){
	            sql.append("   AND createtime=:createTime ");
	            paramMap.put("createTime",eastMoneyBarVO.get("createTime"));
	        }
        	if(eastMoneyBarVO.get("topicId")!=null){
	            sql.append("   AND topicid=:topicId ");
	            paramMap.put("topicId",eastMoneyBarVO.get("topicId"));
	        }
        	if(eastMoneyBarVO.get("url")!=null){
	            sql.append("   AND url=:url ");
	            paramMap.put("url",eastMoneyBarVO.get("url"));
	        }
        }
    }

    
    private String getSelectSQL(){
    	String sql="SELECT stockid,title,time,autor,ip,createtime,topicid,url FROM east_money_bar WHERE 1=1 ";
    	return sql;
    }
  
    /**
     * 查询EastMoneyBar记录
     */
    public List<EastMoneyBarVO> getEastMoneyBarListByParam(Map<String,Object> eastMoneyBarVO,PaginationCondition pc)throws Exception{
    	
        logger.debug("In function : getEastMoneyBarListByParam: eastMoneyBarVO={},pc={}", new Object[]{eastMoneyBarVO,pc});
        Map<String, Object> paramMap = new HashMap<String, Object>();
          
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        
        putWhereParam(eastMoneyBarVO,sql,paramMap);
        String sqlstr=PaginationParams.convertSqlStatement(sql.toString(), pc, paramMap);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sqlstr, paramMap});
        List<EastMoneyBarVO> result = baseDao.namedParameterJdbcTemplate.query(sqlstr, paramMap, new BeanPropertyRowMapper<EastMoneyBarVO>(EastMoneyBarVO.class));  
        
        logger.debug("End function : getEastMoneyBarListByParam");
        return result;
    }
    
    
    /**
     * 查询总数
     */
    public int getTotalEastMoneyBarByParam(Map<String,Object> eastMoneyBarVO) throws Exception{
    	logger.debug("In function : getTotalEastMoneyBarByParam: eastMoneyBarVO={}", new Object[]{eastMoneyBarVO});
    	StringBuilder sql=new StringBuilder();
    	sql.append("SELECT COUNT(1) FROM east_money_bar WHERE 1=1 ");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	putWhereParam(eastMoneyBarVO,sql,paramMap);
    	//查询记录总数
        logger.debug(" About to execute sql={} paramMap={}",new Object[]{sql.toString(), paramMap});
        int result = baseDao.namedParameterJdbcTemplate.queryForInt(sql.toString(), paramMap);
        
        logger.debug(" End Function: getTotalEastMoneyBarByParam");
        return result;
    }

    /**
     * 根据id查询EastMoneyBar记录
     */
    public EastMoneyBarVO getEastMoneyBarById(Integer topicId)throws Exception{
    	
        logger.debug("In function : getEastMoneyBarById: topicId={}", new Object[]{topicId});
        
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectSQL());
        sql.append(" AND topicid=:topicId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("topicId", topicId);
        logger.debug(" About to execute sql={} paramMap={}", new Object[]{sql.toString(), paramMap});
        EastMoneyBarVO result = baseDao.namedParameterJdbcTemplate.queryForObject(sql.toString(), paramMap, new BeanPropertyRowMapper<EastMoneyBarVO>(EastMoneyBarVO.class));   
        logger.debug("End function : getEastMoneyBarById");
        return result;
    }
}