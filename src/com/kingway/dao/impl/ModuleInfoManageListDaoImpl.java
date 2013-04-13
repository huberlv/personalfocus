package com.kingway.dao.impl;

import com.kingway.dao.ModuleInfoManageListDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingway.model.UserModuleInfo;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.UserReceiveInfo;
import com.kingway.model.struct.ModuleInfoManageList;
import com.kingway.util.HibernateUtil;

/**
 * ��ʾ�û����Ĺ����е�ģ������
 * @author ZERO
 * @date 2010-10-02
 */
public class ModuleInfoManageListDaoImpl implements ModuleInfoManageListDao {

	public List<ModuleInfoManageList> manageList = new ArrayList<ModuleInfoManageList>(); // ��ϸ��������
	public List<UserModuleInfo> moduleList; // UserModuleInfo�����ݼ�
	public List<UserReceiveInfo> receiveList; // UserReceiveInfo�����ݼ�
	private String websiteName; // ��վ��
	private String userModuleName; // �û���Ŀ��
	private long userModuleId; // �û�ģ��ID
	private Date mailStartTime; // �ʼ�����ʱ�ο�ʼʱ��
	private Date mailStopTime; // �ʼ�����ʱ�ν���ʱ��
	private String mailSfStr; // �ʼ�����Ƶ���ַ�����ʾ
	private Date mailSf; // �ʼ�����Ƶ��ʱ���ʾ
	private String mailFrequencyType; // ���ŷ���Ƶ�ʵ�λ��Сʱ/����
	private Date messageStartTime; // ���ŷ���ʱ�ο�ʼʱ��
	private Date messageStopTime; // ���ŷ���ʱ�ν���ʱ��
	private int messageMax; // ÿ�ζ��������
	private String messageSfStr; // ���ŷ���Ƶ���ַ�����ʾ
	private Date messageSf; // ���ŷ���Ƶ��ʱ���ʾ
	private int messageReceiveType; // ���ŷ�������
	private String messageFrequencyType; // ���ŷ���Ƶ�ʵ�λ��Сʱ/����
	private boolean email; // �Ƿ����ʼ�
	private int message; // �Ƿ��Ͷ���
	private UserModuleSubgroupInfo subgroup; //������Ϣ

	public ModuleInfoManageListDaoImpl() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		try {
			mailStartTime = sdfTime.parse("00:00:01");
			mailStopTime = sdfTime.parse("23:59:59");
			messageStartTime = sdfTime.parse("08:00:00");
			messageStopTime = sdfTime.parse("20:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		messageMax = 1;
		messageReceiveType = 0;
		email = false;
		message = 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ModuleInfoManageList> getModuleInfoManageList(long id) {
		moduleList = HibernateUtil.list("from UserModuleInfo where userId="
				+ id);
		String[] sendFrequency;
		for (int i = 0; i < moduleList.size(); i++) {
			userModuleId = moduleList.get(i).getUserModuleId();
			userModuleName = moduleList.get(i).getUserModuleName();
			websiteName = moduleList.get(i).getWebsiteName();
			subgroup = moduleList.get(i).getUserModuleSubgroupInfo();
			// ȡ�����û�ÿ��userModuleId��receiveInfo
			receiveList = HibernateUtil.list("from UserReceiveInfo where userModuleId=" + userModuleId);
			for (int j = 0; j < receiveList.size(); j++) {
				int receiveType = receiveList.get(j).getReceiveType();
				// 0��ʾ�����ͣ�
				// 1��ʾ���͸��±������ݣ�2��ʾ���ͱ������ݺ����ӣ�4��ʾֻ����һ��ģ�����Ӹ��û���
				// 7��ʾ�����ʼ���
				// 9��ʾ���͵��ͻ���
				if (receiveType == 7) { // ���͵�����
					email = true;
					mailStartTime = receiveList.get(j).getStartTime();
					mailStopTime = receiveList.get(j).getStopTime();
					mailSf = receiveList.get(j).getSendFrequency();
					sendFrequency = getSendFrequency(mailSf);
					mailSfStr = sendFrequency[0];
					mailFrequencyType = sendFrequency[1];
				}
				if (receiveType != 7 && receiveType != 0) { // ���͵��ֻ�
					message = receiveType;
					messageStartTime = receiveList.get(j).getStartTime();
					messageStopTime = receiveList.get(j).getStopTime();
					messageSf = receiveList.get(j).getSendFrequency();
					messageReceiveType = receiveList.get(j).getReceiveType();
					messageMax = receiveList.get(j).getMaxReceiveNum();
					sendFrequency = getSendFrequency(messageSf);
					messageSfStr = sendFrequency[0];
					messageFrequencyType = sendFrequency[1]; 
				}
			}
			manageList.add(new ModuleInfoManageList(websiteName,
					userModuleName, userModuleId, mailStartTime, mailStopTime,
					mailSfStr, mailFrequencyType, messageStartTime,
					messageStopTime, messageMax, messageSfStr,
					messageReceiveType, messageFrequencyType, email, message, subgroup));
		} // end of moduleList for
		return manageList;
	}

	/**
	 * ���������ʱ���ʾ�ķ���Ƶ��ת��Ϊ��λΪ���ӻ�Сʱ����ֵ��ʾ
	 * @param date
	 * @return
	 */
	public String[] getSendFrequency(Date date) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		String base = "2000-01-01";
		String[] result = new String[2];
		try {
			Date baseDate = sdfDate.parse(base);
			long minutes = (date.getTime() - baseDate.getTime()) / 60000;
			//System.out.println("minutes:" + minutes);
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

}
