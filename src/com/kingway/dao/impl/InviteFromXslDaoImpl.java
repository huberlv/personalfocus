package com.kingway.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.kingway.dao.InviteFromXslDao;
import com.kingway.model.UserActivecode;
import com.kingway.model.UserInfo;
import com.kingway.model.struct.RegistList;
import com.kingway.util.HibernateUtil;

public class InviteFromXslDaoImpl implements InviteFromXslDao {

	String mail = "";
	String phone = "";
	String activeCode = "";
	Long codeId;
	List<RegistList> registList = new ArrayList<RegistList>();

	@Override
	public List<RegistList> getDataFromXsl(String path) {
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int sheetSize = wb.getSheets().length;// ����������

		for (int n = 0; n < sheetSize; n++) {
			Sheet rs = wb.getSheet(n);
			int mailCellPosition = 0;
			int phoneCellPosition = 0;

			try {
				Cell mailCell = rs.findCell("����");
				mailCellPosition = mailCell.getColumn();
				// System.out.println("mailCellPosition"+mailCellPosition);
				Cell phoneCell = rs.findCell("��ϵ�绰");
				phoneCellPosition = phoneCell.getColumn();
				// System.out.println("phoneCellPosition"+phoneCellPosition);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 1; i < rs.getRows(); i++) { // �����¼��
				Cell mailCellContent = rs.getCell(mailCellPosition, i); // �������к���
				Cell phoneCellContent = rs.getCell(phoneCellPosition, i);
				mail = mailCellContent.getContents();
				phone = phoneCellContent.getContents();
				// /System.out.println(mail + "   " + phone);
				RegistList rl = new RegistList(mail, phone);
				registList.add(rl);
				System.out.println(registList.size());
			    saveInveiteCode();
				sendEmail();
			}
		}
		return registList;
	}

	@SuppressWarnings("unchecked")
	private String createInviteCode() {// ���������
		String inviteCode = "";
		ArrayList randomList = new ArrayList();
		for (int i = 0; i < 10; i++)
			randomList.add(i);
		for (char c = 'a'; c <= 'z'; c++)
			randomList.add(c);
		for (int i = 0; i < 21; i++) {
			int mathInt;
			mathInt = (int) (Math.random() * randomList.size());
			inviteCode += randomList.get(mathInt);

			randomList.remove(mathInt);
		}
		System.out.println("inviteCode:" + inviteCode);
		return inviteCode;
	}

	@Override
	public void saveInveiteCode() {
		UserInfo userinfo = new UserInfo();
		userinfo.setUserId(regist());
		UserActivecode code = new UserActivecode();
		activeCode = createInviteCode();
		code.setActiveCode(activeCode);
		code.setActiveCodeSendTime(new Date());
		code.setUserInfo(userinfo);
		HibernateUtil.save(code);
		codeId = code.getActiveCodeId();
		System.out.println("end-------------------");
	}

	private void sendEmail() { // ���������ʼ�
		System.out
				.println("http://localhost:8082/personalfocus/index/activeAccount?codeId="
						+ codeId + "&code=" + activeCode);
	}

	@Override
	public void invite() {
		System.out.println(registList.size());
	}

	@Override
	public Long regist() {
		return new AddUserDaoImpl().adduser("δ����", "000000", null, mail, phone,
				null, 0);
	}

}
