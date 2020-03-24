package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.ChinaParMapper;
import com.ywcjxf.mall.pojo.China;

public interface ChinaMapper extends ChinaParMapper{
    int insert(China record);

    int insertSelective(China record);
}