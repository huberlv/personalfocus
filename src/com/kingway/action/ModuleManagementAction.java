package com.kingway.action;

import java.util.List;

import com.kingway.dao.impl.UpdateUserModuleInfoDaoImpl;

public class ModuleManagementAction {
	private List<String> moduleBox;
	private Long group;

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public List<String> getModuleBox() {
		return moduleBox;
	}

	public void setModuleBox(List<String> moduleBox) {
		this.moduleBox = moduleBox;
	}
	
	/**
	 * ɾ��ģ��
	 * @return
	 */
	public String delmodule(){
		for(String i : moduleBox){
			new UpdateUserModuleInfoDaoImpl().delUserModule(i);
		}
		return "delmodule";
	}
	
	/**
	 * �ı�ģ�����
	 * @return
	 */
	public String changeGroup(){
		UpdateUserModuleInfoDaoImpl u = new UpdateUserModuleInfoDaoImpl();
		for(String i : moduleBox){
			u.changeGroup(i,this.group);
		}
		return "changegroup";
	}
	
	/**
	 * ɾ������
	 * @return
	 */
	public String delGroup(){
		UpdateUserModuleInfoDaoImpl u = new UpdateUserModuleInfoDaoImpl();
		for(String i : moduleBox){
			u.delGroup(i);
		}
		return "delgroup";
	}
}
