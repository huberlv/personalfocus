package com.kingway.action;

import java.util.List;

import com.kingway.model.struct.RegistList;

public class RegistAndInviteAction {
	private List <RegistList> list;
	

	public List<RegistList> getList() {
		return list;
	}


	public void setList(List<RegistList> list) {
		this.list = list;
	}


	public String registAndInvite(){
		System.out.println("ads");
		System.out.println(this.list.size());
		return "success";
	}

}
