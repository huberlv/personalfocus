package finance.datainit.vo;

import java.io.Serializable;
import java.util.Date;

public class EastMoneyBarVO implements Serializable{
	private Integer stockId=null;
	private String title=null;
	private Date time=null;
	private String autor=null;
	private String ip=null;
	private Date createTime=null;
	private Integer topicId=null;
	private String url=null;

	public void setStockId(Integer value) {
		this.stockId = value;
	}
	
	public Integer getStockId() {
		return this.stockId;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTime(Date value) {
		this.time = value;
	}
	
	public Date getTime() {
		return this.time;
	}
	public void setAutor(String value) {
		this.autor = value;
	}
	
	public String getAutor() {
		return this.autor;
	}
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setTopicId(Integer value) {
		this.topicId = value;
	}
	
	public Integer getTopicId() {
		return this.topicId;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	@Override
	public String toString(){
		StringBuilder result=new StringBuilder();
		result.append(super.toString());
		result.append(":{");
		result.append("stockId=");
		result.append(stockId);
		result.append(",");
		result.append("title=");
		result.append(title);
		result.append(",");
		result.append("time=");
		result.append(time);
		result.append(",");
		result.append("autor=");
		result.append(autor);
		result.append(",");
		result.append("ip=");
		result.append(ip);
		result.append(",");
		result.append("createTime=");
		result.append(createTime);
		result.append(",");
		result.append("topicId=");
		result.append(topicId);
		result.append(",");
		result.append("url=");
		result.append(url);
		result.append("}");
		return result.toString();
	}
}