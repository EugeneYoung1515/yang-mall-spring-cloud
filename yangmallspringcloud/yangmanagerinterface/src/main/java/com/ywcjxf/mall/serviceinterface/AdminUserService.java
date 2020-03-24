package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.AdminUser;

public interface AdminUserService {
    AdminUser getAdminUserByUserName(String userName);
    void register(AdminUser adminUser);
    Integer userNameExist(String userName);
}
