package com.kingway.dao;

import java.util.List;

public interface InviteFromXslDao {
	public List getDataFromXsl(String path);
	public Long regist();
	public void invite();
	public void saveInveiteCode();
}
