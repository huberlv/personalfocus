package finance.datainit.service;


import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.PaginationVO;
import finance.datainit.vo.EastMoneyBarVO;
import java.util.Date;
import java.util.List;

public interface EastMoneyBarServiceRemote {
    /**
     * 新增记录
     */
    public boolean addEastMoneyBar(EastMoneyBarVO eastMoneyBarVO)throws Exception;
    /**
     * 删除记录
     */
    public boolean deleteEastMoneyBarById(Integer topicId)throws Exception;
    /**
     * 更新记录
     */
    public boolean updateEastMoneyBarById(Integer topicId,Date createTime)throws Exception;
    /**
     * 根据id查询记录
     */
    public EastMoneyBarVO getEastMoneyBarById(Integer topicId)throws Exception;
    
    /**
     * 查询记录
     */
    public PaginationVO<EastMoneyBarVO> getPaginationEastMoneyBarByParam(Integer stockId,String title,Date time,String autor,String ip,Date createTime,Integer topicId,String url,PaginationCondition pc)throws Exception;
    /**
     * 查询总数
     */
    public int getTotalEastMoneyBarByParam(Integer stockId,String title,Date time,String autor,String ip,Date createTime,Integer topicId,String url)throws Exception;
    
    public boolean batchAddEastMoneyBar(List<EastMoneyBarVO> eastMoneyBarVO)throws Exception;
}