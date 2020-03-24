package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pardao.IndexAdParMapper;
import com.ywcjxf.mall.pojo.IndexAd;

public interface IndexAdMapper extends IndexAdParMapper{
    int deleteByPrimaryKey(Integer indexAdId);

    int insert(IndexAd record);

    int insertSelective(IndexAd record);

    IndexAd selectByPrimaryKey(Integer indexAdId);

    int updateByPrimaryKeySelective(IndexAd record);

    int updateByPrimaryKey(IndexAd record);
}