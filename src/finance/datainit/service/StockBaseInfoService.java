package finance.datainit.service;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance.datainit.util.CommonUtil;
import finance.datainit.vo.PaginationVO;
import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.StockBaseInfoVO;
import finance.datainit.dao.StockBaseInfoDAO;


@Service
public class StockBaseInfoService implements StockBaseInfoServiceRemote{
	
	private static Logger logger = LoggerFactory.getLogger(StockBaseInfoService.class); 
	
    @Autowired
    private StockBaseInfoDAO stockBaseInfoDAO;
    /**
     * 新增记录
     */
    @Override
    public boolean addStockBaseInfo(StockBaseInfoVO stockBaseInfoVO)throws Exception{
    	
    	try{
	    	return stockBaseInfoDAO.addStockBaseInfo(stockBaseInfoVO);
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    /**
     * 删除记录
     */
    @Override
    public boolean deleteStockBaseInfoById(Integer stockId)throws Exception{
    	
    	try{
	    	boolean result=stockBaseInfoDAO.deleteStockBaseInfoById(stockId);
	    	logger.info("End function : deleteStockBaseInfoById");
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
    public boolean updateStockBaseInfoById(Integer stockId,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio)throws Exception{
    	
    	Map<String,Object> stockBaseInfoVO=new HashMap<String,Object>();  
    	stockBaseInfoVO.put("eastmoneyBarUrl",eastmoneyBarUrl);
    	stockBaseInfoVO.put("eastmoneyComment",eastmoneyComment);
    	stockBaseInfoVO.put("earningsRatio",earningsRatio);
    	
    	try{
	    	boolean result=stockBaseInfoDAO.updateStockBaseInfoById(stockId,stockBaseInfoVO);
	    	logger.info("End function : updateStockBaseInfoById");
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
    public StockBaseInfoVO getStockBaseInfoById(Integer stockId)throws Exception{
    	
    	try{
	        StockBaseInfoVO result=stockBaseInfoDAO.getStockBaseInfoById(stockId);
	        logger.info("End function : getStockBaseInfoById");
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
    public PaginationVO<StockBaseInfoVO> getPaginationStockBaseInfoByParam(Integer stockId,String stockCode,String stockName,Date createTime,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio,Date updateDate,PaginationCondition pc)throws Exception{
    	
    	Map<String,Object> stockBaseInfoVO=new HashMap<String,Object>();  
    	stockBaseInfoVO.put("stockId",stockId);
    	stockBaseInfoVO.put("stockCode",stockCode);
    	stockBaseInfoVO.put("stockName",stockName);
    	stockBaseInfoVO.put("createTime",createTime);
    	stockBaseInfoVO.put("eastmoneyBarUrl",eastmoneyBarUrl);
    	stockBaseInfoVO.put("eastmoneyComment",eastmoneyComment);
    	stockBaseInfoVO.put("earningsRatio",earningsRatio);
    	stockBaseInfoVO.put("updateDate",updateDate);
    	try{
	    	PaginationVO<StockBaseInfoVO> result=new PaginationVO<StockBaseInfoVO>();
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        result.setPageNum(pc.getPageNum());
	        result.setPageSize(pc.getPageSize());
	        
	        int total = getTotalStockBaseInfoByParam(stockId,stockCode,stockName,createTime,eastmoneyBarUrl,eastmoneyComment,earningsRatio,updateDate);
	        result.setTotal(total);
	
	        result.setMaxPage(CommonUtil.getMaxPage(total,pc.getPageSize()));
	        if(total!=0){
	        	List<StockBaseInfoVO> list=stockBaseInfoDAO.getStockBaseInfoListByParam(stockBaseInfoVO,pc);
	        	result.setItems(list);
	        }
	        logger.info("End function : getPaginationStockBaseInfoByParam");
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
    public int getTotalStockBaseInfoByParam(Integer stockId,String stockCode,String stockName,Date createTime,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio,Date updateDate)throws Exception{
    	

    	Map<String,Object> stockBaseInfoVO=new HashMap<String,Object>();  
    	stockBaseInfoVO.put("stockId",stockId);
    	stockBaseInfoVO.put("stockCode",stockCode);
    	stockBaseInfoVO.put("stockName",stockName);
    	stockBaseInfoVO.put("createTime",createTime);
    	stockBaseInfoVO.put("eastmoneyBarUrl",eastmoneyBarUrl);
    	stockBaseInfoVO.put("eastmoneyComment",eastmoneyComment);
    	stockBaseInfoVO.put("earningsRatio",earningsRatio);
    	stockBaseInfoVO.put("updateDate",updateDate);
    	try{
	    	int result=stockBaseInfoDAO.getTotalStockBaseInfoByParam(stockBaseInfoVO);
	    	logger.info("End function : getTotalStockBaseInfoByParam");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    public boolean batchAddStockBaseInfo(List<StockBaseInfoVO> stockBaseInfoVOs)throws Exception{
    	for (StockBaseInfoVO stockBaseInfoVO : stockBaseInfoVOs) {
    		try{
    		Map<String ,Object> whereParam=new HashMap<String ,Object>();
    		whereParam.put("stockCode", stockBaseInfoVO.getStockCode());
    		PaginationCondition pc=new PaginationCondition();
    		pc.setPageNum(1);
    		pc.setPageSize(1);
    		List<StockBaseInfoVO> vo=stockBaseInfoDAO.getStockBaseInfoListByParam(whereParam, pc);
    		if(vo.size()>0){
    			Map<String ,Object> setParam=new HashMap<String ,Object>();
    			setParam.put("stockId",stockBaseInfoVO.getStockId());
    			setParam.put("stockCode",stockBaseInfoVO.getStockCode());
    			setParam.put("stockName",stockBaseInfoVO.getStockName());
    			setParam.put("createTime",stockBaseInfoVO.getCreateTime());
    			setParam.put("eastmoneyBarUrl",stockBaseInfoVO.getEastmoneyBarUrl());
    			setParam.put("eastmoneyComment",stockBaseInfoVO.getEastmoneyComment());
    			setParam.put("earningsRatio",stockBaseInfoVO.getEarningsRatio());
    			setParam.put("updateDate",stockBaseInfoVO.getUpdateDate());
    			
    			stockBaseInfoDAO.updateStockBaseInfoById(vo.get(0).getStockId(), setParam);
    		}else{
    			stockBaseInfoDAO.addStockBaseInfo(stockBaseInfoVO);
    		}
    		}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
    	return true;
    }
    

}