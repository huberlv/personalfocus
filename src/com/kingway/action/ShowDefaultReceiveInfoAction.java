package com.kingway.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.URLS;

import com.kingway.dao.impl.ManageDefaultReceiveInfoDaoImpl;
import com.kingway.dao.impl.SubgroupManagementDaoImpl;
import com.kingway.model.UserDefaultReceiveInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.opensymphony.xwork2.ActionContext;

public class ShowDefaultReceiveInfoAction {

	private Date mailStartTime; // �ʼ�����ʱ�ο�ʼʱ��
	private Date mailStopTime; // �ʼ�����ʱ�ν���ʱ��
	private String mailSfStr; // �ʼ�����Ƶ���ַ�����ʾ
	private String mailFrequencyType; // �ʼ�����Ƶ�ʵ�λ��Сʱ/����
	private int mailReceiveType; //�Ƿ����ʼ�
	private Date messageStartTime; // ���ŷ���ʱ�ο�ʼʱ��
	private Date messageStopTime; // ���ŷ���ʱ�ν���ʱ��
	private int messageMax; // ÿ�ζ��������
	private String messageSfStr; // ���ŷ���Ƶ���ַ�����ʾ
	private int messageReceiveType; // ���ŷ�������
	private String messageFrequencyType; // ���ŷ���Ƶ�ʵ�λ��Сʱ/����
	private List<UserModuleSubgroupInfo> subgroupList; // ģ�������Ϣ
    private static  String host=URLS.getHostAddress();
	public List<UserModuleSubgroupInfo> getSubgroupList() {
		return subgroupList;
	}

	public void setSubgroupList(List<UserModuleSubgroupInfo> subgroupList) {
		this.subgroupList = subgroupList;
	}

	public Date getMailStartTime() {
		return mailStartTime;
	}

	public void setMailStartTime(Date mailStartTime) {
		this.mailStartTime = mailStartTime;
	}

	public Date getMailStopTime() {
		return mailStopTime;
	}

	public void setMailStopTime(Date mailStopTime) {
		this.mailStopTime = mailStopTime;
	}

	public String getMailSfStr() {
		return mailSfStr;
	}

	public void setMailSfStr(String mailSfStr) {
		this.mailSfStr = mailSfStr;
	}

	public String getMailFrequencyType() {
		return mailFrequencyType;
	}

	public void setMailFrequencyType(String mailFrequencyType) {
		this.mailFrequencyType = mailFrequencyType;
	}
	
	public int getMailReceiveType() {
		return mailReceiveType;
	}

	public void setMailReceiveType(int mailReceiveType) {
		this.mailReceiveType = mailReceiveType;
	}

	public Date getMessageStartTime() {
		return messageStartTime;
	}

	public void setMessageStartTime(Date messageStartTime) {
		this.messageStartTime = messageStartTime;
	}

	public Date getMessageStopTime() {
		return messageStopTime;
	}

	public void setMessageStopTime(Date messageStopTime) {
		this.messageStopTime = messageStopTime;
	}

	public int getMessageMax() {
		return messageMax;
	}

	public void setMessageMax(int messageMax) {
		this.messageMax = messageMax;
	}

	public String getMessageSfStr() {
		return messageSfStr;
	}

	public void setMessageSfStr(String messageSfStr) {
		this.messageSfStr = messageSfStr;
	}

	public int getMessageReceiveType() {
		return messageReceiveType;
	}

	public void setMessageReceiveType(int messageReceiveType) {
		this.messageReceiveType = messageReceiveType;
	}

	public String getMessageFrequencyType() {
		return messageFrequencyType;
	}

	public void setMessageFrequencyType(String messageFrequencyType) {
		this.messageFrequencyType = messageFrequencyType;
	}

	public void getDefault(){	
		long userId = Long.parseLong((String)ActionContext.getContext().getSession().get("userid"));
		List<UserDefaultReceiveInfo> modifylist = new ManageDefaultReceiveInfoDaoImpl().getDefault(userId);
		UserDefaultReceiveInfo mobile = null;
		UserDefaultReceiveInfo mail = null;
		if (modifylist.size() == 2){
			mobile = modifylist.get(0);
			if (mobile.getReceiveType() == 7) {
				mobile = modifylist.get(1);
				mail = modifylist.get(0);
			} else {
				mail = modifylist.get(1);
			}
			//��ȡ�ֻ����Ž��ձ���Ϣ
			messageStartTime = mobile.getStartTime();
			messageStopTime = mobile.getStopTime();
			messageMax = mobile.getMaxReceiveNum();
			String[] s = getFrequency(mobile.getSendFrequency());
			messageSfStr = s[0];
			messageFrequencyType = s[1];
			messageReceiveType = mobile.getReceiveType();
			//��ȡ�ʼ����ձ���Ϣ
			mailStartTime = mail.getStartTime();
			mailStopTime = mail.getStopTime();
			s = getFrequency(mail.getSendFrequency());
			mailSfStr = s[0];
			mailFrequencyType = s[1];
			mailReceiveType = mail.getReceiveType();
		}
		if(modifylist.size() == 1){
			mobile = modifylist.get(0);
			String[] s = getFrequency(mobile.getSendFrequency());
			if (mobile.getReceiveType() == 7) {
				//����Ϣ���ʼ�������Ϣ
				mailStartTime = mobile.getStartTime();
				mailStopTime = mobile.getStopTime();
				s = getFrequency(mobile.getSendFrequency());
				mailSfStr = s[0];
				mailFrequencyType = s[1];
				mailReceiveType = mobile.getReceiveType();
			} else {
				//����Ϣ�Ƕ��Ž�����Ϣ
				messageStartTime = mobile.getStartTime();
				messageStopTime = mobile.getStopTime();
				messageMax = mobile.getMaxReceiveNum();
				messageSfStr = s[0];
				messageFrequencyType = s[1];
				messageReceiveType = mobile.getReceiveType();
			}
		}
	}
	
	/**
	 * ����Ĭ�ϱ�
	 * @return
	 */
	public String updateDefault(){
		getDefault();
		return "updateDefault";
	}
	
	/**
	 * ��������Ŀ
	 * @return
	 */
	public String showDefault(){
		Long userId = Long.parseLong((String) ActionContext.getContext().getSession().get("userid"));
		subgroupList = new SubgroupManagementDaoImpl().getSubgroupList(userId);
		getDefault();
		return "showDefault";
	}
	
	/**
	 * ���������ʱ���ʾ�ķ���Ƶ��ת��Ϊ��λΪ���ӻ�Сʱ����ֵ��ʾ
	 * @param date
	 * @return String[0]:��ֵ��String[1]:��λ
	 */
	public String[] getFrequency(Date date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		String base = "2000-01-01";
		String[] result = new String[2];
		try {
			Date baseDate = sdfDate.parse(base);
			long minutes = (date.getTime() - baseDate.getTime()) / 60000;
		//	System.out.println("minutes:" + minutes);
			if (minutes % 60 == 0) {
				result[0] = (minutes / 60) + "";
				result[1] = "hour";
				return result;
			} else {
				result[0] = minutes + "";
				result[1] = "minute";
				return result;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public  void setHost(String host) {
		this.host = host;
	}

	public  String getHost() {
		return host;
	}
}
