package finance.datainit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import finance.datainit.dao.BaseTest;
import finance.datainit.dao.StockBaseInfoDAO;
import finance.datainit.util.CommonUtil;
import finance.datainit.util.GetCsvDataFromYahoo;
import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.StockBaseInfoVO;
import finance.datainit.vo.StockDailyVO;


public class StockDailyServiceRemoteTest extends BaseTest { 
    @Autowired
    private StockDailyServiceRemote stockDailyService;
    
    @Autowired
    private StockBaseInfoDAO stockBaseInfoDAO;
    /**
     * 新增记录
     */
	@Test
    public void testInitStockDaily()throws Exception{
		Map<String ,Object> whereParam=new HashMap<String ,Object>();
		PaginationCondition pc=new PaginationCondition();
		pc.setPageNum(1);
		pc.setPageSize(CommonUtil.run_stock_num);
		List<StockBaseInfoVO> stockBaseInfoVOs=stockBaseInfoDAO.getStockBaseInfoListByParam(whereParam, pc);
		for (StockBaseInfoVO stockBaseInfoVO : stockBaseInfoVOs) {
			Map<String ,Object> where=new HashMap<String ,Object>();
			where.put("stockCode", stockBaseInfoVO.getStockCode());
    		PaginationCondition pc1=new PaginationCondition();
    		pc.setPageNum(1);
    		pc.setPageSize(1);
    		List<StockBaseInfoVO> vo=stockBaseInfoDAO.getStockBaseInfoListByParam(where, pc1);
    		if(vo.size()==0){
    			continue;
    		}
			int stockId=vo.get(0).getStockId();
			String url=stockBaseInfoVO.getStockCode();
			if(url.startsWith("60")){
				url=url+".SS";
			}else{
				url=url+".SZ";
			}
			System.out.println("getting data url="+url);
			try {
				getDailyData(url,stockId,false);
			} catch (Exception e) {
				System.out.println("getting data fail url="+url);
				System.out.println("getting data  url="+url);
				url=stockBaseInfoVO.getStockCode()+(url.startsWith("60")?".SZ":".SS");
				try {
					getDailyData(url,stockId,true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			System.out.println("end getting data url="+url);
		}

    }
	
	private void getDailyData(String url,int  stockId,boolean throwe){
		
		try {
			ArrayList list= GetCsvDataFromYahoo.getStockCsvData(url,CommonUtil.daily_t );
			GetCsvDataFromYahoo csvData = null;
			List<StockDailyVO> stockDailyVOs=new LinkedList<StockDailyVO>();
			for (int i = 0; i < list.size(); i++) {
				csvData = (GetCsvDataFromYahoo) list.get(i);
				if((int)csvData.getY5()==0){
					continue;
				}
				
				StockDailyVO vo=new StockDailyVO();
				vo.setStockId(stockId);
				vo.setCreateTime(new Date());
				vo.setDate(csvData.getX());
				vo.setOpen((float)csvData.getY1());
				vo.setHigh((float)csvData.getY2());
				vo.setLow((float)csvData.getY3());
				vo.setClose((float)csvData.getY4());
				vo.setVolume((int)csvData.getY5());
				vo.setAdjClose((float)csvData.getY6());
				stockDailyVOs.add(vo);  
				
			}
	    	stockDailyService.batchAddStockDaily(stockDailyVOs);
		} catch (Exception e) {
			if(throwe){
				e.printStackTrace();
				logger.info("get daily data error:url="+url);
			}
		}

	}


}