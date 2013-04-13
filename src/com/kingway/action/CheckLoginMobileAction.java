package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.kingway.dao.impl.CheckLoginUserDaoImpl;
import com.kingway.dao.impl.MobileUserManagementDaoImpl;
import com.kingway.model.ContentNeedToBeSendView;
import com.kingway.model.UserInfo;
import com.kingway.util.OnlineFocus;

public class CheckLoginMobileAction {

	private String userId;
	private String password;

	private static String separator = "<|"; // �ָ���
	private static String newWeb = "<+";
	private static String newMod = "<-";
	private static String oldMod = "<&";
	public static String regx = "(&nbsp;|&gt;|&quot;|&apos;|&sect|&copy|&reg|&times|&divide;|nbsp;|&raquo;)+";
	private static final int FAIL_LOGIN = -5;
	private static final int SUCCESS_LOGIN = 5;
	private final static int NO_UPDATE = -10;
	private final static int UPDATE = 10;
	private static String END_OF_UPDATE = "<$";
	private String update = "1";// �Ƿ�ֻ������
	/* �Ƿ��һ������ */
	private int isFirst;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public String checkmobile() {
		try{
		PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse(); // ȡresponse����
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		/*
		 * Returns the current session associated with this request, or if the
		 * request does not have a session, creates one.
		 */
		System.out.print(isSessionExist(ServletActionContext.getRequest()));
		ServletActionContext.getRequest().getSession(); // ȡsession����
		// ���û�д���session����ͻ��˷������ݻ����Exception��
		// Cannot create a session after the response has been committed
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserInfo user = new CheckLoginUserDaoImpl().check(userId, password);

		if (user == null) // �����ڸ��û�
		{
			pw.print(FAIL_LOGIN);
			pw.flush();
		} else { // ���ڸ��û�
			List<ContentNeedToBeSendView> content = null;
			long UserModuleId = -1;
			String UserModuleName = "";
			long SubgroupId = -1;
			String WebsiteName = "";
			pw.print(SUCCESS_LOGIN);
			pw.print(separator + user.getUserName());
			MobileUserManagementDaoImpl mb = new MobileUserManagementDaoImpl();
			content = mb.getUserModules(userId);
			System.out.println(update);
			if ("0".equals(update)) {
				pw.print(separator + CheckLoginMobileAction.NO_UPDATE);
				boolean newline = false;
				for (int i = 0; i < content.size(); i++) {
					// �����µ�ģ����Ϣ
					if (content.get(i).getId().getUserModuleId() != UserModuleId
							&& ((content.get(i).getId().getMonitorType() & OnlineFocus.MONITOR_BY_USER) != OnlineFocus.MONITOR_BY_USER)) {
						// �����վ��������
						if (content.get(i).getId().getSubgroupId() != SubgroupId) {
							SubgroupId = content.get(i).getId().getSubgroupId();
							WebsiteName = content.get(i).getId().getGroupName();
							pw.print(CheckLoginMobileAction.separator
									+ CheckLoginMobileAction.newWeb
									+ WebsiteName + "\n");
							pw.print(CheckLoginMobileAction.separator
									+ SubgroupId);
							newline = true;
						}
						UserModuleId = content.get(i).getId().getUserModuleId();
						UserModuleName = content.get(i).getId()
								.getUserModuleName();
						if (newline == true) {
							pw.print(CheckLoginMobileAction.separator 
									+ CheckLoginMobileAction.oldMod+ "|"
									+ UserModuleName + "|");
						} else {
							pw.print(CheckLoginMobileAction.separator
									+ CheckLoginMobileAction.oldMod
									+ UserModuleName + "|");
						}
						pw.print(CheckLoginMobileAction.separator
								+ content.get(i).getId().getUserModuleId());
					}
				} // end of for
			} else {
				boolean isupdate = false;
				for (int i = 0; i < content.size(); i++) {
					if (content.get(i).getId().getIgnoreByUser() == 0) {
						isupdate = true;
					}
				}

				if (isupdate == false) // �����ڸ��¼�¼
				{
					pw.print(separator + CheckLoginMobileAction.NO_UPDATE);
					if (this.isFirst == 0) {
						pw.print(separator + CheckLoginMobileAction.END_OF_UPDATE+separator);
						pw.flush();
						pw.close();
						return "success";   
					}   
				} else { // ���ڸ��¼�¼
					pw.print(separator + CheckLoginMobileAction.UPDATE);
					boolean newline = false;
					for (int i = 0; i < content.size(); i++) {

						// �����µ�ģ����Ϣ
						if (content.get(i).getId().getUserModuleId() != UserModuleId
								&& ((content.get(i).getId().getMonitorType() & OnlineFocus.MONITOR_BY_USER) != OnlineFocus.MONITOR_BY_USER)) {

							UserModuleId = content.get(i).getId()
									.getUserModuleId();
							UserModuleName = content.get(i).getId()
									.getUserModuleName();
							if (content.get(i).getId().getContentType() == 1) {
								// �����վ��������
								if (content.get(i).getId().getSubgroupId() != SubgroupId) {
									SubgroupId = content.get(i).getId()
											.getSubgroupId();
									WebsiteName = content.get(i).getId()
											.getGroupName();
									pw.print(CheckLoginMobileAction.separator
											+ CheckLoginMobileAction.newWeb
											+ WebsiteName + "\n");
									pw.print(CheckLoginMobileAction.separator
											+ SubgroupId);
									newline = true;
								}
								if (newline == true) {
									pw.print(CheckLoginMobileAction.separator
											
											+ CheckLoginMobileAction.newMod+ "|"
											+ UserModuleName + "|");
								} else {
									pw.print(CheckLoginMobileAction.separator
											+ CheckLoginMobileAction.newMod
											+ UserModuleName + "|");
								}
								pw.print(CheckLoginMobileAction.separator
										+ content.get(i).getId()
												.getUserModuleId());
							}

						}
					} // end of for

				}
			}
			pw.append(CheckLoginMobileAction.separator
					+ CheckLoginMobileAction.END_OF_UPDATE);
			System.out.print("�������ݽ���");

		}
		pw.append(CheckLoginMobileAction.separator);

		pw.flush();
		pw.close();
		pw = null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * ���session�Ƿ��Ѿ�����
	 * 
	 * @param request
	 * @return
	 */
	private boolean isSessionExist(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return false;
		else
			return true;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getUpdate() {
		return update;
	}
}
