package finance.datainit.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import finance.datainit.dao.BaseTest;
import finance.datainit.dao.StockBaseInfoDAO;
import finance.datainit.util.CommonUtil;
import finance.datainit.util.PaginationCondition;
import finance.datainit.vo.EastMoneyBarVO;
import finance.datainit.vo.StockBaseInfoVO;


public class EastMoneyBarServiceRemoteTest extends BaseTest { 
    @Autowired
    private EastMoneyBarServiceRemote eastMoneyBarService;
    @Autowired
    private StockBaseInfoDAO stockBaseInfoDAO;
    /**
     * 新增记录
     */
	@Test
    public void testInitEastMoneyBar()throws Exception{
		
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
			
			System.out.println("getting data url="+vo.get(0).getStockCode());
			try {
				getABar(stockId,vo.get(0).getStockCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			System.out.println("end getting data url="+vo.get(0).getStockCode());
		}
    }
	
	
	private void getABar(int stackId,String stackCode) throws Exception{
		boolean isBleak=false;
		for(int i=0;i<300&&!isBleak;i++){
			String index="";
			if(i!=0){
				index="_"+(i+1);
			}
			String url="http://guba4.eastmoney.com/list,"+stackCode+",f"+index+".html";
			URL aurl;
			try {
				aurl = new URL(url);
			} catch (MalformedURLException e1) {
				break;
			}
			Document d;
			try {
				d = Jsoup.parse(aurl, 5000);
			} catch (IOException e1) {
				break;
			}
			Elements urls= d.select("#articlelist .articleh .l2>a");
			Elements authors= d.select("#articlelist .articleh .l3");
			List<EastMoneyBarVO> list =new LinkedList<EastMoneyBarVO>();

			for(int j=1;j<urls.size();j++){
				try{
					EastMoneyBarVO vo=new EastMoneyBarVO();
					vo.setCreateTime(new Date());
					vo.setStockId(stackId);
					vo.setTitle(urls.get(j).text());
					vo.setUrl("http://guba4.eastmoney.com/"+urls.get(j).attr("href"));
					String data[]=authors.get(j).html().split("<br />");
					vo.setAutor(data[0]); 
					if(data.length<2){
						System.out.println("行内容:"+urls.get(j).html());
						System.out.println("url:"+url);
						continue;
					}
					if(islegal(data[1])){
						vo.setTime(CommonUtil.sdfDate.parse("2013-"+data[1]));	
					}else{
						System.out.println("跳出的作者和日期:"+data[0]+","+data[1]);
						isBleak=true;
						break;
					}
					list.add(vo);
				}catch (Exception e) {
					System.out.println("行内容:"+urls.get(j));
					System.out.println("url:"+url);
					e.printStackTrace();
				}
			}
			eastMoneyBarService.batchAddEastMoneyBar(list);
		}
		
	}
	
	private boolean islegal(String time) throws ParseException{
		
		Date current=CommonUtil.sdfDate.parse("2013-"+time);
		if(current.getTime()<(new Date()).getTime()&&current.getTime()>CommonUtil.date_after.getTime()){
			return true;
		}else{
			return false;
		}
	}

}