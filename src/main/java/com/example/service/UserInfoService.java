package com.example.service;

import com.example.entity.UserInfo;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface UserInfoService {

    /**
     * 查询用户信息
     * @param mobile
     * @return
     * @throws Exception
     */
    UserInfo getUserInfo(String mobile,int type) throws Exception;
}
