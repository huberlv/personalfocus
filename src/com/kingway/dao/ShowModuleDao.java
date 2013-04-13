package com.kingway.dao;

import com.kingway.model.ModuleInfoShowView;

public interface ShowModuleDao {

	public String showModule(Long id,String psw,Long moduleId,int source);
	public ModuleInfoShowView getModuleInfoShowView(long moduleId);
}
