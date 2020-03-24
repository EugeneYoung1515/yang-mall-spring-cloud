package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.AdminUser;

public interface AdminUserParMapper {
    AdminUser selectByUserName(String userName);
    Integer selectCountByUserName(String userName);
}
