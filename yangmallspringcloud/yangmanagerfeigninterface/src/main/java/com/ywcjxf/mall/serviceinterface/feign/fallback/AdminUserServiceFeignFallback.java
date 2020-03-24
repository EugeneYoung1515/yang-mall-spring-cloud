package com.ywcjxf.mall.serviceinterface.feign.fallback;

import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.feign.AdminUserServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class AdminUserServiceFeignFallback implements AdminUserServiceFeign {
    @Override
    public AdminUser getAdminUserByUserName(String userName) {
        System.out.println("fallback:getAdminUserByUserName");
        return null;
    }

    @Override
    public void register(AdminUser adminUser) {

    }

    @Override
    public Integer userNameExist(String userName) {
        return null;
    }
}
