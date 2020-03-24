package com.ywcjxf.mall.dao;

import com.ywcjxf.mall.pojo.District;

public interface DistrictMapper {
    int deleteByPrimaryKey(Integer districtId);

    int insert(District record);

    int insertSelective(District record);

    District selectByPrimaryKey(Integer districtId);

    int updateByPrimaryKeySelective(District record);

    int updateByPrimaryKey(District record);
}