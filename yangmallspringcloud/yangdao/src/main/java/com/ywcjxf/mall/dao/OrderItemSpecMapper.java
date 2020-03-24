package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.OrderItemSpecParMapper;
import com.ywcjxf.mall.pojo.OrderItemSpec;

public interface OrderItemSpecMapper extends OrderItemSpecParMapper{
    int insert(OrderItemSpec record);

    int insertSelective(OrderItemSpec record);
}