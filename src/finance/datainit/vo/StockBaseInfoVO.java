package finance.datainit.vo;

import java.io.Serializable;
import java.util.Date;

public class StockBaseInfoVO implements Serializable{
	private Integer stockId=null;
	private String stockCode=null;
	private String stockName=null;
	private Date createTime=null;
	private String eastmoneyBarUrl=null;
	private String eastmoneyComment=null;
	private Float earningsRatio=null;
	private Date updateDate=null;

	public void setStockId(Integer value) {
		this.stockId = value;
	}
	
	public Integer getStockId() {
		return this.stockId;
	}
	public void setStockCode(String value) {
		this.stockCode = value;
	}
	
	public String getStockCode() {
		return this.stockCode;
	}
	public void setStockName(String value) {
		this.stockName = value;
	}
	
	public String getStockName() {
		return this.stockName;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setEastmoneyBarUrl(String value) {
		this.eastmoneyBarUrl = value;
	}
	
	public String getEastmoneyBarUrl() {
		return this.eastmoneyBarUrl;
	}
	public void setEastmoneyComment(String value) {
		this.eastmoneyComment = value;
	}
	
	public String getEastmoneyComment() {
		return this.eastmoneyComment;
	}
	public void setEarningsRatio(Float value) {
		this.earningsRatio = value;
	}
	
	public Float getEarningsRatio() {
		return this.earningsRatio;
	}
	public void setUpdateDate(Date value) {
		this.updateDate = value;
	}
	
	public Date getUpdateDate() {
		return this.updateDate;
	}
	@Override
	public String toString(){
		StringBuilder result=new StringBuilder();
		result.append(super.toString());
		result.append(":{");
		result.append("stockId=");
		result.append(stockId);
		result.append(",");
		result.append("stockCode=");
		result.append(stockCode);
		result.append(",");
		result.append("stockName=");
		result.append(stockName);
		result.append(",");
		result.append("createTime=");
		result.append(createTime);
		result.append(",");
		result.append("eastmoneyBarUrl=");
		result.append(eastmoneyBarUrl);
		result.append(",");
		result.append("eastmoneyComment=");
		result.append(eastmoneyComment);
		result.append(",");
		result.append("earningsRatio=");
		result.append(earningsRatio);
		result.append(",");
		result.append("updateDate=");
		result.append(updateDate);
		result.append("}");
		return result.toString();
	}
}