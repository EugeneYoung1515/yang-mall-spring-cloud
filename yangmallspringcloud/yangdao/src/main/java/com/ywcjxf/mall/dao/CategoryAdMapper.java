package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pojo.CategoryAd;

public interface CategoryAdMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(CategoryAd record);

    int insertSelective(CategoryAd record);

    CategoryAd selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(CategoryAd record);

    int updateByPrimaryKey(CategoryAd record);
}