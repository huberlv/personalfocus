package com.kingway.dao;

import com.kingway.model.UserInfo;

public interface CheckLoginUserDao {
      public UserInfo check(String userid,String password);
}
