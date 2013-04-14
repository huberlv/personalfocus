package finance.datainit.vo;

import java.io.Serializable; 
import java.util.Date;

public class StockDailyVO implements Serializable{
	private Date date=null;
	private Float open=null;
	private Float high=null; 
	private Float low=null;
	private Float close=null;
	private Integer volume=null;
	private Float adjClose=null;
	private Integer stockId=null;
	private Integer dailyId=null;

	public void setDate(Date value) {
		this.date = value;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setOpen(Float value) {
		this.open = value;
	}
	
	public Float getOpen() {
		return this.open;
	}
	public void setHigh(Float value) {
		this.high = value;
	}
	
	public Float getHigh() {
		return this.high;
	}
	public void setLow(Float value) {
		this.low = value;
	}
	
	public Float getLow() {
		return this.low;
	}
	public void setClose(Float value) {
		this.close = value;
	}
	
	public Float getClose() {
		return this.close;
	}
	public void setVolume(Integer value) {
		this.volume = value;
	}
	
	public Integer getVolume() {
		return this.volume;
	}
	public void setAdjClose(Float value) {
		this.adjClose = value;
	}
	
	public Float getAdjClose() {
		return this.adjClose;
	}
	public void setStockId(Integer value) {
		this.stockId = value;
	}
	
	public Integer getStockId() {
		return this.stockId;
	}
	public void setDailyId(Integer value) {
		this.dailyId = value;
	}
	
	public Integer getDailyId() {
		return this.dailyId;
	}
	@Override
	public String toString(){
		StringBuilder result=new StringBuilder();
		result.append(super.toString());
		result.append(":{");
		result.append("date=");
		result.append(date);
		result.append(",");
		result.append("open=");
		result.append(open);
		result.append(",");
		result.append("high=");
		result.append(high);
		result.append(",");
		result.append("low=");
		result.append(low);
		result.append(",");
		result.append("close=");
		result.append(close);
		result.append(",");
		result.append("volume=");
		result.append(volume);
		result.append(",");
		result.append("adjClose=");
		result.append(adjClose);
		result.append(",");
		result.append("stockId=");
		result.append(stockId);
		result.append(",");
		result.append("dailyId=");
		result.append(dailyId);
		result.append("}");
		return result.toString();
	}
}