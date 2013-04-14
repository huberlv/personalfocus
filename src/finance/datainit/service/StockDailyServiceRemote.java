package finance.datainit.service;


import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.PaginationVO;
import finance.datainit.vo.StockDailyVO;
import java.util.Date;

public interface StockDailyServiceRemote {
    /**
     * 新增记录
     */
    public boolean addStockDaily(StockDailyVO stockDailyVO)throws Exception;
    /**
     * 删除记录
     */
    public boolean deleteStockDailyById(Integer dailyId)throws Exception;
    /**
     * 更新记录
     */
    public boolean updateStockDailyById(Integer dailyId,Date date)throws Exception;
    /**
     * 根据id查询记录
     */
    public StockDailyVO getStockDailyById(Integer dailyId)throws Exception;
    
    /**
     * 查询记录
     */
    public PaginationVO<StockDailyVO> getPaginationStockDailyByParam(Date date,Float open,Float high,Float low,Float close,Integer volume,Float adjClose,Integer stockId,Integer dailyId,PaginationCondition pc)throws Exception;
    /**
     * 查询总数
     */
    public int getTotalStockDailyByParam(Date date,Float open,Float high,Float low,Float close,Integer volume,Float adjClose,Integer stockId,Integer dailyId)throws Exception; 
}