package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.UserParMapper;
import com.ywcjxf.mall.pojo.User;

public interface UserMapper extends UserParMapper{
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}