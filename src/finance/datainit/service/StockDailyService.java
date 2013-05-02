package finance.datainit.service;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance.datainit.util.CommonUtil;
import finance.datainit.vo.PaginationVO;
import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.StockDailyVO;
import finance.datainit.dao.StockDailyDAO;


@Service
public class StockDailyService implements StockDailyServiceRemote{
	
	private static Logger logger = LoggerFactory.getLogger(StockDailyService.class); 
	
    @Autowired
    private StockDailyDAO stockDailyDAO;
    /**
     * 新增记录
     */
    @Override
    public boolean addStockDaily(StockDailyVO stockDailyVO)throws Exception{
    	
    	try{
	    	return stockDailyDAO.addStockDaily(stockDailyVO);
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    /**
     * 删除记录
     */
    @Override
    public boolean deleteStockDailyById(Integer dailyId)throws Exception{
    	
    	try{
	    	boolean result=stockDailyDAO.deleteStockDailyById(dailyId);
	    	logger.info("End function : deleteStockDailyById");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		}  	
    }
    
    /**
     * 更新记录
     */
    @Override
    public boolean updateStockDailyById(Integer dailyId,Date date)throws Exception{
    	
    	Map<String,Object> stockDailyVO=new HashMap<String,Object>();  
    	stockDailyVO.put("date",date);
    	
    	try{
	    	boolean result=stockDailyDAO.updateStockDailyById(dailyId,stockDailyVO);
	    	logger.info("End function : updateStockDailyById");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    /**
     * 根据id查询记录
     */
    @Override
    public StockDailyVO getStockDailyById(Integer dailyId)throws Exception{
    	
    	try{
	        StockDailyVO result=stockDailyDAO.getStockDailyById(dailyId);
	        logger.info("End function : getStockDailyById");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    /**
     * 查询记录
     */
    @Override
    public PaginationVO<StockDailyVO> getPaginationStockDailyByParam(Date date,Float open,Float high,Float low,Float close,Integer volume,Float adjClose,Integer stockId,Integer dailyId,Date createTime,PaginationCondition pc)throws Exception{
    	
    	Map<String,Object> stockDailyVO=new HashMap<String,Object>();  
    	stockDailyVO.put("date",date);
    	stockDailyVO.put("open",open);
    	stockDailyVO.put("high",high);
    	stockDailyVO.put("low",low);
    	stockDailyVO.put("close",close);
    	stockDailyVO.put("volume",volume);
    	stockDailyVO.put("adjClose",adjClose);
    	stockDailyVO.put("stockId",stockId);
    	stockDailyVO.put("dailyId",dailyId);
    	stockDailyVO.put("createTime",createTime);
    	try{
	    	PaginationVO<StockDailyVO> result=new PaginationVO<StockDailyVO>();
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        result.setPageNum(pc.getPageNum());
	        result.setPageSize(pc.getPageSize());
	        
	        int total = getTotalStockDailyByParam(date,open,high,low,close,volume,adjClose,stockId,dailyId,createTime);
	        result.setTotal(total);
	
	        result.setMaxPage(CommonUtil.getMaxPage(total,pc.getPageSize()));
	        if(total!=0){
	        	List<StockDailyVO> list=stockDailyDAO.getStockDailyListByParam(stockDailyVO,pc);
	        	result.setItems(list);
	        }
	        logger.info("End function : getPaginationStockDailyByParam");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    /**
     * 查询总数
     */
    @Override
    public int getTotalStockDailyByParam(Date date,Float open,Float high,Float low,Float close,Integer volume,Float adjClose,Integer stockId,Integer dailyId,Date createTime)throws Exception{
    	

    	Map<String,Object> stockDailyVO=new HashMap<String,Object>();  
    	stockDailyVO.put("date",date);
    	stockDailyVO.put("open",open);
    	stockDailyVO.put("high",high);
    	stockDailyVO.put("low",low);
    	stockDailyVO.put("close",close);
    	stockDailyVO.put("volume",volume);
    	stockDailyVO.put("adjClose",adjClose);
    	stockDailyVO.put("stockId",stockId);
    	stockDailyVO.put("dailyId",dailyId);
    	stockDailyVO.put("createTime",createTime);
    	try{
	    	int result=stockDailyDAO.getTotalStockDailyByParam(stockDailyVO);
	    	logger.info("End function : getTotalStockDailyByParam");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
	@Override @Transactional
	public boolean batchAddStockDaily(List<StockDailyVO> stockDailyVOs) throws Exception {
	
		for (StockDailyVO stockDailyVO : stockDailyVOs) {
			try{
				stockDailyDAO.addStockDaily(stockDailyVO);
			}catch (Exception e) {
				e.printStackTrace();
			}

		}  
		return true;
	}
}