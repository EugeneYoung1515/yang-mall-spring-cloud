package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.User;

public interface UserService {

    int sendMessageCode(String phoneNumber);
    User loginOrRegister(String phoneNumber,User user);
}
