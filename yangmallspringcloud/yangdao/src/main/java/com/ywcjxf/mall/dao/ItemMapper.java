package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.ItemParMapper;
import com.ywcjxf.mall.pojo.Item;

public interface ItemMapper extends ItemParMapper{
    int deleteByPrimaryKey(Integer itemId);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}