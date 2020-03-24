package com.ywcjxf.mall.service;

import com.ywcjxf.mall.dao.AdminUserMapper;
import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getAdminUserByUserName(String userName) {
        return adminUserMapper.selectByUserName(userName);
    }

    @Override
    public void register(AdminUser adminUser) {
        adminUserMapper.insertSelective(adminUser);
    }

    @Override
    public Integer userNameExist(String userName) {
        return adminUserMapper.selectCountByUserName(userName);
    }

    @Autowired
    public void setAdminUserMapper(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }
}
