package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.User;

public interface UserParMapper {
    Integer selectCountByPhoneNumber(String phoneNumber);
    User selectByPhoneNumber(String phoneNumber);
    void insertSelectiveAndReturnId(User user);
}
