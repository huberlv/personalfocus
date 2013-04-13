package email;

import java.util.ArrayList;
import java.util.List;

import net.URLS;

import com.kingway.model.MailNeedToBeSendViewId;

/**
 * 吕鸿佩
 * 
 * @author Administrator
 * 
 */
public class EmailStruct {
	private String userName;
	private List<MailNeedToBeSendViewId> mailNeedToBeSend = new ArrayList<MailNeedToBeSendViewId>();

	public EmailStruct(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		StringBuilder content=new StringBuilder();
		content.append("<strong>尊敬的");
		content.append(this.userName);
		content.append(":</strong><br>你有新的消息:<br><center><table border='1' cellpadding='0' cellspacing='0' bordercolor='#66CCFF'><tr><td>栏目名称</td><td>网站名</td><td>最后更新时间</td></tr>");
		MailNeedToBeSendViewId current;
		for(int i=0;i<this.mailNeedToBeSend.size();i++){
			current=this.mailNeedToBeSend.get(i);
			content.append("<tr>");
			content.append("<td>");
			content.append(current.getUserModuleName());
			content.append("</td>");
			content.append("<td>");
			content.append(current.getWebsiteName());
			content.append("</td>");
			content.append("<td>");
			content.append(current.getUpdateTime());
			content.append("</td>");
			content.append("</tr>");
		}
		content.append("</table><br>详情请<a ");
		content.append("href='"+URLS.getHostAddress()+"/personalfocus/'>登录</a></center>");
		return content.toString();
		
	}

	public void setMailNeedToBeSend(List<MailNeedToBeSendViewId> mailNeedToBeSend) {
		this.mailNeedToBeSend = mailNeedToBeSend;
	}

	public List<MailNeedToBeSendViewId> getMailNeedToBeSend() {
		return mailNeedToBeSend;
	}
}
