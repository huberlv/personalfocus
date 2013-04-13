package com.kingway.dao;

import java.util.List;

import com.kingway.model.UserModuleHistoryListView;

public interface ListUserMoudleHistoryDao {
   public List<UserModuleHistoryListView> getUserModuleHistoryList(Long userModuleId,int begin,int max);
   public String getUserModuleStyle(Long userModuleId);
   public int getSize(Long userModuleId);
}
