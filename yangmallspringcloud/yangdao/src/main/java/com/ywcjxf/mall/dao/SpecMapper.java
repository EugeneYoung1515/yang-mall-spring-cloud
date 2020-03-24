package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.SpecParMapper;
import com.ywcjxf.mall.pojo.Spec;

public interface SpecMapper extends SpecParMapper{
    int deleteByPrimaryKey(Integer specId);

    int insert(Spec record);

    int insertSelective(Spec record);

    Spec selectByPrimaryKey(Integer specId);

    int updateByPrimaryKeySelective(Spec record);

    int updateByPrimaryKey(Spec record);
}