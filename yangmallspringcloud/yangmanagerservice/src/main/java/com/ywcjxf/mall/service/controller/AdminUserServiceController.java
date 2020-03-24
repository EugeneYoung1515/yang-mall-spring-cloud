package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserServiceController{

    private AdminUserService adminUserService;

    @RequestMapping("/getAdminUserByUserName")
    public AdminUser getAdminUserByUserName(String userName) {
        return adminUserService.getAdminUserByUserName(userName);
    }

    @RequestMapping("/register")
    public Object register(@RequestBody AdminUser adminUser) {
        adminUserService.register(adminUser);
        return null;
    }


    @RequestMapping("/userNameExist")
    public Integer userNameExist(String userName) {
        return adminUserService.userNameExist(userName);
    }

    @Autowired
    public void setAdminUserService(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }
}
