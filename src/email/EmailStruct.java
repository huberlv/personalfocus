package email;

import java.util.ArrayList;
import java.util.List;

import net.URLS;

import com.kingway.model.MailNeedToBeSendViewId;

/**
 * ������
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
		content.append("<strong>�𾴵�");
		content.append(this.userName);
		content.append(":</strong><br>�����µ���Ϣ:<br><center><table border='1' cellpadding='0' cellspacing='0' bordercolor='#66CCFF'><tr><td>��Ŀ����</td><td>��վ��</td><td>������ʱ��</td></tr>");
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
		content.append("</table><br>������<a ");
		content.append("href='"+URLS.getHostAddress()+"/personalfocus/'>��¼</a></center>");
		return content.toString();
		
	}

	public void setMailNeedToBeSend(List<MailNeedToBeSendViewId> mailNeedToBeSend) {
		this.mailNeedToBeSend = mailNeedToBeSend;
	}

	public List<MailNeedToBeSendViewId> getMailNeedToBeSend() {
		return mailNeedToBeSend;
	}
}
