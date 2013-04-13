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
 * 显示用户订阅管理中的模块内容
 * @author ZERO
 * @date 2010-10-02
 */
public class ModuleInfoManageListDaoImpl implements ModuleInfoManageListDao {

	public List<ModuleInfoManageList> manageList = new ArrayList<ModuleInfoManageList>(); // 详细管理结果集
	public List<UserModuleInfo> moduleList; // UserModuleInfo表数据集
	public List<UserReceiveInfo> receiveList; // UserReceiveInfo表数据集
	private String websiteName; // 网站名
	private String userModuleName; // 用户栏目名
	private long userModuleId; // 用户模块ID
	private Date mailStartTime; // 邮件发送时段开始时间
	private Date mailStopTime; // 邮件发送时段结束时间
	private String mailSfStr; // 邮件发送频率字符串表示
	private Date mailSf; // 邮件发送频率时间表示
	private String mailFrequencyType; // 短信发送频率单位：小时/分钟
	private Date messageStartTime; // 短信发送时段开始时间
	private Date messageStopTime; // 短信发送时段结束时间
	private int messageMax; // 每次短信最大数
	private String messageSfStr; // 短信发送频率字符串表示
	private Date messageSf; // 短信发送频率时间表示
	private int messageReceiveType; // 短信发送类型
	private String messageFrequencyType; // 短信发送频率单位：小时/分钟
	private boolean email; // 是否发送邮件
	private int message; // 是否发送短信
	private UserModuleSubgroupInfo subgroup; //分组信息

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
			// 取出该用户每个userModuleId的receiveInfo
			receiveList = HibernateUtil.list("from UserReceiveInfo where userModuleId=" + userModuleId);
			for (int j = 0; j < receiveList.size(); j++) {
				int receiveType = receiveList.get(j).getReceiveType();
				// 0表示不发送，
				// 1表示发送更新标题内容，2表示发送标题内容和链接，4表示只发送一条模块链接给用户，
				// 7表示发送邮件，
				// 9表示发送到客户端
				if (receiveType == 7) { // 发送到邮箱
					email = true;
					mailStartTime = receiveList.get(j).getStartTime();
					mailStopTime = receiveList.get(j).getStopTime();
					mailSf = receiveList.get(j).getSendFrequency();
					sendFrequency = getSendFrequency(mailSf);
					mailSfStr = sendFrequency[0];
					mailFrequencyType = sendFrequency[1];
				}
				if (receiveType != 7 && receiveType != 0) { // 发送到手机
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
	 * 求出用日期时间表示的发送频率转换为单位为分钟或小时的数值表示
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
