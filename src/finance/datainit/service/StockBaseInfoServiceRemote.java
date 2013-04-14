package finance.datainit.service;


import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.PaginationVO;
import finance.datainit.vo.StockBaseInfoVO;
import java.util.Date;

public interface StockBaseInfoServiceRemote {
    /**
     * 新增记录
     */
    public boolean addStockBaseInfo(StockBaseInfoVO stockBaseInfoVO)throws Exception;
    /**
     * 删除记录
     */
    public boolean deleteStockBaseInfoById(Integer stockId)throws Exception;
    /**
     * 更新记录
     */
    public boolean updateStockBaseInfoById(Integer stockId,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio)throws Exception;
    /**
     * 根据id查询记录
     */
    public StockBaseInfoVO getStockBaseInfoById(Integer stockId)throws Exception;
    
    /**
     * 查询记录
     */
    public PaginationVO<StockBaseInfoVO> getPaginationStockBaseInfoByParam(Integer stockId,String stockCode,String stockName,Date createTime,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio,Date updateDate,PaginationCondition pc)throws Exception;
    /**
     * 查询总数
     */
    public int getTotalStockBaseInfoByParam(Integer stockId,String stockCode,String stockName,Date createTime,String eastmoneyBarUrl,String eastmoneyComment,Float earningsRatio,Date updateDate)throws Exception; 
}