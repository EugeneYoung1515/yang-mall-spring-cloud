package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.OrderParMapper;
import com.ywcjxf.mall.pojo.Order;

public interface OrderMapper extends OrderParMapper{
    int insert(Order record);

    int insertSelective(Order record);
}