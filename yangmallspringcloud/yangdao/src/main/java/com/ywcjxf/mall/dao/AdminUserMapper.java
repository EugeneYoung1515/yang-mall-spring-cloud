package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.AdminUserParMapper;
import com.ywcjxf.mall.pojo.AdminUser;

public interface AdminUserMapper extends AdminUserParMapper{
    int deleteByPrimaryKey(Integer userId);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}