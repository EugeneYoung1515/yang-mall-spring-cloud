package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.Order;

public interface OrderParMapper {
    Order selectByOrderId(Long orderId);
    void updateByOrderId(Order order);
    Order selectByOrderIdAndPayPlatformNumber(Long orderId,String payPlatformNumber);
    Order selectByPayPlatformNumber(String payPlatformNumber);
    void insertSelectiveOrUpdateSelective(Order order);
}
