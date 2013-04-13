package com.kingway.action;

import java.util.List;

import com.kingway.dao.impl.UpdateModuleListDaoImpl;

public class UpdateModuleListAction {

	private List<Long> rightSideModel;//È¡µ½userModuleId
	private List<Long> leftSideModel;

	public List<Long> getRightSideModel() {
		return rightSideModel;
	}

	public void setRightSideModel(List<Long> rightSideModel) {
		this.rightSideModel = rightSideModel;
	}

	public List<Long> getLeftSideModel() {
		return leftSideModel;
	}


	public void setLeftSideModel(List<Long> leftSideModel) {
		this.leftSideModel = leftSideModel;
	}


	public String updatemodulelist(){
		//System.out.println("rightSideModel.size():"+this.rightSideModel.size());
		//System.out.println("leftSideModel.size():"+this.leftSideModel.size());
		
		new UpdateModuleListDaoImpl().updatemodulelist(rightSideModel,leftSideModel);
		return "success";
	}
}
