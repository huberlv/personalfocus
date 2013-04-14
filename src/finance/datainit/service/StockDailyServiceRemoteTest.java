package finance.datainit.service;

import junit.framework.Assert;
import org.junit.Test;
import com.gm.soa.common.util.SoaBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import finance.datainit.dao.BaseTest;


public class StockDailyServiceRemoteTest extends SoaBaseTest { 
    @Autowired
    private StockDailyServiceRemote stockDailyService;
    /**
     * 新增记录
     */
	@Test
    public void testInitStockDaily()throws Exception{
 	
    	Long result=stockDailyService.batchAddStockDaily(List<stockDailyVO>);
    }

}