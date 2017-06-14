package com.example.service.impl;

import com.example.dao.UserInfoDao;
import com.example.entity.UserInfo;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/1.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;
    @Override
    public UserInfo getUserInfo(String mobile,int type) throws Exception {
        if (type == 1) {
            return userInfoDao.getUserInfo(mobile);
        } else {
            return userInfoDao.getUserInfoByMybatis(mobile);
        }
    }
}
