package com.example.dao;

import com.example.entity.UserInfo;

/**
 * Created by Administrator on 2017/6/2.
 */
public interface UserInfoDao {
    UserInfo getUserInfo(String mobile);
}
