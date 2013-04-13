package com.kingway.dao;

import java.util.Date;

public interface AddUserDao {
   public Long adduser(String username,String password,String gender,String mailbox,String phone,Date birthday , int enable);
}
