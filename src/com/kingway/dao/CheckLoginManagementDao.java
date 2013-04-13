package com.kingway.dao;

import com.kingway.model.ManagerInfo;

public interface CheckLoginManagementDao {
      public ManagerInfo check(String userid,String password);
}
