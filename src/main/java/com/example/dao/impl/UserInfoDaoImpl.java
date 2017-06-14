package com.example.dao.impl;

import com.example.dao.BaseDao;
import com.example.dao.UserInfoDao;
import com.example.entity.UserInfo;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/2.
 */
@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserInfoDao {

    @Override
    public UserInfo getUserInfo(String mobile) {
        logger.info("=================查询用户信息");
        String sql = "select * from `user_info` t where t.user_mobile = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<UserInfo>() {
            @Override
            public UserInfo mapRow(ResultSet rs, int i) throws SQLException {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(rs.getLong("user_id"));
                userInfo.setUserName(rs.getString("user_name"));
                userInfo.setUserMobile(rs.getString("user_mobile"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setLastUpdateTime(rs.getDate("last_update_time"));
                return userInfo;
            }
        },mobile);
    }

    @Override
    public UserInfo getUserInfoByMybatis(String mobile) throws Exception {
        ValueOperations<Serializable,Serializable> opsForValue = redisTemplate.opsForValue();
        UserInfo userInfo = (UserInfo) opsForValue.get(mobile);
        logger.info("===========redis中的数据：" + userInfo);
        if (null == userInfo) {
            userInfo = (UserInfo) super.selectOne("getUserInfoByMybatis",mobile);
            if (null != userInfo) {
                opsForValue.set(mobile,userInfo);
            }
        }
        return (UserInfo) super.selectOne("getUserInfoByMybatis",mobile);
    }
}
