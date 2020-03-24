package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.LogisticsParMapper;
import com.ywcjxf.mall.pojo.Logistics;

public interface LogisticsMapper extends LogisticsParMapper{
    int insert(Logistics record);

    int insertSelective(Logistics record);
}