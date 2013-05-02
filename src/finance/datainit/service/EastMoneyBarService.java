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
import finance.datainit.vo.EastMoneyBarVO;
import finance.datainit.dao.EastMoneyBarDAO;


@Service
public class EastMoneyBarService implements EastMoneyBarServiceRemote{
	
	private static Logger logger = LoggerFactory.getLogger(EastMoneyBarService.class); 
	
    @Autowired
    private EastMoneyBarDAO eastMoneyBarDAO;
    /**
     * 新增记录
     */
    @Override
    public boolean addEastMoneyBar(EastMoneyBarVO eastMoneyBarVO)throws Exception{
    	
    	try{
	    	return eastMoneyBarDAO.addEastMoneyBar(eastMoneyBarVO);
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;
		} 
    }
    
    /**
     * 删除记录
     */
    @Override
    public boolean deleteEastMoneyBarById(Integer topicId)throws Exception{
    	
    	try{
	    	boolean result=eastMoneyBarDAO.deleteEastMoneyBarById(topicId);
	    	logger.info("End function : deleteEastMoneyBarById");
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
    public boolean updateEastMoneyBarById(Integer topicId,Date createTime)throws Exception{
    	
    	Map<String,Object> eastMoneyBarVO=new HashMap<String,Object>();  
    	eastMoneyBarVO.put("createTime",createTime);
    	
    	try{
	    	boolean result=eastMoneyBarDAO.updateEastMoneyBarById(topicId,eastMoneyBarVO);
	    	logger.info("End function : updateEastMoneyBarById");
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
    public EastMoneyBarVO getEastMoneyBarById(Integer topicId)throws Exception{
    	
    	try{
	        EastMoneyBarVO result=eastMoneyBarDAO.getEastMoneyBarById(topicId);
	        logger.info("End function : getEastMoneyBarById");
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
    public PaginationVO<EastMoneyBarVO> getPaginationEastMoneyBarByParam(Integer stockId,String title,Date time,String autor,String ip,Date createTime,Integer topicId,String url,PaginationCondition pc)throws Exception{
    	
    	Map<String,Object> eastMoneyBarVO=new HashMap<String,Object>();  
    	eastMoneyBarVO.put("stockId",stockId);
    	eastMoneyBarVO.put("title",title);
    	eastMoneyBarVO.put("time",time);
    	eastMoneyBarVO.put("autor",autor);
    	eastMoneyBarVO.put("ip",ip);
    	eastMoneyBarVO.put("createTime",createTime);
    	eastMoneyBarVO.put("topicId",topicId);
    	eastMoneyBarVO.put("url",url);
    	try{
	    	PaginationVO<EastMoneyBarVO> result=new PaginationVO<EastMoneyBarVO>();
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        result.setPageNum(pc.getPageNum());
	        result.setPageSize(pc.getPageSize());
	        
	        int total = getTotalEastMoneyBarByParam(stockId,title,time,autor,ip,createTime,topicId,url);
	        result.setTotal(total);
	
	        result.setMaxPage(CommonUtil.getMaxPage(total,pc.getPageSize()));
	        if(total!=0){
	        	List<EastMoneyBarVO> list=eastMoneyBarDAO.getEastMoneyBarListByParam(eastMoneyBarVO,pc);
	        	result.setItems(list);
	        }
	        logger.info("End function : getPaginationEastMoneyBarByParam");
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
    public int getTotalEastMoneyBarByParam(Integer stockId,String title,Date time,String autor,String ip,Date createTime,Integer topicId,String url)throws Exception{
    	

    	Map<String,Object> eastMoneyBarVO=new HashMap<String,Object>();  
    	eastMoneyBarVO.put("stockId",stockId);
    	eastMoneyBarVO.put("title",title);
    	eastMoneyBarVO.put("time",time);
    	eastMoneyBarVO.put("autor",autor);
    	eastMoneyBarVO.put("ip",ip);
    	eastMoneyBarVO.put("createTime",createTime);
    	eastMoneyBarVO.put("topicId",topicId);
    	eastMoneyBarVO.put("url",url);
    	try{
	    	int result=eastMoneyBarDAO.getTotalEastMoneyBarByParam(eastMoneyBarVO);
	    	logger.info("End function : getTotalEastMoneyBarByParam");
	    	return result;
    	}catch (Exception e) {
    		logger.info(" ckpt1 exception:{}", new Object[] { e });
    		throw e;  
		} 
    }
    
    public boolean batchAddEastMoneyBar(List<EastMoneyBarVO> eastMoneyBarVO)throws Exception{
    	
    	for (EastMoneyBarVO eastMoneyBarVO2 : eastMoneyBarVO) {
    		try{
    		    eastMoneyBarDAO.addEastMoneyBar(eastMoneyBarVO2);
    		}catch (Exception e) {
				System.out.println(eastMoneyBarVO2);
			}
		}
    	return true;

    }
}