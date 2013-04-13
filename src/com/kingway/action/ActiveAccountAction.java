package com.kingway.action;

import com.kingway.dao.impl.ActiveAccountDaoImpl;

public class ActiveAccountAction {
	private Long codeId;
	private String code;
	
	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String activeAccount(){
		//System.out.println(new ActiveAccountDaoImpl().active(this.codeId, this.code));
		return "success";	
	}
}
