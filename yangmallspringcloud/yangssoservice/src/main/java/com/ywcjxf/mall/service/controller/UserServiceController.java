package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController{

    private UserService userService;

    @RequestMapping("/sendMessageCode")
    public int sendMessageCode(String phoneNumber) {
        return userService.sendMessageCode(phoneNumber);
    }

    @RequestMapping("/loginOrRegister")
    public User loginOrRegister(String phoneNumber, @RequestBody User user) {
        return userService.loginOrRegister(phoneNumber,user);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
