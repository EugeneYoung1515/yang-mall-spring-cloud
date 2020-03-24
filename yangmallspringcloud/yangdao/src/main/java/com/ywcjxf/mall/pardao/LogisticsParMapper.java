package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.Logistics;

public interface LogisticsParMapper {
    Logistics selectByUserIdOrderByIdLimitOne(Integer userId);
    void updateByOrderId(Logistics logistics);
    Logistics selectByOrderId(Long orderId);
    void insertSelectiveOrUpdateSelective(Logistics logistics);
}
