package finance.datainit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import finance.datainit.dao.BaseTest;
import finance.datainit.util.CommonUtil;
import finance.datainit.vo.StockBaseInfoVO;
   
public class StockBaseInfoServiceRemoteTest extends BaseTest{
	@Autowired
	private StockBaseInfoService stockBaseInfoService;
	
	private List<StockBaseInfoVO> transitStockBaseInfo(JSONArray data,Date updateDate){
		List<StockBaseInfoVO> vos=new LinkedList<StockBaseInfoVO>();

        for (Object adata : data) {
        	try{
				String datastr=(String)adata;
				String datas[]=datastr.split(",");
				StockBaseInfoVO vo=new StockBaseInfoVO();
				vo.setCreateTime(new Date());
				vo.setStockCode(datas[0]);
				vo.setEastmoneyBarUrl("http://guba4.eastmoney.com/list,"+datas[0]+".html");
				vo.setStockName(datas[1]);
				vo.setEastmoneyComment(datas[3]);
				try{
				vo.setEarningsRatio(Float.parseFloat(datas[7]));
				}catch (Exception e) {
					e.printStackTrace();
				}
				vo.setUpdateDate(updateDate);
				vos.add(vo);
        	}catch (Exception e) {
				e.printStackTrace();
			}
		}
        return vos;
	} 
	@Test
	public void testInitStockBaseInfo(){
		String url="http://data.eastmoney.com/stockcomment/data.aspx?type=Ranking&market=all&sortType=first&sortRule=0&jsname=evcadusX&pageSize=100000&page=1&rt=45522699";

		String respone=CommonUtil.doGet(url);
		respone=respone.replaceFirst("var evcadusX =", "");
		respone=respone.substring(0,respone.length()-1);
		JSONObject jsonObject = JSONObject.fromObject(respone);
		String date=jsonObject.getString("date");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray data= (JSONArray)jsonObject.get("data");
		try {
			List<StockBaseInfoVO> stockBaseInfoVOs=transitStockBaseInfo(data,dateFormat.parse(date));
			try {
				stockBaseInfoService.batchAddStockBaseInfo(stockBaseInfoVOs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
