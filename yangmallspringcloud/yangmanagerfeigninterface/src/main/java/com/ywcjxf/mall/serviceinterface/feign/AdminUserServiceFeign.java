package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.AdminUserService;
import com.ywcjxf.mall.serviceinterface.feign.fallback.AdminUserServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "manager-service",fallback = AdminUserServiceFeignFallback.class)
public interface AdminUserServiceFeign extends AdminUserService {

    @Override
    @GetMapping("/getAdminUserByUserName")
    AdminUser getAdminUserByUserName(@RequestParam("userName") String userName);

    @Override
    @PostMapping("/register")
    void register(@RequestBody AdminUser adminUser);

    @Override
    @GetMapping("/userNameExist")
    Integer userNameExist(@RequestParam("userName") String userName);
}
