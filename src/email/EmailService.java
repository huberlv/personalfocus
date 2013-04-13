package email;


import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.URLS;


/**
 * 邮件发送服务类
 * 
 * @author ZERO
 * 
 */

public class EmailService {

	public EmailBox emailbox;
	public ArrayList<String> content = null;
	public String receiver = null;
	public ArrayList<OtherStruct> other;
	public EmailSender sender;
	private int sleepTime;
	public String username; 
	public final static String mailCss="<link  rel=stylesheet type=text/css href='"+URLS.getHostAddress()+"/personalfocus/css/userindexcss/mailCss.css'>";

	/**
	 * 
	 * @param emailbox 封装多个邮件的邮箱
	 * @param sender 
	 * @param time
	 */
	public EmailService(EmailBox emailbox,EmailSender sender,int time) {
		this.emailbox = emailbox;
		this.sender = sender;
		this.sleepTime = time*1000;
	}
	
	
	public void sendEmailWithHtml() throws Exception {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", sender.host);
		prop.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(prop, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(sender.from,sender.psw);
			}
		});
		Message message = new MimeMessage(session);
		//session.setDebug(true); // 打开调试模式
		StringBuilder text;
		for (int j = 0;j<emailbox.emails.size();j++) { //读取一封邮件
		
			receiver = emailbox.emails.get(j).receiver;
			content = emailbox.emails.get(j).content;
			 text=new StringBuilder(5120);
			 text.append(mailCss); 
			
			for (int i = 0; i < content.size(); i++) { //读取邮件content内容

				text.append(content.get(i));

			}
            //System.out.println(receiver + " , " + text);
		    other = emailbox.emails.get(j).other;
			if(other != null){
				for (int i = 0; i < other.size(); i++) { //读取邮件other内容
					text.append(other.get(i).text);
					text.append("<br>");
				}
			}
			System.out.println(text);
			try {
				message.setSubject("Email With Html");
				message.setContent(new String(text), "text/html;charset=gb2312");
				message.setSentDate(new Date());
				Address address = new InternetAddress("kingway604@163.com","Freedom");
				Address toAddress = new InternetAddress(receiver);
				message.setFrom(address);
				message.setRecipient(Message.RecipientType.TO, toAddress);
				message.saveChanges();
//				System.out.println("sendEmailWithHtml() 开始发送邮件……");
				Transport.send(message);
				Thread.sleep(sleepTime);
	//			System.out.println("发送成功！");
			} catch (MessagingException e) {
	//			System.out.println("发送失败！");
                e.printStackTrace();
			}
		} //end of for

	} //end of sendEmailWithHtml()

} //end of class
